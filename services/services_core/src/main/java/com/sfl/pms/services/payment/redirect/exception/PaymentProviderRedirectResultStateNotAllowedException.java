package com.sfl.pms.services.payment.redirect.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResultState;

import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 2:52 PM
 */
public class PaymentProviderRedirectResultStateNotAllowedException extends ServicesRuntimeException {
    private static final long serialVersionUID = -2796652394380200340L;

    /* Properties */
    private final PaymentProviderRedirectResultState requiredState;

    private final PaymentProviderRedirectResultState requestState;

    private final Set<PaymentProviderRedirectResultState> allowedStates;

    /* Constructors */
    public PaymentProviderRedirectResultStateNotAllowedException(final PaymentProviderRedirectResultState requiredState, final PaymentProviderRedirectResultState requestState, final Set<PaymentProviderRedirectResultState> allowedStates) {
        super(requiredState + " state is not allowed since notification has state - " + requestState + " but expected request states are - " + allowedStates);
        this.requestState = requestState;
        this.requiredState = requiredState;
        this.allowedStates = allowedStates;
    }

    /* Properties getters and setters */
    public PaymentProviderRedirectResultState getRequiredState() {
        return requiredState;
    }

    public PaymentProviderRedirectResultState getRequestState() {
        return requestState;
    }

    public Set<PaymentProviderRedirectResultState> getAllowedStates() {
        return allowedStates;
    }
}
