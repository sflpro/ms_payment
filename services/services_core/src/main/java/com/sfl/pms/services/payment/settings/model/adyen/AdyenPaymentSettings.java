package com.sfl.pms.services.payment.settings.model.adyen;

import com.sfl.pms.services.country.model.CountryCode;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/23/14
 * Time: 9:45 AM
 */
@Entity
@DiscriminatorValue(value = "ADYEN")
@Table(name = "payment_provider_settings_adyen")
public class AdyenPaymentSettings extends PaymentProviderSettings {

    private static final long serialVersionUID = 284888973600952006L;

    /* Properties */
    @Column(name = "skin_code", nullable = true, unique = false)
    private String skinCode;

    @Column(name = "skin_hmac_key", nullable = true, unique = false)
    private String skinHmacKey;

    @Column(name = "hpp_payment_url", nullable = true, unique = false)
    private String hppPaymentUrl;


    @Column(name = "hpp_payment_method_selection_url", nullable = true, unique = false)
    private String hppPaymentMethodSelectionUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_country", nullable = true)
    private CountryCode defaultCountry;

    @Column(name = "merchant_account", nullable = true, unique = false)
    private String merchantAccount;

    @Column(name = "default_locale", nullable = true)
    private String defaultLocale;

    /* Constructors */
    public AdyenPaymentSettings() {
        setType(PaymentProviderType.ADYEN);
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

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentSettings)) {
            return false;
        }
        final AdyenPaymentSettings that = (AdyenPaymentSettings) o;
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
