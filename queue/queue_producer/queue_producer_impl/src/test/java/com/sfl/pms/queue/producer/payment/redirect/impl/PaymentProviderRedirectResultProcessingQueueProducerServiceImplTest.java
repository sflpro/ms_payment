package com.sfl.pms.queue.producer.payment.redirect.impl;

import com.sfl.pms.queue.amqp.model.payment.redirect.PaymentProviderRedirectResultRPCTransferModel;
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
public class PaymentProviderRedirectResultProcessingQueueProducerServiceImplTest extends AbstractQueueProducerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderRedirectResultProcessingQueueProducerServiceImpl paymentProviderRedirectResultProcessingQueueProducerService = new PaymentProviderRedirectResultProcessingQueueProducerServiceImpl();

    @Mock
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Mock
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public PaymentProviderRedirectResultProcessingQueueProducerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderRedirectResultProcessingEventWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderRedirectResultProcessingQueueProducerService.processPaymentProviderRedirectResultProcessingEvent(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderRedirectResultProcessingEvent() {
        // Test data
        final Long notificationRequestId = 1L;
        // Reset
        resetAll();
        // Expectations
        amqpConnectorService.publishMessage(eq(RPCCallType.START_PAYMENT_PROVIDER_REDIRECT_RESULT_PROCESSING), isA(PaymentProviderRedirectResultRPCTransferModel.class), eq(PaymentProviderRedirectResultRPCTransferModel.class), isA(AmqpResponseHandler.class));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        paymentProviderRedirectResultProcessingQueueProducerService.processPaymentProviderRedirectResultProcessingEvent(notificationRequestId);
        // Verify
        verifyAll();
    }
}
