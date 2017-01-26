package com.sfl.pms.services.payment.common.exception.order;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/20/16
 * Time: 3:17 PM
 */
public class InvalidOrderPaymentRequestPaymentException extends ServicesRuntimeException {

    private static final long serialVersionUID = -3813818841284458318L;

    /* Properties */
    private final Long orderPaymentRequestId;

    private final Long orderPaymentId;

    /* Constructors */
    public InvalidOrderPaymentRequestPaymentException(final Long orderPaymentRequestId, final Long orderPaymentId) {
        super("Order payment request with id - " + orderPaymentRequestId + " refers to a different order the payment with id - " + orderPaymentId + " does");
        this.orderPaymentRequestId = orderPaymentRequestId;
        this.orderPaymentId = orderPaymentId;
    }

    /* Properties getters and setters */
    public Long getOrderPaymentRequestId() {
        return orderPaymentRequestId;
    }

    public Long getOrderPaymentId() {
        return orderPaymentId;
    }
}
