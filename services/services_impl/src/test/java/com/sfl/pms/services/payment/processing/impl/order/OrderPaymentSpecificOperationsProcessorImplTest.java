package com.sfl.pms.services.payment.processing.impl.order;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.dto.OrderStateChangeDto;
import com.sfl.pms.services.order.dto.OrderStateChangeHistoryRecordDto;
import com.sfl.pms.services.order.dto.payment.provider.OrderProviderPaymentChannelDto;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.dto.PaymentStateChangeHistoryRecordDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.order.OrderPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.impl.AbstractPaymentTypeSpecificOperationsProcessorImpl;
import com.sfl.pms.services.payment.processing.impl.AbstractPaymentTypeSpecificOperationsProcessorImplTest;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 10:59 AM
 */
public class OrderPaymentSpecificOperationsProcessorImplTest extends AbstractPaymentTypeSpecificOperationsProcessorImplTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderPaymentSpecificOperationsProcessorImpl orderPaymentSpecificOperationsProcessor = new OrderPaymentSpecificOperationsProcessorImpl();

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public OrderPaymentSpecificOperationsProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessDuplicatePaymentResultWhenPaymentIsNotSuccessfulAndPaymentMethodShouldToBeStored() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long paymentId = 2L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        payment.setStorePaymentMethod(true);
        payment.setCustomer(customer);
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        paymentResultDto.setStatus(PaymentResultStatus.FAILED);
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        orderPaymentSpecificOperationsProcessor.processDuplicatePaymentResult(payment, paymentResultDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithInvalidArguments() {
        // Test data
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentSpecificOperationsProcessor.processPaymentResult(null, paymentResult);
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithInvalidPaymentType() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithNotDefinedPaymentResult() {
        // Test data
        final Long paymentId = 1L;
        final OrderPayment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final Long orderId = 2L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        payment.setOrder(order);
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderId(orderId);
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(new OrderStateChangeHistoryRecordDto(OrderState.PAYMENT_FAILED));
        orderStateChangeDto.setPaymentUuid(payment.getUuId());
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(orderService.updateOrderState(eq(orderStateChangeDto))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), EasyMock.eq(new PaymentStateChangeHistoryRecordDto(PaymentState.FAILED, null)))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = orderPaymentSpecificOperationsProcessor.processPaymentResult(payment, null);
        assertOrderPaymentResult(resultDto, paymentId);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithFailedPaymentResult() {
        // Test data
        final Long paymentId = 1L;
        final OrderPayment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final Long orderId = 2L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        payment.setOrder(order);
        final Long paymentResultId = 3L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setStatus(PaymentResultStatus.REFUSED);
        paymentResult.setId(paymentResultId);
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderId(orderId);
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(new OrderStateChangeHistoryRecordDto(OrderState.PAYMENT_FAILED));
        orderStateChangeDto.setPaymentUuid(payment.getUuId());
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(orderService.updateOrderState(eq(orderStateChangeDto))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.REFUSED, null)))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = orderPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertOrderPaymentResult(resultDto, paymentId);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithSuccessfulPaymentResultWhenOrderIsNotPaidYetAndPaymentMethodShouldNotBeStored() {
        // Test data
        final Long paymentId = 1L;
        final OrderPayment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setStorePaymentMethod(false);
        payment.setId(paymentId);
        final Long orderId = 2L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        payment.setOrder(order);
        final Long paymentResultId = 3L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setStatus(PaymentResultStatus.PAID);
        paymentResult.setId(paymentResultId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(false).once();
        expect(orderService.markOrderPaid(eq(orderId), EasyMock.eq(new OrderProviderPaymentChannelDto(paymentId)))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.PAID, null)))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = orderPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertOrderPaymentResult(resultDto, paymentId);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithSuccessfulPaymentResultWhenOrderIsNotPaidYetAndPaymentMethodShouldBeStored() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long paymentId = 1L;
        final OrderPayment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setStorePaymentMethod(true);
        payment.setId(paymentId);
        payment.setCustomer(customer);
        final Long orderId = 2L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        payment.setOrder(order);
        final Long paymentResultId = 3L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setStatus(PaymentResultStatus.PAID);
        paymentResult.setId(paymentResultId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(false).once();
        expect(orderService.markOrderPaid(eq(orderId), eq(new OrderProviderPaymentChannelDto(paymentId)))).andReturn(null).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.PAID, null)))).andReturn(null).once();
        expect(getCustomerPaymentMethodsSynchronizationService().synchronizeCustomerPaymentMethods(eq(customerId), EasyMock.eq(payment.getPaymentProviderType()))).andReturn(new CustomerPaymentMethodsSynchronizationResult(Collections.EMPTY_LIST, Collections.EMPTY_LIST)).times(PAYMENT_METHOD_SYNCHRONIZATION_MAX_ATTEMPTS + 1);
        getScheduledTaskExecutorService().scheduleTaskForExecution(isA(Runnable.class), eq(PAYMENT_METHOD_SYNCHRONIZATION_WAIT_PERIOD_IN_MILLIS), eq(TimeUnit.MILLISECONDS), eq(true));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).times(PAYMENT_METHOD_SYNCHRONIZATION_MAX_ATTEMPTS + 1);
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = orderPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertOrderPaymentResult(resultDto, paymentId);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithSuccessfulPaymentResultWhenOrderIsAlreadyPaid() {
        // Test data
        final Long paymentId = 1L;
        final OrderPayment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final Long orderId = 2L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setPaymentChannel(getServicesImplTestHelper().createOrderProviderPaymentChannel(getServicesImplTestHelper().createOrderProviderPaymentChannelDto()));
        order.setId(orderId);
        payment.setOrder(order);
        final Long paymentResultId = 3L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setStatus(PaymentResultStatus.PAID);
        paymentResult.setId(paymentResultId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(true).once();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(paymentResult.getStatus().getPaymentState(), null)))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = orderPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertOrderPaymentResult(resultDto, paymentId);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertOrderPaymentResult(final PaymentProcessingResultDetailedInformationDto resultDto, final Long paymentId) {
        assertNotNull(resultDto);
        assertTrue(resultDto instanceof OrderPaymentProcessingResultDetailedInformationDto);
        final OrderPaymentProcessingResultDetailedInformationDto orderPaymentProcessingResultDto = (OrderPaymentProcessingResultDetailedInformationDto) resultDto;
        assertEquals(paymentId, orderPaymentProcessingResultDto.getPaymentId());
    }

    @Override
    protected Payment getPaymentInstance() {
        return getServicesImplTestHelper().createOrderPayment();
    }

    @Override
    protected Payment getPaymentInstanceWithInvalidType() {
        return getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
    }

    @Override
    protected AbstractPaymentTypeSpecificOperationsProcessorImpl getPaymentTypeSpecificOperationsProcessor() {
        return orderPaymentSpecificOperationsProcessor;
    }

}
