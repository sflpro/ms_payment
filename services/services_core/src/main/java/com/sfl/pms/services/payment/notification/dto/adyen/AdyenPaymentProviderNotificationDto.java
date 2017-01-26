package com.sfl.pms.services.payment.notification.dto.adyen;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.adyen.AdyenPaymentMethodType;
import com.sfl.pms.services.payment.notification.dto.PaymentProviderNotificationDto;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

import static com.sfl.pms.services.common.model.AbstractDomainEntityModel.getDoubleValueOrNull;


/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:35 PM
 */
public class AdyenPaymentProviderNotificationDto extends PaymentProviderNotificationDto<AdyenPaymentProviderNotification> {
    private static final long serialVersionUID = 6275642824894207746L;

    /* Properties */
    private String eventCode;

    private String authCode;

    private String pspReference;

    private Boolean success;

    private String merchantReference;

    private AdyenPaymentMethodType paymentMethodType;

    private Currency currency;

    private BigDecimal amount;

    /* Constructors */
    public AdyenPaymentProviderNotificationDto() {
        super(PaymentProviderType.ADYEN);
    }

    public AdyenPaymentProviderNotificationDto(final String rawContent, final String clientIpAddress, final String eventCode, final String authCode, final String pspReference, final Boolean success, final String merchantReference, final AdyenPaymentMethodType paymentMethodType, final Currency currency, final BigDecimal amount) {
        super(PaymentProviderType.ADYEN, rawContent, clientIpAddress);
        this.eventCode = eventCode;
        this.authCode = authCode;
        this.pspReference = pspReference;
        this.success = success;
        this.merchantReference = merchantReference;
        this.paymentMethodType = paymentMethodType;
        this.currency = currency;
        this.amount = amount;
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

    /* Public interface methods */

    @Override
    public void updateDomainEntityProperties(final AdyenPaymentProviderNotification notification) {
        super.updateDomainEntityProperties(notification);
        notification.setAmount(getAmount());
        notification.setAuthCode(getAuthCode());
        notification.setCurrency(getCurrency());
        notification.setEventCode(getEventCode());
        notification.setMerchantReference(getMerchantReference());
        notification.setPaymentMethodType(getPaymentMethodType());
        notification.setPspReference(getPspReference());
        notification.setSuccess(getSuccess());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentProviderNotificationDto)) {
            return false;
        }
        final AdyenPaymentProviderNotificationDto that = (AdyenPaymentProviderNotificationDto) o;
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
