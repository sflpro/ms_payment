package com.sfl.pms.services.order.model.payment.provider;

import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannelType;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 5:00 PM
 */
@Entity
@DiscriminatorValue(value = "PAYMENT_PROVIDER")
@Table(name = "order_payment_channel_provider")
public class OrderProviderPaymentChannel extends OrderPaymentChannel {
    private static final long serialVersionUID = -8203286473862175111L;

    /* Properties */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false, unique = true)
    private OrderPayment payment;

    /* Constructors */
    public OrderProviderPaymentChannel() {
        setType(OrderPaymentChannelType.PAYMENT_PROVIDER);
    }

    /* Properties getters and setters */
    public OrderPayment getPayment() {
        return payment;
    }

    public void setPayment(final OrderPayment orderPayment) {
        this.payment = orderPayment;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderProviderPaymentChannel)) {
            return false;
        }
        final OrderProviderPaymentChannel that = (OrderProviderPaymentChannel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getIdOrNull(this.getPayment()), getIdOrNull(that.getPayment()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(this.getPayment()));
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("payment", getIdOrNull(this.getPayment()));
        return builder.build();
    }
}
