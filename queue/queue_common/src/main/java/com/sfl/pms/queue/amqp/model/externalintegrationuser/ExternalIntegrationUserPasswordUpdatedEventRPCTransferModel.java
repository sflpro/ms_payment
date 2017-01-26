package com.sfl.pms.queue.amqp.model.externalintegrationuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Vazgen Danielyan
 * Company: SFL LLC
 * Time: 9:56 PM
 */
public class ExternalIntegrationUserPasswordUpdatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -5458950703561999145L;

    /* Properties */
    @JsonProperty
    private Long externalIntegrationUserId;

    @JsonProperty
    private String externalIntegrationUserPassword;


    /* Constructors */
    public ExternalIntegrationUserPasswordUpdatedEventRPCTransferModel() {
    }

    public ExternalIntegrationUserPasswordUpdatedEventRPCTransferModel(final Long externalIntegrationUserId, final String externalIntegrationUserPassword) {
        this.externalIntegrationUserId = externalIntegrationUserId;
        this.externalIntegrationUserPassword = externalIntegrationUserPassword;
    }


    /* Getters and setters */
    public Long getExternalIntegrationUserId() {
        return externalIntegrationUserId;
    }

    public void setExternalIntegrationUserId(final Long externalIntegrationUserId) {
        this.externalIntegrationUserId = externalIntegrationUserId;
    }

    public String getExternalIntegrationUserPassword() {
        return externalIntegrationUserPassword;
    }

    public void setExternalIntegrationUserPassword(final String externalIntegrationUserPassword) {
        this.externalIntegrationUserPassword = externalIntegrationUserPassword;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final ExternalIntegrationUserPasswordUpdatedEventRPCTransferModel rhs = (ExternalIntegrationUserPasswordUpdatedEventRPCTransferModel) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.getExternalIntegrationUserId(), rhs.getExternalIntegrationUserId())
                .append(this.getExternalIntegrationUserPassword(), rhs.getExternalIntegrationUserPassword())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getExternalIntegrationUserId())
                .append(getExternalIntegrationUserPassword())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalIntegrationUserId", getExternalIntegrationUserId())
                .append("externalIntegrationUserPassword", getExternalIntegrationUserPassword())
                .toString();
    }
}
