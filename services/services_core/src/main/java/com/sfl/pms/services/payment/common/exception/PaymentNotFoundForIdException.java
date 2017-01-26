package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.common.model.Payment;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:42 PM
 */
public class PaymentNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = 2912093438344959194L;

    public PaymentNotFoundForIdException(final Long id, final Class<? extends Payment> entityClass) {
        super(id, entityClass);
    }
}
