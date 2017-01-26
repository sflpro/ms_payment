package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestEncryptedPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestPaymentMethodDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestEncryptedPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 2:17 PM
 */
@Component
public class OrderRequestEncryptedPaymentMethodDtoHandlerImpl implements OrderRequestEncryptedPaymentMethodDtoHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRequestEncryptedPaymentMethodDtoHandlerImpl.class);

    /* Constructors */
    public OrderRequestEncryptedPaymentMethodDtoHandlerImpl() {
        LOGGER.debug("Initializing order request encrypted payment method DTO handler");
    }

    @Override
    public void assertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer) {
        Assert.notNull(paymentMethodDto, "Payment method DTO should not be null");
        Assert.notNull(customer, "Customer should not be null");
        Assert.isInstanceOf(OrderRequestEncryptedPaymentMethodDto.class, paymentMethodDto, "Payment method DTO should be type of OrderRequestEncryptedPaymentMethod");
        final OrderRequestEncryptedPaymentMethodDto encryptedPaymentMethodDto = (OrderRequestEncryptedPaymentMethodDto) paymentMethodDto;
        Assert.notNull(encryptedPaymentMethodDto.getEncryptedData(), "Encrypted payment method data should not be null in payment method DTO");
        Assert.notNull(encryptedPaymentMethodDto.getPaymentProviderType(), "Payment provider type should not be null in payment method DTO");
        Assert.notNull(encryptedPaymentMethodDto.getPaymentMethodGroupType(), "Payment method group type should not be null in payment method DTO");
    }

    @Override
    public OrderRequestPaymentMethod convertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer) {
        assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        final OrderRequestEncryptedPaymentMethodDto redirectPaymentMethod = (OrderRequestEncryptedPaymentMethodDto) paymentMethodDto;
        // Load customer payment method
        final OrderRequestEncryptedPaymentMethod paymentMethod = new OrderRequestEncryptedPaymentMethod();
        redirectPaymentMethod.updateDomainEntityProperties(paymentMethod);
        LOGGER.debug("Successfully converted order request payment method DTO - {} into domain model object - {}", redirectPaymentMethod, paymentMethod);
        return paymentMethod;
    }
}
