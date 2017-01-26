package com.sfl.pms.services.order.dto.payment.provider;

import com.sfl.pms.services.order.dto.payment.OrderPaymentChannelDto;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannelType;
import com.sfl.pms.services.order.model.payment.provider.OrderProviderPaymentChannel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 5:23 PM
 */
public class OrderProviderPaymentChannelDto extends OrderPaymentChannelDto<OrderProviderPaymentChannel> {

    private static final long serialVersionUID = -2737620768664349054L;

    /* Properties */
    private Long paymentId;

    /* Constructors */
    public OrderProviderPaymentChannelDto() {
        super(OrderPaymentChannelType.PAYMENT_PROVIDER);
    }

    public OrderProviderPaymentChannelDto(final Long paymentId) {
        this();
        this.paymentId = paymentId;
    }

    /* Properties getters and setters */

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final Long paymentId) {
        this.paymentId = paymentId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderProviderPaymentChannelDto)) {
            return false;
        }
        final OrderProviderPaymentChannelDto that = (OrderProviderPaymentChannelDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentId(), that.getPaymentId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentId", this.getPaymentId());
        return builder.build();
    }
}
