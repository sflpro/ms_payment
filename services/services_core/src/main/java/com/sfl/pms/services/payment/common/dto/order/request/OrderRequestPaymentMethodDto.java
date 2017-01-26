package com.sfl.pms.services.payment.common.dto.order.request;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 11:39 AM
 */
public abstract class OrderRequestPaymentMethodDto<T extends OrderRequestPaymentMethod> extends AbstractDomainEntityModelDto<T> {

    private static final long serialVersionUID = 6070881754892787319L;

    /* Properties */
    private final OrderRequestPaymentMethodType type;

    /* Constructors */
    public OrderRequestPaymentMethodDto(final OrderRequestPaymentMethodType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public OrderRequestPaymentMethodType getType() {
        return type;
    }

    /* Public interface methods */

    @Override
    public void updateDomainEntityProperties(T paymentMethod) {
        Assert.notNull("Order request payment method should not be null");
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestPaymentMethodDto)) {
            return false;
        }
        final OrderRequestPaymentMethodDto that = (OrderRequestPaymentMethodDto) o;
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
