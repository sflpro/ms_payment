package com.sfl.pms.services.order.impl;

import com.sfl.pms.persistence.repositories.order.OrderRepository;
import com.sfl.pms.persistence.repositories.order.payment.OrderPaymentChannelRepository;
import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.dto.OrderDto;
import com.sfl.pms.services.order.dto.OrderStateChangeDto;
import com.sfl.pms.services.order.dto.OrderStateChangeHistoryRecordDto;
import com.sfl.pms.services.order.dto.payment.provider.OrderProviderPaymentChannelDto;
import com.sfl.pms.services.order.event.OrderPaidEvent;
import com.sfl.pms.services.order.event.OrderStateChangedEvent;
import com.sfl.pms.services.order.exception.OrderAlreadyExistsForUuIdException;
import com.sfl.pms.services.order.exception.OrderNotFoundForIdException;
import com.sfl.pms.services.order.exception.OrderNotFoundForUuIdException;
import com.sfl.pms.services.order.exception.payment.OrderAlreadyPaidException;
import com.sfl.pms.services.order.impl.payment.provider.OrderProviderPaymentChannelHandler;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.order.model.OrderStateChangeHistoryRecord;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.order.model.payment.provider.OrderProviderPaymentChannel;
import com.sfl.pms.services.system.event.ApplicationEventDistributionService;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.apache.commons.lang3.SerializationUtils;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 12:51 PM
 */
