package com.sfl.pms.services.payment.common.model.order.request;

import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 11:42 AM
 */
@Entity
@DiscriminatorValue(value = "REDIRECT_PAYMENT_METHOD")
public class OrderRequestRedirectPaymentMethod extends OrderRequestPaymentMethod {

    private static final long serialVersionUID = -2468637713077886947L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = true)
    private PaymentMethodType paymentMethodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_type", nullable = true)
    private PaymentProviderType paymentProviderType;

    /* Constructors */
    public OrderRequestRedirectPaymentMethod() {
        setType(OrderRequestPaymentMethodType.REDIRECT_PAYMENT_METHOD);
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }


    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestRedirectPaymentMethod)) {
            return false;
        }
        final OrderRequestRedirectPaymentMethod that = (OrderRequestRedirectPaymentMethod) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodType(), that.getPaymentMethodType());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodType());
        builder.append(this.getPaymentProviderType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodType", this.getPaymentMethodType());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        return builder.build();
    }

}
