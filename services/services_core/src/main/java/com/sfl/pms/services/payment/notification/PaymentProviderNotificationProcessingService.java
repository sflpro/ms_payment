package com.sfl.pms.services.payment.notification;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 6:53 PM
 */
public interface PaymentProviderNotificationProcessingService {

    /**
     * Process payment provider notification request
     *
     * @param notificationRequestId
     * @return paymentProviderNotifications
     */
    List<Long> processPaymentProviderNotificationRequest(@Nonnull final Long notificationRequestId);
}
