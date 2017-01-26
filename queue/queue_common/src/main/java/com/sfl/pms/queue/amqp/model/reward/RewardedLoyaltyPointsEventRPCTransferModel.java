package com.sfl.pms.queue.amqp.model.reward;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/3/15
 * Time: 7:09 PM
 */
public class RewardedLoyaltyPointsEventRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -6065321147163709418L;

    /* Properties */
    @JsonProperty(value = "customerId", required = true)
    private Long customerId;

    @JsonProperty(value = "stationIds", required = true)
    private List<Long> stationIds;

    /* Constructors */
    public RewardedLoyaltyPointsEventRPCTransferModel() {
    }

    public RewardedLoyaltyPointsEventRPCTransferModel(final Long customerId, final List<Long> stationIds) {
        this.customerId = customerId;
        this.stationIds = stationIds;
    }

    /* Properties getters and setters */
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getStationIds() {
        return stationIds;
    }

    public void setStationIds(final List<Long> stationIds) {
        this.stationIds = stationIds;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardedLoyaltyPointsEventRPCTransferModel)) {
            return false;
        }
        final RewardedLoyaltyPointsEventRPCTransferModel that = (RewardedLoyaltyPointsEventRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getCustomerId(), that.getCustomerId());
        builder.append(getStationIds(), that.getStationIds());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getCustomerId());
        builder.append(getStationIds());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("customerId", getCustomerId());
        builder.append("stationIds", getStationIds());
        return builder.build();
    }
}
