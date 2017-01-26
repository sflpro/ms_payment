package com.sfl.pms.queue.amqp.model.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by alfkaghyan
 * Date: 8/22/15
 * Time: 5:10 PM
 */
public class CarWashManagerCreatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -6753387191921016852L;

    /* Properties */
    @JsonProperty
    private Long carWashManagerId;

    /* Constructors */
    public CarWashManagerCreatedEventRPCTransferModel() {
    }

    public CarWashManagerCreatedEventRPCTransferModel(final Long carWashManagerId) {
        this.carWashManagerId = carWashManagerId;
    }

    /* Properties getters and setters */
    public Long getCarWashManagerId() {
        return carWashManagerId;
    }

    public void setCarWashManagerId(final Long carWashManagerId) {
        this.carWashManagerId = carWashManagerId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarWashManagerCreatedEventRPCTransferModel)) {
            return false;
        }

        final CarWashManagerCreatedEventRPCTransferModel that = (CarWashManagerCreatedEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getCarWashManagerId(), that.getCarWashManagerId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getCarWashManagerId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("carWashManagerId", getCarWashManagerId());
        return builder.build();
    }
}
