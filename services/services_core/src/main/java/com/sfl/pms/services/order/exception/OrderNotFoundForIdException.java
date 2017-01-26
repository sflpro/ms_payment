package com.sfl.pms.services.order.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.order.model.Order;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/28/14
 * Time: 4:56 PM
 */
public class OrderNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = -7826497636585019352L;

    /* Constructors */
    public OrderNotFoundForIdException(final Long id) {
        super(id, Order.class);
    }
}
