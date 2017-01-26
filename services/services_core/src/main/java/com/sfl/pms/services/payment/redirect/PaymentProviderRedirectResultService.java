package com.sfl.pms.services.payment.redirect;

import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResultState;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:33 PM
 */
public interface PaymentProviderRedirectResultService extends AbstractPaymentProviderRedirectResultService<PaymentProviderRedirectResult> {

    /**
     * Updates payment provider redirect result state
     *
     * @param paymentProviderRedirectResultId
     * @param state
     * @param allowedInitialStates
     * @return paymentProviderRedirectResult
     */
    PaymentProviderRedirectResult updatePaymentProviderRedirectResultState(@Nonnull final Long paymentProviderRedirectResultId, @Nonnull final PaymentProviderRedirectResultState state, @Nonnull final Set<PaymentProviderRedirectResultState> allowedInitialStates);
}
