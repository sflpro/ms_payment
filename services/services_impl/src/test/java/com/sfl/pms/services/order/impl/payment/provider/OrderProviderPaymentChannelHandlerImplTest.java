package com.sfl.pms.services.order.impl.payment.provider;

import com.sfl.pms.services.order.dto.payment.provider.OrderProviderPaymentChannelDto;
import com.sfl.pms.services.order.exception.payment.provider.InvalidOrderPaymentException;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.order.model.payment.provider.OrderProviderPaymentChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.order.OrderPaymentService;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 9:42 PM
 */
public class OrderProviderPaymentChannelHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderProviderPaymentChannelHandlerImpl orderProviderPaymentChannelHandler = new OrderProviderPaymentChannelHandlerImpl();

    @Mock
    private OrderPaymentService orderPaymentService;

    /* Constructors */
    public OrderProviderPaymentChannelHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertOrderPaymentChannelDtoWithInvalidArguments() {
        // Test data
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderProviderPaymentChannelHandler.convertOrderPaymentChannelDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderProviderPaymentChannelHandler.convertOrderPaymentChannelDto(new OrderProviderPaymentChannelDto(null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertOrderPaymentChannelDto() {
        // Test data
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        final OrderPayment orderPayment = getServicesImplTestHelper().createOrderPayment();
        orderPayment.setId(orderProviderPaymentChannelDto.getPaymentId());
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentService.getPaymentById(eq(orderProviderPaymentChannelDto.getPaymentId()))).andReturn(orderPayment).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentChannel orderPaymentChannel = orderProviderPaymentChannelHandler.convertOrderPaymentChannelDto(orderProviderPaymentChannelDto);
        assertNotNull(orderPaymentChannel);
        assertTrue(orderPaymentChannel instanceof OrderProviderPaymentChannel);
        final OrderProviderPaymentChannel orderProviderPaymentChannel = (OrderProviderPaymentChannel) orderPaymentChannel;
        assertEquals(orderPayment, orderProviderPaymentChannel.getPayment());
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderPaymentChannelWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderProviderPaymentChannelHandler.assertOrderPaymentChannel(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderProviderPaymentChannelHandler.assertOrderPaymentChannel(new OrderProviderPaymentChannelDto(null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    public void testAssertOrderPaymentChannel() {
        // Test data
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        orderProviderPaymentChannelHandler.assertOrderPaymentChannel(orderProviderPaymentChannelDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentChannelIsApplicableForOrderWithInvalidArguments() {
        // Test data
        final Long orderId = 1L;
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderProviderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(null, orderProviderPaymentChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderProviderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(orderId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderProviderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(orderId, new OrderProviderPaymentChannelDto(null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentChannelIsApplicableForOrderWithInvalidOrderPayment() {
        // Test data
        final Long orderId = 1L;
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        final Long orderPaymentId = 2L;
        orderProviderPaymentChannelDto.setPaymentId(orderPaymentId);
        final OrderPayment orderPayment = getServicesImplTestHelper().createOrderPayment();
        orderPayment.setId(orderPaymentId);
        final Long orderPaymentOrderId = 3L;
        final Order orderPaymentOrder = getServicesImplTestHelper().createOrder();
        orderPaymentOrder.setId(orderPaymentOrderId);
        orderPayment.setOrder(orderPaymentOrder);
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentService.getPaymentById(eq(orderPaymentId))).andReturn(orderPayment).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderProviderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(orderId, orderProviderPaymentChannelDto);
            fail("Exception should be thrown");
        } catch (final InvalidOrderPaymentException ex) {
            // Expected
            assertInvalidOrderPaymentException(ex, orderPaymentId, orderPaymentOrderId, orderId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentChannelIsApplicableForOrder() {
        // Test data
        final Long orderId = 1L;
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = getServicesImplTestHelper().createOrderProviderPaymentChannelDto();
        final Long orderPaymentId = 2L;
        orderProviderPaymentChannelDto.setPaymentId(orderPaymentId);
        final OrderPayment orderPayment = getServicesImplTestHelper().createOrderPayment();
        orderPayment.setId(orderPaymentId);
        final Order orderPaymentOrder = getServicesImplTestHelper().createOrder();
        orderPaymentOrder.setId(orderId);
        orderPayment.setOrder(orderPaymentOrder);
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentService.getPaymentById(eq(orderPaymentId))).andReturn(orderPayment).once();
        // Replay
        replayAll();
        // Run test scenario
        orderProviderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(orderId, orderProviderPaymentChannelDto);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertInvalidOrderPaymentException(final InvalidOrderPaymentException ex, final Long orderPaymentId, final Long orderPaymentOrderId, final Long expectedOrderId) {
        assertEquals(orderPaymentId, ex.getOrderPaymentId());
        assertEquals(expectedOrderId, ex.getExpectedOrderId());
        assertEquals(orderPaymentOrderId, ex.getOrderPaymentOrderId());
    }

}
