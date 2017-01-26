package com.sfl.pms.queue.amqp.model.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/18/15
 * Time: 8:34 PM
 */
public class SupportTicketRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -267522528620801120L;

    /* Properties */
    @JsonProperty(value = "supportTicketId", required = true)
    private Long supportTicketId;

    /* Constructors */
    public SupportTicketRPCTransferModel() {
    }

    public SupportTicketRPCTransferModel(final Long supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    /* Properties getters and setters */

    public Long getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(final Long supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupportTicketRPCTransferModel)) {
            return false;
        }
        final SupportTicketRPCTransferModel that = (SupportTicketRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getSupportTicketId(), that.getSupportTicketId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getSupportTicketId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("supportTicketId", supportTicketId);
        return builder.build();
    }
}
