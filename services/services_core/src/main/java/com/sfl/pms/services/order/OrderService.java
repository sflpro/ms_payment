package com.sfl.pms.services.order;

import com.sfl.pms.services.order.dto.OrderDto;
import com.sfl.pms.services.order.dto.OrderStateChangeDto;
import com.sfl.pms.services.order.dto.payment.OrderPaymentChannelDto;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderStateChangeHistoryRecord;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 12:47 PM
 */
public interface OrderService {

    /**
     * Gets order for id
     *
     * @param orderId
     * @return order
     */
    @Nonnull
    Order getOrderById(@Nonnull final Long orderId);

    /**
     * Get order by uuid
     *
     * @param uuId
     * @return
     */
    @Nonnull
    Order getOrderByUuId(@Nonnull final String uuId);

    /**
     * Creates new order for purchase
     *
     * @param customerId
     * @param orderDto
     * @return order
     */
    @Nonnull
    Order createOrder(@Nonnull final Long customerId, @Nonnull final OrderDto orderDto);


    /**
     * Update order state
     *
     *
     * @param orderStateChangeDto@return orderStateChangeHistoryRecord
     */
    @Nonnull
    OrderStateChangeHistoryRecord updateOrderState(@Nonnull final OrderStateChangeDto orderStateChangeDto);


    /**
     * Marks order as paid
     *
     * @param orderId
     * @param paymentChannelDto
     * @return orderPaymentChannel
     */
    @Nonnull
    OrderPaymentChannel markOrderPaid(@Nonnull final Long orderId, @Nonnull final OrderPaymentChannelDto<? extends OrderPaymentChannel> paymentChannelDto);

    /**
     * Gets orders count for customer
     *
     * @param customerId
     * @return ordersCount
     */
    @Nonnull
    Long getOrdersCountForCustomer(@Nonnull final Long customerId);


    /**
     * Get total amount paid for customer
     *
     * @param customerId customer id
     * @return total amount
     */
    @Nullable
    BigDecimal getTotalAmountOrderedForCustomer(@Nonnull final Long customerId);


    /**
     * Checks if order is paid
     *
     * @param orderId
     * @return paid
     */
    boolean checkIfOrderIsPaid(@Nonnull final Long orderId);
}
