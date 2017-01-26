package com.sfl.pms.services.payment.redirect;

import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:33 PM
 */
public interface AbstractPaymentProviderRedirectResultService<T extends PaymentProviderRedirectResult> {

    /**
     * Get payment provider redirect result for id
     *
     * @param redirectResultId
     * @return paymentProviderRedirectResult
     */
    @Nonnull
    T getPaymentProviderRedirectResultById(@Nonnull final Long redirectResultId);

    /**
     * Get payment provider redirect result for uuid
     *
     * @param uuId
     * @return paymentProviderRedirectResult
     */
    @Nonnull
    T getPaymentProviderRedirectResultByUuId(@Nonnull final String uuId);

    /**
     * Updated payment for redirect result
     *
     * @param redirectResultId
     * @param paymentId
     * @return paymentProviderRedirectResult
     */
    @Nonnull
    T updatePaymentForRedirectResult(@Nonnull final Long redirectResultId, @Nonnull final Long paymentId);
}
