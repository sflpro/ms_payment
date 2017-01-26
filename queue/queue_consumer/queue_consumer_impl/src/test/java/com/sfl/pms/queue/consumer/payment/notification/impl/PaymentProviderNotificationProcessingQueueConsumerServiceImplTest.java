package com.sfl.pms.queue.consumer.payment.notification.impl;

import com.sfl.pms.queue.consumer.test.AbstractQueueConsumerUnitTest;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationProcessingService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Arrays;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 7:55 PM
 */
public class PaymentProviderNotificationProcessingQueueConsumerServiceImplTest extends AbstractQueueConsumerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderNotificationProcessingQueueConsumerServiceImpl paymentProviderNotificationProcessingQueueConsumerService = new PaymentProviderNotificationProcessingQueueConsumerServiceImpl();

    @Mock
    private PaymentProviderNotificationProcessingService paymentProviderNotificationProcessingService;

    /* Constructors */
    public PaymentProviderNotificationProcessingQueueConsumerServiceImplTest() {
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
            paymentProviderNotificationProcessingQueueConsumerService.processPaymentProviderNotificationRequest(null);
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
        final Long notificationRequestId = 1L;
        final Long notificationId = 2L;
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(eq(notificationRequestId))).andReturn(Arrays.asList(notificationId)).once();
        // Replay
        replayAll();
        // Run test scenario
        paymentProviderNotificationProcessingQueueConsumerService.processPaymentProviderNotificationRequest(notificationRequestId);
        // Verify
        verifyAll();
    }
}
