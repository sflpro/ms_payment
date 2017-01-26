package com.sfl.pms.services.order.impl.payment;

import com.sfl.pms.services.order.dto.payment.OrderPaymentChannelDto;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 6:09 PM
 */
public interface OrderPaymentChannelHandler {

    /**
     * Asserts order payment channel
     *
     * @param paymentChannelDto
     */
    void assertOrderPaymentChannel(@Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto);

    /**
     * Asserts order payment channel is applicable for order
     *
     * @param orderId
     * @param paymentChannelDto
     */
    void assertPaymentChannelIsApplicableForOrder(@Nonnull final Long orderId, @Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto);

    /**
     * Converts DTO to order payment channel domain object
     *
     * @param paymentChannelDto
     */
    OrderPaymentChannel convertOrderPaymentChannelDto(@Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto);
}
