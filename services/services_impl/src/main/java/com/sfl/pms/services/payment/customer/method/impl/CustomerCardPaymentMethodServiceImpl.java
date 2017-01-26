package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerCardPaymentMethodRepository;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.CustomerCardPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerCardPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:39 PM
 */
@Service
public class CustomerCardPaymentMethodServiceImpl extends AbstractCustomerPaymentMethodServiceImpl<CustomerCardPaymentMethod> implements CustomerCardPaymentMethodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCardPaymentMethodServiceImpl.class);

    /* Constants */
    private static final int MIN_EXPIRY_MONTH = 1;

    private static final int MAX_EXPIRY_MONTH = 12;

    private static final int MIN_EXPIRY_YEAR = 1;

    /* Dependencies */
    @Autowired
    private CustomerCardPaymentMethodRepository customerCardPaymentMethodRepository;

    /* Constructors */
    public CustomerCardPaymentMethodServiceImpl() {
        LOGGER.debug("Initializing customer card payment method service");
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerCardPaymentMethod createCustomerPaymentMethod(@Nonnull final Long customerId, final Long authorizationRequestId, @Nonnull CustomerCardPaymentMethodDto paymentMethodDto, @Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto) {
        Assert.notNull(customerId, "Customer id should not be null");
        assertPaymentMethodDto(paymentMethodDto);
        assertPaymentMethodProviderInformation(paymentMethodProviderInformationDto);
        LOGGER.debug("Creating new customer card payment method, customer id - {}, authorization request id - {}, payment method DTO - {}, payment method provider information DTO - {}", new Object[]{customerId, authorizationRequestId, paymentMethodDto, paymentMethodProviderInformationDto});
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
        // Create new customer card payment method
        CustomerCardPaymentMethod paymentMethod = new CustomerCardPaymentMethod(true);
        // Update properties
        paymentMethodDto.updateDomainEntityProperties(paymentMethod);
        paymentMethod.setCustomer(customer);
        paymentMethod.setAuthorizationRequest(authorizationRequest);
        // Customer payment provider information
        final CustomerPaymentMethodProviderInformation providerInformation = convertCustomerPaymentMethodProviderInformationDto(paymentMethodProviderInformationDto);
        providerInformation.setCustomerPaymentMethod(paymentMethod);
        paymentMethod.setProviderInformation(providerInformation);
        // Persist payment method
        paymentMethod = customerCardPaymentMethodRepository.save(paymentMethod);
        LOGGER.debug("Successfully created new card payment method for customer with id - {}, payment method id - {}, payment method - {}", customer.getId(), paymentMethod.getId(), paymentMethod);
        return paymentMethod;
    }

    /* Utility methods */
    private void assertPaymentMethodDto(final CustomerCardPaymentMethodDto paymentMethodDto) {
        Assert.notNull(paymentMethodDto, "Customer payment method DTO should not be null");
        Assert.notNull(paymentMethodDto.getPaymentMethodType(), "Payment method type in payment method DTO should not be null");
        Assert.isTrue(PaymentMethodGroupType.CARD.equals(paymentMethodDto.getPaymentMethodType().getGroup()), "Payment method type in payment method DTO should be from CARD group");
        Assert.isTrue(paymentMethodDto.getExpiryMonth() >= MIN_EXPIRY_MONTH && paymentMethodDto.getExpiryMonth() <= MAX_EXPIRY_MONTH, "Expiration month in payment method DTO should be in range [1,12]");
        Assert.isTrue(paymentMethodDto.getExpiryYear() >= MIN_EXPIRY_YEAR, "Expiration year in payment method DTO should be positive integer");
        Assert.notNull(paymentMethodDto.getNumberTail(), "Card number tail in payment method DTO should not be null");
        Assert.notNull(paymentMethodDto.getHolderName(), "Card holder name in payment method DTO should not be null");
    }


    @Override
    protected AbstractCustomerPaymentMethodRepository<CustomerCardPaymentMethod> getRepository() {
        return customerCardPaymentMethodRepository;
    }

    @Override
    protected Class<CustomerCardPaymentMethod> getInstanceClass() {
        return CustomerCardPaymentMethod.class;
    }

    /* Properties getters and setters */
    public CustomerCardPaymentMethodRepository getCustomerCardPaymentMethodRepository() {
        return customerCardPaymentMethodRepository;
    }

    public void setCustomerCardPaymentMethodRepository(final CustomerCardPaymentMethodRepository customerCardPaymentMethodRepository) {
        this.customerCardPaymentMethodRepository = customerCardPaymentMethodRepository;
    }
}
