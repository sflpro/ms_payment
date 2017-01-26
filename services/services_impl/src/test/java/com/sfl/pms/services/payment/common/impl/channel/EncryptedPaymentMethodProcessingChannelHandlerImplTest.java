package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.EncryptedPaymentMethodProcessingChannel;
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
public class EncryptedPaymentMethodProcessingChannelHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private EncryptedPaymentMethodProcessingChannelHandlerImpl encryptedPaymentMethodProcessingChannelHandler = new EncryptedPaymentMethodProcessingChannelHandlerImpl();

    /* Constructors */
    public EncryptedPaymentMethodProcessingChannelHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testConvertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final EncryptedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new EncryptedPaymentMethodProcessingChannelDto(null, channelDto.getEncryptedPaymentMethodInformation()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(new EncryptedPaymentMethodProcessingChannelDto(channelDto.getPaymentProviderIntegrationType(), null), customer);
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
        final EncryptedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingChannel paymentProcessingChannel = encryptedPaymentMethodProcessingChannelHandler.convertPaymentProcessingChannelDto(channelDto, customer);
        assertTrue(paymentProcessingChannel instanceof EncryptedPaymentMethodProcessingChannel);
        final EncryptedPaymentMethodProcessingChannel paymentMethodProcessingChannel = (EncryptedPaymentMethodProcessingChannel) paymentProcessingChannel;
        getServicesImplTestHelper().assertEncryptedPaymentMethodProcessingChannel(paymentMethodProcessingChannel, channelDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertPaymentProcessingChannelDtoWithInvalidArguments() {
        // Test data
        final EncryptedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(null, customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(getServicesImplTestHelper().createDeferredPaymentMethodProcessingChannelDto(), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new EncryptedPaymentMethodProcessingChannelDto(null, channelDto.getEncryptedPaymentMethodInformation()), customer);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(new EncryptedPaymentMethodProcessingChannelDto(channelDto.getPaymentProviderIntegrationType(), null), customer);
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
        final EncryptedPaymentMethodProcessingChannelDto channelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        final Customer customer = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        encryptedPaymentMethodProcessingChannelHandler.assertPaymentProcessingChannelDto(channelDto, customer);
        // Verify
        verifyAll();
    }
}
