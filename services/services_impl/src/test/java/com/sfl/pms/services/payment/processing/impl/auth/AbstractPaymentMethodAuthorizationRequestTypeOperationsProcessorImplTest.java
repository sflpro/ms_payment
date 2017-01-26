package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 5:30 PM
 */
public abstract class AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest extends AbstractServicesUnitTest {

    /* Constructors */
    public AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest() {
    }

    @Test
    public void testCreatePaymentDtoWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentMethodAuthorizationRequestTypeOperationsProcessor().createPaymentDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentDtoWithInvalidRequestType() {
        // Test data
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest = getInstanceWithInvalidType();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentMethodAuthorizationRequestTypeOperationsProcessor().createPaymentDto(authorizationRequest);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    /* Abstract methods */
    protected abstract PaymentMethodAuthorizationRequestTypeOperationsProcessor getPaymentMethodAuthorizationRequestTypeOperationsProcessor();

    protected abstract CustomerPaymentMethodAuthorizationRequest getInstanceWithInvalidType();

    protected abstract CustomerPaymentMethodAuthorizationRequest getInstance(final PaymentMethodGroupType paymentMethodGroupType);

    /* Utility methods */
    protected void assertPaymentDto(final CustomerPaymentMethodAuthorizationPaymentDto paymentDto, final CustomerPaymentMethodAuthorizationRequest authorizationRequest, final BigDecimal paymentAmount) {
        assertNotNull(paymentDto);
        assertEquals(authorizationRequest.getClientIpAddress(), paymentDto.getClientIpAddress());
        assertEquals(authorizationRequest.getPaymentProviderType(), paymentDto.getPaymentProviderType());
        assertEquals(authorizationRequest.getCurrency(), paymentDto.getCurrency());
        Assert.assertEquals(paymentAmount, paymentDto.getAmount());
    }
}
