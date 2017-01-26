package com.sfl.pms.persistence.repositories.order.payment;

import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 5:28 PM
 */
public interface AbstractOrderPaymentChannelRepository<T extends OrderPaymentChannel> extends JpaRepository<T, Long> {

    /**
     * Finds payment channel by order
     *
     * @param order
     * @return paymentChannel
     */

    T findByOrder(@Nonnull final Order order);

}

