package com.sfl.pms.services.payment.common.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:43 PM
 */
public abstract class PaymentDto<T extends Payment> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = 2105675072378055537L;

    /* Properties */
    private BigDecimal amount;

    private BigDecimal paymentMethodSurcharge;

    private Currency currency;

    private final PaymentType type;

    private PaymentProviderType paymentProviderType;

    private String clientIpAddress;

    private boolean storePaymentMethod;

    /* Constructors */
    public PaymentDto(final PaymentType type, final PaymentProviderType paymentProviderType, final BigDecimal amount, final BigDecimal paymentMethodSurcharge, final Currency currency, final String clientIpAddress, final boolean storePaymentMethod) {
        this(type);
        this.amount = amount;
        this.paymentMethodSurcharge = paymentMethodSurcharge;
        this.currency = currency;
        this.clientIpAddress = clientIpAddress;
        this.paymentProviderType = paymentProviderType;
        this.storePaymentMethod = storePaymentMethod;
    }

    public PaymentDto(final PaymentType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaymentMethodSurcharge() {
        return paymentMethodSurcharge;
    }

    public void setPaymentMethodSurcharge(final BigDecimal paymentMethodSurcharge) {
        this.paymentMethodSurcharge = paymentMethodSurcharge;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public PaymentType getType() {
        return type;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public boolean isStorePaymentMethod() {
        return storePaymentMethod;
    }

    public void setStorePaymentMethod(final boolean storePaymentMethod) {
        this.storePaymentMethod = storePaymentMethod;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T payment) {
        payment.setAmount(getAmount());
        payment.setPaymentMethodSurcharge(getPaymentMethodSurcharge());
        payment.setCurrency(getCurrency());
        payment.setClientIpAddress(getClientIpAddress());
        payment.setPaymentProviderType(getPaymentProviderType());
        payment.setStorePaymentMethod(isStorePaymentMethod());
        payment.setPaymentTotalAmount(getAmount().add(getPaymentMethodSurcharge()));
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDto)) {
            return false;
        }
        final PaymentDto that = (PaymentDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.isStorePaymentMethod(), that.isStorePaymentMethod());
        builder.append(AbstractDomainEntityModel.getDoubleValueOrNull(this.getAmount()), AbstractDomainEntityModel.getDoubleValueOrNull(that.getAmount()));
        builder.append(AbstractDomainEntityModel.getDoubleValueOrNull(this.getPaymentMethodSurcharge()), AbstractDomainEntityModel.getDoubleValueOrNull(that.getPaymentMethodSurcharge()));
        builder.append(this.getCurrency(), that.getCurrency());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getPaymentProviderType());
        builder.append(this.getClientIpAddress());
        builder.append(this.isStorePaymentMethod());
        builder.append(AbstractDomainEntityModel.getDoubleValueOrNull(this.getAmount()));
        builder.append(AbstractDomainEntityModel.getDoubleValueOrNull(this.getPaymentMethodSurcharge()));
        builder.append(this.getCurrency());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        builder.append("amount", this.getAmount());
        builder.append("paymentMethodSurcharge", this.getPaymentMethodSurcharge());
        builder.append("currency", this.getCurrency());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("storePaymentMethod", this.isStorePaymentMethod());
        return builder.build();
    }
}
