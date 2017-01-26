package com.sfl.pms.services.payment.common.impl.order;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.persistence.repositories.payment.common.order.OrderPaymentRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.exception.payment.OrderAlreadyPaidException;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.order.OrderPaymentDto;
import com.sfl.pms.services.payment.common.impl.AbstractPaymentServiceImpl;
import com.sfl.pms.services.payment.common.impl.channel.PaymentProcessingChannelHandler;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.order.OrderPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:11 PM
 */
@Service
public class OrderPaymentServiceImpl extends AbstractPaymentServiceImpl<OrderPayment> implements OrderPaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);

    /* Dependencies */
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    /* Constructors */
    public OrderPaymentServiceImpl() {
        LOGGER.debug("Initializing order payment service");
    }

    @Transactional
    @Nonnull
    @Override
    public OrderPayment createPayment(@Nonnull final Long orderId, @Nonnull final OrderPaymentDto orderPaymentDto, @Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto) {
        Assert.notNull(orderId, "Order id should not be null");
        assertPaymentDto(orderPaymentDto);
        Assert.notNull(paymentProcessingChannelDto, "Payment processing DTO should not be null");
        LOGGER.debug("Creating order payment, order id - {}, order payment DTO - {}, payment processing DTO - {}", new Object[]{orderId, orderPaymentDto, paymentProcessingChannelDto});
        // Load order
        final Order order = orderService.getOrderById(orderId);
        // Assert order is not paid
        assertOrderIsNotPaid(order);
        // Grab customer
        final Customer customer = order.getCustomer();
        // Grab payment processing channel DTO handler
        final PaymentProcessingChannelHandler paymentProcessingChannelHandler = getPaymentProcessingChannelHandler(paymentProcessingChannelDto.getType());
        paymentProcessingChannelHandler.assertPaymentProcessingChannelDto(paymentProcessingChannelDto, customer);
        // Create new order payment
        OrderPayment orderPayment = new OrderPayment(true);
        // Set properties
        orderPaymentDto.updateDomainEntityProperties(orderPayment);
        orderPayment.setOrder(order);
        orderPayment.setCustomer(order.getCustomer());
        // Create payment processing channel
        final PaymentProcessingChannel paymentProcessingChannel = paymentProcessingChannelHandler.convertPaymentProcessingChannelDto(paymentProcessingChannelDto, customer);
        paymentProcessingChannel.setPayment(orderPayment);
        orderPayment.setPaymentProcessingChannel(paymentProcessingChannel);
        // Persist order payment
        orderPayment = orderPaymentRepository.save(orderPayment);
        LOGGER.debug("Successfully created new order payment with id - {}, order payment - {}", orderPayment.getId(), orderPayment);
        return orderPayment;
    }

    /* Utility methods */
    private void assertOrderIsNotPaid(final Order order) {
        final boolean orderPaid = orderService.checkIfOrderIsPaid(order.getId());
        if (orderPaid) {
            LOGGER.error("Order with id - {} is already considered as paid, do not create order payment", order.getId());
            throw new OrderAlreadyPaidException(order.getId());
        }
    }

    @Override
    protected AbstractPaymentRepository<OrderPayment> getRepository() {
        return orderPaymentRepository;
    }

    @Override
    protected Class<OrderPayment> getInstanceClass() {
        return OrderPayment.class;
    }


    /* Properties getters and setters */
    public OrderPaymentRepository getOrderPaymentRepository() {
        return orderPaymentRepository;
    }

    public void setOrderPaymentRepository(final OrderPaymentRepository orderPaymentRepository) {
        this.orderPaymentRepository = orderPaymentRepository;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(final OrderService orderService) {
        this.orderService = orderService;
    }
}
