package com.sfl.pms.services.payment.common.impl.provider;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.PaymentResult;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/14/15
 * Time: 8:50 PM
 */
public interface PaymentResultHandler {

    /**
     * Asserts payment result DTO
     *
     * @param paymentResultDto
     */
    void assertPaymentResultDto(@Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto);

    /**
     * Converts payment method provider information DTO to entity
     *
     * @param paymentResultDto
     * @return paymentResult
     */
    @Nonnull
    PaymentResult convertPaymentResultDto(@Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto);
}
