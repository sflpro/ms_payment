package com.sfl.pms.queue.consumer.payment.customer.method.impl;

import com.sfl.pms.queue.consumer.test.AbstractQueueConsumerUnitTest;
import com.sfl.pms.services.payment.common.removal.CustomerPaymentMethodRemovalProcessingService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.fail;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
public class PaymentMethodRemovalRequestProcessingConsumerServiceImplTest extends AbstractQueueConsumerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentMethodRemovalRequestProcessingConsumerServiceImpl paymentMethodRemovalRequestProcessingConsumerService = new PaymentMethodRemovalRequestProcessingConsumerServiceImpl();

    @Mock
    public CustomerPaymentMethodRemovalProcessingService customerPaymentMethodRemovalProcessingService;

    /* Constructors */
    public PaymentMethodRemovalRequestProcessingConsumerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentMethodRemovalRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentMethodRemovalRequestProcessingConsumerService.processPaymentMethodRemovalRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentMethodRemovalRequest() {
        // Test data
        final Long removalRequestId = 1L;
        // Reset
        resetAll();
        // Expectations
        customerPaymentMethodRemovalProcessingService.processCustomerPaymentMethodRemovalRequest(eq(removalRequestId));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        paymentMethodRemovalRequestProcessingConsumerService.processPaymentMethodRemovalRequest(removalRequestId);
        // Verify
        verifyAll();
    }
}
