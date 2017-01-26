package com.sfl.pms.persistence.repositories.order;

import com.sfl.pms.services.order.model.Order;
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
 * Date: 8/28/15
 * Time: 1:33 PM
 */
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    /* Dependencies */
    @PersistenceContext
    private EntityManager entityManager;

    /* Constructors */
    public OrderRepositoryImpl() {
    }

    @Override
    public Order findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id) {
        Assert.notNull(id, "Order id should not be null");
        LOGGER.debug("Loading order with id - {} using pessimistic write lock.", id);
        entityManager.flush();
        final Order order = entityManager.find(Order.class, id, LockModeType.PESSIMISTIC_WRITE);
        // Make sure to get latest version from database after acquiring lock
        if (order != null) {
            entityManager.refresh(order);
        }
        LOGGER.debug("Successfully retrieved order with id - {} using pessimistic write lock. Order - {}", id, order);
        return order;
    }
}
