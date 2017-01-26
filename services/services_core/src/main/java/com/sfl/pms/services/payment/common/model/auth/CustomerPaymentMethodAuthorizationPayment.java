package com.sfl.pms.services.payment.common.model.auth;

import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentType;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 9:45 PM
 */
@Entity
@DiscriminatorValue(value = "PAYMENT_METHOD_AUTHORIZATION")
@Table(name = "payment_authorization")
public class CustomerPaymentMethodAuthorizationPayment extends Payment {
    private static final long serialVersionUID = -4533219195413090588L;

    /* Properties */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_request_id", nullable = false, unique = true)
    private CustomerPaymentMethodAuthorizationRequest authorizationRequest;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPayment() {
        initializeDefaults();
    }

    public CustomerPaymentMethodAuthorizationPayment(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequest getAuthorizationRequest() {
        return authorizationRequest;
    }

    public void setAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        this.authorizationRequest = authorizationRequest;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        setType(PaymentType.PAYMENT_METHOD_AUTHORIZATION);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAuthorizationPayment)) {
            return false;
        }
        final CustomerPaymentMethodAuthorizationPayment that = (CustomerPaymentMethodAuthorizationPayment) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getIdOrNull(this.getAuthorizationRequest()), getIdOrNull(that.getAuthorizationRequest()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(this.getAuthorizationRequest()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("authorizationRequest", getIdOrNull(this.getAuthorizationRequest()));
        return builder.build();
    }
}
