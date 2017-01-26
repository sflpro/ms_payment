package com.sfl.pms.queue.consumer.payment.customer.method.impl;

import com.sfl.pms.queue.consumer.payment.customer.method.PaymentMethodRemovalRequestProcessingConsumerService;
import com.sfl.pms.services.payment.common.removal.CustomerPaymentMethodRemovalProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:07 PM
 */
@Service
@Lazy(false)
public class PaymentMethodRemovalRequestProcessingConsumerServiceImpl implements PaymentMethodRemovalRequestProcessingConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodRemovalRequestProcessingConsumerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodRemovalProcessingService customerPaymentMethodRemovalProcessingService;

    /* Constructors */
    public PaymentMethodRemovalRequestProcessingConsumerServiceImpl() {
    }


    /* Public methods */
    //TODO add integration tests
    @Override
    public void processPaymentMethodRemovalRequest(@Nonnull final Long paymentMethodRemovalRequestId) {
        Assert.notNull(paymentMethodRemovalRequestId, "Payment method removal request ID should not be null");
        LOGGER.debug("Processing customer payment method removal for request id - {}", paymentMethodRemovalRequestId);
        customerPaymentMethodRemovalProcessingService.processCustomerPaymentMethodRemovalRequest(paymentMethodRemovalRequestId);
        LOGGER.debug("Successfully finished processing customer payment method removal for request id - {}", paymentMethodRemovalRequestId);
    }

    /* Dependencies getters and setters */
    public CustomerPaymentMethodRemovalProcessingService getCustomerPaymentMethodRemovalProcessingService() {
        return customerPaymentMethodRemovalProcessingService;
    }

    public void setCustomerPaymentMethodRemovalProcessingService(final CustomerPaymentMethodRemovalProcessingService customerPaymentMethodRemovalProcessingService) {
        this.customerPaymentMethodRemovalProcessingService = customerPaymentMethodRemovalProcessingService;
    }
}
