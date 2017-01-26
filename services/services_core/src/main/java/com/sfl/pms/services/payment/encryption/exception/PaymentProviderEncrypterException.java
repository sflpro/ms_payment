package com.sfl.pms.services.payment.encryption.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/21/15
 * Time: 1:32 AM
 */
public class PaymentProviderEncrypterException extends ServicesRuntimeException {
    private static final long serialVersionUID = -5439570902216906578L;

    /* Constructors */
    public PaymentProviderEncrypterException(final String message, final Throwable parent) {
        super(message, parent);
    }

    public PaymentProviderEncrypterException(final Throwable parent) {
        super(parent);
    }
}
