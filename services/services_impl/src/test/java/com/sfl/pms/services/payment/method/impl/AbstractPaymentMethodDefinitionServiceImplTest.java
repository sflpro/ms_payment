package com.sfl.pms.services.payment.method.impl;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionNotFoundForIdException;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:08 PM
 */
public abstract class AbstractPaymentMethodDefinitionServiceImplTest<T extends PaymentMethodDefinition> extends AbstractServicesUnitTest {

    /* Constructors */
    public AbstractPaymentMethodDefinitionServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentMethodDefinitionByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodDefinitionById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodDefinitionByIdWithNotExistingId() {
        // Test data
        final Long definitionId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(definitionId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodDefinitionById(definitionId);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionNotFoundForIdException ex) {
            // Expected
            assertPaymentMethodDefinitionNotFoundForIdException(ex, definitionId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodDefinitionById() {
        final Long definitionId = 1L;
        final T paymentMethodDefinition = getInstance();
        paymentMethodDefinition.setId(definitionId);
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(definitionId))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentMethodDefinitionById(definitionId);
        assertEquals(paymentMethodDefinition, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    protected void assertPaymentMethodDefinitionNotFoundForIdException(final PaymentMethodDefinitionNotFoundForIdException ex, final Long id) {
        assertEquals(getInstanceClass(), ex.getEntityClass());
        assertEquals(id, ex.getId());
    }

    protected void assertPaymentMethodDefinitionAlreadyExistsException(final PaymentMethodDefinitionAlreadyExistsException ex, final Long existingPaymentMethodDefinitionId) {
        assertEquals(existingPaymentMethodDefinitionId, ex.getExistingPaymentMethodDefinitionId());
    }

    /* Abstract methods */
    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();

    protected abstract AbstractPaymentMethodDefinitionServiceImpl<T> getService();

    protected abstract AbstractPaymentMethodDefinitionRepository<T> getRepository();
}
