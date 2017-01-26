package com.sfl.pms.services.payment.customer.method.model.authorization;

import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 9:12 PM
 */
@Entity
@DiscriminatorValue(value = "PROVIDED_PAYMENT_METHOD")
@Table(name = "payment_method_customer_auth_provided_request")
public class CustomerProvidedPaymentMethodAuthorizationRequest extends CustomerPaymentMethodAuthorizationRequest {
    private static final long serialVersionUID = 5585632980590290788L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = false)
    private PaymentMethodType paymentMethodType;

    /* Constructors */
    public CustomerProvidedPaymentMethodAuthorizationRequest() {
        initializeDefaults();
    }

    public CustomerProvidedPaymentMethodAuthorizationRequest(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        setType(CustomerPaymentMethodAuthorizationRequestType.PROVIDED_PAYMENT_METHOD);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerProvidedPaymentMethodAuthorizationRequest)) {
            return false;
        }
        final CustomerProvidedPaymentMethodAuthorizationRequest that = (CustomerProvidedPaymentMethodAuthorizationRequest) o;
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
