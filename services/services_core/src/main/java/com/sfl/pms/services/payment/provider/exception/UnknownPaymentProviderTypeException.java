package com.sfl.pms.services.payment.provider.exception;

import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/14/15
 * Time: 9:02 PM
 */
public class UnknownPaymentProviderTypeException extends IllegalArgumentException {
    private static final long serialVersionUID = 2297246919308652357L;

    /* Properties */
    private final PaymentProviderType paymentProviderType;

    /* Constructors */
    public UnknownPaymentProviderTypeException(final PaymentProviderType paymentProviderType) {
        super("Unknown payment provider type - " + paymentProviderType);
        this.paymentProviderType = paymentProviderType;
    }

    /* Properties getters and setters */
    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }
}
