package com.sfl.pms.services.payment.method.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:22 PM
 */
public class PaymentMethodDefinitionNotFoundForIdException extends EntityNotFoundForIdException {

    private static final long serialVersionUID = 8923038117332388620L;

    /* Constructors */
    public PaymentMethodDefinitionNotFoundForIdException(final Long id, final Class<? extends PaymentMethodDefinition> paymentMethodDefinitionClass) {
        super(id, paymentMethodDefinitionClass);
    }
}
