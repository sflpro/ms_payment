package com.sfl.pms.services.payment.common.model.order.request;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 11:39 AM
 */
@Entity
@Table(name = "payment_order_request_method")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class OrderRequestPaymentMethod extends AbstractDomainEntityModel {

    private static final long serialVersionUID = 6070881754892787319L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private OrderRequestPaymentMethodType type;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_payment_request_id", nullable = false, unique = false)
    private OrderPaymentRequest orderPaymentRequest;

    /* Constructors */
    public OrderRequestPaymentMethod() {
    }

    /* Properties getters and setters */
    public OrderRequestPaymentMethodType getType() {
        return type;
    }

    public void setType(final OrderRequestPaymentMethodType type) {
        this.type = type;
    }

    public OrderPaymentRequest getOrderPaymentRequest() {
        return orderPaymentRequest;
    }

    public void setOrderPaymentRequest(final OrderPaymentRequest order) {
        this.orderPaymentRequest = order;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestPaymentMethod)) {
            return false;
        }
        final OrderRequestPaymentMethod that = (OrderRequestPaymentMethod) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(getIdOrNull(this.getOrderPaymentRequest()), getIdOrNull(that.getOrderPaymentRequest()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(getIdOrNull(this.getOrderPaymentRequest()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("orderPaymentRequest", getIdOrNull(this.getOrderPaymentRequest()));
        return builder.build();
    }
}
