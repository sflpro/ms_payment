package com.sfl.pms.services.payment.method.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinitionType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static com.sfl.pms.services.common.model.AbstractDomainEntityModel.getDoubleValueOrNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:44 PM
 */
public abstract class PaymentMethodDefinitionDto<T extends PaymentMethodDefinition> extends AbstractDomainEntityModelDto<T> {

    private static final long serialVersionUID = 5332221252277167393L;

    /* Properties */
    private final PaymentMethodDefinitionType type;

    private BigDecimal authorizationSurcharge;

    private BigDecimal paymentSurcharge;

    private Currency currency;

    private PaymentProviderType paymentProviderType;

    private boolean recurringPaymentEnabled;

    /* Constructors */
    public PaymentMethodDefinitionDto(final PaymentMethodDefinitionType type) {
        this.type = type;
    }

    public PaymentMethodDefinitionDto(final PaymentMethodDefinitionType type, final BigDecimal authorizationSurcharge, final BigDecimal paymentSurcharge, final Currency currency, final PaymentProviderType paymentProviderType, final boolean recurringPaymentEnabled) {
        this(type);
        this.authorizationSurcharge = authorizationSurcharge;
        this.paymentSurcharge = paymentSurcharge;
        this.currency = currency;
        this.paymentProviderType = paymentProviderType;
        this.recurringPaymentEnabled = recurringPaymentEnabled;
    }

    /* Properties getters and setters */
    public PaymentMethodDefinitionType getType() {
        return type;
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

    public void setRecurringPaymentEnabled(final boolean recurringPaymentEnabled) {
        this.recurringPaymentEnabled = recurringPaymentEnabled;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T paymentMethodDefinition) {
        Assert.notNull(paymentMethodDefinition, "Payment method definition should not be null");
        paymentMethodDefinition.setAuthorizationSurcharge(getAuthorizationSurcharge());
        paymentMethodDefinition.setPaymentSurcharge(getPaymentSurcharge());
        paymentMethodDefinition.setCurrency(getCurrency());
        paymentMethodDefinition.setPaymentProviderType(getPaymentProviderType());
        paymentMethodDefinition.setRecurringPaymentEnabled(isRecurringPaymentEnabled());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodDefinitionDto)) {
            return false;
        }
        final PaymentMethodDefinitionDto that = (PaymentMethodDefinitionDto) o;
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
