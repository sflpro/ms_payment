package com.sfl.pms.services.payment.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 6:20 PM
 */
public class PaymentMethodDefinitionAlreadyExistsException extends ServicesRuntimeException {

    private static final long serialVersionUID = -4896414334913201500L;

    /* Properties */
    private final Long existingPaymentMethodDefinitionId;

    /* Constructors */
    public PaymentMethodDefinitionAlreadyExistsException(final Long existingPaymentMethodDefinitionId) {
        super("Payment method definition with id - " + existingPaymentMethodDefinitionId + " already exists for provided parameters");
        this.existingPaymentMethodDefinitionId = existingPaymentMethodDefinitionId;
    }

    /* Properties getters and setters */
    public Long getExistingPaymentMethodDefinitionId() {
        return existingPaymentMethodDefinitionId;
    }
}
