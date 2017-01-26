package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.ProvidedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.ProvidedPaymentMethodProcessingChannel;
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
public class ProvidedPaymentMethodProcessingChannelHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private ProvidedPaymentMethodProcessingChannelHandlerImpl providedPaymentMethodProcessingChannelHandler = new ProvidedPaymentMethodProcessingChannelHandlerImpl();

    /* Constructors */
    public ProvidedPaymentMethodProcessingChannelHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final ProvidedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            providedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new ProvidedPaymentMethodProcessingChannelDto(null, channelDto.getPaymentMethodType()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new ProvidedPaymentMethodProcessingChannelDto(channelDto.getPaymentProviderIntegrationType(), null), customer);
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
        final ProvidedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingChannel paymentProcessingChannel = providedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, customer);
        assertTrue(paymentProcessingChannel instanceof ProvidedPaymentMethodProcessingChannel);
        final ProvidedPaymentMethodProcessingChannel paymentMethodProcessingChannel = (ProvidedPaymentMethodProcessingChannel) paymentProcessingChannel;
        getServicesImplTestHelper().assertProvidedPaymentMethodProcessingChannel(paymentMethodProcessingChannel, channelDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final ProvidedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            providedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new ProvidedPaymentMethodProcessingChannelDto(null, channelDto.getPaymentMethodType()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            providedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new ProvidedPaymentMethodProcessingChannelDto(channelDto.getPaymentProviderIntegrationType(), null), customer);
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
        final ProvidedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createProvidedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        providedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, customer);
        // Verify
        verifyAll();
    }
}
