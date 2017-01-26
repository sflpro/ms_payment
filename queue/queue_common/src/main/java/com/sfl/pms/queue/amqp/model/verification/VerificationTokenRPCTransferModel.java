package com.sfl.pms.queue.amqp.model.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Alfred Kaghyan
 * Company: SFL LLC
 * Date: 4/29/2015
 * Time: 4:12 PM
 */
public class VerificationTokenRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = 6893768059125470762L;

    /* Properties */
    @JsonProperty(value = "verificationTokenId", required = true)
    private Long verificationTokenId;

    /* Constructors */
    public VerificationTokenRPCTransferModel() {
    }

    public VerificationTokenRPCTransferModel(final Long verificationTokenId) {
        this.verificationTokenId = verificationTokenId;
    }

    /* Properties getters and setters */

    public Long getVerificationTokenId() {
        return verificationTokenId;
    }

    public void setVerificationTokenId(final Long verificationTokenId) {
        this.verificationTokenId = verificationTokenId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VerificationTokenRPCTransferModel)) {
            return false;
        }
        final VerificationTokenRPCTransferModel that = (VerificationTokenRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getVerificationTokenId(), that.getVerificationTokenId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getVerificationTokenId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("verificationTokenId", getVerificationTokenId());
        return builder.build();
    }

}
