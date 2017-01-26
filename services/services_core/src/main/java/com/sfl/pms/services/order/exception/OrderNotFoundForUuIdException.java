package com.sfl.pms.services.order.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForUuIdException;
import com.sfl.pms.services.order.model.Order;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 1/29/15
 * Time: 3:54 PM
 */
public class OrderNotFoundForUuIdException extends EntityNotFoundForUuIdException {

    private static final long serialVersionUID = -1505298075428816221L;

    public OrderNotFoundForUuIdException(final String uuId) {
        super(uuId, Order.class);
    }
}
