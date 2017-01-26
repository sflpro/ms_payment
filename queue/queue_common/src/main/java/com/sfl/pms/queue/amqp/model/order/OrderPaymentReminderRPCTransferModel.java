package com.sfl.pms.queue.amqp.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 10/30/15
 * Time: 3:45 PM
 */
public class OrderPaymentReminderRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -9150667922880656747L;

    /* Properties */
    @JsonProperty
    private String carWashAppointmentUuid;

    /* Constructors */
    public OrderPaymentReminderRPCTransferModel() {
    }

    public OrderPaymentReminderRPCTransferModel(final String carWashAppointmentUuid) {
        this.carWashAppointmentUuid = carWashAppointmentUuid;
    }

    /* Properties getters and setters */
    public String getCarWashAppointmentUuid() {
        return carWashAppointmentUuid;
    }

    public void setCarWashAppointmentUuid(final String carWashAppointmentUuid) {
        this.carWashAppointmentUuid = carWashAppointmentUuid;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentReminderRPCTransferModel)) {
            return false;
        }

        final OrderPaymentReminderRPCTransferModel that = (OrderPaymentReminderRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getCarWashAppointmentUuid(), that.getCarWashAppointmentUuid());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getCarWashAppointmentUuid());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("carWashAppointmentUuid", this.getCarWashAppointmentUuid());
        return builder.build();
    }
}