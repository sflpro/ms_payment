package com.sfl.pms.persistence.repositories.payment.common.order;

import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 12:24 PM
 */
public interface OrderPaymentRequestRepositoryCustom {

    /**
     * Gets order payment request by id with pessimistic write lock
     *
     * @param id
     * @return orderPaymentRequest
     */
    OrderPaymentRequest findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id);
}
