package com.sfl.pms.queue.consumer.payment.redirect.impl;

import com.sfl.pms.queue.consumer.test.AbstractQueueConsumerUnitTest;
import com.sfl.pms.services.payment.redirect.PaymentProviderRedirectResultProcessingService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 7:55 PM
 */
public class PaymentProviderRedirectResultProcessingQueueConsumerServiceImplTest extends AbstractQueueConsumerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderRedirectResultProcessingQueueConsumerServiceImpl paymentProviderRedirectResultProcessingQueueConsumerService = new PaymentProviderRedirectResultProcessingQueueConsumerServiceImpl();

    @Mock
    private PaymentProviderRedirectResultProcessingService paymentProviderRedirectResultProcessingService;

    /* Constructors */
    public PaymentProviderRedirectResultProcessingQueueConsumerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderNotificationRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderRedirectResultProcessingQueueConsumerService.processPaymentProviderRedirectResult(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderNotificationRequest() {
        // Test data
        final Long redirectResultId = 2L;
        // Reset
        resetAll();
        // Expectations
        paymentProviderRedirectResultProcessingService.processPaymentProviderRedirectResult(eq(redirectResultId));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        paymentProviderRedirectResultProcessingQueueConsumerService.processPaymentProviderRedirectResult(redirectResultId);
        // Verify
        verifyAll();
    }
}
