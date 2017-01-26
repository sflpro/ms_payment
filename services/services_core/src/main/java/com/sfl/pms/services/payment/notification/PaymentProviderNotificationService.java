package com.sfl.pms.services.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:49 PM
 */
public interface PaymentProviderNotificationService extends AbstractPaymentProviderNotificationService<PaymentProviderNotification> {

    /**
     * Updates payment provider notification state
     *
     * @param notificationId
     * @param state
     * @param allowedInitialStates
     * @return paymentProviderNotification
     */
    @Nonnull
    PaymentProviderNotification updatePaymentProviderNotificationState(@Nonnull final Long notificationId, @Nonnull final PaymentProviderNotificationState state, @Nonnull final Set<PaymentProviderNotificationState> allowedInitialStates);
}
