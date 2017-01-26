package com.sfl.pms.queue.amqp.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/14/14
 * Time: 1:16 AM
 */
public class CarWashStationRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -267522528620801120L;

    /* Properties */
    @JsonProperty(value = "carWashStationId", required = true)
    private Long carWashStationId;

    /* Constructors */
    public CarWashStationRPCTransferModel() {
    }

    public CarWashStationRPCTransferModel(final Long carWashStationId) {
        this.carWashStationId = carWashStationId;
    }

    /* Properties getters and setters */

    public Long getCarWashStationId() {
        return carWashStationId;
    }

    public void setCarWashStationId(final Long carWashStationId) {
        this.carWashStationId = carWashStationId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarWashStationRPCTransferModel)) {
            return false;
        }
        final CarWashStationRPCTransferModel that = (CarWashStationRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getCarWashStationId(), that.getCarWashStationId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getCarWashStationId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("carWashStationId", carWashStationId);
        return builder.build();
    }
}
