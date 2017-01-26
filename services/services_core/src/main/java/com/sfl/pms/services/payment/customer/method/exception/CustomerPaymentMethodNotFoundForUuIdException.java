package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForUuIdException;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/27/15
 * Time: 5:33 PM
 */
public class CustomerPaymentMethodNotFoundForUuIdException extends EntityNotFoundForUuIdException {
    private static final long serialVersionUID = -940319405209860968L;

    /* Constructors */
    public CustomerPaymentMethodNotFoundForUuIdException(final String uuId, final Class<? extends CustomerPaymentMethod> entityClass) {
        super(uuId, entityClass);
    }
}
