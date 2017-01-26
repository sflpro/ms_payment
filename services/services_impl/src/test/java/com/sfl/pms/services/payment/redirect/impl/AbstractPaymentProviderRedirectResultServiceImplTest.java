package com.sfl.pms.services.payment.redirect.impl;

import com.sfl.pms.persistence.repositories.payment.redirect.AbstractPaymentProviderRedirectResultRepository;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.redirect.AbstractPaymentProviderRedirectResultService;
import com.sfl.pms.services.payment.redirect.exception.PaymentProviderRedirectResultNotFoundForIdException;
import com.sfl.pms.services.payment.redirect.exception.PaymentProviderRedirectResultNotFoundForUuidException;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:51 PM
 */
public abstract class AbstractPaymentProviderRedirectResultServiceImplTest<T extends PaymentProviderRedirectResult> extends AbstractServicesUnitTest {

    /* Mocks */
    @Mock
    private PaymentService paymentService;

    /* Constructors */
    public AbstractPaymentProviderRedirectResultServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentForRedirectResultWithInvalidArguments() {
        // Test data
        final Long redirectResultId = 1L;
        final Long paymentId = 2L;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentForRedirectResult(null, paymentId);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            getService().updatePaymentForRedirectResult(redirectResultId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentForRedirectResultWithNotExistingRedirectResultId() {
        // Test data
        final Long redirectResultId = 1L;
        final Long paymentId = 2L;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(redirectResultId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentForRedirectResult(redirectResultId, paymentId);
            fail("Exception should be thrown");
        } catch (final PaymentProviderRedirectResultNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderRedirectResultNotFoundForIdException(ex, redirectResultId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentForRedirectResult() {
        // Test data
        final Long redirectResultId = 1L;
        final T redirectResult = getInstance();
        redirectResult.setId(redirectResultId);
        final Long paymentId = 2L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(redirectResultId))).andReturn(redirectResult).once();
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).once();
        expect(getRepository().save(isA(getInstanceClass()))).andAnswer(() -> (T) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().updatePaymentForRedirectResult(redirectResultId, paymentId);
        assertEquals(payment, result.getPayment());
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderRedirectResultByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderRedirectResultById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderRedirectResultByIdWithNotExistingId() {
        // Test data
        final Long redirectResultId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(redirectResultId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderRedirectResultById(redirectResultId);
            fail("Exception should be thrown");
        } catch (final PaymentProviderRedirectResultNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderRedirectResultNotFoundForIdException(ex, redirectResultId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderRedirectResultById() {
        // Test data
        final Long redirectResultId = 1L;
        final T redirectResult = getInstance();
        redirectResult.setId(redirectResultId);
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(redirectResultId))).andReturn(redirectResult).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentProviderRedirectResultById(redirectResultId);
        assertNotNull(result);
        assertEquals(redirectResult, result);
        // Verify
        verifyAll();
    }

    public void testGetPaymentProviderRedirectResultByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderRedirectResultByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderRedirectResultByUuIdWithNotExistingId() {
        // Test data
        final String redirectResultUuid = "jflugu9hgvgvhvcfwelerbgrel";
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(redirectResultUuid))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderRedirectResultByUuId(redirectResultUuid);
            fail("Exception should be thrown");
        } catch (final PaymentProviderRedirectResultNotFoundForUuidException ex) {
            // Expected
            assertPaymentProviderRedirectResultNotFoundForUuIdException(ex, redirectResultUuid);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderRedirectResultByUuId() {
        // Test data
        final String redirectResultUuid = "jflugu9hgvgvhvcfwelerbgrel";
        final T redirectResult = getInstance();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(redirectResultUuid))).andReturn(redirectResult).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentProviderRedirectResultByUuId(redirectResultUuid);
        assertNotNull(result);
        assertEquals(redirectResult, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    protected void assertPaymentProviderRedirectResultNotFoundForIdException(final PaymentProviderRedirectResultNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    protected void assertPaymentProviderRedirectResultNotFoundForUuIdException(final PaymentProviderRedirectResultNotFoundForUuidException ex, final String uuid) {
        assertEquals(uuid, ex.getUuId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderRedirectResultRepository<T> getRepository();

    protected abstract AbstractPaymentProviderRedirectResultService<T> getService();

    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();
}
