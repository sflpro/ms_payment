package com.sfl.pms.services.payment.redirect.model;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 11:21 AM
 */
public enum PaymentProviderRedirectResultState {
    CREATED, PROCESSING, PROCESSED, SIGNATURE_VERIFICATION_FAILED, PAYMENT_LOOKUP_FAILED, FAILED
}
