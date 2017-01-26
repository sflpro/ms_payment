package com.sfl.pms.services.payment.notification.impl.processors;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 11:28 AM
 */
public interface PaymentProviderNotificationProcessor {

    /**
     * Create payment provider notification for request
     *
     * @param notificationRequest
     * @return paymentProviderNotifications
     */
    @Nonnull
    List<PaymentProviderNotification> createPaymentProviderNotificationForRequest(@Nonnull final PaymentProviderNotificationRequest notificationRequest);

    /**
     * Process payment provider notification
     *
     * @param notification
     */
    void processPaymentProviderNotification(@Nonnull final PaymentProviderNotification notification);
}
