package com.sfl.pms.services.payment.notification.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;

import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 2:52 PM
 */
public class PaymentProviderNotificationRequestStateNotAllowedException extends ServicesRuntimeException {
    private static final long serialVersionUID = -2796652394380200340L;

    /* Properties */
    private final PaymentProviderNotificationRequestState requiredState;

    private final PaymentProviderNotificationRequestState requestState;

    private final Set<PaymentProviderNotificationRequestState> allowedStates;

    /* Constructors */
    public PaymentProviderNotificationRequestStateNotAllowedException(final PaymentProviderNotificationRequestState requiredState, final PaymentProviderNotificationRequestState requestState, final Set<PaymentProviderNotificationRequestState> allowedStates) {
        super(requiredState + " state is not allowed since request has state - " + requestState + " but expected request states are - " + allowedStates);
        this.requestState = requestState;
        this.requiredState = requiredState;
        this.allowedStates = allowedStates;
    }

    /* Properties getters and setters */
    public PaymentProviderNotificationRequestState getRequiredState() {
        return requiredState;
    }

    public PaymentProviderNotificationRequestState getRequestState() {
        return requestState;
    }

    public Set<PaymentProviderNotificationRequestState> getAllowedStates() {
        return allowedStates;
    }
}
