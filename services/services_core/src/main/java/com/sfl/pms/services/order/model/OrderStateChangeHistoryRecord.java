package com.sfl.pms.services.order.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 7:19 PM
 */
@Entity
@Table(name = "order_state_change_history_record")
public class OrderStateChangeHistoryRecord extends AbstractDomainEntityModel {

    private static final long serialVersionUID = -4756255710362581065L;

    /* Properties */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "initial_state", nullable = true)
    private OrderState initialState;

    @Enumerated(EnumType.STRING)
    @Column(name = "updated_state", nullable = false)
    private OrderState updatedState;

    /* Constructors */
    public OrderStateChangeHistoryRecord() {
    }

    /* Properties getters and setters */
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public OrderState getInitialState() {
        return initialState;
    }

    public void setInitialState(final OrderState initialState) {
        this.initialState = initialState;
    }

    public OrderState getUpdatedState() {
        return updatedState;
    }

    public void setUpdatedState(final OrderState updatedState) {
        this.updatedState = updatedState;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStateChangeHistoryRecord)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final OrderStateChangeHistoryRecord that = (OrderStateChangeHistoryRecord) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getIdOrNull(getOrder()), getIdOrNull(that.getOrder()));
        builder.append(getInitialState(), that.getInitialState());
        builder.append(getUpdatedState(), that.getUpdatedState());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(getOrder()));
        builder.append(getInitialState());
        builder.append(getUpdatedState());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("order", getIdOrNull(getOrder()));
        builder.append("initialState", getInitialState());
        builder.append("updatedState", getUpdatedState());
        return builder.build();
    }
}
