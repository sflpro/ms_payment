package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAlreadyBoundAuthorizationRequestException;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodInvalidCustomerException;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodNotFoundForUuIdException;
import com.sfl.pms.services.payment.customer.method.impl.provider.CustomerPaymentMethodProviderInformationHandler;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.provider.exception.UnknownPaymentProviderTypeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:33 PM
 */
public abstract class AbstractCustomerPaymentMethodServiceImpl<T extends CustomerPaymentMethod> implements AbstractCustomerPaymentMethodService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomerPaymentMethodServiceImpl.class);

    /* Dependencies */
    @Autowired
    @Qualifier("customerPaymentMethodAdyenInformationHandler")
    private CustomerPaymentMethodProviderInformationHandler customerPaymentMethodAdyenInformationConverter;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    @Autowired
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public AbstractCustomerPaymentMethodServiceImpl() {
        LOGGER.debug("Initializing abstract customer payment method service");
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public T getCustomerPaymentMethodById(@Nonnull final Long paymentMethodId) {
        assertCustomerPaymentMethodId(paymentMethodId);
        LOGGER.debug("Retrieving customer payment method for id - {}", paymentMethodId);
        final T customerPaymentMethod = getRepository().findOne(paymentMethodId);
        assertCustomerPaymentMethodNotNull(customerPaymentMethod, paymentMethodId);
        LOGGER.debug("Successfully retrieved payment method for id - {}, payment method - {}", customerPaymentMethod.getId(), customerPaymentMethod);
        return customerPaymentMethod;
    }

    @Nonnull
    @Override
    @Transactional
    public T removeCustomerPaymentMethod(@Nonnull final Long paymentMethodId) {
        assertCustomerPaymentMethodId(paymentMethodId);
        LOGGER.debug("Removing customer payment method for id - {}", paymentMethodId);
        T customerPaymentMethod = getRepository().findOne(paymentMethodId);
        assertCustomerPaymentMethodNotNull(customerPaymentMethod, paymentMethodId);
        /* Mark customer payment method as removed */
        final Date removedDate = new Date();
        customerPaymentMethod.setRemoved(removedDate);
        customerPaymentMethod.setUpdated(removedDate);
        /* Persist customer payment method */
        customerPaymentMethod = getRepository().save(customerPaymentMethod);
        LOGGER.debug("Successfully marked customer payment method with id - {} as removed", customerPaymentMethod.getId());
        return customerPaymentMethod;
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public T getCustomerPaymentMethodByUuId(@Nonnull final String uuId) {
        assertCustomerPaymentMethodUuId(uuId);
        LOGGER.debug("Retrieving customer payment method for uuId - {}", uuId);
        final T customerPaymentMethod = getRepository().findByUuId(uuId);
        assertCustomerPaymentMethodNotNullForUuId(customerPaymentMethod, uuId);
        LOGGER.debug("Successfully retrieved payment method for uuId - {}, payment method - {}", customerPaymentMethod.getUuId(), customerPaymentMethod);
        return customerPaymentMethod;
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<T> getPaymentMethodsForCustomer(@Nonnull final Long customerId) {
        Assert.notNull(customerId, "Customer id should not be null");
        LOGGER.debug("Getting payment methods for customer with id - {}, payment methods class - {}", customerId, getInstanceClass());
        final Customer customer = customerService.getCustomerById(customerId);
        final List<T> paymentMethods = getRepository().findByCustomer(customer);
        LOGGER.debug("Successfully retrieved - {} payment methods for customer with id - {}", paymentMethods.size(), customerId);
        return paymentMethods;
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    /* Utility methods */
    protected void assertCustomerPaymentMethodNotNull(final T paymentMethod, final Long paymentMethodId) {
        if (paymentMethod == null) {
            LOGGER.error("No customer payment method was found for id - {}, payment method class - {}", paymentMethodId, getInstanceClass());
            throw new CustomerPaymentMethodNotFoundForIdException(paymentMethodId, getInstanceClass());
        }
    }

    protected void assertCustomerPaymentMethodNotNullForUuId(final T paymentMethod, final String paymentMethodUuId) {
        if (paymentMethod == null) {
            LOGGER.error("No customer payment method was found for uuId - {}, payment method class - {}", paymentMethodUuId, getInstanceClass());
            throw new CustomerPaymentMethodNotFoundForUuIdException(paymentMethodUuId, getInstanceClass());
        }
    }

    protected void assertCustomerPaymentMethodUuId(final String paymentMethodIUuId) {
        Assert.notNull(paymentMethodIUuId, "Customer payment method uuId should not be null");
    }

    protected void assertCustomerPaymentMethodId(final Long paymentMethodId) {
        Assert.notNull(paymentMethodId, "Customer payment method id should not be null");
    }

    protected void assertPaymentMethodProviderInformation(final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto) {
        Assert.notNull(paymentMethodProviderInformationDto, "Payment method provider information should not be null");
        Assert.notNull(paymentMethodProviderInformationDto.getType(), "Payment provider type in information should not be null");
        final CustomerPaymentMethodProviderInformationHandler converter = getProviderInformationConverterForType(paymentMethodProviderInformationDto.getType());
        converter.assertProviderInformationDto(paymentMethodProviderInformationDto);
    }

    protected void assertNoPaymentMethodForAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        final CustomerPaymentMethod paymentMethod = customerPaymentMethodRepository.findByAuthorizationRequest(authorizationRequest);
        if (paymentMethod != null) {
            LOGGER.error("Customer payment method authorization request already has payment method associated with id. Request id - {}, method id - {}", authorizationRequest.getId(), paymentMethod.getId());
            throw new CustomerPaymentMethodAlreadyBoundAuthorizationRequestException(paymentMethod.getId(), authorizationRequest.getId());
        }
    }

    protected CustomerPaymentMethodProviderInformationHandler getProviderInformationConverterForType(final PaymentProviderType paymentProviderType) {
        switch (paymentProviderType) {
            case ADYEN: {
                return customerPaymentMethodAdyenInformationConverter;
            }
            default: {
                LOGGER.error("Unknown payment provider type - {}", paymentProviderType);
                throw new UnknownPaymentProviderTypeException(paymentProviderType);
            }
        }
    }

    protected void assertProviderInformationUniqueness(final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto) {
        getCustomerPaymentMethodAdyenInformationHandler().assertProviderInformationUniqueness(paymentMethodProviderInformationDto);
    }

    protected CustomerPaymentMethodProviderInformation convertCustomerPaymentMethodProviderInformationDto(final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto) {
        return getCustomerPaymentMethodAdyenInformationHandler().convertPaymentMethodProviderInformationDto(paymentMethodProviderInformationDto);
    }

    protected void assertCustomerForAuthorizationRequest(final Long providedCustomerId, final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        final Long authorizationRequestCustomerId = authorizationRequest.getCustomer().getId();
        if (!providedCustomerId.equals(authorizationRequestCustomerId)) {
            LOGGER.error("Provided customer id does not match with customer if payment authorization request. Provided customer id - {}, authorization request customer id - {}", providedCustomerId, authorizationRequestCustomerId);
            throw new CustomerPaymentMethodInvalidCustomerException(providedCustomerId, authorizationRequestCustomerId);
        }
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodProviderInformationHandler getCustomerPaymentMethodAdyenInformationHandler() {
        return customerPaymentMethodAdyenInformationConverter;
    }

    public void setCustomerPaymentMethodAdyenInformationConverter(final CustomerPaymentMethodProviderInformationHandler customerPaymentMethodAdyenInformationConverter) {
        this.customerPaymentMethodAdyenInformationConverter = customerPaymentMethodAdyenInformationConverter;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerPaymentMethodAuthorizationRequestService getCustomerPaymentMethodAuthorizationRequestService() {
        return customerPaymentMethodAuthorizationRequestService;
    }

    public void setCustomerPaymentMethodAuthorizationRequestService(final CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService) {
        this.customerPaymentMethodAuthorizationRequestService = customerPaymentMethodAuthorizationRequestService;
    }

    public CustomerPaymentMethodRepository getCustomerPaymentMethodRepository() {
        return customerPaymentMethodRepository;
    }

    public void setCustomerPaymentMethodRepository(final CustomerPaymentMethodRepository customerPaymentMethodRepository) {
        this.customerPaymentMethodRepository = customerPaymentMethodRepository;
    }
}
