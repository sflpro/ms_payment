package com.sfl.pms.services.order.event;

import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.system.event.model.ApplicationEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/30/15
 * Time: 6:12 PM
 */
public class OrderStateChangedEvent implements ApplicationEvent {

    /* Properties */
    private final Long orderId;

    private final OrderState orderState;

    private final String paymentUuid;

    /* Constructors */
    public OrderStateChangedEvent(final Long orderId, final OrderState orderState, final String paymentUuid) {
        Assert.notNull(orderId, "Order id should not be null");
        Assert.notNull(orderState, "Order state should not be null");
        this.orderId = orderId;
        this.orderState = orderState;
        this.paymentUuid = paymentUuid;
    }

    /* Properties getters and setters */
    public Long getOrderId() {
        return orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public String getPaymentUuid() {
        return paymentUuid;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStateChangedEvent)) {
            return false;
        }
        final OrderStateChangedEvent that = (OrderStateChangedEvent) o;
        return new EqualsBuilder()
                .append(orderId, that.orderId)
                .append(orderState, that.orderState)
                .append(paymentUuid, that.paymentUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(orderId)
                .append(orderState)
                .append(paymentUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("orderId", orderId)
                .append("orderState", orderState)
                .append("paymentUuid", paymentUuid)
                .toString();
    }
}
