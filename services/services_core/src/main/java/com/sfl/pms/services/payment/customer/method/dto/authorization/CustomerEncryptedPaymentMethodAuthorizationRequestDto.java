package com.sfl.pms.services.payment.customer.method.dto.authorization;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestType;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:59 PM
 */
public class CustomerEncryptedPaymentMethodAuthorizationRequestDto extends CustomerPaymentMethodAuthorizationRequestDto<CustomerEncryptedPaymentMethodAuthorizationRequest> {
    private static final long serialVersionUID = -8049801779047346042L;

    /* Properties */
    private PaymentMethodGroupType paymentMethodGroup;

    private String encryptedData;

    /* Constructors */
    public CustomerEncryptedPaymentMethodAuthorizationRequestDto(final PaymentProviderType paymentProviderType, final PaymentProviderIntegrationType paymentProviderIntegrationType, final String clientIpAddress, final PaymentMethodGroupType paymentMethodGroup, final String encryptedData, final Currency currency) {
        super(CustomerPaymentMethodAuthorizationRequestType.ENCRYPTED_PAYMENT_METHOD, paymentProviderType, paymentProviderIntegrationType, clientIpAddress, currency);
        this.paymentMethodGroup = paymentMethodGroup;
        this.encryptedData = encryptedData;
    }

    public CustomerEncryptedPaymentMethodAuthorizationRequestDto() {
        super(CustomerPaymentMethodAuthorizationRequestType.ENCRYPTED_PAYMENT_METHOD);
    }

    /* Properties getters and setters */
    public PaymentMethodGroupType getPaymentMethodGroup() {
        return paymentMethodGroup;
    }

    public void setPaymentMethodGroup(final PaymentMethodGroupType paymentMethodGroup) {
        this.paymentMethodGroup = paymentMethodGroup;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(final String encryptedData) {
        this.encryptedData = encryptedData;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest) {
        super.updateDomainEntityProperties(authorizationRequest);
        authorizationRequest.setPaymentMethodGroup(getPaymentMethodGroup());
        authorizationRequest.setEncryptedData(getEncryptedData());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerEncryptedPaymentMethodAuthorizationRequestDto)) {
            return false;
        }
        final CustomerEncryptedPaymentMethodAuthorizationRequestDto that = (CustomerEncryptedPaymentMethodAuthorizationRequestDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodGroup(), that.getPaymentMethodGroup());
        builder.append(this.getEncryptedData(), that.getEncryptedData());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodGroup());
        builder.append(this.getEncryptedData());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodGroup", this.getPaymentMethodGroup());
        builder.append("encryptedData", this.getEncryptedData());
        return builder.build();
    }
}
