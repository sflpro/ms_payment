package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.services.common.sortorder.SortDirection;
import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.payment.customer.method.CustomerBankPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.CustomerCardPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodsSynchronizationService;
import com.sfl.pms.services.payment.customer.method.dto.*;
import com.sfl.pms.services.payment.customer.method.exception.UnknownCustomerPaymentMethodException;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import com.sfl.pms.services.payment.processing.impl.PaymentProviderOperationsProcessor;
import com.sfl.pms.services.payment.processing.impl.adyen.AdyenPaymentOperationsProcessor;
import com.sfl.pms.services.payment.provider.exception.UnknownPaymentProviderTypeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/15/15
 * Time: 3:26 PM
 */
@Service
public class CustomerPaymentMethodsSynchronizationServiceImpl implements CustomerPaymentMethodsSynchronizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodsSynchronizationServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerCardPaymentMethodService customerCardPaymentMethodService;

    @Autowired
    private CustomerBankPaymentMethodService customerBankPaymentMethodService;

    @Autowired
    private AdyenPaymentOperationsProcessor adyenPaymentOperationsProcessor;

    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;


    /* Constructors */
    public CustomerPaymentMethodsSynchronizationServiceImpl() {
        LOGGER.debug("Initializing customer payment methods synchronization service");
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerPaymentMethodsSynchronizationResult synchronizeCustomerPaymentMethods(@Nonnull final Long customerId, @Nonnull final PaymentProviderType paymentProviderType) {
        Assert.notNull(customerId, "Customer id should not be null");
        Assert.notNull(paymentProviderType, "Payment provider type should not be null");
        LOGGER.debug("Starting payment methods synchronization for customer with id - {}, payment provider type - {}", customerId, paymentProviderType);
        final PaymentProviderOperationsProcessor providerOperationsProcessor = getPaymentProviderProcessor(paymentProviderType);
        // Create/get customer payment DTO for payment provider
        final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentProviderCustomerPaymentMethods = providerOperationsProcessor.getStoredRecurringPaymentMethods(customerId);
        // Load existing payment methods
        final List<CustomerPaymentMethod> activeCustomerPaymentMethods = getCustomerActivePaymentMethodsForPaymentProvider(paymentProviderType, customerId);
        final Map<String, CustomerPaymentMethod> customerPaymentMethodsToExternalIdentifierMapping = getCustomerPaymentMethodsToExternalIdentifiersMapping(activeCustomerPaymentMethods);
        // Create new customer payment methods
        final List<CustomerPaymentMethod> createdCustomerPaymentMethods = createNewPaymentMethods(customerId, paymentProviderCustomerPaymentMethods, customerPaymentMethodsToExternalIdentifierMapping);
        final List<CustomerPaymentMethod> deactivatedCustomerPaymentMethods = deactivateRemovedPaymentMethods(paymentProviderCustomerPaymentMethods, customerPaymentMethodsToExternalIdentifierMapping);
        // Create synchronization result
        return new CustomerPaymentMethodsSynchronizationResult(createdCustomerPaymentMethods, deactivatedCustomerPaymentMethods);
    }

    /* Utility methods */
    private List<CustomerPaymentMethod> createNewPaymentMethods(final Long customerId, final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentProviderCustomerPaymentMethods, final Map<String, CustomerPaymentMethod> customerPaymentMethodsToExternalIdentifierMapping) {
        final List<CustomerPaymentMethod> createdCustomerPaymentMethods = new ArrayList<>();
        paymentProviderCustomerPaymentMethods.forEach(paymentMethodProviderData -> {
            // Check if payment method already exists
            final String paymentMethodExternalIdentifier = paymentMethodProviderData.getCustomerPaymentMethodProviderInformationDto().getPaymentProviderIdentifierForPaymentMethod();
            final boolean paymentMethodExists = customerPaymentMethodsToExternalIdentifierMapping.containsKey(paymentMethodExternalIdentifier);
            if (paymentMethodExists) {
                return;
            }
            // Create customer payment method
            final CustomerPaymentMethod customerPaymentMethod = createCustomerPaymentMethod(customerId, paymentMethodProviderData);
            LOGGER.debug("Successfully added new customer payment method for customer id - {}, customer payment method - {}", customerId, customerPaymentMethod);
            createdCustomerPaymentMethods.add(customerPaymentMethod);
        });
        return createdCustomerPaymentMethods;
    }

    private List<CustomerPaymentMethod> deactivateRemovedPaymentMethods(final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentProviderCustomerPaymentMethods, final Map<String, CustomerPaymentMethod> customerPaymentMethodsToExternalIdentifierMapping) {
        final List<CustomerPaymentMethod> deactivatedPaymentMethods = new ArrayList<>();
        final Set<String> paymentProviderPaymentMethodsExternalIdentifiers = paymentProviderCustomerPaymentMethods.stream().map(paymentMethodInformation -> paymentMethodInformation.getCustomerPaymentMethodProviderInformationDto().getPaymentProviderIdentifierForPaymentMethod()).collect(Collectors.toCollection(HashSet::new));
        customerPaymentMethodsToExternalIdentifierMapping.entrySet().forEach(entry -> {
            final String paymentMethodExternalIdentifier = entry.getKey();
            final boolean paymentMethodExistsForPaymentProvider = paymentProviderPaymentMethodsExternalIdentifiers.contains(paymentMethodExternalIdentifier);
            if (paymentMethodExistsForPaymentProvider) {
                return;
            }
            final CustomerPaymentMethod customerPaymentMethod = entry.getValue();
            // Check if payment method is already marked as removed
            if (customerPaymentMethod.getRemoved() != null) {
                return;
            }
            // Deactivate payment method
            customerPaymentMethodService.removeCustomerPaymentMethod(customerPaymentMethod.getId());
            deactivatedPaymentMethods.add(customerPaymentMethod);
        });
        return deactivatedPaymentMethods;
    }

    private Map<String, CustomerPaymentMethod> getCustomerPaymentMethodsToExternalIdentifiersMapping(final List<CustomerPaymentMethod> customerPaymentMethods) {
        final Map<String, CustomerPaymentMethod> customerPaymentMethodMap = new HashMap<>();
        customerPaymentMethods.forEach(customerPaymentMethod -> {
            customerPaymentMethodMap.put(customerPaymentMethod.getProviderInformation().getPaymentProviderIdentifierForPaymentMethod(), customerPaymentMethod);
        });
        // Grab recurring details references for existing payment methods
        return customerPaymentMethodMap;
    }

    private List<CustomerPaymentMethod> getCustomerActivePaymentMethodsForPaymentProvider(final PaymentProviderType paymentProviderType, final Long customerId) {
        // Search for customer payment methods
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        lookupParameters.setExcludeRemoved(false);
        lookupParameters.setCustomerId(customerId);
        // Execute search
        final List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(lookupParameters, new SortOrderSpecification<>(CustomerPaymentMethodSortColumn.DATE, SortDirection.ASCENDING), 0L, Integer.MAX_VALUE);
        // For now filter for payment provider in application code, later can be moved to payment method lookup parameters
        final List<CustomerPaymentMethod> customerPaymentMethodsForPaymentProvider = customerPaymentMethods.stream().filter(customerPaymentMethod -> paymentProviderType.equals(customerPaymentMethod.getProviderInformation().getType())).collect(Collectors.toCollection(ArrayList::new));
        LOGGER.debug("{} customer payment method where loaded for customer with id - {}, payment provider type - {}", customerPaymentMethodsForPaymentProvider.size(), customerId, paymentProviderType);
        return customerPaymentMethodsForPaymentProvider;
    }

    private CustomerPaymentMethod createCustomerPaymentMethod(final Long customerId, final PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData customerPaymentMethodProviderData) {
        // Grab data
        final CustomerPaymentMethodDto<? extends CustomerPaymentMethod> customerPaymentMethodDto = customerPaymentMethodProviderData.getCustomerPaymentMethodDto();
        final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> customerPaymentMethodProviderInformationDto = customerPaymentMethodProviderData.getCustomerPaymentMethodProviderInformationDto();
        CustomerPaymentMethod customerPaymentMethod;
        switch (customerPaymentMethodDto.getType()) {
            case CARD: {
                final CustomerCardPaymentMethodDto customerCardPaymentMethodDto = (CustomerCardPaymentMethodDto) customerPaymentMethodDto;
                customerPaymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, null, customerCardPaymentMethodDto, customerPaymentMethodProviderInformationDto);
                break;
            }
            case BANK: {
                final CustomerBankPaymentMethodDto customerBankPaymentMethodDto = (CustomerBankPaymentMethodDto) customerPaymentMethodDto;
                customerPaymentMethod = customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, null, customerBankPaymentMethodDto, customerPaymentMethodProviderInformationDto);
                break;
            }
            default: {
                LOGGER.error("Unknown customer payment method type - {}", customerPaymentMethodDto.getType());
                throw new UnknownCustomerPaymentMethodException(customerPaymentMethodDto.getType());
            }
        }
        return customerPaymentMethod;
    }

    protected PaymentProviderOperationsProcessor getPaymentProviderProcessor(final PaymentProviderType paymentProviderType) {
        switch (paymentProviderType) {
            case ADYEN: {
                return adyenPaymentOperationsProcessor;
            }
            default: {
                LOGGER.error("Unknown payment provider type - {}", paymentProviderType);
                throw new UnknownPaymentProviderTypeException(paymentProviderType);
            }
        }
    }

    /* Properties getters and setters */
    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerCardPaymentMethodService getCustomerCardPaymentMethodService() {
        return customerCardPaymentMethodService;
    }

    public void setCustomerCardPaymentMethodService(final CustomerCardPaymentMethodService customerCardPaymentMethodService) {
        this.customerCardPaymentMethodService = customerCardPaymentMethodService;
    }

    public CustomerBankPaymentMethodService getCustomerBankPaymentMethodService() {
        return customerBankPaymentMethodService;
    }

    public void setCustomerBankPaymentMethodService(final CustomerBankPaymentMethodService customerBankPaymentMethodService) {
        this.customerBankPaymentMethodService = customerBankPaymentMethodService;
    }

    public AdyenPaymentOperationsProcessor getAdyenPaymentOperationsProcessor() {
        return adyenPaymentOperationsProcessor;
    }

    public void setAdyenPaymentOperationsProcessor(final AdyenPaymentOperationsProcessor adyenPaymentOperationsProcessor) {
        this.adyenPaymentOperationsProcessor = adyenPaymentOperationsProcessor;
    }
}
