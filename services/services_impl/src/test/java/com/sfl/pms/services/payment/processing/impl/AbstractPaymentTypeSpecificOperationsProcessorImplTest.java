package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.exception.channel.UnknownPaymentProcessingChannelTypeException;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodsSynchronizationService;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.system.concurrency.ScheduledTaskExecutorService;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 3:04 PM
 */
public abstract class AbstractPaymentTypeSpecificOperationsProcessorImplTest extends AbstractServicesUnitTest {

    /* Constants */
    public static final int PAYMENT_METHOD_SYNCHRONIZATION_WAIT_PERIOD_IN_MILLIS = 60000;

    public static final int PAYMENT_METHOD_SYNCHRONIZATION_MAX_ATTEMPTS = 10;

    /* Test mocks */
    @Mock
    private PaymentProviderOperationsProcessor adyenPaymentProviderOperationsProcessor;

    @Mock
    private ScheduledTaskExecutorService scheduledTaskExecutorService;

    @Mock
    private CustomerPaymentMethodsSynchronizationService customerPaymentMethodsSynchronizationService;

    /* Constructors */
    public AbstractPaymentTypeSpecificOperationsProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testGeneratePaymentRedirectUrlWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentTypeSpecificOperationsProcessor().generatePaymentRedirectUrl(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGeneratePaymentRedirectUrlWithInvalidPaymentType() {
        // Test data
        final Payment payment = getPaymentInstanceWithInvalidType();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentTypeSpecificOperationsProcessor().generatePaymentRedirectUrl(payment);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGeneratePaymentRedirectUrlWithInvalidPaymentProviderIntegrationType() {
        // Test data
        final Payment payment = getPaymentInstance();
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentTypeSpecificOperationsProcessor().generatePaymentRedirectUrl(payment);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGeneratePaymentRedirectUrl() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getPaymentInstance();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.REDIRECT);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        final String redirectUrl = "https://test.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Expectation
        expect(adyenPaymentProviderOperationsProcessor.generatePaymentRedirectUrl(eq(payment.getId()), eq(payment.isStorePaymentMethod()))).andReturn(redirectUrl).once();
        // Replay
        replayAll();
        // Run test scenario
        final String result = getPaymentTypeSpecificOperationsProcessor().generatePaymentRedirectUrl(payment);
        assertEquals(redirectUrl, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentApiTransactionWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentApiTransactionWithInvalidPaymentType() {
        // Test data
        final Payment payment = getPaymentInstanceWithInvalidType();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(payment);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentApiTransactionWithInvalidPaymentProviderIntegrationType() {
        // Test data
        final Payment payment = getPaymentInstance();
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.REDIRECT);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(payment);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentApiTransactionWithInvalidPaymentPaymentProcessingChannelType() {
        // Test data
        final Payment payment = getPaymentInstance();
        PaymentProcessingChannel paymentProcessingChannel = null;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProcessingChannel = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannel();
            paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
            payment.setPaymentProcessingChannel(paymentProcessingChannel);
            getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(payment);
            fail("Exception should be thrown");
        } catch (final UnknownPaymentProcessingChannelTypeException ex) {
            // Expected
            assertUnknownPaymentProcessingChannelTypeException(ex, paymentProcessingChannel.getType());
        }
        try {
            paymentProcessingChannel = getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannel();
            paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
            payment.setPaymentProcessingChannel(paymentProcessingChannel);
            getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(payment);
            fail("Exception should be thrown");
        } catch (final UnknownPaymentProcessingChannelTypeException ex) {
            // Expected
            assertUnknownPaymentProcessingChannelTypeException(ex, paymentProcessingChannel.getType());
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentApiTransactionWithEncryptedPaymentProcessingChannel() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getPaymentInstance();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        // Payment result DTO
        final PaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Expectations
        expect(adyenPaymentProviderOperationsProcessor.processPaymentUsingEncryptedPaymentMethodChannel(eq(paymentId))).andReturn(paymentResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentResultDto<? extends PaymentResult> resultDto = getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(payment);
        assertEquals(paymentResultDto, resultDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentApiTransactionWithCustomerPaymentMethodProcessingChannel() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getPaymentInstance();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        // Payment result DTO
        final PaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Expectations
        expect(adyenPaymentProviderOperationsProcessor.processPaymentUsingCustomerPaymentMethodChannel(eq(paymentId))).andReturn(paymentResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentResultDto<? extends PaymentResult> resultDto = getPaymentTypeSpecificOperationsProcessor().processPaymentApiTransaction(payment);
        assertEquals(paymentResultDto, resultDto);
        // Verify
        verifyAll();
    }

    /* Abstract methods */
    protected abstract Payment getPaymentInstance();

    protected abstract Payment getPaymentInstanceWithInvalidType();

    protected abstract AbstractPaymentTypeSpecificOperationsProcessorImpl getPaymentTypeSpecificOperationsProcessor();

    /* Utility methods */
    private void assertUnknownPaymentProcessingChannelTypeException(final UnknownPaymentProcessingChannelTypeException ex, final PaymentProcessingChannelType paymentProcessingChannelType) {
        assertEquals(paymentProcessingChannelType, ex.getPaymentProcessingChannelType());
    }

    /* Properties getters */
    public PaymentProviderOperationsProcessor getAdyenPaymentProviderOperationsProcessor() {
        return adyenPaymentProviderOperationsProcessor;
    }

    public ScheduledTaskExecutorService getScheduledTaskExecutorService() {
        return scheduledTaskExecutorService;
    }

    public CustomerPaymentMethodsSynchronizationService getCustomerPaymentMethodsSynchronizationService() {
        return customerPaymentMethodsSynchronizationService;
    }
}
