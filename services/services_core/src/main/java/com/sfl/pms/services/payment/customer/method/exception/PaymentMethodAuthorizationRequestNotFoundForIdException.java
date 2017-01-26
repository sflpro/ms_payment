package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 9:05 PM
 */
public class PaymentMethodAuthorizationRequestNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = 5845664432508635101L;

    /* Constructors */
    public PaymentMethodAuthorizationRequestNotFoundForIdException(final Long id, final Class<? extends CustomerPaymentMethodAuthorizationRequest> entityClass) {
        super(id, entityClass);
    }

}
