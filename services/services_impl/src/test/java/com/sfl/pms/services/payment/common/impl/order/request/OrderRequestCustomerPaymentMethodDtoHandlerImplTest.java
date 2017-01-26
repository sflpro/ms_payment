package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestCustomerPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestEncryptedPaymentMethodDto;
import com.sfl.pms.services.payment.common.exception.order.InvalidCustomerPaymentMethodException;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestCustomerPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
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
 * Date: 7/20/15
 * Time: 2:23 PM
 */
public class OrderRequestCustomerPaymentMethodDtoHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderRequestCustomerPaymentMethodDtoHandlerImpl orderRequestCustomerPaymentMethodDtoHandler = new OrderRequestCustomerPaymentMethodDtoHandlerImpl();

    @Mock
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public OrderRequestCustomerPaymentMethodDtoHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertOrderRequestPaymentMethodDtoWithInvalidArguments() {
        // Test data
        final OrderRequestCustomerPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestCustomerPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestCustomerPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestCustomerPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(new OrderRequestCustomerPaymentMethodDto(null), customer);
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
            orderRequestCustomerPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertOrderRequestPaymentMethodDtoWithInvalidCustomer() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long paymentMethodCustomerId = 2L;
        final Customer paymentMethodCustomer = getServicesImplTestHelper().createCustomer();
        paymentMethodCustomer.setId(paymentMethodCustomerId);
        final Long customerPaymentMethodId = 3L;
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(customerPaymentMethodId);
        customerPaymentMethod.setCustomer(paymentMethodCustomer);
        final OrderRequestCustomerPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        paymentMethodDto.setCustomerPaymentMethodId(customerPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(customerPaymentMethodId))).andReturn(customerPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestCustomerPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
            fail("Exception should be thrown");
        } catch (final InvalidCustomerPaymentMethodException ex) {
            // Expected
            assertCustomerPaymentMethodInvalidCustomerException(ex, customerId, paymentMethodCustomerId, customerPaymentMethodId);
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
        final Long customerPaymentMethodId = 3L;
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(customerPaymentMethodId);
        customerPaymentMethod.setCustomer(customer);
        final OrderRequestCustomerPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        paymentMethodDto.setCustomerPaymentMethodId(customerPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(customerPaymentMethodId))).andReturn(customerPaymentMethod).times(2);
        // Replay
        replayAll();
        // Run test scenario
        final OrderRequestPaymentMethod orderRequestPaymentMethod = orderRequestCustomerPaymentMethodDtoHandler.convertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        assertNotNull(orderRequestPaymentMethod);
        assertTrue(orderRequestPaymentMethod instanceof OrderRequestCustomerPaymentMethod);
        final OrderRequestCustomerPaymentMethod orderRequestCustomerPaymentMethod = (OrderRequestCustomerPaymentMethod) orderRequestPaymentMethod;
        getServicesImplTestHelper().assertOrderRequestCustomerPaymentMethod(orderRequestCustomerPaymentMethod, paymentMethodDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDtoWithInvalidArguments() {
        // Test data
        final OrderRequestCustomerPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestCustomerPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestCustomerPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderRequestCustomerPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(new OrderRequestCustomerPaymentMethodDto(null), customer);
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
            orderRequestCustomerPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertOrderRequestPaymentMethodDtoWithInvalidCustomer() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long paymentMethodCustomerId = 2L;
        final Customer paymentMethodCustomer = getServicesImplTestHelper().createCustomer();
        paymentMethodCustomer.setId(paymentMethodCustomerId);
        final Long customerPaymentMethodId = 3L;
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(customerPaymentMethodId);
        customerPaymentMethod.setCustomer(paymentMethodCustomer);
        final OrderRequestCustomerPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        paymentMethodDto.setCustomerPaymentMethodId(customerPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(customerPaymentMethodId))).andReturn(customerPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderRequestCustomerPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
            fail("Exception should be thrown");
        } catch (final InvalidCustomerPaymentMethodException ex) {
            // Expected
            assertCustomerPaymentMethodInvalidCustomerException(ex, customerId, paymentMethodCustomerId, customerPaymentMethodId);
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
        final Long customerPaymentMethodId = 3L;
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(customerPaymentMethodId);
        customerPaymentMethod.setCustomer(customer);
        final OrderRequestCustomerPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        paymentMethodDto.setCustomerPaymentMethodId(customerPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(customerPaymentMethodId))).andReturn(customerPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        orderRequestCustomerPaymentMethodDtoHandler.assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertCustomerPaymentMethodInvalidCustomerException(final InvalidCustomerPaymentMethodException ex, final Long providedCustomerId, final Long paymentMethodCustomerId, final Long paymentMethodId) {
        assertEquals(providedCustomerId, ex.getProvidedCustomerId());
        assertEquals(paymentMethodCustomerId, ex.getPaymentMethodCustomerId());
        assertEquals(paymentMethodId, ex.getPaymentMethodId());
    }

}
