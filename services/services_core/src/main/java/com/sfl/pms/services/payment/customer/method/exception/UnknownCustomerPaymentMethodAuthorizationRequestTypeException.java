package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 4:50 PM
 */
public class UnknownCustomerPaymentMethodAuthorizationRequestTypeException extends ServicesRuntimeException {

    private static final long serialVersionUID = 2533577959545135826L;

    /* Properties */
    private final CustomerPaymentMethodAuthorizationRequestType requestType;

    /* Constructors */
    public UnknownCustomerPaymentMethodAuthorizationRequestTypeException(final CustomerPaymentMethodAuthorizationRequestType requestType) {
        super("Unknown customer payment method authorization request type - " + requestType);
        this.requestType = requestType;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestType getRequestType() {
        return requestType;
    }
}
