package com.sfl.pms.queue.consumer.payment.customer.method.impl;

import com.sfl.pms.queue.consumer.payment.customer.method.PaymentMethodAuthorizationRequestProcessingConsumerService;
import com.sfl.pms.services.payment.processing.auth.CustomerPaymentMethodAuthorizationRequestProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 11:13 PM
 */
@Service
@Lazy(false)
public class PaymentMethodAuthorizationRequestProcessingConsumerServiceImpl implements PaymentMethodAuthorizationRequestProcessingConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodAuthorizationRequestProcessingConsumerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestProcessorService customerPaymentMethodAuthorizationRequestProcessorService;

    /* Constructors */
    public PaymentMethodAuthorizationRequestProcessingConsumerServiceImpl() {
        LOGGER.debug("Initializing payment method authorization request processing consumer service");
    }

    @Override
    public void processPaymentMethodAuthorizationRequest(@Nonnull final Long paymentMethodAuthorizationRequestId) {
        Assert.notNull(paymentMethodAuthorizationRequestId, "Payment method authorization request ID should not be null");
        customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(paymentMethodAuthorizationRequestId);
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestProcessorService getCustomerPaymentMethodAuthorizationRequestProcessorService() {
        return customerPaymentMethodAuthorizationRequestProcessorService;
    }

    public void setCustomerPaymentMethodAuthorizationRequestProcessorService(CustomerPaymentMethodAuthorizationRequestProcessorService customerPaymentMethodAuthorizationRequestProcessorService) {
        this.customerPaymentMethodAuthorizationRequestProcessorService = customerPaymentMethodAuthorizationRequestProcessorService;
    }
}
