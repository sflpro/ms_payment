package com.sfl.pms.services.payment.common.exception.auth;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 7:49 PM
 */
public class CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException extends ServicesRuntimeException {

    private static final long serialVersionUID = -841061073653092156L;
    /* Properties */
    private final Long paymentMethodAuthorizationRequestId;

    private final Long existingPaymentId;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException(final Long paymentMethodAuthorizationRequestId, final Long existingPaymentId) {
        super("Payment with id - " + existingPaymentId + " already exists for payment method authorization request with id - " + paymentMethodAuthorizationRequestId);
        this.existingPaymentId = existingPaymentId;
        this.paymentMethodAuthorizationRequestId = paymentMethodAuthorizationRequestId;
    }

    /* Properties getters and setters */
    public Long getPaymentMethodAuthorizationRequestId() {
        return paymentMethodAuthorizationRequestId;
    }

    public Long getExistingPaymentId() {
        return existingPaymentId;
    }
}
