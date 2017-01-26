package com.sfl.pms.queue.consumer.payment.notification.impl;

import com.sfl.pms.queue.consumer.payment.notification.PaymentProviderNotificationProcessingQueueConsumerService;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 7:37 PM
 */
@Service
@Lazy(false)
public class PaymentProviderNotificationProcessingQueueConsumerServiceImpl implements PaymentProviderNotificationProcessingQueueConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationProcessingQueueConsumerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationProcessingService paymentProviderNotificationProcessingService;

    /* Constructors */
    public PaymentProviderNotificationProcessingQueueConsumerServiceImpl() {
        LOGGER.debug("Initializing payment provider notification processing queue consumer service");
    }

    @Override
    public void processPaymentProviderNotificationRequest(@Nonnull final Long notificationRequestId) {
        Assert.notNull(notificationRequestId, "Notification request id should not be null");
        LOGGER.debug("Received payment provider notification request processing queue message, request id - {}", notificationRequestId);
        final List<Long> notificationIds = paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(notificationRequestId);
        LOGGER.debug("{} notifications were created after processing queue processing request for notification request id  - {}, notifications  - {}", notificationIds.size(), notificationRequestId, notificationIds);
    }

    /* Properties getters and setters */
    public PaymentProviderNotificationProcessingService getPaymentProviderNotificationProcessingService() {
        return paymentProviderNotificationProcessingService;
    }

    public void setPaymentProviderNotificationProcessingService(final PaymentProviderNotificationProcessingService paymentProviderNotificationProcessingService) {
        this.paymentProviderNotificationProcessingService = paymentProviderNotificationProcessingService;
    }
}
