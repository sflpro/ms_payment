package com.sfl.pms.services.payment.settings.exception;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:39 PM
 */
public class PaymentProviderSettingsNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = -4540738832357230339L;

    /* Constructors */
    public PaymentProviderSettingsNotFoundForIdException(final Long id, final Class<? extends PaymentProviderSettings> entityClass) {
        super(id, entityClass);
    }
}
