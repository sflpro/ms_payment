package com.sfl.pms.persistence.repositories.order.payment;

import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 5:29 PM
 */
@Repository
public interface OrderPaymentChannelRepository extends AbstractOrderPaymentChannelRepository<OrderPaymentChannel> {
}
