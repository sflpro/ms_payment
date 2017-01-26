package com.sfl.pms.services.payment.provider.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/13/15
 * Time: 11:38 AM
 */
public class AdyenHmacCalculationDto implements Serializable {

    private static final long serialVersionUID = 7017582619598850645L;

    /* Properties */
    private Long paymentAmount;

    private String currencyCode;

    private String merchantReference;

    private String skinCode;

    private String merchantAccount;

    private String shopperEmail;

    private String shopperReference;

    private String recurringContract;

    private String shipBeforeDate;

    private String sessionValidity;

    private String merchantReturnData;

    /* Constructors */
    public AdyenHmacCalculationDto() {
    }

    /* Properties getters and setters */
    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(final Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(final String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public String getSkinCode() {
        return skinCode;
    }

    public void setSkinCode(final String skinCode) {
        this.skinCode = skinCode;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(final String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(final String shopperEmail) {
        this.shopperEmail = shopperEmail;
    }

    public String getShopperReference() {
        return shopperReference;
    }

    public void setShopperReference(final String shopperReference) {
        this.shopperReference = shopperReference;
    }

    public String getRecurringContract() {
        return recurringContract;
    }

    public void setRecurringContract(final String recurringContract) {
        this.recurringContract = recurringContract;
    }

    public String getShipBeforeDate() {
        return shipBeforeDate;
    }

    public void setShipBeforeDate(final String shipBeforeDate) {
        this.shipBeforeDate = shipBeforeDate;
    }

    public String getSessionValidity() {
        return sessionValidity;
    }

    public void setSessionValidity(final String sessionValidity) {
        this.sessionValidity = sessionValidity;
    }

    public String getMerchantReturnData() {
        return merchantReturnData;
    }

    public void setMerchantReturnData(final String merchantReturnData) {
        this.merchantReturnData = merchantReturnData;
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
        builder.append(this.getCurrencyCode(), that.getCurrencyCode());
        builder.append(this.getPaymentAmount(), that.getPaymentAmount());
        builder.append(this.getMerchantReference(), that.getMerchantReference());
        builder.append(this.getSkinCode(), that.getSkinCode());
        builder.append(this.getMerchantAccount(), that.getMerchantAccount());
        builder.append(this.getShopperEmail(), that.getShopperEmail());
        builder.append(this.getShopperReference(), that.getShopperReference());
        builder.append(this.getRecurringContract(), that.getRecurringContract());
        builder.append(this.getShipBeforeDate(), that.getShipBeforeDate());
        builder.append(this.getSessionValidity(), that.getSessionValidity());
        builder.append(this.getMerchantReturnData(), that.getMerchantReturnData());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getCurrencyCode());
        builder.append(this.getPaymentAmount());
        builder.append(this.getMerchantReference());
        builder.append(this.getSkinCode());
        builder.append(this.getMerchantAccount());
        builder.append(this.getShopperEmail());
        builder.append(this.getShopperReference());
        builder.append(this.getRecurringContract());
        builder.append(this.getShipBeforeDate());
        builder.append(this.getSessionValidity());
        builder.append(this.getMerchantReturnData());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("currencyCode", this.getCurrencyCode());
        builder.append("paymentAmount", this.getPaymentAmount());
        builder.append("merchantReference", this.getMerchantReference());
        builder.append("skinCode", this.getSkinCode());
        builder.append("merchantAccount", this.getMerchantAccount());
        builder.append("shopperEmail", this.getShopperEmail());
        builder.append("shopperReference", this.getShopperReference());
        builder.append("recurringContract", this.getRecurringContract());
        builder.append("shipBeforeDate", this.getShipBeforeDate());
        builder.append("sessionValidity", this.getSessionValidity());
        builder.append("merchantReturnData", this.getMerchantReturnData());
        return builder.build();
    }
}
