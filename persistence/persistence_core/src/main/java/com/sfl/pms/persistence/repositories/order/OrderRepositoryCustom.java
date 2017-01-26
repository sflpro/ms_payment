package com.sfl.pms.persistence.repositories.order;

import com.sfl.pms.services.order.model.Order;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/28/15
 * Time: 1:01 PM
 */
public interface OrderRepositoryCustom {

    /**
     * Gets order by id with pessimistic write lock
     *
     * @param id
     * @return order
     */
    Order findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id);
}
