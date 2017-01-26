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
public class PaymentMethodAuthorizationRequestRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = 3232813304918165607L;

    /* Properties */
    @JsonProperty(value = "authorizationRequestId", required = true)
    private Long authorizationRequestId;

    /* Constructors */
    public PaymentMethodAuthorizationRequestRPCTransferModel() {
    }

    public PaymentMethodAuthorizationRequestRPCTransferModel(final Long authorizationRequestId) {
        this.authorizationRequestId = authorizationRequestId;
    }

    /* Properties getters and setters */
    public Long getAuthorizationRequestId() {
        return authorizationRequestId;
    }

    public void setAuthorizationRequestId(final Long authorizationRequestId) {
        this.authorizationRequestId = authorizationRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodAuthorizationRequestRPCTransferModel)) {
            return false;
        }
        final PaymentMethodAuthorizationRequestRPCTransferModel that = (PaymentMethodAuthorizationRequestRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getAuthorizationRequestId(), that.getAuthorizationRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getAuthorizationRequestId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("authorizationRequestId", getAuthorizationRequestId());
        return builder.build();
    }
}