package com.sfl.pms.queue.amqp.model.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/15/15
 * Time: 11:54 PM
 */
public class CarWashCompanyRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -267522528620801120L;

    /* Properties */
    @JsonProperty(value = "carWashCompanyId", required = true)
    private Long carWashCompanyId;

    /* Constructors */
    public CarWashCompanyRPCTransferModel() {
    }

    public CarWashCompanyRPCTransferModel(final Long carWashCompanyId) {
        this.carWashCompanyId = carWashCompanyId;
    }

    /* Properties getters and setters */

    public Long getCarWashCompanyId() {
        return carWashCompanyId;
    }

    public void setCarWashCompanyId(final Long carWashCompanyId) {
        this.carWashCompanyId = carWashCompanyId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarWashCompanyRPCTransferModel)) {
            return false;
        }
        final CarWashCompanyRPCTransferModel that = (CarWashCompanyRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getCarWashCompanyId(), that.getCarWashCompanyId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getCarWashCompanyId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("carWashCompanyId", carWashCompanyId);
        return builder.build();
    }
}
