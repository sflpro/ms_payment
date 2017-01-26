package com.sfl.pms.services.customer.exception;


import com.sfl.pms.services.common.exception.EntityNotFoundForUuIdException;
import com.sfl.pms.services.customer.model.Customer;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/17/15
 * Time: 2:53 PM
 */
public class CustomerNotFoundForUuidException extends EntityNotFoundForUuIdException {

    private static final long serialVersionUID = -2552415605596284708L;

    /* Constructors */
    public CustomerNotFoundForUuidException(final String uuId) {
        super(uuId, Customer.class);
    }
}
