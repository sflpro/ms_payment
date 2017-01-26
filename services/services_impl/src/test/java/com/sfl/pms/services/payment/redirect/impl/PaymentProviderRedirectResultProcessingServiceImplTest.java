package com.sfl.pms.services.payment.redirect.impl;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.redirect.PaymentProviderRedirectResultService;
import com.sfl.pms.services.payment.redirect.impl.adyen.AdyenRedirectResultProcessor;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResultState;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/12/15
 * Time: 3:53 PM
 */
public class PaymentProviderRedirectResultProcessingServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderRedirectResultProcessingServiceImpl paymentProviderRedirectResultProcessingService = new PaymentProviderRedirectResultProcessingServiceImpl();

    @Mock
    private PaymentProviderRedirectResultService paymentProviderRedirectResultService;

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private AdyenRedirectResultProcessor adyenRedirectResultProcessor;

    /* Constructors */
    public PaymentProviderRedirectResultProcessingServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderRedirectResultWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderRedirectResultProcessingService.processPaymentProviderRedirectResult(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderRedirectResultWhenExceptionIsThrown() {
        // Test data
        final Long redirectResultId = 1L;
        final PaymentProviderRedirectResult redirectResult = getServicesImplTestHelper().createAdyenRedirectResult();
        redirectResult.setId(redirectResultId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderRedirectResultService.updatePaymentProviderRedirectResultState(eq(redirectResultId), eq(PaymentProviderRedirectResultState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(PaymentProviderRedirectResultState.CREATED, PaymentProviderRedirectResultState.FAILED))))).andReturn(redirectResult).once();
        expect(paymentProviderRedirectResultService.updatePaymentProviderRedirectResultState(eq(redirectResultId), eq(PaymentProviderRedirectResultState.FAILED), eq(new LinkedHashSet<>()))).andReturn(redirectResult).once();
        expect(paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(eq(redirectResultId))).andReturn(redirectResult).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(adyenRedirectResultProcessor.processPaymentProviderRedirectResult(eq(redirectResult))).andThrow(new ServicesRuntimeException("Test exception")).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderRedirectResultProcessingService.processPaymentProviderRedirectResult(redirectResultId);
            fail("Exception should be thrown");
        } catch (final ServicesRuntimeException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderRedirectResult() {
        // Test data
        final Long redirectResultId = 1L;
        final PaymentProviderRedirectResult redirectResult = getServicesImplTestHelper().createAdyenRedirectResult();
        redirectResult.setId(redirectResultId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderRedirectResultService.updatePaymentProviderRedirectResultState(eq(redirectResultId), eq(PaymentProviderRedirectResultState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(PaymentProviderRedirectResultState.CREATED, PaymentProviderRedirectResultState.FAILED))))).andReturn(redirectResult).once();
        expect(paymentProviderRedirectResultService.updatePaymentProviderRedirectResultState(eq(redirectResultId), eq(PaymentProviderRedirectResultState.PROCESSED), eq(new LinkedHashSet<>()))).andReturn(redirectResult).once();
        expect(paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(eq(redirectResultId))).andReturn(redirectResult).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(adyenRedirectResultProcessor.processPaymentProviderRedirectResult(eq(redirectResult))).andReturn(PaymentProviderRedirectResultState.PROCESSED).once();
        // Replay
        replayAll();
        // Run test scenario
        paymentProviderRedirectResultProcessingService.processPaymentProviderRedirectResult(redirectResultId);
        // Verify
        verifyAll();
    }
}
