package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.EncryptedPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 3:44 PM
 */
@Component
public class EncryptedPaymentMethodProcessingChannelHandlerImpl implements EncryptedPaymentMethodProcessingChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvidedPaymentMethodProcessingChannelHandlerImpl.class);

    /* Constructors */
    public EncryptedPaymentMethodProcessingChannelHandlerImpl() {
        LOGGER.debug("Initializing encrypted payment method processing channel handler");
    }

    @Override
    public void assertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer) {
        LOGGER.debug("Asserting payment processing channel DTO - {}", paymentProcessingChannelDto);
        Assert.notNull(paymentProcessingChannelDto, "Payment processing DTO should not be null");
        Assert.notNull(paymentProcessingChannelDto.getPaymentProviderIntegrationType(), "Payment provider integration type in payment method type in payment processing DTO should not be null");
        Assert.notNull(customer, "Customer should not be null");
        Assert.isInstanceOf(EncryptedPaymentMethodProcessingChannelDto.class, paymentProcessingChannelDto);
        final EncryptedPaymentMethodProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = (EncryptedPaymentMethodProcessingChannelDto) paymentProcessingChannelDto;
        Assert.notNull(encryptedPaymentMethodProcessingChannelDto.getEncryptedPaymentMethodInformation(), "Encrypted payment method information in payment processing DTO should not be null");
    }

    @Nonnull
    @Override
    public PaymentProcessingChannel convertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer) {
        assertPaymentProcessingChannelDto(paymentProcessingChannelDto, customer);
        LOGGER.debug("Converting payment processing channel DTO - {}", paymentProcessingChannelDto);
        final EncryptedPaymentMethodProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = (EncryptedPaymentMethodProcessingChannelDto) paymentProcessingChannelDto;
        // Create domain model object
        final EncryptedPaymentMethodProcessingChannel paymentMethodProcessingChannel = new EncryptedPaymentMethodProcessingChannel();
        encryptedPaymentMethodProcessingChannelDto.updateDomainEntityProperties(paymentMethodProcessingChannel);
        return paymentMethodProcessingChannel;
    }
}
