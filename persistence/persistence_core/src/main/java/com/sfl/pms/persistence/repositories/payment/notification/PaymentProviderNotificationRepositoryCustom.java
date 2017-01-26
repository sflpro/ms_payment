package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 12:36 PM
 */
public interface PaymentProviderNotificationRepositoryCustom {

    /**
     * Gets payment provider notification by id with pessimistic write lock
     *
     * @param id
     * @return paymentProviderNotification
     */
    PaymentProviderNotification findByIdWithPessimisticWriteLock(@Nonnull final Long id);
}
