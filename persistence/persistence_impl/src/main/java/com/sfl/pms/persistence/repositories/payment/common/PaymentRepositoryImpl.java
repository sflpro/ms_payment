package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.services.payment.common.dto.PaymentSearchParameters;
import com.sfl.pms.services.payment.common.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/5/15
 * Time: 2:24 PM
 */
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRepositoryImpl.class);

    /* Constants */
    private static final String CRITERIA_AND = " and ";

    private static final String PARAMETER_NAME_PAYMENT_STATE = "paymentState";

    private static final String PARAMETER_NAME_PAYMENT_PROVIDER_TYPE = "paymentProviderType";

    private static final String PARAMETER_NAME_STORE_PAYMENT_METHOD = "storePaymentMethod";

    private static final String PARAMETER_NAME_CREATED_AFTER = "createdAfter";

    private static final String PARAMETER_NAME_CREATED_BEFORE = "createdBefore";

    /* Dependencies */
    @PersistenceContext
    private EntityManager entityManager;

    /* Constructors */
    public PaymentRepositoryImpl() {
        LOGGER.debug("Initializing payment repository");
    }

    @Nonnull
    @Override
    public Long getCustomersCountForPaymentSearchParameters(@Nonnull final PaymentSearchParameters parameters) {
        assertSearchParametersNotNull(parameters);
        LOGGER.debug("Executing customers count lookup request for payment search parameters, parameters - {}", parameters);
        // Build query
        final TypedQuery<Long> typedQuery = buildFindCustomersForSearchParametersTypedQuery(parameters, true, Long.class);
        // Execute query
        final Long customersCount = typedQuery.getSingleResult();
        LOGGER.debug("Successfully executed customers count lookup request for payment search parameters, parameters - {}", parameters);
        return customersCount;
    }

    @Nonnull
    @Override
    public List<Long> findCustomersForPaymentSearchParameters(@Nonnull final PaymentSearchParameters parameters, @Nonnull final long startFrom, @Nonnull final int maxCount) {
        assertSearchParametersNotNull(parameters);
        Assert.isTrue(maxCount > 0, "Max count should be positive integer");
        Assert.isTrue(startFrom >= 0, "Start from should be positive integer or zero");
        LOGGER.debug("Executing customer search request for payment search parameters, parameters - {}, startFrom - {} ,maxCount - {}", parameters, startFrom, maxCount);
        // Build query
        final TypedQuery<Long> typedQuery = buildFindCustomersForSearchParametersTypedQuery(parameters, false, Long.class);
        // Set pagination data
        typedQuery.setFirstResult((int) startFrom);
        typedQuery.setMaxResults(maxCount);
        // Execute query
        final List<Long> customersIds = typedQuery.getResultList();
        LOGGER.debug("Successfully executed customers lookup request for payment search parameters, parameters - {}, startFrom - {} ,maxCount - {}", parameters, startFrom, maxCount);
        return customersIds;
    }

    @Override
    public Payment findByIdWithWriteLockFlushedAndFreshData(@Nonnull final Long id) {
        Assert.notNull(id, "Payment id should not be null");
        LOGGER.debug("Loading payment with id - {} using pessimistic write lock.", id);
        entityManager.flush();
        final Payment payment = entityManager.find(Payment.class, id, LockModeType.PESSIMISTIC_WRITE);
        // Make sure to get latest version from database after acquiring lock
        if (payment != null) {
            entityManager.refresh(payment);
        }
        LOGGER.debug("Successfully retrieved payment with id - {} using pessimistic write lock. Payment - {}", id, payment);
        return payment;
    }

    /* Utility methods */
    private void assertSearchParametersNotNull(final PaymentSearchParameters parameters) {
        Assert.notNull(parameters, "Search parameters should not be null");
    }

    private <T> TypedQuery<T> buildFindCustomersForSearchParametersTypedQuery(final PaymentSearchParameters parameters, final boolean countQuery, final Class<T> queryResultType) {
        final String queryString = buildFindCustomersForSearchParametersQueryString(parameters, countQuery);
        // Create typed query
        final TypedQuery<T> typedQuery = entityManager.createQuery(queryString, queryResultType);
        if (parameters.getPaymentState() != null) {
            typedQuery.setParameter(PARAMETER_NAME_PAYMENT_STATE, parameters.getPaymentState());
        }
        if (parameters.getPaymentProviderType() != null) {
            typedQuery.setParameter(PARAMETER_NAME_PAYMENT_PROVIDER_TYPE, parameters.getPaymentProviderType());
        }
        if (parameters.getStorePaymentMethod() != null) {
            typedQuery.setParameter(PARAMETER_NAME_STORE_PAYMENT_METHOD, parameters.getStorePaymentMethod());
        }
        if (parameters.getCreatedAfterDate() != null) {
            typedQuery.setParameter(PARAMETER_NAME_CREATED_AFTER, parameters.getCreatedAfterDate());
        }
        if (parameters.getCreatedBeforeDate() != null) {
            typedQuery.setParameter(PARAMETER_NAME_CREATED_BEFORE, parameters.getCreatedBeforeDate());
        }
        return typedQuery;
    }

    private String buildFindCustomersForSearchParametersQueryString(final PaymentSearchParameters parameters, final boolean countQuery) {
        // Build query string
        final StringBuilder queryBuilder = new StringBuilder();
        if (countQuery) {
            queryBuilder.append(" select count(distinct pmt.customer.id) ");
        } else {
            queryBuilder.append(" select distinct pmt.customer.id ");
        }
        queryBuilder.append(" from Payment pmt ");
        // Join customer
        queryBuilder.append(" left join pmt.customer");
        String criteriaPrefix = " where ";
        // Add payment state criteria
        criteriaPrefix = appendPaymentStateCriteria(parameters, criteriaPrefix, queryBuilder);
        // Add payment provider type criteria
        criteriaPrefix = appendPaymentProviderTypeCriteria(parameters, criteriaPrefix, queryBuilder);
        // Add store payment method criteria
        criteriaPrefix = appendStorePaymentMethodCriteria(parameters, criteriaPrefix, queryBuilder);
        // Add created after
        criteriaPrefix = appendCreatedAfterDateCriteria(parameters, criteriaPrefix, queryBuilder);
        // Add created before
        criteriaPrefix = appendCreatedBeforeDateCriteria(parameters, criteriaPrefix, queryBuilder);
        // Append order by query
        if (!countQuery) {
            queryBuilder.append(" order by pmt.customer.id asc ");
        }
        // Return query
        return queryBuilder.toString();
    }

    private String appendPaymentStateCriteria(final PaymentSearchParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getPaymentState() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" pmt.lastState=:" + PARAMETER_NAME_PAYMENT_STATE + " ");
        }
        return updatedCriteriaPrefix;
    }

    private String appendPaymentProviderTypeCriteria(final PaymentSearchParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getPaymentProviderType() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" pmt.paymentProviderType=:" + PARAMETER_NAME_PAYMENT_PROVIDER_TYPE + " ");
        }
        return updatedCriteriaPrefix;
    }

    private String appendStorePaymentMethodCriteria(final PaymentSearchParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getStorePaymentMethod() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" pmt.storePaymentMethod=:" + PARAMETER_NAME_STORE_PAYMENT_METHOD + " ");
        }
        return updatedCriteriaPrefix;
    }

    private String appendCreatedAfterDateCriteria(final PaymentSearchParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getCreatedAfterDate() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" pmt.created>=:" + PARAMETER_NAME_CREATED_AFTER + " ");
        }
        return updatedCriteriaPrefix;
    }

    private String appendCreatedBeforeDateCriteria(final PaymentSearchParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getCreatedBeforeDate() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" pmt.created<=:" + PARAMETER_NAME_CREATED_BEFORE + " ");
        }
        return updatedCriteriaPrefix;
    }

    /* Properties getters and setters */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
