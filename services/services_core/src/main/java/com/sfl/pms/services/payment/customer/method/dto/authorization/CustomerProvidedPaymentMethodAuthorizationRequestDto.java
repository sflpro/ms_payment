package com.sfl.pms.services.payment.customer.method.dto.authorization;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestType;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 1:10 PM
 */
public class CustomerProvidedPaymentMethodAuthorizationRequestDto extends CustomerPaymentMethodAuthorizationRequestDto<CustomerProvidedPaymentMethodAuthorizationRequest> {

    private static final long serialVersionUID = -8452523774490021153L;

    /* Properties */
    private PaymentMethodType paymentMethodType;

    /* Constructors */
    public CustomerProvidedPaymentMethodAuthorizationRequestDto() {
        super(CustomerPaymentMethodAuthorizationRequestType.PROVIDED_PAYMENT_METHOD);
    }

    public CustomerProvidedPaymentMethodAuthorizationRequestDto(final PaymentProviderType paymentProviderType, final PaymentProviderIntegrationType paymentProviderIntegrationType, final String clientIpAddress, final PaymentMethodType paymentMethodType, final Currency currency) {
        super(CustomerPaymentMethodAuthorizationRequestType.PROVIDED_PAYMENT_METHOD, paymentProviderType, paymentProviderIntegrationType, clientIpAddress, currency);
        this.paymentMethodType = paymentMethodType;
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
    public void updateDomainEntityProperties(final CustomerProvidedPaymentMethodAuthorizationRequest request) {
        super.updateDomainEntityProperties(request);
        request.setPaymentMethodType(getPaymentMethodType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerProvidedPaymentMethodAuthorizationRequestDto)) {
            return false;
        }
        final CustomerProvidedPaymentMethodAuthorizationRequestDto that = (CustomerProvidedPaymentMethodAuthorizationRequestDto) o;
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
