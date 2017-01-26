package com.sfl.pms.services.payment.customer.method.exception.adyen;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 4:45 PM
 */
public class AdyenRecurringDetailReferenceAlreadyUsedException extends ServicesRuntimeException {
    private static final long serialVersionUID = 1268238589214028907L;

    /* Properties */
    private final String recurringDetailReference;

    private final Long adyenPaymentMethodInformationId;

    /* Constructors */
    public AdyenRecurringDetailReferenceAlreadyUsedException(final String recurringDetailReference, final Long adyenPaymentMethodInformationId) {
        super("Recurring details reference - " + recurringDetailReference + " is already used for payment method information with id - " + adyenPaymentMethodInformationId);
        this.recurringDetailReference = recurringDetailReference;
        this.adyenPaymentMethodInformationId = adyenPaymentMethodInformationId;
    }

    /* Properties getters and setters */
    public String getRecurringDetailReference() {
        return recurringDetailReference;
    }

    public Long getAdyenPaymentMethodInformationId() {
        return adyenPaymentMethodInformationId;
    }
}
