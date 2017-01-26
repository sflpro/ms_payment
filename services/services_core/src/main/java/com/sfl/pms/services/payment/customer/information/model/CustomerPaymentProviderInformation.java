package com.sfl.pms.services.payment.customer.information.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/23/14
 * Time: 11:00 AM
 */
@Entity
@Table(name = "payment_customer_information", uniqueConstraints = {@UniqueConstraint(name = "UK_customer_payment_provider_type", columnNames = {"customer_id", "type"})}, indexes = {@Index(name = "IDX_payment_customer_information_type", columnList = "type")})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class CustomerPaymentProviderInformation extends AbstractDomainEntityModel {
    private static final long serialVersionUID = 2070946026451733930L;

    /* Properties */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentProviderType type;

    /* Constructors */
    public CustomerPaymentProviderInformation() {
    }

    /* Properties getters and setters */
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public PaymentProviderType getType() {
        return type;
    }

    public void setType(final PaymentProviderType type) {
        this.type = type;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentProviderInformation)) {
            return false;
        }
        final CustomerPaymentProviderInformation that = (CustomerPaymentProviderInformation) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(getIdOrNull(this.getCustomer()), getIdOrNull(that.getCustomer()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(getIdOrNull(this.getCustomer()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("customer", getIdOrNull(this.getCustomer()));
        return builder.build();
    }
}
