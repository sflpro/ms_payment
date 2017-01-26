package com.sfl.pms.persistence.repositories.payment.notification.adyen;

import com.sfl.pms.persistence.repositories.payment.notification.AbstractPaymentProviderNotificationRepository;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:53 PM
 */
@Repository
public interface AdyenPaymentProviderNotificationRepository extends AbstractPaymentProviderNotificationRepository<AdyenPaymentProviderNotification> {
}
