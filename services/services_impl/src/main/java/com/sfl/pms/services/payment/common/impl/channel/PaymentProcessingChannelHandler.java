package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 12:16 PM
 */
public interface PaymentProcessingChannelHandler {

    /**
     * Asserts payment processing channel DTO
     *
     * @param paymentProcessingChannelDto
     * @param customer
     */
    void assertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer);

    /**
     * Converts payment processing channel DTO to entity
     *
     * @param paymentProcessingChannelDto
     * @param customer
     * @return paymentResult
     */
    @Nonnull
    PaymentProcessingChannel convertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer);
}
