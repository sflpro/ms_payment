package com.sfl.pms.services.order.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/27/14
 * Time: 6:17 PM
 */
public class OrderAlreadyExistsForUuIdException extends ServicesRuntimeException {

    private static final long serialVersionUID = -178536122095994039L;

    /* Properties */
    private String uuId;

    private Long orderId;

    /* Constructors */
    public OrderAlreadyExistsForUuIdException(final String uuId, final Long orderId) {
        super("Order with id - " + orderId + " already has uuid - " + uuId);
        this.uuId = uuId;
        this.orderId = orderId;
    }

    /* Properties getters and setters */
    public String getUuId() {
        return uuId;
    }

    public void setUuId(final String uuId) {
        this.uuId = uuId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
