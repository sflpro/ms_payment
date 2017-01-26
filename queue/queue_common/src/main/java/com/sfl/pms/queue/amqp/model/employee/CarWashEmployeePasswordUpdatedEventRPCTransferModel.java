package com.sfl.pms.queue.amqp.model.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 2/11/15
 * Time: 3:41 PM
 */
public class CarWashEmployeePasswordUpdatedEventRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -6541822477728644260L;

    /* Properties */
    @JsonProperty
    private Long carWashEmployeeId;

    @JsonProperty
    private String carWashEmployeePassword;

    /* Constructors */
    public CarWashEmployeePasswordUpdatedEventRPCTransferModel() {
    }

    public CarWashEmployeePasswordUpdatedEventRPCTransferModel(final Long carWashEmployeeId, final String carWashEmployeePassword) {
        this.carWashEmployeeId = carWashEmployeeId;
        this.carWashEmployeePassword = carWashEmployeePassword;
    }

    /* Getters and setters */
    public Long getCarWashEmployeeId() {
        return carWashEmployeeId;
    }

    public void setCarWashEmployeeId(final Long carWashEmployeeId) {
        this.carWashEmployeeId = carWashEmployeeId;
    }

    public String getCarWashEmployeePassword() {
        return carWashEmployeePassword;
    }

    public void setCarWashEmployeePassword(final String carWashEmployeePassword) {
        this.carWashEmployeePassword = carWashEmployeePassword;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarWashEmployeePasswordUpdatedEventRPCTransferModel)) {
            return false;
        }
        final CarWashEmployeePasswordUpdatedEventRPCTransferModel that = (CarWashEmployeePasswordUpdatedEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getCarWashEmployeeId(), that.getCarWashEmployeeId());
        builder.append(getCarWashEmployeePassword(), that.getCarWashEmployeePassword());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getCarWashEmployeeId());
        builder.append(getCarWashEmployeePassword());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("carWashEmployeeId", getCarWashEmployeeId());
        builder.append("carWashEmployeePassword", getCarWashEmployeePassword());
        return builder.build();
    }
}
