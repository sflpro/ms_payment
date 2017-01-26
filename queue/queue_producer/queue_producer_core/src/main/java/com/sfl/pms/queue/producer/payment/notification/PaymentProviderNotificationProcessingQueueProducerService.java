package com.sfl.pms.queue.producer.payment.notification;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 6:12 PM
 */
public interface PaymentProviderNotificationProcessingQueueProducerService {

    /**
     * Process payment provider notification request
     *
     * @param notificationRequestId
     */
    void processPaymentProviderNotificationRequestProcessingEvent(@Nonnull final Long notificationRequestId);
}
