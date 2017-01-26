package com.sfl.pms.services.payment.customer.method.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 8:33 PM
 */
@Entity
@Table(name = "payment_method_customer_provider_info")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class CustomerPaymentMethodProviderInformation extends AbstractDomainEntityModel {
    private static final long serialVersionUID = -208170812813344019L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentProviderType type;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_payment_method_id", nullable = false)
    private CustomerPaymentMethod customerPaymentMethod;

    /* Constructors */
    public CustomerPaymentMethodProviderInformation() {
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    public void setType(final PaymentProviderType type) {
        this.type = type;
    }

    public CustomerPaymentMethod getCustomerPaymentMethod() {
        return customerPaymentMethod;
    }

    public void setCustomerPaymentMethod(final CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethod = customerPaymentMethod;
    }

    /* Abstract methods */
    public abstract String getPaymentProviderIdentifierForPaymentMethod();

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodProviderInformation)) {
            return false;
        }
        final CustomerPaymentMethodProviderInformation that = (CustomerPaymentMethodProviderInformation) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(getIdOrNull(this.getCustomerPaymentMethod()), getIdOrNull(that.getCustomerPaymentMethod()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(getIdOrNull(this.getCustomerPaymentMethod()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("customerPaymentMethod", getIdOrNull(this.getCustomerPaymentMethod()));
        return builder.build();
    }

}
