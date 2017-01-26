package com.sfl.pms.services.order.impl.payment.provider;

import com.sfl.pms.services.order.dto.payment.OrderPaymentChannelDto;
import com.sfl.pms.services.order.dto.payment.provider.OrderProviderPaymentChannelDto;
import com.sfl.pms.services.order.exception.payment.provider.InvalidOrderPaymentException;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.order.model.payment.provider.OrderProviderPaymentChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.order.OrderPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 6:11 PM
 */
@Component("orderProviderPaymentChannelHandler")
public class OrderProviderPaymentChannelHandlerImpl implements OrderProviderPaymentChannelHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProviderPaymentChannelHandlerImpl.class);

    /* Dependencies */
    @Autowired
    private OrderPaymentService orderPaymentService;

    /* Constructors */
    public OrderProviderPaymentChannelHandlerImpl() {
        LOGGER.debug("Initializing order provider payment channel handler");
    }

    /* Public interface methods */
    @Override
    public void assertOrderPaymentChannel(@Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto) {
        Assert.notNull(paymentChannelDto, "Order payment channel DTO should not be null");
        Assert.isInstanceOf(OrderProviderPaymentChannelDto.class, paymentChannelDto, "Order payment channel DTO should be instance of order provider payment channel");
        // Cast and assert payment specific fields
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = (OrderProviderPaymentChannelDto) paymentChannelDto;
        Assert.notNull(orderProviderPaymentChannelDto.getPaymentId(), "Payment id in order payment channel DTO should not be null");
    }

    @Override
    public void assertPaymentChannelIsApplicableForOrder(@Nonnull final Long orderId, @Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto) {
        assertOrderPaymentChannel(paymentChannelDto);
        Assert.notNull(orderId, "Order id should not be null");
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = (OrderProviderPaymentChannelDto) paymentChannelDto;
        // Load payment and assert with order
        final OrderPayment orderPayment = orderPaymentService.getPaymentById(orderProviderPaymentChannelDto.getPaymentId());
        assertOrderPayment(orderId, orderPayment);
    }

    @Override
    public OrderPaymentChannel convertOrderPaymentChannelDto(@Nonnull OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto) {
        assertOrderPaymentChannel(paymentChannelDto);
        final OrderProviderPaymentChannelDto orderProviderPaymentChannelDto = (OrderProviderPaymentChannelDto) paymentChannelDto;
        // Load payment and assert with order
        final OrderPayment orderPayment = orderPaymentService.getPaymentById(orderProviderPaymentChannelDto.getPaymentId());
        // Create order payment channel
        final OrderProviderPaymentChannel orderProviderPaymentChannel = new OrderProviderPaymentChannel();
        orderProviderPaymentChannel.setPayment(orderPayment);
        return orderProviderPaymentChannel;
    }

    /* Utility methods */
    private void assertOrderPayment(final Long orderId, final OrderPayment orderPayment) {
        if (!orderId.equals(orderPayment.getOrder().getId())) {
            LOGGER.error("Order payment with id - {} belongs to order with id - {}. Current order id - {}", orderPayment.getId(), orderPayment.getOrder().getId(), orderId);
            throw new InvalidOrderPaymentException(orderPayment.getId(), orderPayment.getOrder().getId(), orderId);
        }
    }

    /* Properties getters and setters */
    public OrderPaymentService getOrderPaymentService() {
        return orderPaymentService;
    }

    public void setOrderPaymentService(final OrderPaymentService orderPaymentService) {
        this.orderPaymentService = orderPaymentService;
    }
}
