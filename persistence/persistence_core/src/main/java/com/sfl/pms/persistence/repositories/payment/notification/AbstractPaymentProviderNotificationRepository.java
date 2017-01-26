package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:52 PM
 */
public interface AbstractPaymentProviderNotificationRepository<T extends PaymentProviderNotification> extends JpaRepository<T, Long> {
}
