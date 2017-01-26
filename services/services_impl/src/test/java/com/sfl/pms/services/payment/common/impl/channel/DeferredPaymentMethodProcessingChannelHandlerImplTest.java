package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.DeferredPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.DeferredPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 12:23 PM
 */
public class DeferredPaymentMethodProcessingChannelHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private DeferredPaymentMethodProcessingChannelHandlerImpl deferredPaymentMethodProcessingChannelHandler = new DeferredPaymentMethodProcessingChannelHandlerImpl();

    /* Constructors */
    public DeferredPaymentMethodProcessingChannelHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final DeferredPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            deferredPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            deferredPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            deferredPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(getServicesImplTestHelper().createCustomerPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            deferredPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new DeferredPaymentMethodProcessingChannelDto(null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentProcessingChannel() {
        // Test data
        final DeferredPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingChannel paymentProcessingChannel = deferredPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, customer);
        assertTrue(paymentProcessingChannel instanceof DeferredPaymentMethodProcessingChannel);
        final DeferredPaymentMethodProcessingChannel paymentMethodProcessingChannel = (DeferredPaymentMethodProcessingChannel) paymentProcessingChannel;
        getServicesImplTestHelper().assertDeferredPaymentMethodProcessingChannel(paymentMethodProcessingChannel, channelDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final DeferredPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            deferredPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            deferredPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            deferredPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            deferredPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new DeferredPaymentMethodProcessingChannelDto(null), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDto() {
        // Test data
        final DeferredPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        deferredPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, customer);
        // Verify
        verifyAll();
    }
}
