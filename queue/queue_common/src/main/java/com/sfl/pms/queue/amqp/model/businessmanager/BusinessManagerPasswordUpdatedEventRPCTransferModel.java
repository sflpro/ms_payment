package com.sfl.pms.queue.amqp.model.businessmanager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Alfred Kaghyan
 * Company: SFL LLC
 * Date: 8/13/15
 * Time: 5:10 PM
 */
public class BusinessManagerPasswordUpdatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -8626722703122676293L;

    /* Properties */
    @JsonProperty
    private Long businessManagerId;

    @JsonProperty
    private String businessManagerPassword;

    /* Constructors */
    public BusinessManagerPasswordUpdatedEventRPCTransferModel() {
    }

    public BusinessManagerPasswordUpdatedEventRPCTransferModel(final Long businessManagerId, final String businessManagerPassword) {
        this.businessManagerId = businessManagerId;
        this.businessManagerPassword = businessManagerPassword;
    }

    /* Getters and setters */
    public Long getBusinessManagerId() {
        return businessManagerId;
    }

    public void setBusinessManagerId(final Long businessManagerId) {
        this.businessManagerId = businessManagerId;
    }

    public String getBusinessManagerPassword() {
        return businessManagerPassword;
    }

    public void setBusinessManagerPassword(final String businessManagerPassword) {
        this.businessManagerPassword = businessManagerPassword;
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
        final BusinessManagerPasswordUpdatedEventRPCTransferModel that = (BusinessManagerPasswordUpdatedEventRPCTransferModel) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(getBusinessManagerId(), that.getBusinessManagerId())
                .append(getBusinessManagerPassword(), that.getBusinessManagerPassword())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getBusinessManagerId())
                .append(getBusinessManagerPassword())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("businessManagerId", getBusinessManagerId())
                .append("businessManagerPassword", getBusinessManagerPassword())
                .toString();
    }
}
