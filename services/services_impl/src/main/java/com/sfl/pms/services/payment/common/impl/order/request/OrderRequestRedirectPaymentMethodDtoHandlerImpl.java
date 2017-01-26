package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestRedirectPaymentMethodDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestRedirectPaymentMethod;
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
public class OrderRequestRedirectPaymentMethodDtoHandlerImpl implements OrderRequestRedirectPaymentMethodDtoHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRequestRedirectPaymentMethodDtoHandlerImpl.class);

    /* Constructors */
    public OrderRequestRedirectPaymentMethodDtoHandlerImpl() {
        LOGGER.debug("Initializing order request redirect payment method DTO handler");
    }

    @Override
    public void assertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer) {
        Assert.notNull(paymentMethodDto, "Payment method DTO should not be null");
        Assert.notNull(customer, "Customer should not be null");
        Assert.isInstanceOf(OrderRequestRedirectPaymentMethodDto.class, paymentMethodDto, "Payment method DTO should be type of OrderRequestRedirectPaymentMethodDto");
        final OrderRequestRedirectPaymentMethodDto redirectPaymentMethodDto = (OrderRequestRedirectPaymentMethodDto) paymentMethodDto;
        Assert.notNull(redirectPaymentMethodDto.getPaymentProviderType(), "Payment provider type should not be null in payment method DTO");
    }

    @Override
    public OrderRequestPaymentMethod convertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer) {
        assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        final OrderRequestRedirectPaymentMethodDto redirectPaymentMethod = (OrderRequestRedirectPaymentMethodDto) paymentMethodDto;
        // Load customer payment method
        final OrderRequestRedirectPaymentMethod paymentMethod = new OrderRequestRedirectPaymentMethod();
        redirectPaymentMethod.updateDomainEntityProperties(paymentMethod);
        LOGGER.debug("Successfully converted order request payment method DTO - {} into domain model object - {}", redirectPaymentMethod, paymentMethod);
        return paymentMethod;
    }
}
