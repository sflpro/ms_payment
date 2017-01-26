package com.sfl.pms.services.payment.redirect;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/12/15
 * Time: 3:47 PM
 */
public interface PaymentProviderRedirectResultProcessingService {

    /**
     * Process payment provider redirect result
     *
     * @param paymentProviderRedirectResultId
     */
    void processPaymentProviderRedirectResult(@Nonnull final Long paymentProviderRedirectResultId);
}
