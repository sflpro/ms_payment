package com.sfl.pms.queue.amqp.model.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/15/15
 * Time: 10:22 PM
 */
public class RewardedVouchersGenerationRequestRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -8018794638474813025L;

    /* Properties */
    @JsonProperty(value = "rewardedVouchersGenerationRequestId", required = true)
    private Long rewardedVouchersGenerationRequestId;

    /* Constructors */
    public RewardedVouchersGenerationRequestRPCTransferModel() {
    }

    public RewardedVouchersGenerationRequestRPCTransferModel(final Long rewardedVouchersGenerationRequestId) {
        this.rewardedVouchersGenerationRequestId = rewardedVouchersGenerationRequestId;
    }

    /* Properties getters and setters */

    public Long getRewardedVouchersGenerationRequestId() {
        return rewardedVouchersGenerationRequestId;
    }

    public void setRewardedVouchersGenerationRequestId(final Long rewardedVouchersGenerationRequestId) {
        this.rewardedVouchersGenerationRequestId = rewardedVouchersGenerationRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RewardedVouchersGenerationRequestRPCTransferModel)) {
            return false;
        }
        final RewardedVouchersGenerationRequestRPCTransferModel that = (RewardedVouchersGenerationRequestRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getRewardedVouchersGenerationRequestId(), that.getRewardedVouchersGenerationRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getRewardedVouchersGenerationRequestId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("rewardedVouchersGenerationRequestId", rewardedVouchersGenerationRequestId);
        return builder.build();
    }
}
