package com.sfl.pms.services.payment.common.dto.channel;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:40 AM
 */
public abstract class PaymentProcessingChannelDto<T extends PaymentProcessingChannel> extends AbstractDomainEntityModelDto<T> {

    private static final long serialVersionUID = -656784483097739256L;

    /* Properties */
    private final PaymentProcessingChannelType type;

    private PaymentProviderIntegrationType paymentProviderIntegrationType;

    /* Constructors */
    public PaymentProcessingChannelDto(final PaymentProcessingChannelType type) {
        this.type = type;
    }

    public PaymentProcessingChannelDto(final PaymentProcessingChannelType type, final PaymentProviderIntegrationType paymentProviderIntegrationType) {
        this(type);
        this.paymentProviderIntegrationType = paymentProviderIntegrationType;
    }

    /* Properties getters and setters */
    public PaymentProcessingChannelType getType() {
        return type;
    }

    public PaymentProviderIntegrationType getPaymentProviderIntegrationType() {
        return paymentProviderIntegrationType;
    }

    public void setPaymentProviderIntegrationType(PaymentProviderIntegrationType paymentProviderIntegrationType) {
        this.paymentProviderIntegrationType = paymentProviderIntegrationType;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T paymentProcessingChannel) {
        Assert.notNull(paymentProcessingChannel, "Payment processing channel should not be null");
        paymentProcessingChannel.setPaymentProviderIntegrationType(getPaymentProviderIntegrationType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProcessingChannelDto)) {
            return false;
        }
        final PaymentProcessingChannelDto that = (PaymentProcessingChannelDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getPaymentProviderIntegrationType(), that.getPaymentProviderIntegrationType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getPaymentProviderIntegrationType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("paymentProviderIntegrationType", this.getPaymentProviderIntegrationType());
        return builder.build();
    }
}
