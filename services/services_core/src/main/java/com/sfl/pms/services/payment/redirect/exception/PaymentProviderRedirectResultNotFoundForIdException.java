package com.sfl.pms.services.payment.redirect.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 2:16 PM
 */
public class PaymentProviderRedirectResultNotFoundForIdException extends EntityNotFoundForIdException {

    private static final long serialVersionUID = 3941699328410717075L;

    public PaymentProviderRedirectResultNotFoundForIdException(final Long id, final Class<? extends PaymentProviderRedirectResult> entityClass) {
        super(id, entityClass);
    }
}
