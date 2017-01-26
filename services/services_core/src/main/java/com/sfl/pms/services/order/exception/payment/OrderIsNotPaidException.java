package com.sfl.pms.services.order.exception.payment;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 2/19/15
 * Time: 6:14 PM
 */
public class OrderIsNotPaidException extends ServicesRuntimeException {

    private static final long serialVersionUID = -8071177674582388102L;

    /* Properties */
    private final Long orderId;

    /* Constructors */
    public OrderIsNotPaidException(final Long orderId) {
        super("Order with id - " + orderId + " is not paid yet");
        this.orderId = orderId;
    }

    /* Properties getters and setters */
    public Long getOrderId() {
        return orderId;
    }
}
