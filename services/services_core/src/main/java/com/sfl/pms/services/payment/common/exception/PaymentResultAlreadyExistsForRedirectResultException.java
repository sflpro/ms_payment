package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/6/15
 * Time: 4:57 PM
 */
public class PaymentResultAlreadyExistsForRedirectResultException extends ServicesRuntimeException {

    private static final long serialVersionUID = 5437996594904609246L;

    /* Properties */
    private final Long redirectResultId;

    private final Long existingPaymentResultId;

    /* Constructors */
    public PaymentResultAlreadyExistsForRedirectResultException(final Long redirectResultId, final Long existingPaymentResultId) {
        super("Payment result with id - " + existingPaymentResultId + " already exists for redirect result with id - " + redirectResultId);
        this.redirectResultId = redirectResultId;
        this.existingPaymentResultId = existingPaymentResultId;
    }

    /* Properties getters and setters */
    public Long getRedirectResultId() {
        return redirectResultId;
    }

    public Long getExistingPaymentResultId() {
        return existingPaymentResultId;
    }
}
