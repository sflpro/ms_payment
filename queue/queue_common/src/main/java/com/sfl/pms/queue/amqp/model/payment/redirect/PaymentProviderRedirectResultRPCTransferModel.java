package com.sfl.pms.queue.amqp.model.payment.redirect;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/10/15
 * Time: 4:28 PM
 */
public class PaymentProviderRedirectResultRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -7442648754340921086L;

    /* Properties */
    @JsonProperty(value = "redirectResultId", required = true)
    private Long redirectResultId;

    /* Constructors */
    public PaymentProviderRedirectResultRPCTransferModel(final Long redirectResultId) {
        this.redirectResultId = redirectResultId;
    }

    public PaymentProviderRedirectResultRPCTransferModel() {
    }

    /* Properties getters and setters */
    public Long getRedirectResultId() {
        return redirectResultId;
    }

    public void setRedirectResultId(final Long redirectResultId) {
        this.redirectResultId = redirectResultId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderRedirectResultRPCTransferModel)) {
            return false;
        }
        final PaymentProviderRedirectResultRPCTransferModel that = (PaymentProviderRedirectResultRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getRedirectResultId(), that.getRedirectResultId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getRedirectResultId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("redirectResultId", getRedirectResultId());
        return builder.build();
    }
}
