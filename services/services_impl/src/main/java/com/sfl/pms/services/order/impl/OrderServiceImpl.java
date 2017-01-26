package com.sfl.pms.services.order.impl;

import com.sfl.pms.persistence.repositories.order.OrderRepository;
import com.sfl.pms.persistence.repositories.order.payment.OrderPaymentChannelRepository;
import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.dto.OrderDto;
import com.sfl.pms.services.order.dto.OrderStateChangeDto;
import com.sfl.pms.services.order.dto.OrderStateChangeHistoryRecordDto;
import com.sfl.pms.services.order.dto.payment.OrderPaymentChannelDto;
import com.sfl.pms.services.order.event.OrderPaidEvent;
import com.sfl.pms.services.order.event.OrderStateChangedEvent;
import com.sfl.pms.services.order.exception.OrderAlreadyExistsForUuIdException;
import com.sfl.pms.services.order.exception.OrderNotFoundForIdException;
import com.sfl.pms.services.order.exception.OrderNotFoundForUuIdException;
import com.sfl.pms.services.order.exception.payment.OrderAlreadyPaidException;
import com.sfl.pms.services.order.exception.payment.UnknownOrderPaymentChannelType;
import com.sfl.pms.services.order.impl.payment.OrderPaymentChannelHandler;
import com.sfl.pms.services.order.impl.payment.provider.OrderProviderPaymentChannelHandler;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.order.model.OrderStateChangeHistoryRecord;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannelType;
import com.sfl.pms.services.system.event.ApplicationEventDistributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 12:49 PM
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    /* Dependencies */
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPaymentChannelRepository orderPaymentChannelRepository;

    @Autowired
    @Qualifier("orderProviderPaymentChannelHandler")
    private OrderProviderPaymentChannelHandler orderProviderPaymentChannelHandler;

    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    @Autowired
    private CustomerService customerService;

    /* Constructors */
    public OrderServiceImpl() {
        LOGGER.debug("Initializing order service");
    }

    @Nonnull
    @Override
    public Order getOrderById(@Nonnull final Long orderId) {
        assertOrderId(orderId);
        LOGGER.debug("Loading order for id - {}", orderId);
        final Order order = orderRepository.findOne(orderId);
        assertOrderNotNullForId(order, orderId);
        LOGGER.debug("Successfully retrieved order for id - {}, order - {}", orderId, order);
        return order;
    }

    @Nonnull
    @Override
    public Order getOrderByUuId(@Nonnull String uuId) {
        assertOrderUuId(uuId);
        LOGGER.debug("Getting order for uuId - {}", uuId);
        final Order order = orderRepository.findByUuId(uuId);
        assertOrderNotNullForUuId(order, uuId);
        LOGGER.debug("Successfully loaded order for uuId - {}, order - {}", uuId, order);
        return order;
    }

    @Transactional
    @Nonnull
    @Override
    public Order createOrder(@Nonnull final Long customerId, @Nonnull final OrderDto orderDto) {
        assertCustomerId(customerId);
        assertOrderDto(orderDto);
        LOGGER.debug("Creating order for customer with id - {}, order DTO - {}", customerId, orderDto);
        final Customer customer = customerService.getCustomerById(customerId);
        assertNoOrderExistsForUuId(orderDto.getUuId());
        // Create order
        Order order = new Order();
        // Set order properties
        order.setCustomer(customer);
        orderDto.updateDomainEntityProperties(order);
        // Persist order
        order = orderRepository.save(order);
        LOGGER.debug("Successfully created for with id - {}, order - {}", order.getId(), order);
        return order;
    }

    @Transactional
    @Nonnull
    @Override
    public OrderStateChangeHistoryRecord updateOrderState(@Nonnull final OrderStateChangeDto orderStateChangeDto) {
        Assert.notNull(orderStateChangeDto, "Order state change dto should not be null");
        assertOrderId(orderStateChangeDto.getOrderId());
        assertOrderStateChangeHistoryRecordDto(orderStateChangeDto.getOrderStateChangeHistoryRecordDto());
        Assert.isTrue(!OrderState.PAID.equals(orderStateChangeDto.getOrderStateChangeHistoryRecordDto().getUpdatedState()), "Order can be set as paid only by calling dedicated method, see OrderService.markOrderPaid.");
        LOGGER.debug("Updating order state for order with id - {}, order state change history record DTO - {}", orderStateChangeDto.getOrderId(), orderStateChangeDto.getOrderStateChangeHistoryRecordDto());
        Order order = orderRepository.findByIdWithWriteLockFlushedAndFreshData(orderStateChangeDto.getOrderId());
        assertOrderNotNullForId(order, orderStateChangeDto.getOrderId());
        // Update order state
        final OrderStateChangeHistoryRecord historyRecord = order.updateOrderState(orderStateChangeDto.getOrderStateChangeHistoryRecordDto().getUpdatedState());
        // Persist order
        order = orderRepository.saveAndFlush(order);
        LOGGER.debug("Successfully updated order state for order with id - {}, order state change history record - {}", order.getId(), historyRecord);
        // Publish order paid event after transaction successfully commits
        persistenceUtilityService.runAfterTransactionCommitIsSuccessful(() ->
                applicationEventDistributionService.publishSynchronousEvent(new OrderStateChangedEvent(orderStateChangeDto.getOrderId(), orderStateChangeDto.getOrderStateChangeHistoryRecordDto().getUpdatedState(), orderStateChangeDto.getPaymentUuid())), false);
        return historyRecord;
    }

    @Transactional
    @Nonnull
    @Override
    public OrderPaymentChannel markOrderPaid(@Nonnull final Long orderId, @Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto) {
        assertOrderId(orderId);
        Assert.notNull(paymentChannelDto, "Payment channel DTO should not be null");
        Assert.notNull(paymentChannelDto.getType(), "Type in payment channel DTO should not be null");
        LOGGER.debug("Marking order with id - {} as paid, order payment channel - {}", orderId, paymentChannelDto);
        // Grab order channel payment handler
        final OrderPaymentChannelHandler orderPaymentChannelHandler = getOrderPaymentChannelHandler(paymentChannelDto.getType());
        orderPaymentChannelHandler.assertOrderPaymentChannel(paymentChannelDto);
        // Load and assert order
        Order order = orderRepository.findByIdWithWriteLockFlushedAndFreshData(orderId);
        assertOrderNotNullForId(order, orderId);
        // Assert order is not paid yet
        final OrderPaymentChannel existingOrderPaymentChannel = orderPaymentChannelRepository.findByOrder(order);
        assertOrderIsNotPaid(existingOrderPaymentChannel, orderId);
        // Assert order payment is applicable for order
        orderPaymentChannelHandler.assertPaymentChannelIsApplicableForOrder(orderId, paymentChannelDto);
        // Create order payment channel
        final OrderPaymentChannel orderPaymentChannel = orderPaymentChannelHandler.convertOrderPaymentChannelDto(paymentChannelDto);
        // Build relation of domain objects
        orderPaymentChannel.setOrder(order);
        order.setPaymentChannel(orderPaymentChannel);
        // Update state on order
        order.updateOrderState(OrderState.PAID);
        order.setUpdated(new Date());
        // Persist order
        order = orderRepository.saveAndFlush(order);
        LOGGER.debug("Successfully marked order as paid, order id - {}, payment channel DTO - {}", orderId, paymentChannelDto);
        // Publish order paid event after transaction successfully commits
        persistenceUtilityService.runAfterTransactionCommitIsSuccessful(() -> {
            applicationEventDistributionService.publishSynchronousEvent(new OrderPaidEvent(orderId));
        }, false);
        return order.getPaymentChannel();
    }

    @Nonnull
    @Override
    public Long getOrdersCountForCustomer(@Nonnull final Long customerId) {
        assertCustomerId(customerId);
        LOGGER.debug("Getting orders count for customer with id  - {}", customerId);
        final Customer customer = customerService.getCustomerById(customerId);
        final Long ordersCount = orderRepository.countByCustomer(customer);
        LOGGER.debug("Orders count for customer with id  - {} is - {}", customerId, ordersCount);
        return ordersCount;
    }

    @Nullable
    @Override
    public BigDecimal getTotalAmountOrderedForCustomer(@Nonnull final Long customerId) {
        assertCustomerId(customerId);
        LOGGER.debug("Getting total amount ordered for customer with id  - {}", customerId);
        final Customer customer = customerService.getCustomerById(customerId);
        final BigDecimal totalAmount = orderRepository.getTotalAmountPaidForCustomer(customer.getId());
        LOGGER.debug("Getting total amount ordered for customer with id - {} is - {}", customerId, totalAmount);
        return totalAmount;
    }

    @Override
    public boolean checkIfOrderIsPaid(@Nonnull final Long orderId) {
        assertOrderId(orderId);
        LOGGER.debug("Checking if order with id - {} is paid or not.", orderId);
        final Order order = orderRepository.findOne(orderId);
        assertOrderNotNullForId(order, orderId);
        final OrderPaymentChannel orderPaymentChannel = orderPaymentChannelRepository.findByOrder(order);
        final boolean isOrderPaid = (orderPaymentChannel != null);
        LOGGER.debug("Check result if order with id - {} is paid or not is - {}", orderId, isOrderPaid);
        return isOrderPaid;
    }

    /* Utility methods */
    private void assertNoOrderExistsForUuId(final String uuId) {
        final Order order = orderRepository.findByUuId(uuId);
        if (order != null) {
            LOGGER.error("Order with id - {} already exists for uuid - {}", order.getId(), uuId);
            throw new OrderAlreadyExistsForUuIdException(uuId, order.getId());
        }
    }

    private void assertOrderDto(final OrderDto orderDto) {
        Assert.notNull(orderDto, "Order DTO should not be null");
        Assert.notNull(orderDto.getUuId(), "UuId in order DTO should not be null");
        Assert.notNull(orderDto.getCurrency(), "Currency in order DTO should not be null");
        Assert.notNull(orderDto.getPaymentTotalWithVat(), "Payment total with vat in order DTO should not be null");
    }

    private OrderPaymentChannelHandler getOrderPaymentChannelHandler(final OrderPaymentChannelType channelType) {
        switch (channelType) {
            case PAYMENT_PROVIDER: {
                return orderProviderPaymentChannelHandler;
            }
            default: {
                LOGGER.error("Unknown order payment channel type - {}", channelType);
                throw new UnknownOrderPaymentChannelType(channelType);
            }
        }
    }

    private void assertOrderIsNotPaid(final OrderPaymentChannel existingOrderPaymentChannel, final Long orderId) {
        if (existingOrderPaymentChannel != null) {
            LOGGER.error("Order with id - {} is already considered paid, payment channel id - {}", orderId, existingOrderPaymentChannel.getId());
            throw new OrderAlreadyPaidException(orderId);
        }
    }

    private void assertOrderUuId(final String uuId) {
        Assert.notNull(uuId, "Order uuId should not be null");
    }

    private void assertOrderStateChangeHistoryRecordDto(final OrderStateChangeHistoryRecordDto orderStateChangeHistoryRecordDto) {
        Assert.notNull(orderStateChangeHistoryRecordDto, "Order state change history record DTO should not be null");
        Assert.notNull(orderStateChangeHistoryRecordDto.getUpdatedState(), "Updated state in order state change history record DTO should not be null");
    }

    private void assertOrderNotNullForUuId(final Order order, final String orderUuId) {
        if (order == null) {
            LOGGER.error("Order was not found for uuId - {}", orderUuId);
            throw new OrderNotFoundForUuIdException(orderUuId);
        }
    }

    private void assertOrderNotNullForId(final Order order, final Long orderId) {
        if (order == null) {
            LOGGER.error("No error was found for id - {}", orderId);
            throw new OrderNotFoundForIdException(orderId);
        }
    }

    private void assertOrderId(final Long orderId) {
        Assert.notNull(orderId, "Order id should not be null");
    }

    private void assertCustomerId(final Long customerId) {
        Assert.notNull(customerId, "Customer id should not be null");
    }

    /* Dependencies getters and setters */
    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderPaymentChannelRepository getOrderPaymentChannelRepository() {
        return orderPaymentChannelRepository;
    }

    public void setOrderPaymentChannelRepository(final OrderPaymentChannelRepository orderPaymentChannelRepository) {
        this.orderPaymentChannelRepository = orderPaymentChannelRepository;
    }

    public OrderPaymentChannelHandler getOrderProviderPaymentChannelHandler() {
        return orderProviderPaymentChannelHandler;
    }

    public void setOrderProviderPaymentChannelHandler(OrderProviderPaymentChannelHandler orderProviderPaymentChannelHandler) {
        this.orderProviderPaymentChannelHandler = orderProviderPaymentChannelHandler;
    }

    public ApplicationEventDistributionService getApplicationEventDistributionService() {
        return applicationEventDistributionService;
    }

    public void setApplicationEventDistributionService(final ApplicationEventDistributionService applicationEventDistributionService) {
        this.applicationEventDistributionService = applicationEventDistributionService;
    }

    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }
}
