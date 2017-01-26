package com.sfl.pms.services.payment.settings.dto.adyen;

import com.sfl.pms.services.country.model.CountryCode;
import com.sfl.pms.services.payment.settings.dto.PaymentProviderSettingsDto;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:35 PM
 */
public class AdyenPaymentSettingsDto extends PaymentProviderSettingsDto<AdyenPaymentSettings> {
    private static final long serialVersionUID = -1716499063763172611L;

    /* Properties */
    private String skinCode;

    private String skinHmacKey;

    private String hppPaymentUrl;

    private String hppPaymentMethodSelectionUrl;

    private CountryCode defaultCountry;

    private String merchantAccount;

    private String defaultLocale;

    private String notificationsToken;

    /* Constructors */
    public AdyenPaymentSettingsDto() {
    }

    public AdyenPaymentSettingsDto(final EnvironmentType environmentType, final String userName, final String password, final String notificationsToken) {
        super(environmentType, userName, password, notificationsToken);
    }

    /* Properties getters and setters */
    public String getSkinCode() {
        return skinCode;
    }

    public void setSkinCode(final String skinCode) {
        this.skinCode = skinCode;
    }

    public String getSkinHmacKey() {
        return skinHmacKey;
    }

    public void setSkinHmacKey(final String skinHmacKey) {
        this.skinHmacKey = skinHmacKey;
    }

    public String getHppPaymentUrl() {
        return hppPaymentUrl;
    }

    public void setHppPaymentUrl(final String hppPaymentUrl) {
        this.hppPaymentUrl = hppPaymentUrl;
    }

    public String getHppPaymentMethodSelectionUrl() {
        return hppPaymentMethodSelectionUrl;
    }

    public void setHppPaymentMethodSelectionUrl(final String hppPaymentMethodSelectionUrl) {
        this.hppPaymentMethodSelectionUrl = hppPaymentMethodSelectionUrl;
    }

    public CountryCode getDefaultCountry() {
        return defaultCountry;
    }

    public void setDefaultCountry(final CountryCode defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(final String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(final String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getNotificationsToken() {
        return notificationsToken;
    }

    public void setNotificationsToken(final String notificationsToken) {
        this.notificationsToken = notificationsToken;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final AdyenPaymentSettings settings) {
        super.updateDomainEntityProperties(settings);
        settings.setSkinCode(getSkinCode());
        settings.setSkinHmacKey(getSkinHmacKey());
        settings.setHppPaymentUrl(getHppPaymentUrl());
        settings.setHppPaymentMethodSelectionUrl(getHppPaymentMethodSelectionUrl());
        settings.setDefaultCountry(getDefaultCountry());
        settings.setMerchantAccount(getMerchantAccount());
        settings.setDefaultLocale(getDefaultLocale());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentSettingsDto)) {
            return false;
        }
        final AdyenPaymentSettingsDto that = (AdyenPaymentSettingsDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getSkinCode(), that.getSkinCode());
        builder.append(this.getSkinHmacKey(), that.getSkinHmacKey());
        builder.append(this.getHppPaymentUrl(), that.getHppPaymentUrl());
        builder.append(this.getHppPaymentMethodSelectionUrl(), that.getHppPaymentMethodSelectionUrl());
        builder.append(this.getDefaultCountry(), that.getDefaultCountry());
        builder.append(this.getMerchantAccount(), that.getMerchantAccount());
        builder.append(this.getDefaultLocale(), that.getDefaultLocale());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getSkinCode());
        builder.append(this.getSkinHmacKey());
        builder.append(this.getHppPaymentUrl());
        builder.append(this.getHppPaymentMethodSelectionUrl());
        builder.append(this.getDefaultCountry());
        builder.append(this.getMerchantAccount());
        builder.append(this.getDefaultLocale());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("skinCode", this.getSkinCode());
        builder.append("skinHmacKey", this.getSkinHmacKey());
        builder.append("hppPaymentUrl", this.getHppPaymentUrl());
        builder.append("hppPaymentMethodSelectionUrl", this.getHppPaymentMethodSelectionUrl());
        builder.append("defaultCountry", this.getDefaultCountry());
        builder.append("merchantAccount", this.getMerchantAccount());
        builder.append("defaultLocale", this.getDefaultLocale());
        return builder.build();
    }
}
