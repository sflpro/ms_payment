package com.sfl.pms.queue.amqp.model.payment.common.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 4:34 PM
 */
public class OrderPaymentRequestRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = 5756339458066263058L;

    /* Properties */
    @JsonProperty(value = "orderPaymentRequestId", required = true)
    private Long orderPaymentRequestId;

    /* Constructors */
    public OrderPaymentRequestRPCTransferModel() {
    }

    public OrderPaymentRequestRPCTransferModel(final Long orderPaymentRequestId) {
        this.orderPaymentRequestId = orderPaymentRequestId;
    }

    /* Properties getters and setters */
    public Long getOrderPaymentRequestId() {
        return orderPaymentRequestId;
    }

    public void setOrderPaymentRequestId(final Long orderPaymentRequestId) {
        this.orderPaymentRequestId = orderPaymentRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentRequestRPCTransferModel)) {
            return false;
        }
        final OrderPaymentRequestRPCTransferModel that = (OrderPaymentRequestRPCTransferModel) o;
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
