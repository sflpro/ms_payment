package com.sfl.pms.services.payment.customer.method.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/15/15
 * Time: 5:15 PM
 */
public class UnknownCustomerPaymentMethodException extends ServicesRuntimeException {
    private static final long serialVersionUID = -1758959561331372821L;

    /* Properties */
    private final CustomerPaymentMethodType customerPaymentMethodType;

    /* Constructors */
    public UnknownCustomerPaymentMethodException(final CustomerPaymentMethodType customerPaymentMethodType) {
        super("Unknown customer payment method type - " + customerPaymentMethodType);
        this.customerPaymentMethodType = customerPaymentMethodType;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodType getCustomerPaymentMethodType() {
        return customerPaymentMethodType;
    }
}
