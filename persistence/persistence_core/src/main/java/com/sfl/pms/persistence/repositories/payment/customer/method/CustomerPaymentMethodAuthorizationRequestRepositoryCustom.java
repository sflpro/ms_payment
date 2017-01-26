package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 12:30 PM
 */
public interface CustomerPaymentMethodAuthorizationRequestRepositoryCustom {

    /**
     * Gets payment method authorization request by id with write pessimistic lock
     *
     * @param id
     * @return CustomerPaymentMethodAuthorizationRequest
     */
    CustomerPaymentMethodAuthorizationRequest findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id);
}
