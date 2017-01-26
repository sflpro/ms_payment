package com.sfl.pms.services.payment.common.model.channel;

import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:13 AM
 */
@Entity
@DiscriminatorValue(value = "ENCRYPTED_PAYMENT_METHOD")
@Table(name = "payment_processing_channel_encrypted_method")
public class EncryptedPaymentMethodProcessingChannel extends PaymentProcessingChannel {

    private static final long serialVersionUID = 8700127098122443611L;

    /* Properties */
    @Column(name = "encrypted_data", nullable = false, length = 2000)
    private String encryptedData;

    /* Constructors */
    public EncryptedPaymentMethodProcessingChannel() {
        setType(PaymentProcessingChannelType.ENCRYPTED_PAYMENT_METHOD);
    }

    /* Properties getters and setters */
    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(final String encryptedPaymentMethodInformation) {
        this.encryptedData = encryptedPaymentMethodInformation;
    }

    /* Public interface methods */
    @Override
    public PaymentMethodType getPaymentMethodTypeIfDefined() {
        return null;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncryptedPaymentMethodProcessingChannel)) {
            return false;
        }
        final EncryptedPaymentMethodProcessingChannel that = (EncryptedPaymentMethodProcessingChannel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getEncryptedData(), that.getEncryptedData());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getEncryptedData());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("encryptedPaymentMethodInformation", this.getEncryptedData());
        return builder.build();
    }
}
