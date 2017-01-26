package com.sfl.pms.services.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:48 PM
 */
public interface AbstractPaymentProviderNotificationService<T extends PaymentProviderNotification> {

    /**
     * Get payment provider notification by id
     *
     * @param notificationId
     * @return paymentProviderNotification
     */
    @Nonnull
    T getPaymentProviderNotificationById(@Nonnull final Long notificationId);

    /**
     * Update payment for notification
     *
     * @param notificationId
     * @param paymentId
     * @return paymentProviderNotification
     */
    @Nonnull
    T updatePaymentForNotification(@Nonnull final Long notificationId, @Nonnull final Long paymentId);
}
