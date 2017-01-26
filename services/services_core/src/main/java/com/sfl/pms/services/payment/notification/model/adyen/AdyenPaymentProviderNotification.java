package com.sfl.pms.services.payment.notification.model.adyen;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.adyen.AdyenPaymentMethodType;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 7:38 PM
 */
@Entity
@DiscriminatorValue(value = "ADYEN")
@Table(name = "payment_provider_notification_adyen")
public class AdyenPaymentProviderNotification extends PaymentProviderNotification {
    private static final long serialVersionUID = -263207451343076074L;

    /*  Properties  */
    @Column(name = "event_code", nullable = true)
    private String eventCode;

    @Column(name = "auth_code", nullable = true)
    private String authCode;

    @Column(name = "psp_reference", nullable = true)
    private String pspReference;

    @Column(name = "success", nullable = true)
    private Boolean success;

    @Column(name = "merchant_reference", nullable = true)
    private String merchantReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = true)
    private AdyenPaymentMethodType paymentMethodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = true)
    private Currency currency;

    @Column(name = "amount", nullable = true)
    private BigDecimal amount;

    /* Constructors */
    public AdyenPaymentProviderNotification() {
        setType(PaymentProviderType.ADYEN);
    }

    public AdyenPaymentProviderNotification(final boolean generateUuId) {
        super(generateUuId);
        setType(PaymentProviderType.ADYEN);

    }

    /* Properties getters and setters */
    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(final String eventCode) {
        this.eventCode = eventCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(final String authCode) {
        this.authCode = authCode;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(final String pspReference) {
        this.pspReference = pspReference;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(final String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public AdyenPaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final AdyenPaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentProviderNotification)) {
            return false;
        }
        final AdyenPaymentProviderNotification that = (AdyenPaymentProviderNotification) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getEventCode(), that.getEventCode());
        builder.append(this.getAuthCode(), that.getAuthCode());
        builder.append(this.getPspReference(), that.getPspReference());
        builder.append(this.getSuccess(), that.getSuccess());
        builder.append(this.getMerchantReference(), that.getMerchantReference());
        builder.append(this.getPaymentMethodType(), that.getPaymentMethodType());
        builder.append(this.getCurrency(), that.getCurrency());
        builder.append(getDoubleValueOrNull(this.getAmount()), getDoubleValueOrNull(that.getAmount()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getEventCode());
        builder.append(this.getAuthCode());
        builder.append(this.getPspReference());
        builder.append(this.getSuccess());
        builder.append(this.getMerchantReference());
        builder.append(this.getPaymentMethodType());
        builder.append(this.getCurrency());
        builder.append(getDoubleValueOrNull(this.getAmount()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("eventCode", this.getEventCode());
        builder.append("authCode", this.getAuthCode());
        builder.append("pspReference", this.getPspReference());
        builder.append("success", this.getSuccess());
        builder.append("merchantReference", this.getMerchantReference());
        builder.append("paymentMethodType", this.getPaymentMethodType());
        builder.append("currency", this.getCurrency());
        builder.append("amount", this.getAmount());
        return builder.build();
    }
}
