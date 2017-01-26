package com.sfl.pms.services.order.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 2/19/15
 * Time: 1:24 PM
 */
public class OrderCreatedEvent implements ApplicationEvent {

    /* Properties */
    private final Long orderId;

    /* Constructors */
    public OrderCreatedEvent(final Long orderId) {
        Assert.notNull(orderId);
        this.orderId = orderId;
    }

    /* Getters */
    public Long getOrderId() {
        return orderId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderCreatedEvent)) {
            return false;
        }
        final OrderCreatedEvent that = (OrderCreatedEvent) o;
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
