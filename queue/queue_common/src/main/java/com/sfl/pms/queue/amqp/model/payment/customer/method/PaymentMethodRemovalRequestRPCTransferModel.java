package com.sfl.pms.queue.amqp.model.payment.customer.method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 10:58 PM
 */
public class PaymentMethodRemovalRequestRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = 3232813304918165607L;

    /* Properties */
    @JsonProperty(value = "paymentMethodId", required = true)
    private Long paymentMethodId;

    /* Constructors */
    public PaymentMethodRemovalRequestRPCTransferModel() {
    }

    public PaymentMethodRemovalRequestRPCTransferModel(final Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /* Properties getters and setters */
    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(final Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodRemovalRequestRPCTransferModel)) {
            return false;
        }
        final PaymentMethodRemovalRequestRPCTransferModel that = (PaymentMethodRemovalRequestRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getPaymentMethodId(), that.getPaymentMethodId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getPaymentMethodId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("paymentMethodId", getPaymentMethodId());
        return builder.build();
    }
}