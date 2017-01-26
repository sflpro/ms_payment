package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:29 AM
 */
@Repository
public interface PaymentProviderNotificationRequestRepository extends JpaRepository<PaymentProviderNotificationRequest, Long>, PaymentProviderNotificationRequestRepositoryCustom {

}
