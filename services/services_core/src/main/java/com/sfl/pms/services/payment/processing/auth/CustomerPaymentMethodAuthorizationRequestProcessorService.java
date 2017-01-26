package com.sfl.pms.services.payment.processing.auth;

import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 3:19 PM
 */
public interface CustomerPaymentMethodAuthorizationRequestProcessorService {

    /**
     * Creates and processes customer payment method authorization request payment
     *
     * @param customerPaymentMethodAuthorizationRequestId
     * @return paymentProcessingResultDetailedInformationDto
     */
    @Nonnull
    PaymentProcessingResultDetailedInformationDto processCustomerPaymentMethodAuthorizationRequest(@Nonnull final Long customerPaymentMethodAuthorizationRequestId);
}
