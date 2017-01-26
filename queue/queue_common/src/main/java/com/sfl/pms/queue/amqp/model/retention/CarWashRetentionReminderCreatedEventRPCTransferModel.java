package com.sfl.pms.queue.amqp.model.retention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 12/1/15
 * Time: 10:20 AM
 */
public class CarWashRetentionReminderCreatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = 2057356305563748662L;

    /* Properties */
    @JsonProperty
    private Long carWashRetentionReminderId;

    /* Constructors */
    public CarWashRetentionReminderCreatedEventRPCTransferModel() {
    }

    public CarWashRetentionReminderCreatedEventRPCTransferModel(final Long carWashRetentionReminderId) {
        this.carWashRetentionReminderId = carWashRetentionReminderId;
    }

    /* Properties getters and setters */
    public Long getCarWashRetentionReminderId() {
        return carWashRetentionReminderId;
    }

    public void setCarWashRetentionReminderId(final Long carWashRetentionReminderId) {
        this.carWashRetentionReminderId = carWashRetentionReminderId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarWashRetentionReminderCreatedEventRPCTransferModel)) {
            return false;
        }

        final CarWashRetentionReminderCreatedEventRPCTransferModel that = (CarWashRetentionReminderCreatedEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getCarWashRetentionReminderId(), that.getCarWashRetentionReminderId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getCarWashRetentionReminderId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("carWashRetentionReminderId", this.getCarWashRetentionReminderId());
        return builder.build();
    }
}
