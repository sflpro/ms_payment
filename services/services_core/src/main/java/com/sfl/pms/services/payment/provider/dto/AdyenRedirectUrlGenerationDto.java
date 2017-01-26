package com.sfl.pms.services.payment.provider.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/1/15
 * Time: 1:15 PM
 */
public class AdyenRedirectUrlGenerationDto extends AdyenHmacCalculationDto implements Serializable {

    private static final long serialVersionUID = 1153061575666752775L;

    /* Properties */
    private String countryCode;

    private String shopperLocale;

    private String signatureHmacKey;

    private String adyenRedirectBaseUrl;

    private String brandCode;

    /* Constructors */
    public AdyenRedirectUrlGenerationDto() {
    }

    /* Properties getters and setters */
    public String getSignatureHmacKey() {
        return signatureHmacKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setSignatureHmacKey(final String signatureHmacKey) {
        this.signatureHmacKey = signatureHmacKey;
    }

    public String getAdyenRedirectBaseUrl() {
        return adyenRedirectBaseUrl;
    }

    public void setAdyenRedirectBaseUrl(final String adyenRedirectBaseUrl) {
        this.adyenRedirectBaseUrl = adyenRedirectBaseUrl;
    }

    public String getShopperLocale() {
        return shopperLocale;
    }

    public void setShopperLocale(final String shopperLocale) {
        this.shopperLocale = shopperLocale;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(final String brandCode) {
        this.brandCode = brandCode;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenRedirectUrlGenerationDto)) {
            return false;
        }
        final AdyenRedirectUrlGenerationDto that = (AdyenRedirectUrlGenerationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getCountryCode(), that.getCountryCode());
        builder.append(this.getSignatureHmacKey(), that.getSignatureHmacKey());
        builder.append(this.getAdyenRedirectBaseUrl(), that.getAdyenRedirectBaseUrl());
        builder.append(this.getShopperLocale(), that.getShopperLocale());
        builder.append(this.getBrandCode(), that.getBrandCode());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getCountryCode());
        builder.append(this.getSignatureHmacKey());
        builder.append(this.getAdyenRedirectBaseUrl());
        builder.append(this.getShopperLocale());
        builder.append(this.getBrandCode());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("countryCode", this.getCountryCode());
        builder.append("adyenRedirectBaseUrl", this.getAdyenRedirectBaseUrl());
        builder.append("shopperLocale", this.getShopperLocale());
        builder.append("brandCode", this.getBrandCode());
        return builder.build();
    }
}
