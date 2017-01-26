package com.sfl.pms.persistence.repositories.payment.common.order;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:44 AM
 */
@Repository
public interface OrderPaymentRepository extends AbstractPaymentRepository<OrderPayment> {
}
