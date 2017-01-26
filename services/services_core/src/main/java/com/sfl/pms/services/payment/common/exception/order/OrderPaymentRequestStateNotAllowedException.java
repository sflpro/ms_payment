package com.sfl.pms.services.payment.common.exception.order;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequestState;

import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 2:52 PM
 */
public class OrderPaymentRequestStateNotAllowedException extends ServicesRuntimeException {
    private static final long serialVersionUID = -2796652394380200340L;

    /* Properties */
    private final OrderPaymentRequestState requiredState;

    private final OrderPaymentRequestState requestState;

    private final Set<OrderPaymentRequestState> allowedStates;

    /* Constructors */
    public OrderPaymentRequestStateNotAllowedException(final OrderPaymentRequestState requiredState, final OrderPaymentRequestState requestState, final Set<OrderPaymentRequestState> allowedStates) {
        super(requiredState + " state is not allowed since request has state - " + requestState + " but expected request states are - " + allowedStates);
        this.requestState = requestState;
        this.requiredState = requiredState;
        this.allowedStates = allowedStates;
    }

    /* Properties getters and setters */
    public OrderPaymentRequestState getRequiredState() {
        return requiredState;
    }

    public OrderPaymentRequestState getRequestState() {
        return requestState;
    }

    public Set<OrderPaymentRequestState> getAllowedStates() {
        return allowedStates;
    }
}
