package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerBankPaymentMethodRepository;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.CustomerBankPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerBankPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerBankPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/3/15
 * Time: 4:39 PM
 */
@Service
public class CustomerBankPaymentMethodServiceImpl extends AbstractCustomerPaymentMethodServiceImpl<CustomerBankPaymentMethod> implements CustomerBankPaymentMethodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBankPaymentMethodServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerBankPaymentMethodRepository customerBankPaymentMethodRepository;

    /* Constructors */
    public CustomerBankPaymentMethodServiceImpl() {
        LOGGER.debug("Initializing customer bank payment method service");
    }

    @Nonnull
    @Override
    public CustomerBankPaymentMethod createCustomerPaymentMethod(@Nonnull final Long customerId, final Long authorizationRequestId, @Nonnull CustomerBankPaymentMethodDto paymentMethodDto, @Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto) {
        Assert.notNull(customerId, "Customer id should not be null");
        assertPaymentMethodDto(paymentMethodDto);
        assertPaymentMethodProviderInformation(paymentMethodProviderInformationDto);
        LOGGER.debug("Creating new customer bank payment method, customer id - {}, authorization request id - {}, payment method DTO - {}, payment method provider information DTO - {}", new Object[]{customerId, authorizationRequestId, paymentMethodDto, paymentMethodProviderInformationDto});
        final Customer customer = getCustomerService().getCustomerById(customerId);
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest;
        if (authorizationRequestId != null) {
            authorizationRequest = getCustomerPaymentMethodAuthorizationRequestService().getPaymentMethodAuthorizationRequestById(authorizationRequestId);
            // Assert provided customer and request customer
            assertCustomerForAuthorizationRequest(customerId, authorizationRequest);
            // Verify that no payment method is created for request
            assertNoPaymentMethodForAuthorizationRequest(authorizationRequest);
        } else {
            authorizationRequest = null;
        }
        // Verify payment information is not used
        assertProviderInformationUniqueness(paymentMethodProviderInformationDto);
        // Create new customer bank payment method
        CustomerBankPaymentMethod paymentMethod = new CustomerBankPaymentMethod(true);
        // Update properties
        paymentMethodDto.updateDomainEntityProperties(paymentMethod);
        paymentMethod.setCustomer(customer);
        paymentMethod.setAuthorizationRequest(authorizationRequest);
        // Customer payment provider information
        final CustomerPaymentMethodProviderInformation providerInformation = convertCustomerPaymentMethodProviderInformationDto(paymentMethodProviderInformationDto);
        providerInformation.setCustomerPaymentMethod(paymentMethod);
        paymentMethod.setProviderInformation(providerInformation);
        // Persist payment method
        paymentMethod = customerBankPaymentMethodRepository.save(paymentMethod);
        LOGGER.debug("Successfully created new bank payment method for customer with id - {}, payment method id - {}, payment method - {}", customer.getId(), paymentMethod.getId(), paymentMethod);
        return paymentMethod;
    }

    /* Utility methods */
    private void assertPaymentMethodDto(final CustomerBankPaymentMethodDto paymentMethodDto) {
        Assert.notNull(paymentMethodDto, "Payment method DTO should not be null");
        Assert.notNull(paymentMethodDto.getPaymentMethodType(), "Payment method type in payment method DTO should not be null");
        Assert.notNull(paymentMethodDto.getBankName(), "Bank name number in payment method DTO should not be null");
        Assert.notNull(paymentMethodDto.getCountryCode(), "Country code in payment method DTO should not be null");
        Assert.notNull(paymentMethodDto.getOwnerName(), "Owner name in payment method DTO should not be null");
        Assert.isTrue(PaymentMethodGroupType.BANK_TRANSFER.equals(paymentMethodDto.getPaymentMethodType().getGroup()), "Payment method type in payment method DTO should be from BANK_TRANSFER group");
    }

    @Override
    protected AbstractCustomerPaymentMethodRepository<CustomerBankPaymentMethod> getRepository() {
        return customerBankPaymentMethodRepository;
    }

    @Override
    protected Class<CustomerBankPaymentMethod> getInstanceClass() {
        return CustomerBankPaymentMethod.class;
    }
}
