package com.sfl.pms.services.payment.common.dto.channel;

import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.common.model.channel.ProvidedPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:45 AM
 */
public class ProvidedPaymentMethodProcessingChannelDto extends PaymentProcessingChannelDto<ProvidedPaymentMethodProcessingChannel> {

    private static final long serialVersionUID = 5821200953815305703L;

    /* Properties */
    private PaymentMethodType paymentMethodType;

    /* Constructors */
    public ProvidedPaymentMethodProcessingChannelDto() {
        super(PaymentProcessingChannelType.PROVIDED_PAYMENT_METHOD);
    }

    public ProvidedPaymentMethodProcessingChannelDto(final PaymentProviderIntegrationType paymentProviderIntegrationType, final PaymentMethodType paymentMethodType) {
        super(PaymentProcessingChannelType.PROVIDED_PAYMENT_METHOD, paymentProviderIntegrationType);
        this.paymentMethodType = paymentMethodType;
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final ProvidedPaymentMethodProcessingChannel paymentProcessingChannel) {
        super.updateDomainEntityProperties(paymentProcessingChannel);
        paymentProcessingChannel.setPaymentMethodType(getPaymentMethodType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvidedPaymentMethodProcessingChannelDto)) {
            return false;
        }
        final ProvidedPaymentMethodProcessingChannelDto that = (ProvidedPaymentMethodProcessingChannelDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodType(), that.getPaymentMethodType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodType", this.getPaymentMethodType());
        return builder.build();
    }

}
