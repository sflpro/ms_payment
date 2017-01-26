package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;

import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 8:43 PM
 */
public class CustomerPaymentMethodAuthorizationRequestStateNotAllowedException extends ServicesRuntimeException {

    private static final long serialVersionUID = 1007076056076795856L;

    /* Properties */
    private final CustomerPaymentMethodAuthorizationRequestState requiredState;

    private final CustomerPaymentMethodAuthorizationRequestState requestState;

    private final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestStateNotAllowedException(final CustomerPaymentMethodAuthorizationRequestState requiredState, final CustomerPaymentMethodAuthorizationRequestState requestState, final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates) {
        super(requiredState + " state is not allowed since request has state - " + requestState + " but expected request states are - " + allowedStates);
        this.requestState = requestState;
        this.requiredState = requiredState;
        this.allowedStates = allowedStates;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestState getRequiredState() {
        return requiredState;
    }

    public CustomerPaymentMethodAuthorizationRequestState getRequestState() {
        return requestState;
    }

    public Set<CustomerPaymentMethodAuthorizationRequestState> getAllowedStates() {
        return allowedStates;
    }
}
