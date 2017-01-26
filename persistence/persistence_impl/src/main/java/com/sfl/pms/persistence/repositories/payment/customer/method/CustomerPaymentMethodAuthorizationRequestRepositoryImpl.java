package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
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
 * Time: 12:31 PM
 */
public class CustomerPaymentMethodAuthorizationRequestRepositoryImpl implements CustomerPaymentMethodAuthorizationRequestRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAuthorizationRequestRepositoryImpl.class);

    /* Dependencies */
    @PersistenceContext
    private EntityManager entityManager;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestRepositoryImpl() {
        LOGGER.debug("Initializing customer payment method authorization request repository");
    }

    @Override
    public CustomerPaymentMethodAuthorizationRequest findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id) {
        Assert.notNull(id, "Customer payment method authorization request id should not be null");
        LOGGER.debug("Loading customer payment method authorization request with id - {} using pessimistic write lock.", id);
        entityManager.flush();
        final CustomerPaymentMethodAuthorizationRequest customerPaymentMethodAuthorizationRequest = entityManager.find(CustomerPaymentMethodAuthorizationRequest.class, id, LockModeType.PESSIMISTIC_WRITE);
        if (customerPaymentMethodAuthorizationRequest != null) {
            entityManager.refresh(customerPaymentMethodAuthorizationRequest);
        }
        LOGGER.debug("Successfully retrieved customer payment method authorization request with id - {} using pessimistic write lock. Customer payment method authorization request - {}", id, customerPaymentMethodAuthorizationRequest);
        return customerPaymentMethodAuthorizationRequest;
    }
}
