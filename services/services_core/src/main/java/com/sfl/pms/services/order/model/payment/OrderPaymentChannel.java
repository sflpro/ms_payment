package com.sfl.pms.services.order.model.payment;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.order.model.Order;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/30/15
 * Time: 8:41 PM
 */
@Entity
@Table(name = "order_payment_channel")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class OrderPaymentChannel extends AbstractDomainEntityModel {

    private static final long serialVersionUID = 1988440983591557362L;

    /* Properties */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private OrderPaymentChannelType type;

    /* Constructors */
    public OrderPaymentChannel() {
    }

    /* Properties getters and setters */
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public OrderPaymentChannelType getType() {
        return type;
    }

    public void setType(final OrderPaymentChannelType type) {
        this.type = type;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentChannel)) {
            return false;
        }
        final OrderPaymentChannel that = (OrderPaymentChannel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(getIdOrNull(this.getOrder()), getIdOrNull(that.getOrder()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(getIdOrNull(this.getOrder()));
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("order", getIdOrNull(getOrder()));
        return builder.build();
    }
}
