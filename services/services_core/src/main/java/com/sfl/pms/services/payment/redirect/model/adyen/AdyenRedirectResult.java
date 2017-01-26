package com.sfl.pms.services.payment.redirect.model.adyen;

import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 11:29 AM
 */
@Entity
@DiscriminatorValue(value = "ADYEN")
@Table(name = "payment_redirect_result_adyen")
public class AdyenRedirectResult extends PaymentProviderRedirectResult {

    private static final long serialVersionUID = 5392873399880573798L;

    /* Properties */
    @Column(name = "auth_result", nullable = false)
    private String authResult;

    @Column(name = "psp_reference", nullable = true)
    private String pspReference;

    @Column(name = "merchant_reference", nullable = false)
    private String merchantReference;

    @Column(name = "skin_code", nullable = false)
    private String skinCode;

    @Column(name = "merchant_sig", nullable = false)
    private String merchantSig;

    @Column(name = "payment_method", nullable = true)
    private String paymentMethod;

    @Column(name = "shopper_locale", nullable = true)
    private String shopperLocale;

    @Column(name = "merchant_return_data", nullable = true)
    private String merchantReturnData;

    /* Constructors */
    public AdyenRedirectResult() {
        setType(PaymentProviderType.ADYEN);
    }

    public AdyenRedirectResult(final boolean generateUuId) {
        super(generateUuId);
        setType(PaymentProviderType.ADYEN);
    }

    /* Properties getters and setters */
    public String getAuthResult() {
        return authResult;
    }

    public void setAuthResult(final String authResult) {
        this.authResult = authResult;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(final String pspReference) {
        this.pspReference = pspReference;
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

    public String getMerchantSig() {
        return merchantSig;
    }

    public void setMerchantSig(final String merchantSig) {
        this.merchantSig = merchantSig;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShopperLocale() {
        return shopperLocale;
    }

    public void setShopperLocale(final String shopperLocale) {
        this.shopperLocale = shopperLocale;
    }

    public String getMerchantReturnData() {
        return merchantReturnData;
    }

    public void setMerchantReturnData(final String merchantReturnData) {
        this.merchantReturnData = merchantReturnData;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenRedirectResult)) {
            return false;
        }
        final AdyenRedirectResult that = (AdyenRedirectResult) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getAuthResult(), that.getAuthResult());
        builder.append(this.getPspReference(), that.getPspReference());
        builder.append(this.getMerchantReference(), that.getMerchantReference());
        builder.append(this.getSkinCode(), that.getSkinCode());
        builder.append(this.getMerchantSig(), that.getMerchantSig());
        builder.append(this.getPaymentMethod(), that.getPaymentMethod());
        builder.append(this.getShopperLocale(), that.getShopperLocale());
        builder.append(this.getMerchantReturnData(), that.getMerchantReturnData());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getAuthResult());
        builder.append(this.getPspReference());
        builder.append(this.getMerchantReference());
        builder.append(this.getSkinCode());
        builder.append(this.getMerchantSig());
        builder.append(this.getPaymentMethod());
        builder.append(this.getShopperLocale());
        builder.append(this.getMerchantReturnData());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("authResult", this.getAuthResult());
        builder.append("pspReference", this.getPspReference());
        builder.append("merchantReference", this.getMerchantReference());
        builder.append("skinCode", this.getSkinCode());
        builder.append("merchantSig", this.getMerchantSig());
        builder.append("paymentMethod", this.getPaymentMethod());
        builder.append("shopperLocale", this.getShopperLocale());
        builder.append("merchantReturnData", this.getMerchantReturnData());
        return builder.build();
    }
}
