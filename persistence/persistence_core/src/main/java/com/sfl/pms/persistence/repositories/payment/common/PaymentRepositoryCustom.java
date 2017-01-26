package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.services.payment.common.dto.PaymentSearchParameters;
import com.sfl.pms.services.payment.common.model.Payment;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/4/14
 * Time: 10:07 PM
 */
public interface PaymentRepositoryCustom {

    /**
     * Gets customers count for payment search parameters
     *
     * @param parameters
     * @return count
     */
    @Nonnull
    Long getCustomersCountForPaymentSearchParameters(@Nonnull final PaymentSearchParameters parameters);

    /**
     * Loads customer for provided payment search parameters
     *
     * @param parameters
     * @param maxCount
     * @return stations
     */
    @Nonnull
    List<Long> findCustomersForPaymentSearchParameters(@Nonnull final PaymentSearchParameters parameters, @Nonnull final long startFrom, @Nonnull final int maxCount);

    /**
     * Gets payment by id with pessimistic write lock
     *
     * @param id
     * @return payment
     */
    Payment findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id);
}
