package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.common.sortorder.SortDirection;
import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 5:35 PM
 */
public class CustomerPaymentMethodServiceIntegrationTest extends AbstractCustomerPaymentMethodServiceIntegrationTest<CustomerPaymentMethod> {

    /* Constants */
    private static final Long RESULT_LIST_START_FROM = 0l;

    private static final Integer RESULT_LIST_MAX_RESULTS = 10;

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public CustomerPaymentMethodServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerPaymentMethodsCountForSearchParametersWithCustomerFilter() {
        /* Create test data */
        final Customer firstCustomer = getServicesTestHelper().createCustomer("mher.sargsyan@sflpro.com");
        final Customer secondCustomer = getServicesTestHelper().createCustomer("vazgen.danielyan@sflpro.com");
        /* Create payment method for first customer */
        final CustomerPaymentMethod customerPaymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(firstCustomer);
        flushAndClear();
        /* Create lookup parameters */
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        /* Set second customer id as lookup parameter */
        lookupParameters.setCustomerId(secondCustomer.getId());
        Long customerPaymentMethodsCount = customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(lookupParameters);
        /* number of payment methods should be 0 */
        assertNotNull(customerPaymentMethodsCount);
        assertTrue(customerPaymentMethodsCount == 0);
        /* Flush, clear and reload customer payment methods for first customer */
        flushAndClear();
        lookupParameters.setCustomerId(firstCustomer.getId());
        customerPaymentMethodsCount = customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(lookupParameters);
        assertNotNull(customerPaymentMethodsCount);
        assertTrue(customerPaymentMethodsCount == 1);
    }

    @Test
    public void testGetCarWashAppointmentsForSearchParametersWithCustomerFilter() {
        /* Create test data */
        final Customer firstCustomer = getServicesTestHelper().createCustomer("mher.sargsyan@sflpro.com");
        final Customer secondCustomer = getServicesTestHelper().createCustomer("vazgen.danielyan@sflpro.com");
        flushAndClear();
        /* Create payment method for first customer */
        CustomerPaymentMethod customerPaymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(firstCustomer);
        flushAndClear();
        /* Create lookup parameters */
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        /* Set second customer id as lookup parameter */
        lookupParameters.setCustomerId(secondCustomer.getId());
        List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(lookupParameters, getSortOrderSpecification(), RESULT_LIST_START_FROM, RESULT_LIST_MAX_RESULTS);
        /* List of payment methods should be empty */
        assertNotNull(customerPaymentMethods);
        assertEquals(customerPaymentMethods.size(), 0);
        /* Flush, clear and reload payment method for first customer */
        lookupParameters.setCustomerId(firstCustomer.getId());
        customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(lookupParameters, getSortOrderSpecification(), RESULT_LIST_START_FROM, RESULT_LIST_MAX_RESULTS);
        assertNotNull(customerPaymentMethods);
        assertEquals(customerPaymentMethods.size(), 1);
        assertEquals(customerPaymentMethods.get(0), customerPaymentMethod);
    }

    @Test
    public void testGetCustomerPaymentMethodsForSearchParametersWithTypeFilter() {
        /* Create test data */
        final Customer firstCustomer = getServicesTestHelper().createCustomer("mher.sargsyan@sflpro.com");
        /* Create payment method for first customer */
        final CustomerPaymentMethod customerPaymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(firstCustomer);
        flushAndClear();
        /* Create lookup parameters */
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        /* Set type filter lookup parameter */
        lookupParameters.setType(CustomerPaymentMethodType.CARD);
        List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(lookupParameters, getSortOrderSpecification(), RESULT_LIST_START_FROM, RESULT_LIST_MAX_RESULTS);
        /* number of payment methods should be 1 */
        assertNotNull(customerPaymentMethods);
        assertEquals(customerPaymentMethods.size(), 1);
        assertEquals(customerPaymentMethods.get(0), customerPaymentMethod);
    }

    @Test
    public void testGetCustomerPaymentMethodsCountForSearchParametersWithTypeFilter() {
        /* Create test data */
        final Customer firstCustomer = getServicesTestHelper().createCustomer("mher.sargsyan@sflpro.com");
        /* Create payment method for first customer */
        final CustomerPaymentMethod customerPaymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(firstCustomer);
        flushAndClear();
        /* Create lookup parameters */
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        /* Set type filter lookup parameter */
        lookupParameters.setType(CustomerPaymentMethodType.CARD);
        Long customerPaymentMethodsCount = customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(lookupParameters);
        /* number of payment methods should be 1 */
        assertNotNull(customerPaymentMethodsCount);
        assertTrue(customerPaymentMethodsCount == 1);
    }

