package com.sfl.pms.queue.amqp.model.businessmanager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 8/22/15
 * Time: 1:01 PM
 */
public class BusinessManagerCreatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = 3572644876429568311L;

    /* Properties */
    @JsonProperty
    private Long businessManagerId;

    /* Constructors */
    public BusinessManagerCreatedEventRPCTransferModel() {
    }

    public BusinessManagerCreatedEventRPCTransferModel(final Long businessManagerId) {
        this.businessManagerId = businessManagerId;
    }

    /* Properties getters and setters */
    public Long getBusinessManagerId() {
        return businessManagerId;
    }

    public void setBusinessManagerId(final Long businessManagerId) {
        this.businessManagerId = businessManagerId;
    }


    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessManagerCreatedEventRPCTransferModel)) {
            return false;
        }

        final BusinessManagerCreatedEventRPCTransferModel that = (BusinessManagerCreatedEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getBusinessManagerId(), that.getBusinessManagerId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getBusinessManagerId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("businessManagerId", businessManagerId);
        return builder.build();
    }
}
