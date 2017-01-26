package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodRepository;
import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:37 PM
 */
@Service
public class CustomerPaymentMethodServiceImpl extends AbstractCustomerPaymentMethodServiceImpl<CustomerPaymentMethod> implements CustomerPaymentMethodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    /* Constructors */
    public CustomerPaymentMethodServiceImpl() {
        LOGGER.debug("Initializing customer payment method service");
    }


    /* Public methods */
    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentMethod> getCustomerPaymentMethodsForSearchParameters(@Nonnull final CustomerPaymentMethodLookupParameters parameters, @Nonnull final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, @Nonnull final Long startFrom, @Nonnull final Integer maxCount) {
        assertCustomerPaymentMethodsLookupParameters(parameters);
        Assert.notNull(sortOrderSpecification, "Customer payment methods search sort order specification should not be null");
        Assert.notNull(startFrom, "Customer payment methods search start from should not be null");
        Assert.notNull(maxCount, "Customer payment methods search max count should not be null");
        Assert.isTrue(maxCount >= 0, "Customer payment methods max count should be positive integer or zero");
        Assert.isTrue(startFrom >= 0, "Customer payment methods start from should be positive integer or zero");
        LOGGER.debug("Loading customer payment methods for search parameters - {}, startFrom - {}, maxCount - {}", parameters, startFrom, maxCount);
        final List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodRepository.findCustomerPaymentMethods(parameters, sortOrderSpecification, startFrom, maxCount);
        LOGGER.debug("{} customer payment methods were returned for search parameters - {}, startFrom - {}, maxCount - {}", customerPaymentMethods.size(), parameters, startFrom, maxCount);
        return customerPaymentMethods;
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public Long getCustomerPaymentMethodsCountForSearchParameters(@Nonnull final CustomerPaymentMethodLookupParameters parameters) {
        assertCustomerPaymentMethodsLookupParameters(parameters);
        LOGGER.debug("Loading customer payment methods count for search parameters - {}", parameters);
        final Long count = customerPaymentMethodRepository.getCustomerPaymentMethodsCount(parameters);
        LOGGER.debug("{} customer payment methods were returned for search parameters - {}", count, parameters);
        return count;
    }

    /* Utility methods */
    private void assertCustomerPaymentMethodsLookupParameters(final CustomerPaymentMethodLookupParameters parameters) {
        Assert.notNull(parameters, "Customer payment methods search parameters should not be null");
    }

    /* Abstract method overrides */
    @Override
    protected AbstractCustomerPaymentMethodRepository<CustomerPaymentMethod> getRepository() {
        return customerPaymentMethodRepository;
    }

    @Override
    protected Class<CustomerPaymentMethod> getInstanceClass() {
        return CustomerPaymentMethod.class;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodRepository getCustomerPaymentMethodRepository() {
        return customerPaymentMethodRepository;
    }

    public void setCustomerPaymentMethodRepository(final CustomerPaymentMethodRepository customerPaymentMethodRepository) {
        this.customerPaymentMethodRepository = customerPaymentMethodRepository;
    }
}
