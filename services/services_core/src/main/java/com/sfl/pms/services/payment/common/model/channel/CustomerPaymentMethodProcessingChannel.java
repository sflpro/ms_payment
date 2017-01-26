package com.sfl.pms.services.payment.common.model.channel;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/7/15
 * Time: 11:19 AM
 */
@Entity
@DiscriminatorValue(value = "CUSTOMER_PAYMENT_METHOD")
@Table(name = "payment_processing_channel_customer_method")
public class CustomerPaymentMethodProcessingChannel extends PaymentProcessingChannel {

    private static final long serialVersionUID = -6123238620038087005L;

    /* Properties */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_payment_method_id", nullable = false, unique = false)
    private CustomerPaymentMethod customerPaymentMethod;

    /* Constructors */
    public CustomerPaymentMethodProcessingChannel() {
        setType(PaymentProcessingChannelType.CUSTOMER_PAYMENT_METHOD);
    }

    /* Properties getters and setters */
    public CustomerPaymentMethod getCustomerPaymentMethod() {
        return customerPaymentMethod;
    }

    public void setCustomerPaymentMethod(final CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethod = customerPaymentMethod;
    }

    /* Public interface methods */
    @Override
    public PaymentMethodType getPaymentMethodTypeIfDefined() {
        return customerPaymentMethod.getPaymentMethodType();
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodProcessingChannel)) {
            return false;
        }
        final CustomerPaymentMethodProcessingChannel that = (CustomerPaymentMethodProcessingChannel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(AbstractDomainEntityModel.getIdOrNull(this.getCustomerPaymentMethod()), AbstractDomainEntityModel.getIdOrNull(that.getCustomerPaymentMethod()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(AbstractDomainEntityModel.getIdOrNull(this.getCustomerPaymentMethod()));
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("customerPaymentMethod", AbstractDomainEntityModel.getIdOrNull(this.getCustomerPaymentMethod()));
        return builder.build();
    }
}
