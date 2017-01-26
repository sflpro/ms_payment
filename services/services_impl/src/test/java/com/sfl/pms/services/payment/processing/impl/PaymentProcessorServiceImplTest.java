package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.dto.PaymentStateChangeHistoryRecordDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultType;
import com.sfl.pms.services.payment.processing.dto.order.OrderPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.redirect.PaymentRedirectProcessingInformationDto;
import com.sfl.pms.services.payment.processing.impl.auth.CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor;
import com.sfl.pms.services.payment.processing.impl.order.OrderPaymentSpecificOperationsProcessor;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/1/15
 * Time: 9:33 PM
 */
public class PaymentProcessorServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProcessorServiceImpl paymentProcessorService = new PaymentProcessorServiceImpl();

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor;

    @Mock
    private OrderPaymentSpecificOperationsProcessor orderPaymentSpecificOperationsProcessor;

    @Mock
    private PaymentResultProcessor paymentResultProcessor;

    /* Constructors */
    public PaymentProcessorServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProcessorService.processPayment(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessOrderPaymentWithApiIntegration() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        final PaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final PaymentProcessingResultDetailedInformationDto paymentProcessingResultDetailedInformationDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).times(2);
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), EasyMock.eq(new PaymentStateChangeHistoryRecordDto(PaymentState.STARTED_PROCESSING, null)))).andReturn(null).once();
        expect(orderPaymentSpecificOperationsProcessor.processPaymentApiTransaction(eq(payment))).andReturn(paymentResultDto).once();
        expect(paymentResultProcessor.processPaymentResult(eq(payment.getId()), isNull(), isNull(), eq(paymentResultDto))).andReturn(new PaymentProcessingResultDto(paymentProcessingResultDetailedInformationDto, PaymentProcessingResultType.RESULT_PROCESSED)).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto result = paymentProcessorService.processPayment(paymentId);
        assertEquals(paymentProcessingResultDetailedInformationDto, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessOrderPaymentWithRedirectIntegration() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.REDIRECT);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        final PaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final String redirectUrl = "http://adyen.com/pay.shtml";
        final PaymentProcessingResultDetailedInformationDto paymentProcessingResultDetailedInformationDto = new PaymentRedirectProcessingInformationDto(paymentId, redirectUrl);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.STARTED_PROCESSING, null)))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.GENERATED_REDIRECT_URL, null)))).andReturn(null).once();
        expect(orderPaymentSpecificOperationsProcessor.generatePaymentRedirectUrl(eq(payment))).andReturn(redirectUrl).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto result = paymentProcessorService.processPayment(paymentId);
        assertEquals(paymentProcessingResultDetailedInformationDto, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationPaymentWithApiIntegration() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        final PaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final PaymentProcessingResultDetailedInformationDto paymentProcessingResultDetailedInformationDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).times(2);
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.STARTED_PROCESSING, null)))).andReturn(null).once();
        expect(customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentApiTransaction(eq(payment))).andReturn(paymentResultDto).once();
        expect(paymentResultProcessor.processPaymentResult(eq(payment.getId()), isNull(), isNull(), eq(paymentResultDto))).andReturn(new PaymentProcessingResultDto(paymentProcessingResultDetailedInformationDto, PaymentProcessingResultType.RESULT_PROCESSED)).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto result = paymentProcessorService.processPayment(paymentId);
        assertEquals(paymentProcessingResultDetailedInformationDto, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationPaymentWithRedirectIntegration() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.REDIRECT);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        final PaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final String redirectUrl = "http://adyen.com/pay.shtml";
        final PaymentProcessingResultDetailedInformationDto paymentProcessingResultDetailedInformationDto = new PaymentRedirectProcessingInformationDto(paymentId, redirectUrl);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.STARTED_PROCESSING, null)))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.GENERATED_REDIRECT_URL, null)))).andReturn(null).once();
        expect(customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.generatePaymentRedirectUrl(eq(payment))).andReturn(redirectUrl).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto result = paymentProcessorService.processPayment(paymentId);
        assertEquals(paymentProcessingResultDetailedInformationDto, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentWhenExceptionIsThrown() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannel();
        paymentProcessingChannel.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.API);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).times(2);
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.STARTED_PROCESSING, null)))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.FAILED, null)))).andReturn(null).once();
        expect(orderPaymentSpecificOperationsProcessor.processPaymentApiTransaction(eq(payment))).andThrow(new ServicesRuntimeException("Testing behavior when unexpected exception is thrown")).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProcessorService.processPayment(paymentId);
            fail("Exception should be thrown");
        } catch (final ServicesRuntimeException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }
}