public class OrderServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mock */
    @TestSubject
    private OrderServiceImpl orderService = new OrderServiceImpl();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderProviderPaymentChannelHandler orderProviderPaymentChannelHandler;

    @Mock
    private OrderPaymentChannelRepository orderPaymentChannelRepository;

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private ApplicationEventDistributionService applicationEventDistributionService;

    /* Constructors */
    public OrderServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreateOrderWithInvalidArguments() {
        // Test data
        final Long customerId = 1L;
        final OrderDto orderDto = getServicesImplTestHelper().createOrderDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.createOrder(null, orderDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderService.createOrder(customerId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderService.createOrder(customerId, new OrderDto(null, orderDto.getPaymentTotalWithVat(), orderDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderService.createOrder(customerId, new OrderDto(orderDto.getUuId(), null, orderDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderService.createOrder(customerId, new OrderDto(orderDto.getUuId(), orderDto.getPaymentTotalWithVat(), null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithExistingUuId() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        // Order
        final OrderDto orderDto = getServicesImplTestHelper().createOrderDto();
        final Long existingOrderId = 2L;
        final Order existingOrder = getServicesImplTestHelper().createOrder();
        existingOrder.setId(existingOrderId);
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(orderRepository.findByUuId(eq(orderDto.getUuId()))).andReturn(existingOrder).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.createOrder(customerId, orderDto);
            fail("Exception should be thrown");
        } catch (final OrderAlreadyExistsForUuIdException ex) {
            // Expected
            assertOrderAlreadyExistsForUuIdException(ex, existingOrderId, orderDto.getUuId());
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrder() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        // Order
        final OrderDto orderDto = getServicesImplTestHelper().createOrderDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(orderRepository.findByUuId(eq(orderDto.getUuId()))).andReturn(null).once();
        expect(orderRepository.save(isA(Order.class))).andAnswer(() -> (Order) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final Order result = orderService.createOrder(customerId, orderDto);
        getServicesImplTestHelper().assertOrder(result, orderDto);
        assertNotNull(result.getCustomer());
        assertEquals(customer, result.getCustomer());
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfOrdersIsPaidWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.checkIfOrderIsPaid(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfOrdersIsPaidWithNotExistingOrderId() {
        // Test data
        final Long orderId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findOne(eq(orderId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.checkIfOrderIsPaid(orderId);
            fail("Exception should be thrown");
        } catch (final OrderNotFoundForIdException ex) {
            // Expected
            assertOrderNotFoundForIdException(ex, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfOrdersIsPaidWhenItIsNotPaidYet() {
        // Test data
        final Long orderId = 1L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findOne(eq(orderId))).andReturn(order).once();
        expect(orderPaymentChannelRepository.findByOrder(eq(order))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = orderService.checkIfOrderIsPaid(orderId);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfOrdersIsPaidWhenItIsAlreadyPaid() {
        // Test data
        final Long orderId = 1L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        final Long orderPaymentChanelId = 2L;
        final OrderPaymentChannel orderPaymentChannel = getServicesImplTestHelper().createOrderProviderPaymentChannel();
        orderPaymentChannel.setId(orderPaymentChanelId);
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findOne(eq(orderId))).andReturn(order).once();
        expect(orderPaymentChannelRepository.findByOrder(eq(order))).andReturn(orderPaymentChannel).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = orderService.checkIfOrderIsPaid(orderId);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetTotalAmountOrderedForCustomerWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.getTotalAmountOrderedForCustomer(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetTotalAmountOrderedForCustomer() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(orderRepository.getTotalAmountPaidForCustomer(eq(customerId))).andReturn(BigDecimal.ONE).once();
        // Replay
        replayAll();
        // Run test scenario
        final BigDecimal result = orderService.getTotalAmountOrderedForCustomer(customerId);
        assertEquals(result, BigDecimal.ONE);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrdersCountForCustomerWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.getOrdersCountForCustomer(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrdersCountForCustomer() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long ordersCount = 101L;
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(orderRepository.countByCustomer(eq(customer))).andReturn(ordersCount).once();
        // Replay
        replayAll();
        // Run test scenario
        final Long result = orderService.getOrdersCountForCustomer(customerId);
        assertEquals(ordersCount, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testMarkOrderPaidWithInvalidArguments() {
        // Test data
        final Long orderId = 1L;
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.markOrderPaid(null, orderProviderPaymentChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderService.markOrderPaid(orderId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testMarkOrderPaidWithNotExistingOrderId() {
        // Test data
        final Long orderId = 1L;
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        // Reset
        resetAll();
        // Expectations
        orderProviderPaymentChannelHandler.assertOrderPaymentChannel(eq(orderProviderPaymentChannelDto));
        expectLastCall();
        expect(orderRepository.findByIdWithWriteLockFlushedAndFreshData(eq(orderId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.markOrderPaid(orderId, orderProviderPaymentChannelDto);
            fail("Exception should be thrown");
        } catch (final OrderNotFoundForIdException ex) {
            // Expected
            assertOrderNotFoundForIdException(ex, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testMarkOrderPaidWhenPaymentChannelAlreadyExists() {
        // Test data
        final Long orderId = 1L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        final Long orderPaymentChannelId = 2L;
        final OrderPaymentChannel orderPaymentChannel = getServicesImplTestHelper().createOrderProviderPaymentChannel(orderProviderPaymentChannelDto);
        orderPaymentChannel.setId(orderPaymentChannelId);
        // Reset
        resetAll();
        // Expectations
        orderProviderPaymentChannelHandler.assertOrderPaymentChannel(eq(orderProviderPaymentChannelDto));
        expectLastCall();
        expect(orderRepository.findByIdWithWriteLockFlushedAndFreshData(eq(orderId))).andReturn(order).once();
        expect(orderPaymentChannelRepository.findByOrder(eq(order))).andReturn(orderPaymentChannel).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.markOrderPaid(orderId, orderProviderPaymentChannelDto);
            fail("Exception should be thrown");
        } catch (final OrderAlreadyPaidException ex) {
            // Expected
            assertOrderAlreadyPaidException(ex, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testMarkOrderPaidWithPaymentProviderChannel() {
        // Test data
        final Long orderId = 1L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        final Date orderUpdatedDate = order.getUpdated();
        final OrderState orderInitialState = order.getLastState();
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        final OrderProviderPaymentChannel orderProviderPaymentChannel = getServicesImplTestHelper().createOrderProviderPaymentChannel(orderProviderPaymentChannelDto);
        final int initialOrderStatesCount = order.getStateChangeHistoryRecords().size();
        // Reset
        resetAll();
        // Expectations
        orderProviderPaymentChannelHandler.assertOrderPaymentChannel(eq(orderProviderPaymentChannelDto));
        expectLastCall();
        expect(orderRepository.findByIdWithWriteLockFlushedAndFreshData(eq(orderId))).andReturn(order).once();
        expect(orderPaymentChannelRepository.findByOrder(eq(order))).andReturn(null).once();
        orderProviderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(eq(orderId), eq(orderProviderPaymentChannelDto));
        expectLastCall();
        expect(orderProviderPaymentChannelHandler.convertOrderPaymentChannelDto(eq(orderProviderPaymentChannelDto))).andReturn(orderProviderPaymentChannel).once();
        expect(orderRepository.saveAndFlush(isA(Order.class))).andAnswer(() -> {
            final Order updatedOrder = (Order) getCurrentArguments()[0];
            assertOrderAfterPayment(updatedOrder, orderUpdatedDate, orderInitialState, initialOrderStatesCount);
            return order;
        }).once();
        persistenceUtilityService.runAfterTransactionCommitIsSuccessful(isA(Runnable.class), eq(false));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            assertNotNull(runnable);
            runnable.run();
            return null;
        }).once();
        applicationEventDistributionService.publishSynchronousEvent(EasyMock.eq(new OrderPaidEvent(orderId)));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentChannel orderPaymentChannel = orderService.markOrderPaid(orderId, orderProviderPaymentChannelDto);
        assertNotNull(orderPaymentChannel);
        assertEquals(orderProviderPaymentChannel, orderPaymentChannel);
        assertOrderAfterPayment(orderPaymentChannel.getOrder(), orderUpdatedDate, orderInitialState, initialOrderStatesCount);
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderStateWithInvalidArguments() {
        // Test data
        final Long orderId = 1l;
        final OrderStateChangeHistoryRecordDto recordDto = getServicesImplTestHelper().createOrderStateChangeHistoryRecordDto();
        final String paymentUuid = UUID.randomUUID().toString();
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(recordDto);
        orderStateChangeDto.setOrderId(orderId);
        orderStateChangeDto.setPaymentUuid(paymentUuid);
        OrderStateChangeDto invalidDto;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            invalidDto = null;
            orderService.updateOrderState(invalidDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            invalidDto = SerializationUtils.clone(orderStateChangeDto);
            invalidDto.setOrderId(null);
            orderService.updateOrderState(invalidDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            invalidDto = SerializationUtils.clone(orderStateChangeDto);
            invalidDto.setOrderStateChangeHistoryRecordDto(null);
            orderService.updateOrderState(invalidDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            invalidDto = SerializationUtils.clone(orderStateChangeDto);
            invalidDto.getOrderStateChangeHistoryRecordDto().setUpdatedState(null);
            orderService.updateOrderState(invalidDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderStateWithNotExistingOrderId() {
        // Test data
        final Long orderId = 1l;
        final OrderStateChangeHistoryRecordDto recordDto = getServicesImplTestHelper().createOrderStateChangeHistoryRecordDto();
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderId(orderId);
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(recordDto);
        orderStateChangeDto.setPaymentUuid(UUID.randomUUID().toString());
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findByIdWithWriteLockFlushedAndFreshData(eq(orderId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.updateOrderState(orderStateChangeDto);
            fail("Exception should be thrown");
        } catch (final OrderNotFoundForIdException ex) {
            // Expected
            assertOrderNotFoundForIdException(ex, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderState() {
        // Test data
        final Long orderId = 1l;
        final OrderStateChangeHistoryRecordDto recordDto = getServicesImplTestHelper().createOrderStateChangeHistoryRecordDto();
        final Order order = getServicesImplTestHelper().createOrder();
        final OrderState orderStateBeforeUpdate = order.getLastState();
        final int historyRecordsCount = order.getStateChangeHistoryRecords().size();
        final Date orderUpdatedDate = order.getUpdated();
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderId(orderId);
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(recordDto);
        orderStateChangeDto.setPaymentUuid(UUID.randomUUID().toString());
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findByIdWithWriteLockFlushedAndFreshData(eq(orderId))).andReturn(order).once();
        expect(orderRepository.saveAndFlush(isA(Order.class))).andAnswer(() -> {
            final Order updatedOrder = (Order) getCurrentArguments()[0];
            assertTrue(updatedOrder.getUpdated().compareTo(orderUpdatedDate) >= 0 && orderUpdatedDate != updatedOrder.getUpdated());
            return updatedOrder;
        }).once();
        persistenceUtilityService.runAfterTransactionCommitIsSuccessful(isA(Runnable.class), eq(false));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).once();
        applicationEventDistributionService.publishSynchronousEvent(eq(new OrderStateChangedEvent(orderId, recordDto.getUpdatedState(), orderStateChangeDto.getPaymentUuid())));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderStateChangeHistoryRecord historyRecord = orderService.updateOrderState(orderStateChangeDto);
        getServicesImplTestHelper().assertOrderStateChangeHistoryRecordDto(historyRecord, recordDto);
        getServicesImplTestHelper().assertOrderLastState(order, orderStateBeforeUpdate, recordDto.getUpdatedState(), historyRecordsCount + 1);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.getOrderByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderByUuIdWithNotExistingUuId() {
        // Expected
        final String uuId = "not_existing_uuid";
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findByUuId(eq(uuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.getOrderByUuId(uuId);
            fail("Exception should be thrown");
        } catch (final OrderNotFoundForUuIdException ex) {
            // Expected
            assertOrderNotFoundForUuIdException(ex, uuId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderByUuId() {
        // Expected
        final Order order = getServicesImplTestHelper().createOrder();
        final String uuId = order.getUuId();
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findByUuId(eq(uuId))).andReturn(order).once();
        // Replay
        replayAll();
        // Run test scenario
        final Order result = orderService.getOrderByUuId(uuId);
        assertEquals(order, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.getOrderById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderByIdWithNotExistingId() {
        // Test data
        final Long orderId = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findOne(eq(orderId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderService.getOrderById(orderId);
            fail("Exception should be thrown");
        } catch (final OrderNotFoundForIdException ex) {
            // Expected
            assertOrderNotFoundForIdException(ex, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderById() {
        // Test data
        final Long orderId = 1l;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        // Reset
        resetAll();
        // Expectations
        expect(orderRepository.findOne(eq(orderId))).andReturn(order).once();
        // Replay
        replayAll();
        // Run test scenario
        final Order result = orderService.getOrderById(orderId);
        assertEquals(order, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertOrderNotFoundForIdException(final OrderNotFoundForIdException ex, final Long orderId) {
        assertEquals(orderId, ex.getId());
        assertEquals(Order.class, ex.getEntityClass());
    }

    private void assertOrderNotFoundForUuIdException(final OrderNotFoundForUuIdException ex, final String uuId) {
        Assert.assertEquals(Order.class, ex.getEntityClass());
        Assert.assertEquals(uuId, ex.getUuId());
    }

    private void assertOrderAlreadyPaidException(final OrderAlreadyPaidException ex, final Long orderId) {
        assertEquals(orderId, ex.getOrderId());
    }

    private void assertOrderAfterPayment(final Order order, final Date orderInitialUpdatedDate, final OrderState orderInitialState, final int initialOrderStatesCount) {
        assertTrue(order.getUpdated().compareTo(orderInitialUpdatedDate) >= 0 && orderInitialUpdatedDate != order.getUpdated());
        getServicesImplTestHelper().assertOrderLastState(order, orderInitialState, OrderState.PAID, initialOrderStatesCount + 1);
    }

    private void assertOrderAlreadyExistsForUuIdException(final OrderAlreadyExistsForUuIdException ex, final Long orderId, final String uuId) {
        assertEquals(uuId, ex.getUuId());
        assertEquals(orderId, ex.getOrderId());

    }

}
