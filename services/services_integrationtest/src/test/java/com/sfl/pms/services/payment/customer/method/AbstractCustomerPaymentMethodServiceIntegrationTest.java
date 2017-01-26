package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 5:23 PM
 */
@Ignore
public abstract class AbstractCustomerPaymentMethodServiceIntegrationTest<T extends CustomerPaymentMethod> extends AbstractServiceIntegrationTest {

    /* Constructors */
    public AbstractCustomerPaymentMethodServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerPaymentMethodById() {
        // Prepare data
        final T customerPaymentMethod = getInstance();
        flushAndClear();
        // Reload and assert again
        final T result = getService().getCustomerPaymentMethodById(customerPaymentMethod.getId());
        assertEquals(customerPaymentMethod, result);
    }

    @Test
    public void testRemoveCustomerPaymentMethod() {
        /* Test data */
        T customerPaymentMethod = getInstance();
        assertNull(customerPaymentMethod.getRemoved());
        flushAndClear();
        /* Remove payment method */
        customerPaymentMethod = getService().removeCustomerPaymentMethod(customerPaymentMethod.getId());
        assertCustomerPaymentMethodRemoved(customerPaymentMethod);
        /* Flush, reload and assert again */
        flushAndClear();
        customerPaymentMethod = getService().getCustomerPaymentMethodById(customerPaymentMethod.getId());
        assertCustomerPaymentMethodRemoved(customerPaymentMethod);
    }

    @Test
    public void testGetCustomerPaymentMethodByUuId() {
        // Prepare data
        final T customerPaymentMethod = getInstance();
        flushAndClear();
        // Reload and assert again
        final T result = getService().getCustomerPaymentMethodByUuId(customerPaymentMethod.getUuId());
        assertEquals(customerPaymentMethod, result);
    }

    @Test
    public void testGetPaymentMethodsForCustomer() {
        final Customer ownerCustomer = getServicesTestHelper().createCustomer();
        flushAndClear();
        // Prepare data
        final List<T> initialPaymentMethods = getService().getPaymentMethodsForCustomer(ownerCustomer.getId());
        // Create new payment method for customer
        final T customerPaymentMethod = getInstance(ownerCustomer);
        // Check again
        List<T> updatedPaymentMethods = getService().getPaymentMethodsForCustomer(ownerCustomer.getId());
        assertPaymentMethodsAfterAddingNewOne(customerPaymentMethod, initialPaymentMethods, updatedPaymentMethods);
        // Flush reload and assert again
        flushAndClear();
        updatedPaymentMethods = getService().getPaymentMethodsForCustomer(ownerCustomer.getId());
        assertPaymentMethodsAfterAddingNewOne(customerPaymentMethod, initialPaymentMethods, updatedPaymentMethods);
        // Create second customer to check that payment methods are indeed filtered
        final Customer secondCustomer = getServicesTestHelper().createCustomer("second.customer@gmail.com");
        flushAndClear();
        // Check that second customer does not have any payment methods, to be sure that payment methods of different customers does not interfere
        final List<T> paymentMethods = getService().getPaymentMethodsForCustomer(secondCustomer.getId());
        assertEquals(0, paymentMethods.size());
    }


    /* Utility methods */
    private void assertPaymentMethodsAfterAddingNewOne(final T createdPaymentMethod, final List<T> paymentMethodsBeforeCreation, final List<T> paymentMethodsAfterCreation) {
        assertNotNull(paymentMethodsBeforeCreation);
        assertNotNull(paymentMethodsAfterCreation);
        assertEquals(paymentMethodsBeforeCreation.size() + 1, paymentMethodsAfterCreation.size());
        // Check if created item is in list
        final List<T> filteredPaymentMethods = paymentMethodsAfterCreation.stream().filter(paymentMethod -> {
            return paymentMethod.getId().equals(createdPaymentMethod.getId());
        }).collect(Collectors.toCollection(ArrayList<T>::new));
        assertEquals(1, filteredPaymentMethods.size());
    }

    protected void assertCustomerPaymentMethodRemoved(final T customerPaymentMethod) {
        assertNotNull(customerPaymentMethod);
        assertNotNull(customerPaymentMethod.getRemoved());
        assertEquals(customerPaymentMethod.getRemoved(), customerPaymentMethod.getUpdated());
    }

    private T getInstance() {
        return getInstance(getServicesTestHelper().createCustomer());
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodService<T> getService();

    protected abstract T getInstance(final Customer customer);
}
