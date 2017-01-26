package com.sfl.pms.services.payment.common.impl.channel;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.channel.CustomerPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.exception.order.InvalidCustomerPaymentMethodException;
import com.sfl.pms.services.payment.common.model.channel.CustomerPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerPaymentMethodProcessingChannelHandlerImpl implements CustomerPaymentMethodProcessingChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodProcessingChannelHandlerImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public CustomerPaymentMethodProcessingChannelHandlerImpl() {
        LOGGER.debug("Initializing customer payment method processing channel handler");
    }

    @Override
    public void assertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer) {
        LOGGER.debug("Asserting payment processing channel DTO - {}", paymentProcessingChannelDto);
        Assert.notNull(paymentProcessingChannelDto, "Payment processing DTO should not be null");
        Assert.notNull(paymentProcessingChannelDto.getPaymentProviderIntegrationType(), "Payment provider integration type in payment method type in payment processing DTO should not be null");
        Assert.notNull(customer, "Customer should not be null");
        Assert.isInstanceOf(CustomerPaymentMethodProcessingChannelDto.class, paymentProcessingChannelDto);
        final CustomerPaymentMethodProcessingChannelDto customerPaymentMethodProcessingChannelDto = (CustomerPaymentMethodProcessingChannelDto) paymentProcessingChannelDto;
        Assert.notNull(customerPaymentMethodProcessingChannelDto.getCustomerPaymentMethodId(), "Customer payment method information in payment processing DTO should not be null");
        // Load customer payment method and assert
        final CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodService.getCustomerPaymentMethodById(customerPaymentMethodProcessingChannelDto.getCustomerPaymentMethodId());
        final Long providedCustomerId = customer.getId();
        final Long paymentMethodCustomerId = customerPaymentMethod.getCustomer().getId();
        if (!providedCustomerId.equals(paymentMethodCustomerId)) {
            LOGGER.error("Provided customer id - {} differs from customer of payment method with id - {}. Payment method customer id - {}", providedCustomerId, customerPaymentMethod.getId(), paymentMethodCustomerId);
            throw new InvalidCustomerPaymentMethodException(providedCustomerId, customerPaymentMethod.getId(), paymentMethodCustomerId);
        }
    }

    @Nonnull
    @Override
    public PaymentProcessingChannel convertPaymentProcessingChannelDto(@Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto, @Nonnull final Customer customer) {
        assertPaymentProcessingChannelDto(paymentProcessingChannelDto, customer);
        LOGGER.debug("Converting payment processing channel DTO - {}", paymentProcessingChannelDto);
        final CustomerPaymentMethodProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = (CustomerPaymentMethodProcessingChannelDto) paymentProcessingChannelDto;
        // Create domain model object
        final CustomerPaymentMethodProcessingChannel paymentMethodProcessingChannel = new CustomerPaymentMethodProcessingChannel();
        encryptedPaymentMethodProcessingChannelDto.updateDomainEntityProperties(paymentMethodProcessingChannel);
        // Load customer payment method
        final CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodService.getCustomerPaymentMethodById(encryptedPaymentMethodProcessingChannelDto.getCustomerPaymentMethodId());
        paymentMethodProcessingChannel.setCustomerPaymentMethod(customerPaymentMethod);
        return paymentMethodProcessingChannel;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodService getCustomerPaymentMethodService() {
        return customerPaymentMethodService;
    }

    public void setCustomerPaymentMethodService(final CustomerPaymentMethodService customerPaymentMethodService) {
        this.customerPaymentMethodService = customerPaymentMethodService;
    }
}
