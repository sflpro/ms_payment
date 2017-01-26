package com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 12:50 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdyenNotificationAdditionalDataJsonModel implements Serializable {

    private static final long serialVersionUID = -879181590304804626L;

    /* Properties */
    @JsonProperty("expiryDate")
    private String expiryDate;

    @JsonProperty("issuerCountry")
    private String issuerCountry;

    @JsonProperty("authCode")
    private String authCode;

    @JsonProperty("cardSummary")
    private String cardSummary;

    @JsonProperty("cardHolderName")
    private String cardHolderName;

    @JsonProperty("shopperInteraction")
    private String shopperInteraction;

    @JsonProperty("shopperEmail")
    private String shopperEmail;

    @JsonProperty("hmacSignature")
    private String hmacSignature;

    @JsonProperty("shopperReference")
    private String shopperReference;

    /* Constructors */
    public AdyenNotificationAdditionalDataJsonModel() {
    }

    /* Properties getters and setters */
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIssuerCountry() {
        return issuerCountry;
    }

    public void setIssuerCountry(final String issuerCountry) {
        this.issuerCountry = issuerCountry;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(final String authCode) {
        this.authCode = authCode;
    }

    public String getCardSummary() {
        return cardSummary;
    }

    public void setCardSummary(final String cardSummary) {
        this.cardSummary = cardSummary;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(final String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getShopperInteraction() {
        return shopperInteraction;
    }

    public void setShopperInteraction(final String shopperInteraction) {
        this.shopperInteraction = shopperInteraction;
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(final String shopperEmail) {
        this.shopperEmail = shopperEmail;
    }

    public String getHmacSignature() {
        return hmacSignature;
    }

    public void setHmacSignature(final String hmacSignature) {
        this.hmacSignature = hmacSignature;
    }

    public String getShopperReference() {
        return shopperReference;
    }

    public void setShopperReference(final String shopperReference) {
        this.shopperReference = shopperReference;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenNotificationAdditionalDataJsonModel)) {
            return false;
        }
        final AdyenNotificationAdditionalDataJsonModel that = (AdyenNotificationAdditionalDataJsonModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getExpiryDate(), that.getExpiryDate());
        builder.append(this.getIssuerCountry(), that.getIssuerCountry());
        builder.append(this.getAuthCode(), that.getAuthCode());
        builder.append(this.getCardSummary(), that.getCardSummary());
        builder.append(this.getCardHolderName(), that.getCardHolderName());
        builder.append(this.getShopperInteraction(), that.getShopperInteraction());
        builder.append(this.getShopperEmail(), that.getShopperEmail());
        builder.append(this.getHmacSignature(), that.getHmacSignature());
        builder.append(this.getShopperReference(), that.getShopperReference());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getExpiryDate());
        builder.append(this.getIssuerCountry());
        builder.append(this.getAuthCode());
        builder.append(this.getCardSummary());
        builder.append(this.getCardHolderName());
        builder.append(this.getShopperInteraction());
        builder.append(this.getShopperEmail());
        builder.append(this.getHmacSignature());
        builder.append(this.getShopperReference());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("expiryDate", this.getExpiryDate());
        builder.append("issuerCountry", this.getIssuerCountry());
        builder.append("authCode", this.getAuthCode());
        builder.append("cardSummary", this.getCardSummary());
        builder.append("cardHolderName", this.getCardHolderName());
        builder.append("shopperInteraction", this.getShopperInteraction());
        builder.append("shopperEmail", this.getShopperEmail());
        builder.append("hmacSignature", this.getHmacSignature());
        builder.append("shopperReference", this.getShopperReference());
        return builder.build();
    }
}
