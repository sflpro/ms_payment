package com.sfl.pms.services.payment.common.impl.status;

import com.sfl.pms.externalclients.payment.adyen.model.datatypes.AdyenPaymentStatus;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/13/15
 * Time: 2:40 PM
 */
public class PaymentResultStatusMapperImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentResultStatusMapperImpl paymentResultStatusMapper = new PaymentResultStatusMapperImpl();

    /* Constructors */
    public PaymentResultStatusMapperImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentResultStatusForAdyenPaymentStatusWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentResultStatusForAdyenPaymentStatus() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        PaymentResultStatus paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(AdyenPaymentStatus.AUTHORISED);
        assertEquals(PaymentResultStatus.PAID, paymentResultStatus);
        paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(AdyenPaymentStatus.CANCELLED);
        assertEquals(PaymentResultStatus.CANCELLED, paymentResultStatus);
        paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(AdyenPaymentStatus.ERROR);
        assertEquals(PaymentResultStatus.ERROR, paymentResultStatus);
        paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(AdyenPaymentStatus.PENDING);
        assertEquals(PaymentResultStatus.PENDING, paymentResultStatus);
        paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(AdyenPaymentStatus.REFUSED);
        assertEquals(PaymentResultStatus.REFUSED, paymentResultStatus);
        paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(AdyenPaymentStatus.RECEIVED);
        assertEquals(PaymentResultStatus.RECEIVED, paymentResultStatus);
        // Check that all payment statuses are covered
        for (AdyenPaymentStatus adyenPaymentStatus : AdyenPaymentStatus.values()) {
            paymentResultStatus = paymentResultStatusMapper.getPaymentResultStatusForAdyenPaymentStatus(adyenPaymentStatus);
            assertNotNull(paymentResultStatus);
        }
        // Verify
        verifyAll();
    }
}
