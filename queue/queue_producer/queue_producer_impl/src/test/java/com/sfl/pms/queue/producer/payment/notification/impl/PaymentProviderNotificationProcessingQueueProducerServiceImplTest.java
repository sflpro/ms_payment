package com.sfl.pms.queue.producer.payment.notification.impl;

import com.sfl.pms.queue.amqp.model.payment.notification.PaymentProviderNotificationRequestRPCTransferModel;
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
 * Date: 4/28/15
 * Time: 6:17 PM
 */
public class PaymentProviderNotificationProcessingQueueProducerServiceImplTest extends AbstractQueueProducerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderNotificationProcessingQueueProducerServiceImpl paymentProviderNotificationProcessingProducerService = new PaymentProviderNotificationProcessingQueueProducerServiceImpl();

    @Mock
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Mock
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public PaymentProviderNotificationProcessingQueueProducerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderNotificationRequestProcessingEventWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationProcessingProducerService.processPaymentProviderNotificationRequestProcessingEvent(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderNotificationRequestProcessingEvent() {
        // Test data
        final Long notificationRequestId = 1L;
        // Reset
        resetAll();
        // Expectations
        amqpConnectorService.publishMessage(eq(RPCCallType.START_PAYMENT_PROVIDER_NOTIFICATION_REQUEST_PROCESSING), isA(PaymentProviderNotificationRequestRPCTransferModel.class), eq(PaymentProviderNotificationRequestRPCTransferModel.class), isA(AmqpResponseHandler.class));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        paymentProviderNotificationProcessingProducerService.processPaymentProviderNotificationRequestProcessingEvent(notificationRequestId);
        // Verify
        verifyAll();
    }
}
