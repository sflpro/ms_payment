package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 12:40 PM
 */
public class PaymentProviderNotificationRequestRepositoryImpl implements PaymentProviderNotificationRequestRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationRequestRepositoryImpl.class);

    /* Dependencies */
    @PersistenceContext
    private EntityManager entityManager;

    /* Constructors */
    public PaymentProviderNotificationRequestRepositoryImpl() {
        LOGGER.debug("Initializing payment provider notification request repository");
    }

    @Override
    public PaymentProviderNotificationRequest findByIdWithPessimisticWriteLock(@Nonnull Long id) {
        Assert.notNull(id, "Payment provider notification request id should not be null");
        LOGGER.debug("Loading payment provider notification request with id - {} using pessimistic write lock.", id);
        final PaymentProviderNotificationRequest paymentProviderNotificationRequest = entityManager.find(PaymentProviderNotificationRequest.class, id, LockModeType.PESSIMISTIC_WRITE);
        LOGGER.debug("Successfully retrieved payment provider notification request with id - {} using pessimistic write lock. Payment provider notification request - {}", id, paymentProviderNotificationRequest);
        return paymentProviderNotificationRequest;
    }
}
