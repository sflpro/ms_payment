package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/15/15
 * Time: 3:22 PM
 */
public interface CustomerPaymentMethodsSynchronizationService {


    /**
     * Synchronizes customer payment methods for specified payment provider
     *
     * @param customerId
     * @param paymentProviderType
     * @return customerPaymentMethodsSynchronizationResult
     */
    @Nonnull
    CustomerPaymentMethodsSynchronizationResult synchronizeCustomerPaymentMethods(@Nonnull final Long customerId, @Nonnull final PaymentProviderType paymentProviderType);
}
