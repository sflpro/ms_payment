package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestEncryptedPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestRedirectPaymentMethodDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestEncryptedPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
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
public class OrderRequestEncryptedPaymentMethodDtoHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderRequestEncryptedPaymentMethodDtoHandlerImpl orderRequestEncryptedPaymentMethodDtoHandler = new OrderRequestEncryptedPaymentMethodDtoHandlerImpl();

    /* Constructors */
    public OrderRequestEncryptedPaymentMethodDtoHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertOrderRequestPaymentMethodDtoWithInvalidArguments() {
        // Test data
        final OrderRequestEncryptedPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(new OrderRequestEncryptedPaymentMethodDto(null, paymentMethodDto.getPaymentProviderType(), paymentMethodDto.getPaymentMethodGroupType()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(new OrderRequestEncryptedPaymentMethodDto(paymentMethodDto.getEncryptedData(), null, paymentMethodDto.getPaymentMethodGroupType()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(new OrderRequestEncryptedPaymentMethodDto(paymentMethodDto.getEncryptedData(), paymentMethodDto.getPaymentProviderType(), null), customer);
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
        final OrderRequestRedirectPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
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
        final OrderRequestEncryptedPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final OrderRequestPaymentMethod orderRequestPaymentMethod = orderRequestEncryptedPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        assertNotNull(orderRequestPaymentMethod);
        assertTrue(orderRequestPaymentMethod instanceof OrderRequestEncryptedPaymentMethod);
        final OrderRequestEncryptedPaymentMethod orderRequestCustomerPaymentMethod = (OrderRequestEncryptedPaymentMethod) orderRequestPaymentMethod;
        getServicesImplTestHelper().assertOrderRequestEncryptedPaymentMethod(orderRequestCustomerPaymentMethod, paymentMethodDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDtoWithInvalidArguments() {
        // Test data
        final OrderRequestEncryptedPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(new OrderRequestEncryptedPaymentMethodDto(null, paymentMethodDto.getPaymentProviderType(), paymentMethodDto.getPaymentMethodGroupType()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(new OrderRequestEncryptedPaymentMethodDto(paymentMethodDto.getEncryptedData(), null, paymentMethodDto.getPaymentMethodGroupType()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(new OrderRequestEncryptedPaymentMethodDto(paymentMethodDto.getEncryptedData(), paymentMethodDto.getPaymentProviderType(), null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDtoWithInvalidPaymentMethodType() {
        // Test data
        final OrderRequestRedirectPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
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
        final OrderRequestEncryptedPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        orderRequestEncryptedPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        // Verify
        verifyAll();
    }

}
