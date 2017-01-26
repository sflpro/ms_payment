package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestEncryptedPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestRedirectPaymentMethodDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestRedirectPaymentMethod;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 2:23 PM
 */
public class OrderRequestRedirectPaymentMethodDtoHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderRequestRedirectPaymentMethodDtoHandlerImpl orderRequestRedirectPaymentMethodDtoHandler = new OrderRequestRedirectPaymentMethodDtoHandlerImpl();

    /* Constructors */
    public OrderRequestRedirectPaymentMethodDtoHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertOrderRequestPaymentMethodDtoWithInvalidArguments() {
        // Test data
        final OrderRequestRedirectPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestRedirectPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestRedirectPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestRedirectPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(new OrderRequestRedirectPaymentMethodDto(PaymentMethodType.IDEAL, null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertOrderRequestPaymentMethodDtoWithInvalidPaymentMethodType() {
        // Test data
        final OrderRequestEncryptedPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestRedirectPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertOrderRequestPaymentMethodDto() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final OrderRequestRedirectPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final OrderRequestPaymentMethod orderRequestPaymentMethod = orderRequestRedirectPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        assertNotNull(orderRequestPaymentMethod);
        assertTrue(orderRequestPaymentMethod instanceof OrderRequestRedirectPaymentMethod);
        final OrderRequestRedirectPaymentMethod orderRequestRedirectPaymentMethod = (OrderRequestRedirectPaymentMethod) orderRequestPaymentMethod;
        getServicesImplTestHelper().assertOrderRequestRedirectPaymentMethod(orderRequestRedirectPaymentMethod, paymentMethodDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDtoWithInvalidArguments() {
        // Test data
        final OrderRequestRedirectPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestRedirectPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestRedirectPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestRedirectPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(new OrderRequestRedirectPaymentMethodDto(PaymentMethodType.IDEAL, null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDtoWithInvalidPaymentMethodType() {
        // Test data
        final OrderRequestEncryptedPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestRedirectPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDto() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final OrderRequestRedirectPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        orderRequestRedirectPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        // Verify
        verifyAll();
    }

}
