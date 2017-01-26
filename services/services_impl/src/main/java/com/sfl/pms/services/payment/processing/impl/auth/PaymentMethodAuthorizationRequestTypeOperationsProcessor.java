package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 4:07 PM
 */
public interface PaymentMethodAuthorizationRequestTypeOperationsProcessor {

    /**
     * Creates payment processing channel DTO
     *
     * @param authorizationRequest
     * @return paymentProcessingChannelDto
     */
    @Nonnull
    PaymentProcessingChannelDto<? extends PaymentProcessingChannel> createPaymentProcessingChannelDto(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest);

    /**
     * Creates payment DTO
     *
     * @param authorizationRequest
     * @return paymentDto
     */
    @Nonnull
    CustomerPaymentMethodAuthorizationPaymentDto createPaymentDto(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest);
}
