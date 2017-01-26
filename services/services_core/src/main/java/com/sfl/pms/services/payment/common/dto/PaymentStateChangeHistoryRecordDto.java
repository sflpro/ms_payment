package com.sfl.pms.services.payment.common.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.PaymentStateChangeHistoryRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/18/15
 * Time: 1:38 PM
 */
public class PaymentStateChangeHistoryRecordDto extends AbstractDomainEntityModelDto<PaymentStateChangeHistoryRecord> {
    private static final long serialVersionUID = 8310955696426229689L;

    /* Properties */
    private PaymentState updatedState;

    private String information;

    /* Constructors */
    public PaymentStateChangeHistoryRecordDto(final PaymentState updatedState, final String information) {
        this.updatedState = updatedState;
        this.information = information;
    }

    public PaymentStateChangeHistoryRecordDto() {
    }

    /* Properties getters and setters */
    public PaymentState getUpdatedState() {
        return updatedState;
    }

    public void setUpdatedState(final PaymentState updatedState) {
        this.updatedState = updatedState;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(final String information) {
        this.information = information;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final PaymentStateChangeHistoryRecord historyRecord) {
        historyRecord.setUpdatedState(getUpdatedState());
        historyRecord.setInformation(getInformation());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentStateChangeHistoryRecordDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final PaymentStateChangeHistoryRecordDto that = (PaymentStateChangeHistoryRecordDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getUpdatedState(), that.getUpdatedState());
        builder.append(getInformation(), that.getInformation());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getUpdatedState());
        builder.append(getInformation());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("updatedState", getUpdatedState());
        builder.append("information", getInformation());
        return builder.build();
    }

}
