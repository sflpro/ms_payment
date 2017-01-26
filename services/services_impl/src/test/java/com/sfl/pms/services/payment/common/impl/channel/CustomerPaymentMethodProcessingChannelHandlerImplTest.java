package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.CustomerPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.exception.order.InvalidCustomerPaymentMethodException;
import com.sfl.pms.services.payment.common.model.channel.CustomerPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
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
 * Date: 7/7/15
 * Time: 12:23 PM
 */
public class CustomerPaymentMethodProcessingChannelHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodProcessingChannelHandlerImpl customerPaymentMethodProcessingChannelHandler = new CustomerPaymentMethodProcessingChannelHandlerImpl();

    @Mock
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public CustomerPaymentMethodProcessingChannelHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final CustomerPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new CustomerPaymentMethodProcessingChannelDto(null, channelDto.getCustomerPaymentMethodId()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new CustomerPaymentMethodProcessingChannelDto(channelDto.getPaymentProviderIntegrationType(), null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentProcessingChannelDtoWithInvalidCustomer() {
        // Test data
        final CustomerPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final Long providedCustomerId = 1L;
        final Customer providedCustomer = getServicesImplTestHelper().createCustomer();
        providedCustomer.setId(providedCustomerId);
        final Long paymentMethodCustomerId = 2L;
        final Customer paymentMethodCustomer = getServicesImplTestHelper().createCustomer();
        paymentMethodCustomer.setId(paymentMethodCustomerId);
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(channelDto.getCustomerPaymentMethodId());
        customerPaymentMethod.setCustomer(paymentMethodCustomer);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(customerPaymentMethod.getId()))).andReturn(customerPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, providedCustomer);
            fail("Exception should be thrown");
        } catch (final InvalidCustomerPaymentMethodException ex) {
            // Expected
            assertInvalidCustomerPaymentMethodException(ex, providedCustomerId, customerPaymentMethod.getId(), paymentMethodCustomerId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentProcessingChannel() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(channelDto.getCustomerPaymentMethodId());
        customerPaymentMethod.setCustomer(customer);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(channelDto.getCustomerPaymentMethodId()))).andReturn(customerPaymentMethod).times(2);
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingChannel paymentProcessingChannel = customerPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, customer);
        assertTrue(paymentProcessingChannel instanceof CustomerPaymentMethodProcessingChannel);
        final CustomerPaymentMethodProcessingChannel paymentMethodProcessingChannel = (CustomerPaymentMethodProcessingChannel) paymentProcessingChannel;
        getServicesImplTestHelper().assertCustomerPaymentMethodProcessingChannel(paymentMethodProcessingChannel, channelDto);
        assertEquals(customerPaymentMethod, paymentMethodProcessingChannel.getCustomerPaymentMethod());
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final CustomerPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new CustomerPaymentMethodProcessingChannelDto(null, channelDto.getCustomerPaymentMethodId()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new CustomerPaymentMethodProcessingChannelDto(channelDto.getPaymentProviderIntegrationType(), null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDtoWithInvalidCustomer() {
        // Test data
        final CustomerPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final Long providedCustomerId = 1L;
        final Customer providedCustomer = getServicesImplTestHelper().createCustomer();
        providedCustomer.setId(providedCustomerId);
        final Long paymentMethodCustomerId = 2L;
        final Customer paymentMethodCustomer = getServicesImplTestHelper().createCustomer();
        paymentMethodCustomer.setId(paymentMethodCustomerId);
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(channelDto.getCustomerPaymentMethodId());
        customerPaymentMethod.setCustomer(paymentMethodCustomer);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(customerPaymentMethod.getId()))).andReturn(customerPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, providedCustomer);
            fail("Exception should be thrown");
        } catch (final InvalidCustomerPaymentMethodException ex) {
            // Expected
            assertInvalidCustomerPaymentMethodException(ex, providedCustomerId, customerPaymentMethod.getId(), paymentMethodCustomerId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDto() {
        // Test data
        final CustomerPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto();
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(channelDto.getCustomerPaymentMethodId());
        customerPaymentMethod.setCustomer(customer);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(channelDto.getCustomerPaymentMethodId()))).andReturn(customerPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        customerPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, customer);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertInvalidCustomerPaymentMethodException(final InvalidCustomerPaymentMethodException ex, final Long providedCustomerId, final Long paymentMethodId, final Long paymentMethodCustomerId) {
        assertEquals(providedCustomerId, ex.getProvidedCustomerId());
        assertEquals(paymentMethodCustomerId, ex.getPaymentMethodCustomerId());
        assertEquals(paymentMethodId, ex.getPaymentMethodId());
    }
}
