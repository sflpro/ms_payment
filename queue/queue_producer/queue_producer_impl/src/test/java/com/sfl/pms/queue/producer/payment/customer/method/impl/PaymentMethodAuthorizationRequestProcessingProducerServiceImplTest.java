package com.sfl.pms.queue.producer.payment.customer.method.impl;

import com.sfl.pms.queue.amqp.model.payment.customer.method.PaymentMethodAuthorizationRequestRPCTransferModel;
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
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 10:40 PM
 */
public class PaymentMethodAuthorizationRequestProcessingProducerServiceImplTest extends AbstractQueueProducerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentMethodAuthorizationRequestProcessingProducerServiceImpl paymentMethodAuthorizationRequestProcessingProducerService = new PaymentMethodAuthorizationRequestProcessingProducerServiceImpl();

    @Mock
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public PaymentMethodAuthorizationRequestProcessingProducerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentMethodAuthorizationRequestCreatedEventWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentMethodAuthorizationRequestProcessingProducerService.processPaymentMethodAuthorizationRequestCreatedEvent(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentMethodAuthorizationRequestCreatedEvent() {
        // Test data
        final Long requestId = 1L;
        // Reset
        resetAll();
        // Expectations
        amqpConnectorService.publishMessage(eq(RPCCallType.START_PAYMENT_METHOD_AUTHORIZATION_PROCESSING), isA(PaymentMethodAuthorizationRequestRPCTransferModel.class), eq(PaymentMethodAuthorizationRequestRPCTransferModel.class), isA(AmqpResponseHandler.class));
        expectLastCall();
        // Replay
        replayAll();
        // Run test scenario
        paymentMethodAuthorizationRequestProcessingProducerService.processPaymentMethodAuthorizationRequestCreatedEvent(requestId);
        // Verify
        verifyAll();
    }
}
