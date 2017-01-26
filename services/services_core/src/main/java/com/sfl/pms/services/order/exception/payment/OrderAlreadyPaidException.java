package com.sfl.pms.services.order.exception.payment;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 5:58 PM
 */
public class OrderAlreadyPaidException extends ServicesRuntimeException {
    private static final long serialVersionUID = -354988583415916535L;

    /* Properties */
    private final Long orderId;

    /* Constructors */
    public OrderAlreadyPaidException(final Long orderId) {
        super("Order with id - " + orderId + " is already considered paid");
        this.orderId = orderId;
    }

    /* Properties getters and setters */
    public Long getOrderId() {
        return orderId;
    }
}
