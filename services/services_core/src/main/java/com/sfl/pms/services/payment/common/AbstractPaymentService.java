package com.sfl.pms.services.payment.common;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:37 AM
 */
public interface AbstractPaymentService<T extends Payment> {

    /**
     * Returns payment for id
     *
     * @param paymentId
     * @return payment
     */
    @Nonnull
    T getPaymentById(@Nonnull final Long paymentId);

    /**
     * Get payment for UUID
     *
     * @param paymentUuId
     * @return payment
     */
    @Nonnull
    T getPaymentByUuId(@Nonnull final String paymentUuId);

    /**
     * Updates payment result for payment
     *
     * @param paymentId
     * @param paymentProviderNotificationId
     * @param paymentProviderRedirectResultId
     * @param paymentResultDto
     * @return paymentResult
     */
    @Nonnull
    PaymentResult createPaymentResultForPayment(@Nonnull final Long paymentId, final Long paymentProviderNotificationId, final Long paymentProviderRedirectResultId, @Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto);
}
