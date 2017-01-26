package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.services.payment.common.model.Payment;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:43 AM
 */
@Repository
public interface PaymentRepository extends AbstractPaymentRepository<Payment>, PaymentRepositoryCustom {
}
