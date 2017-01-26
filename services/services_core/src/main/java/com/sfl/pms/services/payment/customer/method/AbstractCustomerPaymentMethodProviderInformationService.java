package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:20 AM
 */
public interface AbstractCustomerPaymentMethodProviderInformationService<T extends CustomerPaymentMethodProviderInformation> {

    /**
     * Retrieve customer payment method provider information by ID
     *
     * @param paymentMethodProviderInformationId
     * @return
     */
    @Nonnull
    T getCustomerPaymentMethodProviderInformationById(@Nonnull final Long paymentMethodProviderInformationId);
}
