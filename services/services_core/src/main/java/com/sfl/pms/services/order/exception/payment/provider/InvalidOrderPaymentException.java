package com.sfl.pms.services.order.exception.payment.provider;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 6:28 PM
 */
public class InvalidOrderPaymentException extends ServicesRuntimeException {
    private static final long serialVersionUID = -1682339306654049165L;

    /* Properties */
    private final Long orderPaymentId;

    private final Long orderPaymentOrderId;

    private final Long expectedOrderId;

    /* Constructors */
    public InvalidOrderPaymentException(final Long orderPaymentId, final Long orderPaymentOrderId, final Long expectedOrderId) {
        super("Order payment with id - " + orderPaymentId + " belongs to order with id - " + orderPaymentOrderId + ". Current order id - " + expectedOrderId);
        this.orderPaymentId = orderPaymentId;
        this.orderPaymentOrderId = orderPaymentOrderId;
        this.expectedOrderId = expectedOrderId;
    }

    /* Properties getters and setters */
    public Long getOrderPaymentId() {
        return orderPaymentId;
    }

    public Long getOrderPaymentOrderId() {
        return orderPaymentOrderId;
    }

    public Long getExpectedOrderId() {
        return expectedOrderId;
    }
}
