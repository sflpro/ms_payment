package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.services.payment.common.model.PaymentResult;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/14/15
 * Time: 10:20 PM
 */
@Repository
public interface PaymentResultRepository extends AbstractPaymentResultRepository<PaymentResult> {
}
