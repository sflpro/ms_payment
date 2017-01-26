package com.sfl.pms.externalclients.payment.adyen.exception;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/4/15
 * Time: 2:17 PM
 */
public class PaymentRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1500446069235144723L;

    /* Constructors */
    public PaymentRuntimeException(final String message) {
        super(message);
    }
}
