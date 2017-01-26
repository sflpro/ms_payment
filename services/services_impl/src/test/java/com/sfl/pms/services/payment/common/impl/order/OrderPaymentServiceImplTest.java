package com.sfl.pms.services.payment.common.impl.order;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.persistence.repositories.payment.common.order.OrderPaymentRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.exception.payment.OrderAlreadyPaidException;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.dto.channel.CustomerPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.order.OrderPaymentDto;
import com.sfl.pms.services.payment.common.impl.AbstractPaymentServiceImplTest;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.channel.CustomerPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:26 PM
 */
public class OrderPaymentServiceImplTest extends AbstractPaymentServiceImplTest<OrderPayment> {

    /* Test subject and mocks */
    @TestSubject
    private OrderPaymentServiceImpl orderPaymentService = new OrderPaymentServiceImpl();

    @Mock
    private OrderPaymentRepository orderPaymentRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderService orderService;

    /* Constructors */
    public OrderPaymentServiceImplTest() {
    }

    @Test
    public void testCreatePaymentWithInvalidArguments() {
        // Test data
        final Long orderId = 1l;
        final OrderPaymentDto paymentDto = getServicesImplTestHelper().createOrderPaymentDto();
        final CustomerPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentService.createPayment(null, paymentDto, paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentService.createPayment(orderId, null, paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentService.createPayment(orderId, paymentDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentService.createPayment(orderId, new OrderPaymentDto(null, paymentDto.getAmount(), paymentDto.getPaymentMethodSurcharge(), paymentDto.getCurrency(), paymentDto.getClientIpAddress(), true), paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentService.createPayment(orderId, new OrderPaymentDto(paymentDto.getPaymentProviderType(), paymentDto.getAmount(), paymentDto.getPaymentMethodSurcharge(), null, paymentDto.getClientIpAddress(), true), paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentService.createPayment(orderId, new OrderPaymentDto(paymentDto.getPaymentProviderType(), null, paymentDto.getPaymentMethodSurcharge(), paymentDto.getCurrency(), paymentDto.getClientIpAddress(), true), paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentService.createPayment(orderId, new OrderPaymentDto(paymentDto.getPaymentProviderType(), paymentDto.getAmount(), null, paymentDto.getCurrency(), paymentDto.getClientIpAddress(), true), paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentWithInvalidPaymentProcessingChannel() {
        // Test data
        final Long orderId = 1l;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        final Long customerId = 2L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        order.setCustomer(customer);
        final Long customerPaymentMethodId = 2l;
        final OrderPaymentDto paymentDto = getServicesImplTestHelper().createOrderPaymentDto();
        final CustomerPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        paymentMethodProcessingChannelDto.setCustomerPaymentMethodId(customerPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        expect(orderService.getOrderById(eq(orderId))).andReturn(order).once();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(false).once();
        getPaymentProcessingChannelHandler(paymentMethodProcessingChannelDto.getType()).assertPaymentProcessingChannelDto(eq(paymentMethodProcessingChannelDto), eq(customer));
        expectLastCall().andThrow(new IllegalArgumentException()).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentService.createPayment(orderId, paymentDto, paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentWhenOrderIsAlreadyPaid() {
        // Test data
        final Long orderCustomerId = 3l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(orderCustomerId);
        final Long orderId = 1l;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        order.setCustomer(customer);
        final OrderPaymentDto paymentDto = getServicesImplTestHelper().createOrderPaymentDto();
        final CustomerPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        // Reset
        resetAll();
        // Expectations
        expect(orderService.getOrderById(eq(orderId))).andReturn(order).once();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(true).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentService.createPayment(orderId, paymentDto, paymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final OrderAlreadyPaidException ex) {
            // Expected
            assertOrderAlreadyPaidException(ex, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePayment() {
        // Test data
        final Long orderCustomerId = 3l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(orderCustomerId);
        final Long orderId = 1l;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        order.setCustomer(customer);
        final OrderPaymentDto paymentDto = getServicesImplTestHelper().createOrderPaymentDto();
        final CustomerPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final CustomerPaymentMethodProcessingChannel paymentMethodProcessingChannel = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannel(paymentMethodProcessingChannelDto);
        // Reset
        resetAll();
        // Expectations
        expect(orderService.getOrderById(eq(orderId))).andReturn(order).once();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(false).once();
        expect(orderPaymentRepository.save(isA(OrderPayment.class))).andAnswer(() -> (OrderPayment) getCurrentArguments()[0]).once();
        getPaymentProcessingChannelHandler(paymentMethodProcessingChannelDto.getType()).assertPaymentProcessingChannelDto(eq(paymentMethodProcessingChannelDto), eq(customer));
        expectLastCall().once();
        expect(getPaymentProcessingChannelHandler(paymentMethodProcessingChannelDto.getType()).convertPaymentProcessingChannelDto(eq(paymentMethodProcessingChannelDto), eq(customer))).andReturn(paymentMethodProcessingChannel).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPayment orderPayment = orderPaymentService.createPayment(orderId, paymentDto, paymentMethodProcessingChannelDto);
        getServicesImplTestHelper().assertOrderPayment(orderPayment, paymentDto);
        assertEquals(order, orderPayment.getOrder());
        assertEquals(order.getCustomer(), orderPayment.getCustomer());
        getServicesImplTestHelper().assertPaymentLastState(orderPayment, PaymentState.CREATED, null, 1);
        // Verify
        verifyAll();
    }


    /* Utility methods */
    private void assertOrderAlreadyPaidException(final OrderAlreadyPaidException ex, final Long orderId) {
        assertEquals(orderId, ex.getOrderId());
    }

    @Override
    protected AbstractPaymentService<OrderPayment> getService() {
        return orderPaymentService;
    }

    @Override
    protected AbstractPaymentRepository<OrderPayment> getRepository() {
        return orderPaymentRepository;
    }

    @Override
    protected OrderPayment getInstance() {
        return getServicesImplTestHelper().createOrderPayment();
    }

    @Override
    protected Class<OrderPayment> getInstanceClass() {
        return OrderPayment.class;
    }

}
