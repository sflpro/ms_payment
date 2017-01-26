package com.sfl.pms.queue.consumer.payment.common.order.impl;

import com.sfl.pms.queue.consumer.test.AbstractQueueConsumerUnitTest;
import com.sfl.pms.services.payment.processing.order.OrderPaymentRequestProcessorService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 5:15 PM
 */
public class OrderPaymentRequestProcessingConsumerServiceImplTest extends AbstractQueueConsumerUnitTest {

    /* Test subject */
    @TestSubject
    private OrderPaymentRequestProcessingConsumerServiceImpl orderPaymentRequestProcessingConsumerService = new OrderPaymentRequestProcessingConsumerServiceImpl();

    @Mock
    private OrderPaymentRequestProcessorService orderPaymentRequestProcessorService;

    /* Constructors */
    public OrderPaymentRequestProcessingConsumerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessOrderPaymentRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestProcessingConsumerService.processOrderPaymentRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessOrderPaymentRequest() {
        // Test data
        final Long orderPaymentRequestId = 1L;
        final Long paymentId = 2L;
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestProcessorService.processOrderPaymentRequest(eq(orderPaymentRequestId))).andReturn(paymentId).once();
        // Replay
        replayAll();
        // Run test scenario
        orderPaymentRequestProcessingConsumerService.processOrderPaymentRequest(orderPaymentRequestId);
        // Verify
        verifyAll();
    }
}
