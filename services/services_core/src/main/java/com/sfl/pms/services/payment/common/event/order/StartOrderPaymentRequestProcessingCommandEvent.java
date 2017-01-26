package com.sfl.pms.services.payment.common.event.order;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 3:55 PM
 */
public class StartOrderPaymentRequestProcessingCommandEvent implements ApplicationEvent {

    /* Properties */
    private final Long orderPaymentRequestId;

    /* Constructors */
    public StartOrderPaymentRequestProcessingCommandEvent(final Long orderPaymentRequestId) {
        Assert.notNull(orderPaymentRequestId, "Order payment request id should not be null");
        this.orderPaymentRequestId = orderPaymentRequestId;
    }

    /* Properties getters and setters */
    public Long getOrderPaymentRequestId() {
        return orderPaymentRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartOrderPaymentRequestProcessingCommandEvent)) {
            return false;
        }
        final StartOrderPaymentRequestProcessingCommandEvent that = (StartOrderPaymentRequestProcessingCommandEvent) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getOrderPaymentRequestId(), that.getOrderPaymentRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getOrderPaymentRequestId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("orderPaymentRequestId", getOrderPaymentRequestId());
        return builder.build();
    }
}
