package com.sfl.pms.queue.amqp.model.businesscompany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 9/29/15
 * Time: 5:26 PM
 */
public class BusinessCompanyActivatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = 6910847136543457585L;

    /* Properties */
    @JsonProperty
    private Long businessManagerId;

    /* Constructors */
    public BusinessCompanyActivatedEventRPCTransferModel() {
    }

    public BusinessCompanyActivatedEventRPCTransferModel(final Long businessManagerId) {
        this.businessManagerId = businessManagerId;
    }

    /* Getters and setters */
    public Long getBusinessManagerId() {
        return businessManagerId;
    }

    public void setBusinessManagerId(final Long businessManagerId) {
        this.businessManagerId = businessManagerId;
    }

    /* Equals, HashCode, ToString overrides */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessCompanyActivatedEventRPCTransferModel)) {
            return false;
        }

        final BusinessCompanyActivatedEventRPCTransferModel that = (BusinessCompanyActivatedEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getBusinessManagerId(), that.getBusinessManagerId());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getBusinessManagerId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("businessManagerId", this.getBusinessManagerId());
        return builder.build();
    }
}
