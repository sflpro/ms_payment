package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 12:39 PM
 */
public interface PaymentProviderNotificationRequestRepositoryCustom {

    /**
     * Gets payment provider notification request by id with pessimistic write lock
     *
     * @param id
     * @return paymentProviderNotificationRequest
     */
    PaymentProviderNotificationRequest findByIdWithPessimisticWriteLock(@Nonnull final Long id);
}
