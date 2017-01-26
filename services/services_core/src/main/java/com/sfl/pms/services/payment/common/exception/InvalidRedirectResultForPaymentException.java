package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/6/15
 * Time: 4:44 PM
 */
public class InvalidRedirectResultForPaymentException extends ServicesRuntimeException {

    private static final long serialVersionUID = 2090626483638347157L;

    /* Properties */
    private final Long redirectResultId;

    private final Long paymentId;

    /* Constructors */
    public InvalidRedirectResultForPaymentException(final Long redirectResultId, final Long paymentId) {
        super("Redirect result with id - " + redirectResultId + " can not be used with payment with id - " + paymentId);
        this.redirectResultId = redirectResultId;
        this.paymentId = paymentId;
    }

    /* Properties getters and setters */
    public Long getRedirectResultId() {
        return redirectResultId;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}
