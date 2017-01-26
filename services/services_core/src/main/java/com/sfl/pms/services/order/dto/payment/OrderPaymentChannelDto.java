package com.sfl.pms.services.order.dto.payment;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannelType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 5:19 PM
 */
public abstract class OrderPaymentChannelDto<T extends OrderPaymentChannel> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = -2010899281385193327L;

    /* Properties */
    private final OrderPaymentChannelType type;

    /* Constructors */
    public OrderPaymentChannelDto(final OrderPaymentChannelType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public OrderPaymentChannelType getType() {
        return type;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T paymentChannel) {
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentChannelDto)) {
            return false;
        }
        final OrderPaymentChannelDto that = (OrderPaymentChannelDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        return builder.build();
    }
}
