package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodRepository;
import com.sfl.pms.services.common.sortorder.SortDirection;
import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:46 PM
 */
public class CustomerPaymentMethodServiceImplTest extends AbstractCustomerPaymentMethodServiceImplTest<CustomerPaymentMethod> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodServiceImpl customerPaymentMethodService = new CustomerPaymentMethodServiceImpl();

    @Mock
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    /* Constructors */
    public CustomerPaymentMethodServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerPaymentMethodsForSearchParametersWithInvalidArguments() {
        /* Test data */
        final CustomerPaymentMethodLookupParameters customerPaymentMethodLookupParameters = new CustomerPaymentMethodLookupParameters();
        final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification = getSortOrderSpecification();
        final int maxResults = 10;
        final long startFrom = 0L;
        /* Reset mocks */
        resetAll();
        /* Register expectations */
        /* Replay mocks */
        replayAll();
        /* Test scenarios */
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(null, sortOrderSpecification, startFrom, maxResults);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            //Exception
        }
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(customerPaymentMethodLookupParameters, null, startFrom, maxResults);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            //Exception
        }
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(customerPaymentMethodLookupParameters, sortOrderSpecification, null, maxResults);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            //Exception
        }
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(customerPaymentMethodLookupParameters, sortOrderSpecification, startFrom, null);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            //Exception
        }
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(customerPaymentMethodLookupParameters, sortOrderSpecification, startFrom, -1);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            //Exception
        }
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(customerPaymentMethodLookupParameters, sortOrderSpecification, -1l, maxResults);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            //Exception
        }
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodsForSearchParameters() {
        /* Test data */
        final CustomerPaymentMethodLookupParameters customerPaymentMethodLookupParameters = new CustomerPaymentMethodLookupParameters();
        final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification = getSortOrderSpecification();
        final int maxResults = 10;
        final long startFrom = 0L;
        /* Reset mocks */
        resetAll();
        /* Register expectations */
        expect(customerPaymentMethodRepository.findCustomerPaymentMethods(eq(customerPaymentMethodLookupParameters), eq(sortOrderSpecification), eq(startFrom), eq(maxResults))).andReturn(new ArrayList<>()).once();
        /* Replay mocks */
        replayAll();
        /* Test scenarios */
        final List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(customerPaymentMethodLookupParameters, sortOrderSpecification, startFrom, maxResults);
        assertNotNull(customerPaymentMethods);
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodsCountForSearchParametersWithInvalidArguments() {
        /* Reset mocks */
        resetAll();
        /* Replay mocks */
        replayAll();
        /* Test scenarios */
        try {
            customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodsCountForSearchParameters() {
        /* Test data */
        final CustomerPaymentMethodLookupParameters parameters = new CustomerPaymentMethodLookupParameters();
        final Long count = 10l;
        /* Reset mocks */
        resetAll();
        /* Register expectations */
        expect(customerPaymentMethodRepository.getCustomerPaymentMethodsCount(eq(parameters))).andReturn(count).once();
        /* Replay mocks */
        replayAll();
        /* Test scenarios */
        final Long result = customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(parameters);
        assertNotNull(result);
        assertEquals(result, count);
        verifyAll();
    }

    /* Utility methods */
    private SortOrderSpecification<CustomerPaymentMethodSortColumn> getSortOrderSpecification() {
        return new SortOrderSpecification<>(CustomerPaymentMethodSortColumn.TYPE, SortDirection.ASCENDING);
    }

    /* Abstract method overrides */
    @Override
    protected CustomerPaymentMethod getInstance() {
        return getServicesImplTestHelper().createCustomerCardPaymentMethod();
    }

    @Override
    protected Class<CustomerPaymentMethod> getInstanceClass() {
        return CustomerPaymentMethod.class;
    }

    @Override
    protected AbstractCustomerPaymentMethodService<CustomerPaymentMethod> getService() {
        return customerPaymentMethodService;
    }

    @Override
    protected AbstractCustomerPaymentMethodRepository<CustomerPaymentMethod> getRepository() {
        return customerPaymentMethodRepository;
    }

}
