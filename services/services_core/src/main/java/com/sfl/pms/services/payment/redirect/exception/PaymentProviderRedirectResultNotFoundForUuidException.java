package com.sfl.pms.services.payment.redirect.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForUuIdException;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 2:16 PM
 */
public class PaymentProviderRedirectResultNotFoundForUuidException extends EntityNotFoundForUuIdException {

    private static final long serialVersionUID = 3941699328410717075L;

    public PaymentProviderRedirectResultNotFoundForUuidException(final String uuid, final Class<? extends PaymentProviderRedirectResult> entityClass) {
        super(uuid, entityClass);
    }
}
