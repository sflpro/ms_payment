package com.sfl.pms.services.payment.notification.adyen;

import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.dto.adyen.AdyenPaymentProviderNotificationDto;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:50 PM
 */
public interface AdyenPaymentProviderNotificationService extends AbstractPaymentProviderNotificationService<AdyenPaymentProviderNotification> {

    /**
     * Creates new Adyen payment provider notification for provided DTO
     *
     * @param notificationRequestId
     * @param notificationDto
     * @return adyenPaymentProviderNotification
     */
    @Nonnull
    AdyenPaymentProviderNotification createPaymentProviderNotification(@Nonnull final Long notificationRequestId, @Nonnull final AdyenPaymentProviderNotificationDto notificationDto);
}
