package com.sfl.pms.services.payment.common.model.order;

import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/24/14
 * Time: 11:39 PM
 */
@Entity
@DiscriminatorValue(value = "ORDER")
@Table(name = "payment_order")
public class OrderPayment extends Payment {

    private static final long serialVersionUID = -2355136676513100183L;

    /* Properties */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = false)
    private Order order;

    /* Constructors */
    public OrderPayment() {
        initializeDefaults();
    }

    public OrderPayment(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        setType(PaymentType.ORDER);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPayment)) {
            return false;
        }
        final OrderPayment that = (OrderPayment) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getIdOrNull(this.getOrder()), getIdOrNull(that.getOrder()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(this.getOrder()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("order", getIdOrNull(this.getOrder()));
        return builder.build();
    }
}
