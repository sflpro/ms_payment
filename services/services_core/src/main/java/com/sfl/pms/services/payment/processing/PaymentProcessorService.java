package com.sfl.pms.services.payment.processing;

import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/1/15
 * Time: 9:28 PM
 */
public interface PaymentProcessorService {

    /**
     * Process payment by provided id
     *
     * @param paymentId
     * @return paymentProcessingResultDto
     */
    @Nonnull
    PaymentProcessingResultDetailedInformationDto processPayment(@Nonnull final Long paymentId);
}
