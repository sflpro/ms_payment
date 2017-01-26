package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForUuIdException;
import com.sfl.pms.services.payment.common.model.Payment;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:42 PM
 */
public class PaymentNotFoundForUuIdException extends EntityNotFoundForUuIdException {
    private static final long serialVersionUID = -833482579964483864L;

    public PaymentNotFoundForUuIdException(final String uuId, final Class<? extends Payment> entityClass) {
        super(uuId, entityClass);
    }
}