    @Test
    public void testGetCustomerPaymentMethodsForSearchParametersExcludeRemoved() {
        /* Create test data */
        final Customer firstCustomer = getServicesTestHelper().createCustomer("mher.sargsyan@sflpro.com");
        /* Create payment method for first customer */
        final CustomerPaymentMethod customerPaymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(firstCustomer);
        flushAndClear();
        /* Create lookup parameters */
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        /* Set exclude removed filter lookup parameter */
        lookupParameters.setExcludeRemoved(true);
        List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(lookupParameters, getSortOrderSpecification(), RESULT_LIST_START_FROM, RESULT_LIST_MAX_RESULTS);
        /* number of payment methods should be 1 */
        assertNotNull(customerPaymentMethods);
        assertTrue(customerPaymentMethods.size() == 1);
        assertEquals(customerPaymentMethods.get(0), customerPaymentMethod);
        /* Flush, clear and remove payment method */
        flushAndClear();
        final CustomerPaymentMethod removedCustomerPaymentMethod = customerPaymentMethodService.removeCustomerPaymentMethod(customerPaymentMethod.getId());
        assertNotNull(removedCustomerPaymentMethod);
        assertNotNull(removedCustomerPaymentMethod.getRemoved());
        assertEquals(removedCustomerPaymentMethod.getRemoved(), removedCustomerPaymentMethod.getUpdated());
        /* Flush, clear and reload payment methods */
        flushAndClear();
        customerPaymentMethods = customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(lookupParameters, getSortOrderSpecification(), RESULT_LIST_START_FROM, RESULT_LIST_MAX_RESULTS);
        assertNotNull(customerPaymentMethods);
        assertTrue(customerPaymentMethods.size() == 0);
    }

    @Test
    public void testGetCustomerPaymentMethodsCountForSearchParametersExcludeRemoved() {
        /* Create test data */
        final Customer firstCustomer = getServicesTestHelper().createCustomer("mher.sargsyan@sflpro.com");
        /* Create payment method for first customer */
        final CustomerPaymentMethod customerPaymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(firstCustomer);
        flushAndClear();
        /* Create lookup parameters */
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        /* Set exclude removed filter lookup parameter */
        lookupParameters.setExcludeRemoved(true);
        Long customerPaymentMethodsCount = customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(lookupParameters);
        /* number of payment methods should be 1 */
        assertNotNull(customerPaymentMethodsCount);
        assertTrue(customerPaymentMethodsCount == 1);
        /* Flush, clear and remove payment method */
        flushAndClear();
        final CustomerPaymentMethod removedCustomerPaymentMethod = customerPaymentMethodService.removeCustomerPaymentMethod(customerPaymentMethod.getId());
        assertNotNull(removedCustomerPaymentMethod);
        assertNotNull(removedCustomerPaymentMethod.getRemoved());
        assertEquals(removedCustomerPaymentMethod.getRemoved(), removedCustomerPaymentMethod.getUpdated());
        /* Flush, clear and reload payment methods */
        flushAndClear();
        customerPaymentMethodsCount = customerPaymentMethodService.getCustomerPaymentMethodsCountForSearchParameters(lookupParameters);
        assertNotNull(customerPaymentMethodsCount);
        /* number of payment methods should be 0 */
        assertTrue(customerPaymentMethodsCount == 0);
    }

    /* Utility methods */
    private SortOrderSpecification<CustomerPaymentMethodSortColumn> getSortOrderSpecification() {
        return new SortOrderSpecification<>(CustomerPaymentMethodSortColumn.TYPE, SortDirection.DESCENDING);
    }

    /* Abstract method overrides */
    @Override
    protected AbstractCustomerPaymentMethodService<CustomerPaymentMethod> getService() {
        return customerPaymentMethodService;
    }

    @Override
    protected CustomerPaymentMethod getInstance(final Customer customer) {
        return getServicesTestHelper().createCustomerCardPaymentMethod(customer);
    }
}
