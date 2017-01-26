package com.sfl.pms.services.payment.customer.information.impl;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.exception.CustomerPaymentProviderInformationNotFoundForIdException;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:17 PM
 */
public abstract class AbstractCustomerPaymentProviderInformationServiceImplTest<T extends CustomerPaymentProviderInformation> extends AbstractServicesUnitTest {

    /* Constructors */
    public AbstractCustomerPaymentProviderInformationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerPaymentProviderInformationByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getCustomerPaymentProviderInformationById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentProviderInformationByIdWithNotExistingId() {
        // Test data
        final Long informationId = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(informationId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getCustomerPaymentProviderInformationById(informationId);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentProviderInformationNotFoundForIdException ex) {
            // Expected
            assertCustomerPaymentProviderInformationNotFoundException(ex, informationId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentProviderInformationById() {
        // Test data
        final Long informationId = 1l;
        final T information = getInstance();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(informationId))).andReturn(information).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getCustomerPaymentProviderInformationById(informationId);
        assertNotNull(result);
        assertEquals(information, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    protected void assertCustomerPaymentProviderInformationNotFoundException(final CustomerPaymentProviderInformationNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentProviderInformationService<T> getService();

    protected abstract AbstractCustomerPaymentProviderInformationRepository<T> getRepository();

    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();
}
