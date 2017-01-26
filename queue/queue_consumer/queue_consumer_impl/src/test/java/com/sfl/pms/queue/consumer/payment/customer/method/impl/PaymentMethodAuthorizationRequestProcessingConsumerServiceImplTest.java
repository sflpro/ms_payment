package com.sfl.pms.queue.consumer.payment.customer.method.impl;

import com.sfl.pms.queue.consumer.test.AbstractQueueConsumerUnitTest;
import com.sfl.pms.services.payment.processing.auth.CustomerPaymentMethodAuthorizationRequestProcessorService;
import com.sfl.pms.services.payment.processing.dto.auth.CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 11:16 PM
 */
public class PaymentMethodAuthorizationRequestProcessingConsumerServiceImplTest extends AbstractQueueConsumerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentMethodAuthorizationRequestProcessingConsumerServiceImpl paymentMethodAuthorizationRequestProcessingConsumerService = new PaymentMethodAuthorizationRequestProcessingConsumerServiceImpl();

    @Mock
    public CustomerPaymentMethodAuthorizationRequestProcessorService customerPaymentMethodAuthorizationRequestProcessorService;

    /* Constructors */
    public PaymentMethodAuthorizationRequestProcessingConsumerServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentMethodAuthorizationRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentMethodAuthorizationRequestProcessingConsumerService.processPaymentMethodAuthorizationRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentMethodAuthorizationRequest() {
        // Test data
        final Long authorizationRequestId = 1L;
        final Long paymentId = 2L;
        final List<Long> result = new ArrayList<>();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(eq(authorizationRequestId))).andReturn(new CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto(paymentId, result)).once();
        // Replay
        replayAll();
        // Run test scenario
        paymentMethodAuthorizationRequestProcessingConsumerService.processPaymentMethodAuthorizationRequest(authorizationRequestId);
        // Verify
        verifyAll();
    }
}
