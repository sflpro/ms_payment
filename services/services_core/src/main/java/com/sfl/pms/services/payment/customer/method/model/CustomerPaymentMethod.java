package com.sfl.pms.services.payment.customer.method.model;

import com.sfl.pms.services.common.model.AbstractDomainUuIdAwareEntityModel;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/24/14
 * Time: 11:58 PM
 */
@Entity
@Table(name = "payment_method_customer", indexes = {@Index(name = "IDX_payment_method_customer_type", columnList = "type"), @Index(name = "IDX_payment_method_customer_removed", columnList = "removed"), @Index(name = "IDX_payment_method_customer_created", columnList = "created"), @Index(name = "IDX_payment_method_customer_payment_method_type", columnList = "payment_method_type")})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class CustomerPaymentMethod extends AbstractDomainUuIdAwareEntityModel {
    private static final long serialVersionUID = 6283659811750028190L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private CustomerPaymentMethodType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = false)
    private PaymentMethodType paymentMethodType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_request_id", nullable = true, unique = true)
    private CustomerPaymentMethodAuthorizationRequest authorizationRequest;

    @OneToOne(mappedBy = "customerPaymentMethod", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomerPaymentMethodProviderInformation providerInformation;

    /* Constructors */
    public CustomerPaymentMethod() {
    }

    public CustomerPaymentMethod(final boolean generateUuId) {
        super(generateUuId);
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public CustomerPaymentMethodType getType() {
        return type;
    }

    public void setType(final CustomerPaymentMethodType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public CustomerPaymentMethodProviderInformation getProviderInformation() {
        return providerInformation;
    }

    public void setProviderInformation(final CustomerPaymentMethodProviderInformation providerInformation) {
        this.providerInformation = providerInformation;
    }

    public CustomerPaymentMethodAuthorizationRequest getAuthorizationRequest() {
        return authorizationRequest;
    }

    public void setAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest paymentMethodAuthorizationRequest) {
        this.authorizationRequest = paymentMethodAuthorizationRequest;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethod)) {
            return false;
        }
        final CustomerPaymentMethod that = (CustomerPaymentMethod) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getPaymentMethodType(), that.getPaymentMethodType());
        builder.append(getIdOrNull(this.getCustomer()), getIdOrNull(that.getCustomer()));
        builder.append(getIdOrNull(this.getAuthorizationRequest()), getIdOrNull(that.getAuthorizationRequest()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getPaymentMethodType());
        builder.append(getIdOrNull(this.getCustomer()));
        builder.append(getIdOrNull(this.getAuthorizationRequest()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("paymentMethod", this.getPaymentMethodType());
        builder.append("customer", getIdOrNull(this.getCustomer()));
        builder.append("authorizationRequest", getIdOrNull(this.getAuthorizationRequest()));
        return builder.build();
    }
}
