package com.sfl.pms.queue.amqp.model.payment.common.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 10/13/15
 * Time: 1:45 PM
 */
public class OrderPaymentDeferredRequestRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = 8997749442833037242L;

    /* Properties */
    @JsonProperty
    private Long orderId;

    /* Constructors */
    public OrderPaymentDeferredRequestRPCTransferModel() {
    }

    public OrderPaymentDeferredRequestRPCTransferModel(final Long orderId) {
        this.orderId = orderId;
    }

    /* Properties getters and setters */
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    /* Equals, HashCode, ToString overrides */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentDeferredRequestRPCTransferModel)) {
            return false;
        }

        final OrderPaymentDeferredRequestRPCTransferModel that = (OrderPaymentDeferredRequestRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getOrderId(), that.getOrderId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getOrderId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("orderId", this.getOrderId());
        return builder.build();
    }
}
