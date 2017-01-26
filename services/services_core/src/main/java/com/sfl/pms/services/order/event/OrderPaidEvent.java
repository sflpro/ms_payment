package com.sfl.pms.services.order.event;

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
public class OrderPaidEvent implements ApplicationEvent {

    /* Properties */
    private final Long orderId;

    /* Constructors */
    public OrderPaidEvent(final Long orderId) {
        Assert.notNull(orderId, "Order id should not be null");
        this.orderId = orderId;
    }

    /* Properties getters and setters */
    public Long getOrderId() {
        return orderId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaidEvent)) {
            return false;
        }
        final OrderPaidEvent that = (OrderPaidEvent) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getOrderId(), that.getOrderId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getOrderId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("orderId", getOrderId());
        return builder.build();
    }
}
