package com.sfl.pms.services.payment.common.impl.provider.adyen;

import com.sfl.pms.services.payment.common.dto.adyen.AdyenPaymentResultDto;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/14/15
 * Time: 9:53 PM
 */
public class AdyenPaymentResultHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private AdyenPaymentResultHandlerImpl adyenPaymentResultHandler = new AdyenPaymentResultHandlerImpl();

    /* Constructors */
    public AdyenPaymentResultHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testAssertPaymentResultDto() {
        // Test data
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenPaymentResultHandler.assertPaymentResultDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentResultHandler.assertPaymentResultDto(new AdyenPaymentResultDto(null, paymentResultDto.getAuthCode(), paymentResultDto.getPspReference(), paymentResultDto.getResultCode(), paymentResultDto.getRefusalReason()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentResultHandler.assertPaymentResultDto(new AdyenPaymentResultDto(paymentResultDto.getStatus(), paymentResultDto.getAuthCode(), paymentResultDto.getPspReference(), null, paymentResultDto.getRefusalReason()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentResultDtoWithInvalidArguments() {
        // Test data
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenPaymentResultHandler.convertPaymentResultDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentResultHandler.convertPaymentResultDto(new AdyenPaymentResultDto(null, paymentResultDto.getAuthCode(), paymentResultDto.getPspReference(), paymentResultDto.getResultCode(), paymentResultDto.getRefusalReason()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentResultHandler.convertPaymentResultDto(new AdyenPaymentResultDto(paymentResultDto.getStatus(), paymentResultDto.getAuthCode(), paymentResultDto.getPspReference(), null, paymentResultDto.getRefusalReason()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentResultDto() {
        // Test data
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentResult paymentResult = adyenPaymentResultHandler.convertPaymentResultDto(paymentResultDto);
        assertTrue(paymentResult instanceof AdyenPaymentResult);
        getServicesImplTestHelper().assertAdyenPaymentResult((AdyenPaymentResult) paymentResult, paymentResultDto);
        // Verify
        verifyAll();
    }
}
