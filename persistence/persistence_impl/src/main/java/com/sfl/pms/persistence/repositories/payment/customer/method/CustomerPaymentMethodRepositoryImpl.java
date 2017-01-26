package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 3/5/15
 * Time: 2:59 PM
 */
public class CustomerPaymentMethodRepositoryImpl implements CustomerPaymentMethodRepositoryCustom {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodRepositoryImpl.class);

    /* Constants */
    private static final String CRITERIA_AND = " and ";

    private static final String PARAMETER_NAME_TYPE = "type";

    private static final String PARAMETER_NAME_CUSTOMER = "customerId";

    /* Dependencies */
    @PersistenceContext
    private EntityManager entityManager;

    /* Constructors */
    public CustomerPaymentMethodRepositoryImpl() {
    }


    @Nonnull
    @Override
    public Long getCustomerPaymentMethodsCount(@Nonnull final CustomerPaymentMethodLookupParameters parameters) {
        LOGGER.debug("Executing customer payment methods count lookup request, parameters - {}", parameters);
        /* Build query */
        final TypedQuery<Long> typedQuery = buildFindCustomerPaymentMethodsTypedQuery(parameters, null, true, Long.class);
        /* Execute query */
        final Long customerPaymentMethodsCount = typedQuery.getSingleResult();
        LOGGER.debug("Successfully executed customer payment methods count lookup request, parameters - {}", parameters);
        return customerPaymentMethodsCount;
    }

    @Nonnull
    @Override
    public List<CustomerPaymentMethod> findCustomerPaymentMethods(@Nonnull final CustomerPaymentMethodLookupParameters parameters, @Nonnull final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, long startFrom, int maxCount) {
        LOGGER.debug("Perform customer payment methods search request with parameters - {}, start from - {}, maxCount", parameters, startFrom, maxCount);
        Assert.isTrue(maxCount >= 0, "Max count should be positive integer or zero");
        Assert.isTrue(startFrom >= 0, "Start from should be positive integer or zero");
        /* Build query */
        final TypedQuery<CustomerPaymentMethod> typedQuery = buildFindCustomerPaymentMethodsTypedQuery(parameters, sortOrderSpecification, false, CustomerPaymentMethod.class);
        /* Set pagination data */
        typedQuery.setFirstResult((int) startFrom);
        typedQuery.setMaxResults(maxCount);
        /* Execute query */
        final List<CustomerPaymentMethod> customerPaymentMethods = typedQuery.getResultList();
        LOGGER.debug("Successfully executed customer payment methods lookup request, parameters - {}, startFrom - {} ,maxCount - {}", parameters, startFrom, maxCount);
        return customerPaymentMethods;
    }

    /* Utility methods */
    private <T> TypedQuery<T> buildFindCustomerPaymentMethodsTypedQuery(final CustomerPaymentMethodLookupParameters parameters, final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, final boolean countQuery,
                                                                        final Class<T> queryResultType) {
        final String queryString = buildFindCustomerPaymentMethodsQueryString(parameters, sortOrderSpecification, countQuery);
        // Create typed query
        final TypedQuery<T> typedQuery = entityManager.createQuery(queryString, queryResultType);
        // Set parameters
        if (parameters.getType() != null) {
            typedQuery.setParameter(PARAMETER_NAME_TYPE, parameters.getType());
        }
        if (parameters.getCustomerId() != null) {
            typedQuery.setParameter(PARAMETER_NAME_CUSTOMER, parameters.getCustomerId());
        }
        return typedQuery;
    }

    private String buildFindCustomerPaymentMethodsQueryString(final CustomerPaymentMethodLookupParameters parameters, final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, final boolean countQuery) {
        /* Build query string */
        final boolean hasCustomerRelatedCriteria = (parameters.getCustomerId() != null);
        final StringBuilder queryBuilder = new StringBuilder();
        if (countQuery) {
            queryBuilder.append(" select count(cpm) ");
        } else {
            queryBuilder.append(" select cpm ");
        }
        queryBuilder.append(" from CustomerPaymentMethod cpm ");
        /* Join customer */
        if (hasCustomerRelatedCriteria) {
            queryBuilder.append(" left join cpm.customer ");
        }
        String criteriaPrefix = " where ";
        /* Append customer criteria */
        criteriaPrefix = appendCustomerCriteria(parameters, criteriaPrefix, queryBuilder);
        /* Append customer payment method type criteria */
        criteriaPrefix = appendTypeCriteria(parameters, criteriaPrefix, queryBuilder);
        /* Append exclude removed criteria */
        criteriaPrefix = appendExcludeRemovedCriteria(parameters, criteriaPrefix, queryBuilder);
        /* Append order by query */
        if (!countQuery) {
            appendOrderByQuery(sortOrderSpecification, queryBuilder);
        }
        /* Return query */
        return queryBuilder.toString();
    }


    private String appendCustomerCriteria(final CustomerPaymentMethodLookupParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getCustomerId() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" cpm.customer.id=:" + PARAMETER_NAME_CUSTOMER);
        }
        return updatedCriteriaPrefix;
    }

    private String appendTypeCriteria(final CustomerPaymentMethodLookupParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getType() != null) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" cpm.type=:" + PARAMETER_NAME_TYPE + " ");
        }
        return updatedCriteriaPrefix;
    }

    private void appendOrderByQuery(final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, final StringBuilder queryBuilder) {
        if (sortOrderSpecification != null) {
            queryBuilder.append(" order by ")
                    .append(sortOrderSpecification.getSortColumn().getSpecification())
                    .append(" ")
                    .append(sortOrderSpecification.getSortDirection().getShortName())
                    .append(" , cpm.created desc ");
        } else {
            queryBuilder.append(" order by cpm.created desc ");
        }
    }

    private String appendExcludeRemovedCriteria(final CustomerPaymentMethodLookupParameters parameters, final String criteriaPrefix, final StringBuilder queryBuilder) {
        String updatedCriteriaPrefix = criteriaPrefix;
        if (parameters.getExcludeRemoved() != null && parameters.getExcludeRemoved()) {
            queryBuilder.append(criteriaPrefix);
            updatedCriteriaPrefix = CRITERIA_AND;
            queryBuilder.append(" cpm.removed is null ");
        }
        return updatedCriteriaPrefix;
    }
}
