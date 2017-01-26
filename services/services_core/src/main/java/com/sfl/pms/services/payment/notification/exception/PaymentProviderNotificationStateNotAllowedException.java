package com.sfl.pms.services.payment.notification.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;

import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 2:52 PM
 */
public class PaymentProviderNotificationStateNotAllowedException extends ServicesRuntimeException {
    private static final long serialVersionUID = -2796652394380200340L;

    /* Properties */
    private final PaymentProviderNotificationState requiredState;

    private final PaymentProviderNotificationState requestState;

    private final Set<PaymentProviderNotificationState> allowedStates;

    /* Constructors */
    public PaymentProviderNotificationStateNotAllowedException(final PaymentProviderNotificationState requiredState, final PaymentProviderNotificationState requestState, final Set<PaymentProviderNotificationState> allowedStates) {
        super(requiredState + " state is not allowed since notification has state - " + requestState + " but expected request states are - " + allowedStates);
        this.requestState = requestState;
        this.requiredState = requiredState;
        this.allowedStates = allowedStates;
    }

    /* Properties getters and setters */
    public PaymentProviderNotificationState getRequiredState() {
        return requiredState;
    }

    public PaymentProviderNotificationState getRequestState() {
        return requestState;
    }

    public Set<PaymentProviderNotificationState> getAllowedStates() {
        return allowedStates;
    }
}
