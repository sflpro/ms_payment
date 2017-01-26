package com.sfl.pms.services.order;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.dto.OrderDto;
import com.sfl.pms.services.order.dto.OrderStateChangeDto;
import com.sfl.pms.services.order.dto.OrderStateChangeHistoryRecordDto;
import com.sfl.pms.services.order.dto.payment.provider.OrderProviderPaymentChannelDto;
import com.sfl.pms.services.order.exception.payment.OrderAlreadyPaidException;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.order.model.OrderStateChangeHistoryRecord;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 12:56 PM
 */
public class OrderServiceIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private OrderService orderService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public OrderServiceIntegrationTest() {

    }

    /* Test methods */
    @Test
    public void testCheckIfOrderIsPaid() {
        // Prepare data
        final Order order = getServicesTestHelper().createOrder();
        flushAndClear();
        // Check if order is paid
        boolean result = orderService.checkIfOrderIsPaid(order.getId());
        assertFalse(result);
        // Create payment
        final Payment payment = getServicesTestHelper().createOrderPayment(order);
        // Create order payment channel
        final OrderPaymentChannel orderPaymentChannel = orderService.markOrderPaid(order.getId(), new OrderProviderPaymentChannelDto(payment.getId()));
        assertNotNull(orderPaymentChannel);
        // Check again
        result = orderService.checkIfOrderIsPaid(order.getId());
        assertTrue(result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = orderService.checkIfOrderIsPaid(order.getId());
        assertTrue(result);
    }

    @Test
    public void testGetOrdersCountForCustomer() {
        // Prepare data
        final Customer firstCustomer = getServicesTestHelper().createCustomer("dummy@dummy.com");
        final Customer secondCustomer = getServicesTestHelper().createCustomer("ruben.dilanyan@sflpro.com");
        flushAndClear();
        // Get orders count
        Long result = orderService.getOrdersCountForCustomer(firstCustomer.getId());
        assertEquals(Long.valueOf(0), result);
        result = orderService.getOrdersCountForCustomer(secondCustomer.getId());
        assertEquals(Long.valueOf(0), result);
        // Create order and assert count
        Order order = getServicesTestHelper().createOrder(firstCustomer);
        assertNotNull(order);
        result = orderService.getOrdersCountForCustomer(firstCustomer.getId());
        assertEquals(Long.valueOf(1), result);
        result = orderService.getOrdersCountForCustomer(secondCustomer.getId());
        assertEquals(Long.valueOf(0), result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = orderService.getOrdersCountForCustomer(firstCustomer.getId());
        assertEquals(Long.valueOf(1), result);
        result = orderService.getOrdersCountForCustomer(secondCustomer.getId());
        assertEquals(Long.valueOf(0), result);
        // Create order for second customer
        order = getServicesTestHelper().createOrder(secondCustomer);
        assertNotNull(order);
        result = orderService.getOrdersCountForCustomer(firstCustomer.getId());
        assertEquals(Long.valueOf(1), result);
        result = orderService.getOrdersCountForCustomer(secondCustomer.getId());
        assertEquals(Long.valueOf(1), result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = orderService.getOrdersCountForCustomer(firstCustomer.getId());
        assertEquals(Long.valueOf(1), result);
        result = orderService.getOrdersCountForCustomer(secondCustomer.getId());
        assertEquals(Long.valueOf(1), result);

    }

    @Test
    public void testMarkOrderPaidWithPaymentProviderChannel() {
        // Prepare data
        final OrderPayment orderPayment = getServicesTestHelper().createOrderPayment();
        Order order = orderPayment.getOrder();
        assertNull(order.getPaymentChannel());
        final OrderProviderPaymentChannelDto paymentChannelDto = new OrderProviderPaymentChannelDto(orderPayment.getId());
        final OrderState orderInitialState = order.getLastState();
        final int initialOrderStatesCount = order.getStateChangeHistoryRecords().size();
        flushAndClear();
        // Update mark order as paid
        OrderPaymentChannel orderPaymentChannel = orderService.markOrderPaid(order.getId(), paymentChannelDto);
        order = orderPaymentChannel.getOrder();
        assertOrderPaymentProviderChannel(orderPaymentChannel, orderPayment, order, orderInitialState, initialOrderStatesCount);
        // Flush, reload and try again
        flushAndClear();
        order = orderService.getOrderById(order.getId());
        orderPaymentChannel = order.getPaymentChannel();
        assertOrderPaymentProviderChannel(orderPaymentChannel, orderPayment, order, orderInitialState, initialOrderStatesCount);
    }

    @Test
    public void testMarkOrderPaidWhenOrderIsAlreadyPaid() {
        // Prepare data
        final OrderPayment orderPayment = getServicesTestHelper().createOrderPayment();
        Order order = orderPayment.getOrder();
        final OrderProviderPaymentChannelDto paymentChannelDto = new OrderProviderPaymentChannelDto(orderPayment.getId());
        flushAndClear();
        // Update mark order as paid
        orderService.markOrderPaid(order.getId(), paymentChannelDto);
        try {
            orderService.markOrderPaid(order.getId(), paymentChannelDto);
            fail("Exception should be thrown");
        } catch (final OrderAlreadyPaidException ex) {
            // Expected
        }
        // Flush, reload and try again
        flushAndClear();
        try {
            orderService.markOrderPaid(order.getId(), paymentChannelDto);
            fail("Exception should be thrown");
        } catch (final OrderAlreadyPaidException ex) {
            // Expected
        }
    }

    @Test
    public void testUpdateOrderState() {
        // Prepare data
        Order order = getServicesTestHelper().createOrder();
        final OrderState orderStateBeforeUpdate = order.getLastState();
        final int historyRecordsCount = order.getStateChangeHistoryRecords().size();
        final OrderStateChangeHistoryRecordDto historyRecordDto = getServicesTestHelper().createOrderStateChangeHistoryRecordDto();
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderId(order.getId());
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(historyRecordDto);
        flushAndClear();
        // Update order state
        final OrderStateChangeHistoryRecord historyRecord = orderService.updateOrderState(orderStateChangeDto);
        getServicesTestHelper().assertOrderStateChangeHistoryRecordDto(historyRecord, historyRecordDto);
        getServicesTestHelper().assertOrderLastState(historyRecord.getOrder(), orderStateBeforeUpdate, historyRecordDto.getUpdatedState(), historyRecordsCount + 1);
        // Flush, reload and assert again
        flushAndClear();
        order = orderService.getOrderById(order.getId());
        getServicesTestHelper().assertOrderLastState(historyRecord.getOrder(), orderStateBeforeUpdate, historyRecordDto.getUpdatedState(), historyRecordsCount + 1);
    }

    @Test
    public void testGetOrderById() {
        // Create order
        final Order order = getServicesTestHelper().createOrder();
        flushAndClear();
        // Reload and assert
        final Order result = orderService.getOrderById(order.getId());
        assertEquals(order, result);
    }

    @Test
    public void testGetVehicleLicensePlateByUuId() {
        // Create order
        final Order order = getServicesTestHelper().createOrder();
        // Flush, reload and assert
        flushAndClear();
        final Order result = orderService.getOrderByUuId(order.getUuId());
        assertEquals(order, result);
    }

    @Test
    public void testCreateOrder() {
        // Create order
        final Customer customer = getServicesTestHelper().createCustomer();
        final OrderDto orderDto = getServicesTestHelper().createOrderDto();
        Order order = orderService.createOrder(customer.getId(), orderDto);
        getServicesTestHelper().assertCreatedOrder(order, customer, orderDto);
        // Flush reload and assert again
        flushAndClear();
        order = orderService.getOrderById(order.getId());
        getServicesTestHelper().assertCreatedOrder(order, customer, orderDto);
    }


    @Test
    public void testGetTotalAmountOrderedForCustomer() {
        // Prepare data
        final Customer firstCustomer = getServicesTestHelper().createCustomer("dummy@dummy.com");
        flushAndClear();

        // Get orders total amount
        BigDecimal result = orderService.getTotalAmountOrderedForCustomer(firstCustomer.getId());
        assertNull(result);

        // Create order and assert amount
        final Order firstOrder = getServicesTestHelper().createOrder(firstCustomer);
        assertNotNull(firstOrder);

        // Flush, clear and get amount paid for customer
        result = orderService.getTotalAmountOrderedForCustomer(firstCustomer.getId());
        assertNull(result);

        // Flush, clear and pay order and get amount for customer
        flushAndClear();
        getServicesTestHelper().createOrderAndMarkAsPaid(firstOrder);
        result = orderService.getTotalAmountOrderedForCustomer(firstCustomer.getId());

        assertEquals(0, firstOrder.getPaymentTotalWithVat().compareTo(result));
    }

    /* Utility methods */
    private void assertOrderPaymentProviderChannel(final OrderPaymentChannel orderPaymentChannel, final OrderPayment orderPayment, final Order order, final OrderState orderInitialState, final int initialOrderStatesCount) {
        getServicesTestHelper().assertOrderLastState(order, orderInitialState, OrderState.PAID, initialOrderStatesCount + 1);
        getServicesTestHelper().assertOrderProviderPaymentChannel(orderPaymentChannel, orderPayment, order);
    }
}
