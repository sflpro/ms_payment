package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 10:40 AM
 */
public interface PaymentTypeSpecificOperationsProcessor {

    /**
     * Processes payment transaction
     *
     * @param payment
     * @return paymentResultDto
     */
    @Nonnull
    PaymentResultDto<? extends PaymentResult> processPaymentApiTransaction(@Nonnull final Payment payment);


    /**
     * Processes payment result
     *
     * @param payment
     * @param paymentResult
     * @return paymentProcessingResultDto
     */
    @Nonnull
    PaymentProcessingResultDetailedInformationDto processPaymentResult(@Nonnull final Payment payment, @Nonnull final PaymentResult paymentResult);


    /**
     * Processes duplicate payment result
     *
     * @param payment
     * @param paymentResultDto
     * @return paymentProcessingResultDto
     */
    PaymentProcessingResultDetailedInformationDto processDuplicatePaymentResult(@Nonnull final Payment payment, @Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto);


    /**
     * Generates payment redirect URL
     *
     * @param payment
     * @return paymentRedirectUrl
     */
    @Nonnull
    String generatePaymentRedirectUrl(@Nonnull final Payment payment);
}
