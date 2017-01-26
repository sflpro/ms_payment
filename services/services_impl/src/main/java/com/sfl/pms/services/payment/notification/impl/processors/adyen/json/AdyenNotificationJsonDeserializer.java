package com.sfl.pms.services.payment.notification.impl.processors.adyen.json;

import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationJsonModel;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 12:20 PM
 */
public interface AdyenNotificationJsonDeserializer {

    /**
     * Deserialize Adyen notification into JSON model
     *
     * @param notificationRawContent
     * @return adyenNotificationJsonModel
     */
    @Nonnull
    AdyenNotificationJsonModel deserializeAdyenNotification(@Nonnull final String notificationRawContent);
}
