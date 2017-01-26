package com.sfl.pms.services.payment.customer.method.model.authorization;

import com.sfl.pms.services.common.model.AbstractDomainUuIdAwareEntityModel;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 9:05 PM
 */
@Entity
@Table(name = "payment_method_customer_auth_request")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class CustomerPaymentMethodAuthorizationRequest extends AbstractDomainUuIdAwareEntityModel {
    private static final long serialVersionUID = 1133580233194852858L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private CustomerPaymentMethodAuthorizationRequestType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_type", nullable = false)
    private PaymentProviderType paymentProviderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_integration_type", nullable = false)
    private PaymentProviderIntegrationType paymentProviderIntegrationType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "authorizationRequest", optional = true, fetch = FetchType.LAZY)
    private CustomerPaymentMethod customerPaymentMethod;

    @OneToOne(mappedBy = "authorizationRequest", optional = true, fetch = FetchType.LAZY)
    private CustomerPaymentMethodAuthorizationPayment payment;

    @Column(name = "client_ip_address", nullable = true)
    private String clientIpAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private CustomerPaymentMethodAuthorizationRequestState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "payment_redirect_url", nullable = true, length = 800)
    private String paymentRedirectUrl;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequest() {
        initializeDefaults();
    }

    public CustomerPaymentMethodAuthorizationRequest(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getter and setters */
    public CustomerPaymentMethodAuthorizationRequestType getType() {
        return type;
    }

    public void setType(final CustomerPaymentMethodAuthorizationRequestType type) {
        this.type = type;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    public PaymentProviderIntegrationType getPaymentProviderIntegrationType() {
        return paymentProviderIntegrationType;
    }

    public void setPaymentProviderIntegrationType(final PaymentProviderIntegrationType paymentProviderIntegrationType) {
        this.paymentProviderIntegrationType = paymentProviderIntegrationType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public CustomerPaymentMethod getCustomerPaymentMethod() {
        return customerPaymentMethod;
    }

    public void setCustomerPaymentMethod(final CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethod = customerPaymentMethod;
    }

    public CustomerPaymentMethodAuthorizationPayment getPayment() {
        return payment;
    }

    public void setPayment(final CustomerPaymentMethodAuthorizationPayment payment) {
        this.payment = payment;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public CustomerPaymentMethodAuthorizationRequestState getState() {
        return state;
    }

    public void setState(final CustomerPaymentMethodAuthorizationRequestState state) {
        this.state = state;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public String getPaymentRedirectUrl() {
        return paymentRedirectUrl;
    }

    public void setPaymentRedirectUrl(final String paymentRedirectUrl) {
        this.paymentRedirectUrl = paymentRedirectUrl;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.state = CustomerPaymentMethodAuthorizationRequestState.CREATED;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAuthorizationRequest)) {
            return false;
        }
        final CustomerPaymentMethodAuthorizationRequest that = (CustomerPaymentMethodAuthorizationRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.getPaymentProviderIntegrationType(), that.getPaymentProviderIntegrationType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getState(), that.getState());
        builder.append(this.getCurrency(), that.getCurrency());
        builder.append(this.getPaymentRedirectUrl(), that.getPaymentRedirectUrl());
        builder.append(getIdOrNull(this.getCustomer()), getIdOrNull(that.getCustomer()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getPaymentProviderType());
        builder.append(this.getPaymentProviderIntegrationType());
        builder.append(this.getClientIpAddress());
        builder.append(this.getState());
        builder.append(this.getCurrency());
        builder.append(this.getPaymentRedirectUrl());
        builder.append(getIdOrNull(this.getCustomer()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        builder.append("paymentProviderIntegrationType", this.getPaymentProviderIntegrationType());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("state", this.getState());
        builder.append("currency", this.getCurrency());
        builder.append("paymentRedirectUrl", this.getPaymentRedirectUrl());
        builder.append("customer", getIdOrNull(this.getCustomer()));
        return builder.build();
    }
}
