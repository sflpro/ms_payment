package com.sfl.pms.services.customer.exception;


import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.customer.model.Customer;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/19/14
 * Time: 10:09 AM
 */
public class CustomerNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = 947713076446052984L;

    public CustomerNotFoundForIdException(final Long id) {
        super(id, Customer.class);
    }
}
