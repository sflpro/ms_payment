package com.sfl.pms.queue.consumer.payment.notification;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 7:34 PM
 */
public interface PaymentProviderNotificationProcessingQueueConsumerService {

    /**
     * Process payment provider notification request
     *
     * @param notificationRequestId
     */
    void processPaymentProviderNotificationRequest(@Nonnull final Long notificationRequestId);
}
