package com.sfl.pms.services.payment.common.dto.channel;

import com.sfl.pms.services.payment.common.model.channel.DeferredPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:54 AM
 */
public class DeferredPaymentMethodProcessingChannelDto extends PaymentProcessingChannelDto<DeferredPaymentMethodProcessingChannel> {

    private static final long serialVersionUID = 5442621743004428756L;

    /* Constructors */
    public DeferredPaymentMethodProcessingChannelDto() {
        super(PaymentProcessingChannelType.DEFERRED_PAYMENT_METHOD);
    }

    public DeferredPaymentMethodProcessingChannelDto(final PaymentProviderIntegrationType paymentProviderIntegrationType) {
        super(PaymentProcessingChannelType.DEFERRED_PAYMENT_METHOD, paymentProviderIntegrationType);
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
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        return builder.build();
    }
}
