package com.sfl.pms.queue.amqp.model.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 3/20/15
 * Time: 11:33 AM
 */
public class CarWashAppointmentRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -5170376642627015522L;

    /* Properties */
    @JsonProperty
    private Long carWashAppointmentId;

    /* Constructors */
    public CarWashAppointmentRPCTransferModel() {
    }

    public CarWashAppointmentRPCTransferModel(final Long carWashAppointmentId) {
        this.carWashAppointmentId = carWashAppointmentId;
    }

    /* Getters and setters */
    public Long getCarWashAppointmentId() {
        return carWashAppointmentId;
    }

    public void setCarWashAppointmentId(final Long carWashAppointmentId) {
        this.carWashAppointmentId = carWashAppointmentId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarWashAppointmentRPCTransferModel)) {
            return false;
        }
        final CarWashAppointmentRPCTransferModel that = (CarWashAppointmentRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getCarWashAppointmentId(), that.getCarWashAppointmentId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getCarWashAppointmentId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("carWashAppointmentId", getCarWashAppointmentId());
        return builder.build();
    }
}
