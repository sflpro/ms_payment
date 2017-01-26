package com.sfl.pms.services.payment.common.exception.order;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 1:53 PM
 */
public class InvalidCustomerPaymentMethodException extends ServicesRuntimeException {

    /* Properties */
    private final Long providedCustomerId;

    private final Long paymentMethodId;

    private final Long paymentMethodCustomerId;

    /* Constructors */
    public InvalidCustomerPaymentMethodException(final Long providedCustomerId, final Long paymentMethodId, final Long paymentMethodCustomerId) {
        super("Provided customer id - " + providedCustomerId + " and payment method with id - " + paymentMethodId + " has different customers. Payment method customer id - " + paymentMethodCustomerId);
        this.providedCustomerId = providedCustomerId;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodCustomerId = paymentMethodCustomerId;
    }

    /* Properties getters and setters */
    public Long getProvidedCustomerId() {
        return providedCustomerId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public Long getPaymentMethodCustomerId() {
        return paymentMethodCustomerId;
    }
}
