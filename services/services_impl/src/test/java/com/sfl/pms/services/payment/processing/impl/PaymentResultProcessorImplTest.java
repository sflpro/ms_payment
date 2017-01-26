package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultType;
import com.sfl.pms.services.payment.processing.dto.auth.CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.order.OrderPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.impl.adyen.AdyenPaymentOperationsProcessorImpl;
import com.sfl.pms.services.payment.processing.impl.auth.CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor;
import com.sfl.pms.services.payment.processing.impl.order.OrderPaymentSpecificOperationsProcessor;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 12:15 PM
 */
public class PaymentResultProcessorImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentResultProcessorImpl paymentResultProcessor = new PaymentResultProcessorImpl();

    @Mock
    private PaymentService paymentService;

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor;

    @Mock
    private OrderPaymentSpecificOperationsProcessor orderPaymentSpecificOperationsProcessor;

    @Mock
    private PaymentProviderOperationsProcessor adyenPaymentSpecificOperationsProcessor = new AdyenPaymentOperationsProcessorImpl();

    /* Constructors */
    public PaymentResultProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentResultWithInvalidArguments() {
        // Test data
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentResultProcessor.processPaymentResult(null, null, null, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultForOrderPaymentWhenResultDoesNotExistYet() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 1L;
        final AdyenPaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final OrderPaymentProcessingResultDetailedInformationDto orderPaymentProcessingResultDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.getPaymentByIdWithPessimisticWriteLock(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        expect(adyenPaymentSpecificOperationsProcessor.checkIfPaymentResultAlreadyExists(eq(payment), eq(paymentResultDto))).andReturn(false).once();
        expect(paymentService.createPaymentResultForPayment(eq(paymentId), isNull(), isNull(), eq(paymentResultDto))).andReturn(paymentResult).once();
        expect(orderPaymentSpecificOperationsProcessor.processPaymentResult(eq(payment), eq(paymentResult))).andReturn(orderPaymentProcessingResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDto result = paymentResultProcessor.processPaymentResult(payment.getId(), null, null, paymentResultDto);
        assertTrue(result.getInformationDto() instanceof OrderPaymentProcessingResultDetailedInformationDto);
        assertEquals(orderPaymentProcessingResultDto, result.getInformationDto());
        assertEquals(PaymentProcessingResultType.RESULT_PROCESSED, result.getResultType());
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultForOrderPaymentWhenResultAlreadyExists() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 1L;
        final AdyenPaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final OrderPaymentProcessingResultDetailedInformationDto orderPaymentProcessingResultDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.getPaymentByIdWithPessimisticWriteLock(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        expect(adyenPaymentSpecificOperationsProcessor.checkIfPaymentResultAlreadyExists(eq(payment), eq(paymentResultDto))).andReturn(true).once();
        expect(orderPaymentSpecificOperationsProcessor.processDuplicatePaymentResult(eq(payment), eq(paymentResultDto))).andReturn(orderPaymentProcessingResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDto result = paymentResultProcessor.processPaymentResult(payment.getId(), null, null, paymentResultDto);
        assertNotNull(result);
        assertEquals(orderPaymentProcessingResultDto, result.getInformationDto());
        assertEquals(PaymentProcessingResultType.RESULT_ALREADY_EXISTED, result.getResultType());
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultForOrderPaymentWithNotDefinedResult() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final OrderPaymentProcessingResultDetailedInformationDto orderPaymentProcessingResultDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.getPaymentByIdWithPessimisticWriteLock(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        expect(orderPaymentSpecificOperationsProcessor.processPaymentResult(eq(payment), isNull())).andReturn(orderPaymentProcessingResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDto result = paymentResultProcessor.processPaymentResult(payment.getId(), null, null, null);
        assertTrue(result.getInformationDto() instanceof OrderPaymentProcessingResultDetailedInformationDto);
        assertEquals(orderPaymentProcessingResultDto, result.getInformationDto());
        assertEquals(PaymentProcessingResultType.RESULT_PROCESSED, result.getResultType());
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationPaymentResultForPaymentWhenResultDoesNotExistYet() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 1L;
        final AdyenPaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final List<Long> customerPaymentMethods = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        final CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto paymentProcessingResultDto = new CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto(paymentId, customerPaymentMethods);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.getPaymentByIdWithPessimisticWriteLock(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        expect(adyenPaymentSpecificOperationsProcessor.checkIfPaymentResultAlreadyExists(eq(payment), eq(paymentResultDto))).andReturn(false).once();
        expect(paymentService.createPaymentResultForPayment(eq(paymentId), isNull(), isNull(), eq(paymentResultDto))).andReturn(paymentResult).once();
        expect(customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentResult(eq(payment), eq(paymentResult))).andReturn(paymentProcessingResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        // Run test scenario
        final PaymentProcessingResultDto result = paymentResultProcessor.processPaymentResult(payment.getId(), null, null, paymentResultDto);
        assertTrue(result.getInformationDto() instanceof CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto);
        assertEquals(paymentProcessingResultDto, result.getInformationDto());
        assertEquals(PaymentProcessingResultType.RESULT_PROCESSED, result.getResultType());
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationPaymentResultForPaymentWhenResultAlreadyExists() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long paymentResultId = 1L;
        final AdyenPaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        final List<Long> customerPaymentMethods = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        final CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto paymentProcessingResultDto = new CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto(paymentId, customerPaymentMethods);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.getPaymentByIdWithPessimisticWriteLock(eq(paymentId))).andReturn(payment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        expect(adyenPaymentSpecificOperationsProcessor.checkIfPaymentResultAlreadyExists(eq(payment), eq(paymentResultDto))).andReturn(true).once();
        expect(customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processDuplicatePaymentResult(eq(payment), eq(paymentResultDto))).andReturn(paymentProcessingResultDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDto result = paymentResultProcessor.processPaymentResult(payment.getId(), null, null, paymentResultDto);
        assertNotNull(result);
        assertEquals(paymentProcessingResultDto, result.getInformationDto());
        assertEquals(PaymentProcessingResultType.RESULT_ALREADY_EXISTED, result.getResultType());
        // Verify
        verifyAll();
    }
}
