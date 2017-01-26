package com.sfl.pms.services.order.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.order.model.OrderStateChangeHistoryRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 10:38 AM
 */
public class OrderStateChangeHistoryRecordDto extends AbstractDomainEntityModelDto<OrderStateChangeHistoryRecord> {

    private static final long serialVersionUID = -432944945215787918L;

    /* Properties */
    private OrderState updatedState;

    /* Constructors */
    public OrderStateChangeHistoryRecordDto() {
    }

    public OrderStateChangeHistoryRecordDto(final OrderState updatedState) {
        this.updatedState = updatedState;
    }

    /* Properties getters and setters */
    public OrderState getUpdatedState() {
        return updatedState;
    }

    public void setUpdatedState(OrderState updatedState) {
        this.updatedState = updatedState;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final OrderStateChangeHistoryRecord historyRecord) {
        historyRecord.setUpdatedState(getUpdatedState());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStateChangeHistoryRecordDto)) {
            return false;
        }
        final OrderStateChangeHistoryRecordDto that = (OrderStateChangeHistoryRecordDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getUpdatedState(), that.getUpdatedState());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getUpdatedState());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("updatedState", getUpdatedState());
        return builder.build();
    }
}
