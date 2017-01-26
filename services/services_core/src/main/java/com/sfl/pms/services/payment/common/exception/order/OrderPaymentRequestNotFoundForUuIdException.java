package com.sfl.pms.services.payment.common.exception.order;

import com.sfl.pms.services.common.exception.EntityNotFoundForUuIdException;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 1:20 PM
 */
public class OrderPaymentRequestNotFoundForUuIdException extends EntityNotFoundForUuIdException {
    private static final long serialVersionUID = 6214135604450195873L;

    /* Constructors */
    public OrderPaymentRequestNotFoundForUuIdException(final String uuId) {
        super(uuId, OrderPaymentRequest.class);
    }
}
