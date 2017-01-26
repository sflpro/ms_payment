package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDto;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 12:13 PM
 */
public interface PaymentResultProcessor {

    /**
     * Process payment result
     *
     * @param paymentId
     * @param notificationId
     * @param paymentResultDto
     * @return paymentProcessingResultDto
     */
    PaymentProcessingResultDto processPaymentResult(@Nonnull final Long paymentId, final Long notificationId, final Long redirectResultId, @Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto);
}
