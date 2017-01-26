package com.sfl.pms.externalclients.payment.adyen.model.payment;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.datatypes.AdyenPaymentStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 7:02 PM
 */
public class AdyenPaymentResultModel extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 2054571052807228336L;

    /* Properties */
    private String authCode;

    private String pspReference;

    private AdyenPaymentStatus resultCode;

    private String refusalReason;

    /* Constructors */
    public AdyenPaymentResultModel(final String authCode, final String pspReference, final AdyenPaymentStatus resultCode, final String refusalReason) {
        this.authCode = authCode;
        this.pspReference = pspReference;
        this.resultCode = resultCode;
        this.refusalReason = refusalReason;
    }

    public static AdyenPaymentResultModel buildFromResponseBody(final MultiValueMap<String, String> responseBody) {
        Assert.notNull(responseBody);
        Assert.isTrue(responseBody.size() > 0);
        final Map<String, String> singleValues = responseBody.toSingleValueMap();
        final String authCode = singleValues.get(PaymentAttributeMapping.RESULT_AUTH_CODE);
        final String resultCode = singleValues.get(PaymentAttributeMapping.RESULT_CODE);
        final AdyenPaymentStatus adyenPaymentStatus = AdyenPaymentStatus.fromString(resultCode);
        final String pspReference = singleValues.get(PaymentAttributeMapping.RESULT_PSP_REFERENCE);
        final String refusalReason = singleValues.get(PaymentAttributeMapping.RESULT_REFUSAL_REASON);
        /* Create instance of payment result */
        final AdyenPaymentResultModel instance = new AdyenPaymentResultModel(authCode, pspReference, adyenPaymentStatus, refusalReason);
        return instance;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(String pspReference) {
        this.pspReference = pspReference;
    }

    public AdyenPaymentStatus getResultCode() {
        return resultCode;
    }

    public void setResultCode(AdyenPaymentStatus resultCode) {
        this.resultCode = resultCode;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentResultModel)) {
            return false;
        }
        AdyenPaymentResultModel that = (AdyenPaymentResultModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(authCode, that.getAuthCode());
        builder.append(pspReference, that.getPspReference());
        builder.append(resultCode, that.getResultCode());
        builder.append(refusalReason, that.getRefusalReason());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(authCode);
        builder.append(pspReference);
        builder.append(resultCode);
        builder.append(refusalReason);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("authCode", authCode);
        builder.append("pspReference", pspReference);
        builder.append("resultCode", resultCode);
        builder.append("refusalReason", refusalReason);
        return builder.build();
    }
}
