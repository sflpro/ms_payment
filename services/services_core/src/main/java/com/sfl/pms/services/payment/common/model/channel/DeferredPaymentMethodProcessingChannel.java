package com.sfl.pms.services.payment.common.model.channel;

import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:37 AM
 */
@Entity
@DiscriminatorValue(value = "DEFERRED_PAYMENT_METHOD")
@Table(name = "payment_processing_channel_deferred_method")
public class DeferredPaymentMethodProcessingChannel extends PaymentProcessingChannel {

    private static final long serialVersionUID = -558591362567517805L;

    /* Constructors */
    public DeferredPaymentMethodProcessingChannel() {
        setType(PaymentProcessingChannelType.DEFERRED_PAYMENT_METHOD);
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
        if (!(o instanceof DeferredPaymentMethodProcessingChannel)) {
            return false;
        }
        final DeferredPaymentMethodProcessingChannel that = (DeferredPaymentMethodProcessingChannel) o;
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
