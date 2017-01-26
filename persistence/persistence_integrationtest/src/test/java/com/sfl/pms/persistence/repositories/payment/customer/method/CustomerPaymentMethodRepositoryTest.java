package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.common.sortorder.SortDirection;
import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 3/5/15
 * Time: 3:29 PM
 */
public class CustomerPaymentMethodRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    /* Constructors */
    public CustomerPaymentMethodRepositoryTest() {
    }


    /* Test methods */
    @Test
    public void testGetCustomerPaymentMethodsCount() {
        /* Create lookup parameters with default values */
        CustomerPaymentMethodLookupParameters searchParameters = createLookupParameters();
        /* Execute count query */
        Long result = customerPaymentMethodRepository.getCustomerPaymentMethodsCount(searchParameters);
        assertNotNull(result);
        assertEquals(Long.valueOf(0l), result);
        /* Add criteria */
        appendLookupParameters(searchParameters);
        /* Execute count query */
        result = customerPaymentMethodRepository.getCustomerPaymentMethodsCount(searchParameters);
        assertNotNull(result);
        assertEquals(Long.valueOf(0l), result);
    }

    @Test
    public void testFindCustomerPaymentMethods() {
        // Positive values
        CustomerPaymentMethodLookupParameters searchParameters = createLookupParameters();
        // Execute count query
        List<CustomerPaymentMethod> result = customerPaymentMethodRepository.findCustomerPaymentMethods(searchParameters, createSortOrderSpecification(), 0, Integer.MAX_VALUE);
        assertNotNull(result);
        assertEquals(0, result.size());
        /* Add criteria */
        appendLookupParameters(searchParameters);
        /* Execute find query */
        result = customerPaymentMethodRepository.findCustomerPaymentMethods(searchParameters, createSortOrderSpecification(), 0, Integer.MAX_VALUE);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /* Utility methods */
    private CustomerPaymentMethodLookupParameters createLookupParameters() {
        return new CustomerPaymentMethodLookupParameters();
    }

    private void appendLookupParameters(final CustomerPaymentMethodLookupParameters searchParameters) {
        searchParameters.setExcludeRemoved(true);
        searchParameters.setType(CustomerPaymentMethodType.CARD);
        searchParameters.setCustomerId(1l);
    }

    private static SortOrderSpecification<CustomerPaymentMethodSortColumn> createSortOrderSpecification() {
        return new SortOrderSpecification<>(CustomerPaymentMethodSortColumn.TYPE, SortDirection.DESCENDING);
    }
}
