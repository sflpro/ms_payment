package com.sfl.pms.queue.amqp.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 10/21/15
 * Time: 3:57 PM
 */
public class OrderPaymentRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -1031819569663859346L;

    /* Properties */
    @JsonProperty
    private String carWashAppointmentUuid;

    /* Constructors */
    public OrderPaymentRPCTransferModel() {
    }

    public OrderPaymentRPCTransferModel(final String carWashAppointmentUuid) {
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
        if (!(o instanceof OrderPaymentRPCTransferModel)) {
            return false;
        }

        final OrderPaymentRPCTransferModel that = (OrderPaymentRPCTransferModel) o;
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
