package com.sfl.pms.queue.producer.payment.common.order.impl;

import com.sfl.pms.queue.amqp.model.payment.common.order.OrderPaymentRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.test.AbstractQueueProducerUnitTest;
import com.sfl.pms.services.system.event.ApplicationEventDistributionService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 4:37 PM
 */
public class OrderPaymentRequestProcessingProducerServiceImplTest extends AbstractQueueProducerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderPaymentRequestProcessingProducerServiceImpl orderPaymentRequestProcessingProducerService = new OrderPaymentRequestProcessingProducerServiceImpl();

    @Mock
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Mock
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public OrderPaymentRequestProcessingProducerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessOrderPaymentRequestProcessingEventWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestProcessingProducerService.processOrderPaymentRequestProcessingEvent(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessOrderPaymentRequestProcessingEvent() {
        // Test data
        final Long requestId = 1L;
        // Reset
        resetAll();
        // Expectations
        amqpConnectorService.publishMessage(eq(RPCCallType.START_ORDER_PAYMENT_REQUEST_PROCESSING), isA(OrderPaymentRequestRPCTransferModel.class), eq(OrderPaymentRequestRPCTransferModel.class), isA(AmqpResponseHandler.class));
        expectLastCall();
        // Replay
        replayAll();
        // Run test scenario
        orderPaymentRequestProcessingProducerService.processOrderPaymentRequestProcessingEvent(requestId);
        // Verify
        verifyAll();
    }
}
