package com.sfl.pms.queue.amqp.model.externalintegrationuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 8/26/15
 * Time: 12:48 PM
 */
public class ExternalIntegrationUserRegisteredEventRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -8749421545470890201L;

    /* Properties */
    @JsonProperty
    private Long externalIntegrationUserId;

    /* Constructors */
    public ExternalIntegrationUserRegisteredEventRPCTransferModel() {
    }

    public ExternalIntegrationUserRegisteredEventRPCTransferModel(final Long externalIntegrationUserId) {
        this.externalIntegrationUserId = externalIntegrationUserId;
    }

    /* Properties getters and setters */
    public Long getExternalIntegrationUserId() {
        return externalIntegrationUserId;
    }

    public void setExternalIntegrationUserId(final Long externalIntegrationUserId) {
        this.externalIntegrationUserId = externalIntegrationUserId;
    }

    /* Equals, HashCode, ToString methods overrides */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExternalIntegrationUserRegisteredEventRPCTransferModel)) {
            return false;
        }

        final ExternalIntegrationUserRegisteredEventRPCTransferModel that = (ExternalIntegrationUserRegisteredEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(this.getExternalIntegrationUserId(), that.getExternalIntegrationUserId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getExternalIntegrationUserId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("externalIntegrationUserId", this.getExternalIntegrationUserId());
        return builder.build();
    }
}
