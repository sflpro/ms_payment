package com.sfl.pms.services.payment.common.dto.channel;

import com.sfl.pms.services.payment.common.model.channel.EncryptedPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:49 AM
 */
public class EncryptedPaymentMethodProcessingChannelDto extends PaymentProcessingChannelDto<EncryptedPaymentMethodProcessingChannel> {

    private static final long serialVersionUID = 7747420418133763510L;

    /* Properties */
    private String encryptedPaymentMethodInformation;

    /* Constructors */
    public EncryptedPaymentMethodProcessingChannelDto() {
        super(PaymentProcessingChannelType.ENCRYPTED_PAYMENT_METHOD);
    }

    public EncryptedPaymentMethodProcessingChannelDto(final PaymentProviderIntegrationType paymentProviderIntegrationType, final String encryptedPaymentMethodInformation) {
        super(PaymentProcessingChannelType.ENCRYPTED_PAYMENT_METHOD, paymentProviderIntegrationType);
        this.encryptedPaymentMethodInformation = encryptedPaymentMethodInformation;
    }


    /* Properties getters and setters */
    public String getEncryptedPaymentMethodInformation() {
        return encryptedPaymentMethodInformation;
    }

    public void setEncryptedPaymentMethodInformation(final String encryptedPaymentMethodInformation) {
        this.encryptedPaymentMethodInformation = encryptedPaymentMethodInformation;
    }

    /* Public interface methods */

    @Override
    public void updateDomainEntityProperties(final EncryptedPaymentMethodProcessingChannel paymentProcessingChannel) {
        super.updateDomainEntityProperties(paymentProcessingChannel);
        paymentProcessingChannel.setEncryptedData(getEncryptedPaymentMethodInformation());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncryptedPaymentMethodProcessingChannelDto)) {
            return false;
        }
        final EncryptedPaymentMethodProcessingChannelDto that = (EncryptedPaymentMethodProcessingChannelDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getEncryptedPaymentMethodInformation(), that.getEncryptedPaymentMethodInformation());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getEncryptedPaymentMethodInformation());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("encryptedPaymentMethodInformation", this.getEncryptedPaymentMethodInformation());
        return builder.build();
    }
}
