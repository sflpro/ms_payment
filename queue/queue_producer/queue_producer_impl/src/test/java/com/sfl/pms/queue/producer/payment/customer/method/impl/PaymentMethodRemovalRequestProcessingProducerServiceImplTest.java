package com.sfl.pms.queue.producer.payment.customer.method.impl;

import com.sfl.pms.queue.amqp.model.payment.customer.method.PaymentMethodRemovalRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.test.AbstractQueueProducerUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.fail;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
public class PaymentMethodRemovalRequestProcessingProducerServiceImplTest extends AbstractQueueProducerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentMethodRemovalRequestProcessingProducerServiceImpl paymentMethodRemovalRequestProcessingProducerService = new PaymentMethodRemovalRequestProcessingProducerServiceImpl();

    @Mock
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public PaymentMethodRemovalRequestProcessingProducerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentMethodRemovalRequestCreatedEventWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentMethodRemovalRequestProcessingProducerService.processPaymentMethodRemovalRequestCreatedEvent(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentMethodRemovalRequestCreatedEvent() {
        // Test data
        final Long requestId = 1L;
        // Reset
        resetAll();
        // Expectations
        amqpConnectorService.publishMessage(eq(RPCCallType.START_PAYMENT_METHOD_REMOVAL_PROCESSING), isA(PaymentMethodRemovalRequestRPCTransferModel.class), eq(PaymentMethodRemovalRequestRPCTransferModel.class), isA(AmqpResponseHandler.class));
        expectLastCall();
        // Replay
        replayAll();
        // Run test scenario
        paymentMethodRemovalRequestProcessingProducerService.processPaymentMethodRemovalRequestCreatedEvent(requestId);
        // Verify
        verifyAll();
    }
}
