package com.sfl.pms.services.payment.method.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:08 PM
 */
@Entity
@Table(name = "payment_method_definition", uniqueConstraints = {@UniqueConstraint(name = "UK_payment_method_type_currency_provider_method", columnNames = {"type", "currency", "payment_provider_type", "payment_method_type"}), @UniqueConstraint(name = "UK_payment_method_type_currency_provider_group", columnNames = {"type", "currency", "payment_provider_type", "payment_method_group_type"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentMethodDefinition extends AbstractDomainEntityModel {

    private static final long serialVersionUID = -1977080798232063334L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentMethodDefinitionType type;

    @Column(name = "authorization_surcharge", nullable = false)
    private BigDecimal authorizationSurcharge;

    @Column(name = "payment_surcharge", nullable = false)
    private BigDecimal paymentSurcharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_type", nullable = false)
    private PaymentProviderType paymentProviderType;

    @Column(name = "recurring_payment_enabled", nullable = false)
    private boolean recurringPaymentEnabled;

    /* Constructors */
    public PaymentMethodDefinition() {
    }

    /* Properties getters and setters */
    public PaymentMethodDefinitionType getType() {
        return type;
    }

    public void setType(final PaymentMethodDefinitionType type) {
        this.type = type;
    }

    public BigDecimal getAuthorizationSurcharge() {
        return authorizationSurcharge;
    }

    public void setAuthorizationSurcharge(final BigDecimal authorizationSurcharge) {
        this.authorizationSurcharge = authorizationSurcharge;
    }

    public BigDecimal getPaymentSurcharge() {
        return paymentSurcharge;
    }

    public void setPaymentSurcharge(final BigDecimal paymentSurcharge) {
        this.paymentSurcharge = paymentSurcharge;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    public boolean isRecurringPaymentEnabled() {
        return recurringPaymentEnabled;
    }

    public void setRecurringPaymentEnabled(final boolean storeEnabled) {
        this.recurringPaymentEnabled = storeEnabled;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodDefinition)) {
            return false;
        }
        final PaymentMethodDefinition that = (PaymentMethodDefinition) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(getDoubleValueOrNull(this.getAuthorizationSurcharge()), getDoubleValueOrNull(that.getAuthorizationSurcharge()));
        builder.append(getDoubleValueOrNull(this.getPaymentSurcharge()), getDoubleValueOrNull(that.getPaymentSurcharge()));
        builder.append(this.getCurrency(), that.getCurrency());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.isRecurringPaymentEnabled(), that.isRecurringPaymentEnabled());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(getDoubleValueOrNull(this.getAuthorizationSurcharge()));
        builder.append(getDoubleValueOrNull(this.getPaymentSurcharge()));
        builder.append(this.getCurrency());
        builder.append(this.getPaymentProviderType());
        builder.append(this.isRecurringPaymentEnabled());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("authorizationSurcharge", this.getAuthorizationSurcharge());
        builder.append("paymentMethodSurcharge", this.getPaymentSurcharge());
        builder.append("currency", this.getCurrency());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        builder.append("recurringPaymentEnabled", this.isRecurringPaymentEnabled());
        return builder.build();
    }
}
