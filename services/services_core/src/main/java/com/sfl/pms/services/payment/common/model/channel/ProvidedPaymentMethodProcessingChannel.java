package com.sfl.pms.services.payment.common.model.channel;

import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:08 AM
 */
@Entity
@DiscriminatorValue(value = "PROVIDED_PAYMENT_METHOD")
@Table(name = "payment_processing_channel_provided_method")
public class ProvidedPaymentMethodProcessingChannel extends PaymentProcessingChannel {

    private static final long serialVersionUID = -3398426328437418353L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = false)
    private PaymentMethodType paymentMethodType;

    /* Constructors */
    public ProvidedPaymentMethodProcessingChannel() {
        setType(PaymentProcessingChannelType.PROVIDED_PAYMENT_METHOD);
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
    public PaymentMethodType getPaymentMethodTypeIfDefined() {
        return paymentMethodType;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvidedPaymentMethodProcessingChannel)) {
            return false;
        }
        final ProvidedPaymentMethodProcessingChannel that = (ProvidedPaymentMethodProcessingChannel) o;
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
