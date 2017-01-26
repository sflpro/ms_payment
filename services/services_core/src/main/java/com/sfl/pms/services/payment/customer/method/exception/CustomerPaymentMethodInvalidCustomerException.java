package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 2:24 PM
 */
public class CustomerPaymentMethodInvalidCustomerException extends ServicesRuntimeException {
    private static final long serialVersionUID = -7679346197898365433L;

    /* Properties */
    private final Long providedCustomerId;

    private final Long paymentMethodCustomerId;

    public CustomerPaymentMethodInvalidCustomerException(final Long providedCustomerId, final Long paymentMethodCustomerId) {
        super("Customer payment method customer and provided customer are different. Provided customer id - " + providedCustomerId + ", authorization request customer - " + paymentMethodCustomerId);
        this.providedCustomerId = providedCustomerId;
        this.paymentMethodCustomerId = paymentMethodCustomerId;
    }

    /* Properties getters and setters */
    public Long getProvidedCustomerId() {
        return providedCustomerId;
    }

    public Long getPaymentMethodCustomerId() {
        return paymentMethodCustomerId;
    }
}
