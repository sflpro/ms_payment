package com.sfl.pms.queue.consumer.payment.redirect.impl;

import com.sfl.pms.queue.consumer.payment.redirect.PaymentProviderRedirectResultProcessingQueueConsumerService;
import com.sfl.pms.services.payment.redirect.PaymentProviderRedirectResultProcessingService;
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
 * Date: 4/28/15
 * Time: 7:37 PM
 */
@Service
@Lazy(false)
public class PaymentProviderRedirectResultProcessingQueueConsumerServiceImpl implements PaymentProviderRedirectResultProcessingQueueConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderRedirectResultProcessingQueueConsumerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderRedirectResultProcessingService paymentProviderRedirectResultProcessingService;

    /* Constructors */
    public PaymentProviderRedirectResultProcessingQueueConsumerServiceImpl() {
        LOGGER.debug("Initializing payment provider notification processing queue consumer service");
    }

    @Override
    public void processPaymentProviderRedirectResult(@Nonnull final Long redirectResultId) {
        Assert.notNull(redirectResultId, "Notification request id should not be null");
        LOGGER.debug("Received payment provider redirect result processing queue message, request id - {}", redirectResultId);
        paymentProviderRedirectResultProcessingService.processPaymentProviderRedirectResult(redirectResultId);
        LOGGER.debug("Successfully processed payment provider redirect result with id - {}", redirectResultId);
    }

    /* Properties getters and setters */
    public PaymentProviderRedirectResultProcessingService getPaymentProviderRedirectResultProcessingService() {
        return paymentProviderRedirectResultProcessingService;
    }

    public void setPaymentProviderRedirectResultProcessingService(final PaymentProviderRedirectResultProcessingService paymentProviderRedirectResultProcessingService) {
        this.paymentProviderRedirectResultProcessingService = paymentProviderRedirectResultProcessingService;
    }
}
