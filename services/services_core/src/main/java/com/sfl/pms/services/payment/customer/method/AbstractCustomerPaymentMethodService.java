package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:12 PM
 */
public interface AbstractCustomerPaymentMethodService<T extends CustomerPaymentMethod> {

    /**
     * Returns customer payment method for id
     *
     * @param paymentMethodId
     * @return customerPaymentMethod
     */
    @Nonnull
    T getCustomerPaymentMethodById(@Nonnull final Long paymentMethodId);

    /**
     * Remove customer payment method
     *
     * @param paymentMethodId
     * @return paymentMethod
     */
    @Nonnull
    T removeCustomerPaymentMethod(@Nonnull final Long paymentMethodId);


    /**
     * Returns customer payment method for uuId
     *
     * @param uuId
     * @return customerPaymentMethod
     */
    @Nonnull
    T getCustomerPaymentMethodByUuId(@Nonnull final String uuId);

    /**
     * Returns payment methods for customer
     *
     * @param customerId
     * @return customerPaymentMethods
     */
    @Nonnull
    List<T> getPaymentMethodsForCustomer(@Nonnull final Long customerId);
}
