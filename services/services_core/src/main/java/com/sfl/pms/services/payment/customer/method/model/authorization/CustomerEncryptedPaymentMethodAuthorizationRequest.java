package com.sfl.pms.services.payment.customer.method.model.authorization;

import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
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
@DiscriminatorValue(value = "ENCRYPTED_PAYMENT_METHOD")
@Table(name = "payment_method_customer_auth_encrypted_request")
public class CustomerEncryptedPaymentMethodAuthorizationRequest extends CustomerPaymentMethodAuthorizationRequest {
    private static final long serialVersionUID = 5585632980590290788L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_group", nullable = false)
    private PaymentMethodGroupType paymentMethodGroup;

    @Column(name = "encrypted_data", nullable = false, length = 2000)
    private String encryptedData;

    /* Constructors */
    public CustomerEncryptedPaymentMethodAuthorizationRequest() {
        initializeDefaults();
    }

    public CustomerEncryptedPaymentMethodAuthorizationRequest(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
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

    /* Private utility methods */
    private void initializeDefaults() {
        setType(CustomerPaymentMethodAuthorizationRequestType.ENCRYPTED_PAYMENT_METHOD);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerEncryptedPaymentMethodAuthorizationRequest)) {
            return false;
        }
        final CustomerEncryptedPaymentMethodAuthorizationRequest that = (CustomerEncryptedPaymentMethodAuthorizationRequest) o;
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
