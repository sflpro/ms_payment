package com.sfl.pms.core.api.internal.model.redirect.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractRequestModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 11:31 AM
 */
public class CreateAdyenRedirectResultRequest extends AbstractRequestModel {

    private static final long serialVersionUID = -8606347085565785403L;

    /* Properties */
    @JsonProperty("authResult")
    private String authResult;

    @JsonProperty("pspReference")
    private String pspReference;

    @JsonProperty("merchantReference")
    private String merchantReference;

    @JsonProperty("skinCode")
    private String skinCode;

    @JsonProperty("merchantSignature")
    private String merchantSignature;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("shopperLocale")
    private String shopperLocale;

    @JsonProperty("merchantReturnData")
    private String merchantReturnData;

    /* Constructors */
    public CreateAdyenRedirectResultRequest() {
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

    public String getMerchantSignature() {
        return merchantSignature;
    }

    public void setMerchantSignature(final String merchantSignature) {
        this.merchantSignature = merchantSignature;
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

    /* Validation methods */
    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final ArrayList<ErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(getAuthResult())) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_REDIRECT_RESULT_MISSING_AUTH_RESULT));
        }
        if (StringUtils.isBlank(getMerchantReference())) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_REDIRECT_RESULT_MISSING_MERCHANT_REFERENCE));
        }
        if (StringUtils.isBlank(getSkinCode())) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_REDIRECT_RESULT_MISSING_SKIN_CODE));
        }
        if (StringUtils.isBlank(getMerchantSignature())) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_REDIRECT_RESULT_MISSING_MERCHANT_SIGNATURE));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateAdyenRedirectResultRequest)) {
            return false;
        }
        final CreateAdyenRedirectResultRequest that = (CreateAdyenRedirectResultRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getAuthResult(), that.getAuthResult());
        builder.append(this.getPspReference(), that.getPspReference());
        builder.append(this.getMerchantReference(), that.getMerchantReference());
        builder.append(this.getMerchantReturnData(), that.getMerchantReturnData());
        builder.append(this.getMerchantSignature(), that.getMerchantSignature());
        builder.append(this.getPaymentMethod(), that.getPaymentMethod());
        builder.append(this.getShopperLocale(), that.getShopperLocale());
        builder.append(this.getSkinCode(), that.getSkinCode());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getAuthResult());
        builder.append(this.getPspReference());
        builder.append(this.getMerchantReference());
        builder.append(this.getMerchantReturnData());
        builder.append(this.getMerchantSignature());
        builder.append(this.getPaymentMethod());
        builder.append(this.getShopperLocale());
        builder.append(this.getSkinCode());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("authResult", this.getAuthResult());
        builder.append("pspReference", this.getPspReference());
        builder.append("merchantReference", this.getMerchantReference());
        builder.append("merchantReturnData", this.getMerchantReturnData());
        builder.append("merchantSignature", this.getMerchantSignature());
        builder.append("paymentMethod", this.getPaymentMethod());
        builder.append("shopperLocale", this.getShopperLocale());
        builder.append("skinCode", this.getSkinCode());
        return builder.build();
    }
}
