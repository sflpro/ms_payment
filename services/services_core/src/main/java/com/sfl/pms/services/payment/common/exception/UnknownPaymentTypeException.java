package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.model.PaymentType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 11:48 AM
 */
public class UnknownPaymentTypeException extends ServicesRuntimeException {

    private static final long serialVersionUID = -7603990632995065413L;

    /* Properties */
    private final PaymentType paymentType;

    /* Constructors */
    public UnknownPaymentTypeException(final PaymentType paymentType) {
        super("Unknown payment type - " + paymentType);
        this.paymentType = paymentType;
    }

    /* Properties getters and setters */
    public PaymentType getPaymentType() {
        return paymentType;
    }
}
