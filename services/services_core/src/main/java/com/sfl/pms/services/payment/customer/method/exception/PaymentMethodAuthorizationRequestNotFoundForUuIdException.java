package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/20/15
 * Time: 2:40 PM
 */
public class PaymentMethodAuthorizationRequestNotFoundForUuIdException extends ServicesRuntimeException {
    private static final long serialVersionUID = 4055053896585980930L;

    /* Properties */
    private final String uuId;

    private final Class<? extends CustomerPaymentMethodAuthorizationRequest> authorizationRequestClass;

    /* Constructors */
    public PaymentMethodAuthorizationRequestNotFoundForUuIdException(final String uuId, final Class<? extends CustomerPaymentMethodAuthorizationRequest> authorizationRequestClass) {
        super("No customer payment method authorization request was found for UUID - " + uuId + " and class - " + authorizationRequestClass);
        this.uuId = uuId;
        this.authorizationRequestClass = authorizationRequestClass;
    }

    /* Properties getters and setters */
    public String getUuId() {
        return uuId;
    }

    public Class<? extends CustomerPaymentMethodAuthorizationRequest> getAuthorizationRequestClass() {
        return authorizationRequestClass;
    }
}
