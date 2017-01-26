package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodNotFoundForUuIdException;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:43 PM
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCustomerPaymentMethodServiceImplTest<T extends CustomerPaymentMethod> extends AbstractServicesUnitTest {

    /* Mocks */
    @Mock
    private CustomerService customerService;

    /* Constructors */
    public AbstractCustomerPaymentMethodServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerPaymentMethodByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getCustomerPaymentMethodByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodByUuIdWithNotExistingUuId() {
        // Expected
        final String uuId = "not_existing_uuid";
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(uuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getCustomerPaymentMethodByUuId(uuId);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodNotFoundForUuIdException ex) {
            // Expected
            assertCustomerPaymentMethodNotFoundForUuIdException(ex, uuId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodByUuId() {
        // Expected
        final T paymentMethod = getInstance();
        final String uuId = paymentMethod.getUuId();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(uuId))).andReturn(paymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getCustomerPaymentMethodByUuId(uuId);
        assertEquals(paymentMethod, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodsForCustomerWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodsForCustomer(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodsForCustomer() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final List<T> paymentMethods = Arrays.asList(getInstance(), getInstance(), getInstance());
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(getRepository().findByCustomer(eq(customer))).andReturn(paymentMethods).once();
        // Replay
        replayAll();
        // Run test scenario
        final List<T> result = getService().getPaymentMethodsForCustomer(customerId);
        assertEquals(paymentMethods, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getCustomerPaymentMethodById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodByIdWithNotExistingId() {
        // Test data
        final Long paymentMethodId = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(paymentMethodId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getCustomerPaymentMethodById(paymentMethodId);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodNotFoundForIdException ex) {
            // Expected
            assertCustomerPaymentMethodNotFoundException(ex, paymentMethodId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodById() {
        // Test data
        final Long paymentMethodId = 1l;
        final T paymentMethod = getInstance();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(paymentMethodId))).andReturn(paymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getCustomerPaymentMethodById(paymentMethodId);
        assertEquals(paymentMethod, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testRemoveCustomerPaymentMethodInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().removeCustomerPaymentMethod(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testRemoveCustomerPaymentMethodWithNonExistingId() {
        // Test data
        final Long paymentMethodId = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(paymentMethodId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().removeCustomerPaymentMethod(paymentMethodId);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodNotFoundForIdException ex) {
            // Expected
            assertCustomerPaymentMethodNotFoundException(ex, paymentMethodId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testRemoveCustomerPaymentMethod() {
        // Test data
        final Long paymentMethodId = 1l;
        final T paymentMethod = getInstance();
        assertNull(paymentMethod.getRemoved());
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(paymentMethodId))).andReturn(paymentMethod).once();
        expect(getRepository().save(isA(getInstanceClass()))).andAnswer(new IAnswer<T>() {
            @Override
            public T answer() throws Throwable {
                return (T) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().removeCustomerPaymentMethod(paymentMethodId);
        assertNotNull(result);
        assertNotNull(result.getRemoved());
        Assert.assertEquals(result.getRemoved(), result.getUpdated());
        // Verify
        verifyAll();
    }

    /* Abstract methods */
    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();

    protected abstract AbstractCustomerPaymentMethodService<T> getService();

    protected abstract AbstractCustomerPaymentMethodRepository<T> getRepository();

    /* Utility methods */
    private void assertCustomerPaymentMethodNotFoundException(final CustomerPaymentMethodNotFoundForIdException ex, final Long paymentMethodId) {
        assertEquals(paymentMethodId, ex.getId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    private void assertCustomerPaymentMethodNotFoundForUuIdException(final CustomerPaymentMethodNotFoundForUuIdException ex, final String paymentMethodUuId) {
        assertEquals(paymentMethodUuId, ex.getUuId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    public CustomerService getCustomerService() {
        return customerService;
    }
}
