package com.sfl.pms.services.payment.customer.method.exception.adyen;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 12:16 PM
 */
public class PaymentMethodAdyenInformationNotFoundForDetailReferenceException extends ServicesRuntimeException {
    private static final long serialVersionUID = 5313671853348112804L;

    /* Properties */
    private final String recurringDetailReference;

    public PaymentMethodAdyenInformationNotFoundForDetailReferenceException(final String recurringDetailReference) {
        super("No customer payment method Adyen information was found for recurring detail reference - " + recurringDetailReference);
        this.recurringDetailReference = recurringDetailReference;
    }

    /* Properties getters and setters */
    public String getRecurringDetailReference() {
        return recurringDetailReference;
    }
}
