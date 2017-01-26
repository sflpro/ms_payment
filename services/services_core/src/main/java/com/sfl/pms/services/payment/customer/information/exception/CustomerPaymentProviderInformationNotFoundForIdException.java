package com.sfl.pms.services.payment.customer.information.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:49 AM
 */
public class CustomerPaymentProviderInformationNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = 4590570076175737292L;

    /* Constructors */
    public CustomerPaymentProviderInformationNotFoundForIdException(final Long id, final Class<? extends CustomerPaymentProviderInformation> entityClass) {
        super(id, entityClass);
    }

}
