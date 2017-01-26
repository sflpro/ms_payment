package com.sfl.pms.services.payment.method;

import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:41 PM
 */
public interface AbstractPaymentMethodDefinitionService<T extends PaymentMethodDefinition> {

    /**
     * Gets payment method definition by id
     *
     * @param paymentMethodDefinitionId
     * @return paymentMethodDefinition
     */
    T getPaymentMethodDefinitionById(@Nonnull final Long paymentMethodDefinitionId);
}
