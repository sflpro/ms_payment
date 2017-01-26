package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 2:52 PM
 */
public class CustomerPaymentMethodAlreadyBoundAuthorizationRequestException extends ServicesRuntimeException {
    private static final long serialVersionUID = 4634787570617379207L;

    /* Properties */
    private final Long existingPaymentMethodId;

    private final Long authorizationRequestId;

    /* Constructors */
    public CustomerPaymentMethodAlreadyBoundAuthorizationRequestException(final Long existingPaymentMethodId, final Long authorizationRequestId) {
        super("Payment authorization request with id - " + authorizationRequestId + " is already associated with payment method with id - " + existingPaymentMethodId);
        this.existingPaymentMethodId = existingPaymentMethodId;
        this.authorizationRequestId = authorizationRequestId;
    }

    /* Properties getters and setters */
    public Long getExistingPaymentMethodId() {
        return existingPaymentMethodId;
    }

    public Long getAuthorizationRequestId() {
        return authorizationRequestId;
    }
}
