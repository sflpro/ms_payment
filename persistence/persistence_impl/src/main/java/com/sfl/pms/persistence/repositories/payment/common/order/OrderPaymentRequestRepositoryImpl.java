package com.sfl.pms.persistence.repositories.payment.common.order;

import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;
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
 * Time: 12:25 PM
 */
public class OrderPaymentRequestRepositoryImpl implements OrderPaymentRequestRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentRequestRepositoryImpl.class);

    /* Dependencies */
    @PersistenceContext
    private EntityManager entityManager;

    /* Constructors */
    public OrderPaymentRequestRepositoryImpl() {
        LOGGER.debug("Initializing order payment request repository");
    }

    @Override
    public OrderPaymentRequest findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id) {
        Assert.notNull(id, "Order payment request id should not be null");
        LOGGER.debug("Loading order payment request with id - {} using pessimistic write lock.", id);
        entityManager.flush();
        final OrderPaymentRequest orderPaymentRequest = entityManager.find(OrderPaymentRequest.class, id, LockModeType.PESSIMISTIC_WRITE);
        if (orderPaymentRequest != null) {
            entityManager.refresh(orderPaymentRequest);
        }
        LOGGER.debug("Successfully retrieved order payment request with id - {} using pessimistic write lock. Order payment request - {}", id, orderPaymentRequest);
        return orderPaymentRequest;
    }
}
