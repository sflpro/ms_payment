package com.sfl.pms.services.payment.common.order;

import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.order.OrderPaymentDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:40 AM
 */
public interface OrderPaymentService extends AbstractPaymentService<OrderPayment> {

    /**
     * Creates new order payment
     *
     * @param orderId
     * @param orderPaymentDto
     * @param paymentProcessingChannelDto
     * @return orderPayment
     */
    @Nonnull
    OrderPayment createPayment(@Nonnull final Long orderId, @Nonnull final OrderPaymentDto orderPaymentDto, @Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto);
}
