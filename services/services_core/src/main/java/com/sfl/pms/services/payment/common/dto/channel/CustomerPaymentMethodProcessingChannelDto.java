package com.sfl.pms.services.payment.common.dto.channel;

import com.sfl.pms.services.payment.common.model.channel.CustomerPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:51 AM
 */
public class CustomerPaymentMethodProcessingChannelDto extends PaymentProcessingChannelDto<CustomerPaymentMethodProcessingChannel> {

    private static final long serialVersionUID = 7022340779142700068L;

    /* Properties */
    private Long customerPaymentMethodId;

    /* Constructors */
    public CustomerPaymentMethodProcessingChannelDto() {
        super(PaymentProcessingChannelType.CUSTOMER_PAYMENT_METHOD);
    }

    public CustomerPaymentMethodProcessingChannelDto(final PaymentProviderIntegrationType paymentProviderIntegrationType, final Long customerPaymentMethodId) {
        super(PaymentProcessingChannelType.CUSTOMER_PAYMENT_METHOD, paymentProviderIntegrationType);
        this.customerPaymentMethodId = customerPaymentMethodId;
    }

    /* Properties getters and setters */
    public Long getCustomerPaymentMethodId() {
        return customerPaymentMethodId;
    }

    public void setCustomerPaymentMethodId(final Long customerPaymentMethodId) {
        this.customerPaymentMethodId = customerPaymentMethodId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodProcessingChannelDto)) {
            return false;
        }
        final CustomerPaymentMethodProcessingChannelDto that = (CustomerPaymentMethodProcessingChannelDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getCustomerPaymentMethodId(), that.getCustomerPaymentMethodId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getCustomerPaymentMethodId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("customerPaymentMethodId", getCustomerPaymentMethodId());
        return builder.build();
    }
}
