package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.DeferredPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.DeferredPaymentMethodProcessingChannel;
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
public class DeferredPaymentMethodProcessingChannelHandlerImpl implements DeferredPaymentMethodProcessingChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeferredPaymentMethodProcessingChannelHandlerImpl.class);

    /* Constructors */
    public DeferredPaymentMethodProcessingChannelHandlerImpl() {
        LOGGER.debug("Initializing customer payment method processing channel handler");
    }

    @Override
    public void assertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer) {
        LOGGER.debug("Asserting payment processing channel DTO - {}", paymentProcessingChannelDto);
        Assert.notNull(paymentProcessingChannelDto, "Payment processing DTO should not be null");
        Assert.notNull(paymentProcessingChannelDto.getPaymentProviderIntegrationType(), "Payment provider integration type in payment method type in payment processing DTO should not be null");
        Assert.notNull(customer, "Customer should not be null");
        Assert.isInstanceOf(DeferredPaymentMethodProcessingChannelDto.class, paymentProcessingChannelDto);
    }

    @Nonnull
    @Override
    public PaymentProcessingChannel convertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer) {
        assertPaymentProcessingChannelDto(paymentProcessingChannelDto, customer);
        LOGGER.debug("Converting payment processing channel DTO - {}", paymentProcessingChannelDto);
        final DeferredPaymentMethodProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = (DeferredPaymentMethodProcessingChannelDto) paymentProcessingChannelDto;
        // Create domain model object
        final DeferredPaymentMethodProcessingChannel paymentMethodProcessingChannel = new DeferredPaymentMethodProcessingChannel();
        encryptedPaymentMethodProcessingChannelDto.updateDomainEntityProperties(paymentMethodProcessingChannel);
        return paymentMethodProcessingChannel;
    }
}
