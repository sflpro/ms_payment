package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 5:21 PM
 */
public class CustomerPaymentMethodNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = -1441467355815318695L;

    /* Constructors */
    public CustomerPaymentMethodNotFoundForIdException(final Long id, Class<? extends CustomerPaymentMethod> entityClass) {
        super(id, entityClass);
    }
}
