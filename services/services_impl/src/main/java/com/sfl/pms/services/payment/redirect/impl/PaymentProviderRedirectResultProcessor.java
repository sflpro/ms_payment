package com.sfl.pms.services.payment.redirect.impl;

import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResultState;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/13/15
 * Time: 10:40 AM
 */
public interface PaymentProviderRedirectResultProcessor {

    /**
     * Process payment provider redirect result
     *
     * @param paymentProviderRedirectResult
     * @return paymentProviderRedirectResultState
     */
    PaymentProviderRedirectResultState processPaymentProviderRedirectResult(@Nonnull final PaymentProviderRedirectResult paymentProviderRedirectResult);
}
