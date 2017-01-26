package com.sfl.pms.services.payment.customer.method.dto.authorization;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:57 PM
 */
public abstract class CustomerPaymentMethodAuthorizationRequestDto<T extends CustomerPaymentMethodAuthorizationRequest> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = 4184651907020720660L;

    /* Properties */
    private final CustomerPaymentMethodAuthorizationRequestType type;

    private String clientIpAddress;

    private PaymentProviderType paymentProviderType;

    private PaymentProviderIntegrationType paymentProviderIntegrationType;

    private Currency currency;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestDto(final CustomerPaymentMethodAuthorizationRequestType type) {
        this.type = type;
    }

    public CustomerPaymentMethodAuthorizationRequestDto(final CustomerPaymentMethodAuthorizationRequestType type, final PaymentProviderType paymentProviderType, final PaymentProviderIntegrationType paymentProviderIntegrationType, final String clientIpAddress, final Currency currency) {
        this(type);
        this.paymentProviderType = paymentProviderType;
        this.paymentProviderIntegrationType = paymentProviderIntegrationType;
        this.clientIpAddress = clientIpAddress;
        this.currency = currency;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestType getType() {
        return type;
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

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T request) {
        Assert.notNull(request, "Authorization request should not be null");
        request.setPaymentProviderType(getPaymentProviderType());
        request.setPaymentProviderIntegrationType(getPaymentProviderIntegrationType());
        request.setClientIpAddress(getClientIpAddress());
        request.setCurrency(getCurrency());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAuthorizationRequestDto)) {
            return false;
        }
        final CustomerPaymentMethodAuthorizationRequestDto that = (CustomerPaymentMethodAuthorizationRequestDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.getPaymentProviderIntegrationType(), that.getPaymentProviderIntegrationType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
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
        return builder.build();
    }
}
