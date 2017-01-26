package com.sfl.pms.services.payment.common.dto.adyen;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 6:23 PM
 */
public class AdyenPaymentResultDto extends PaymentResultDto<AdyenPaymentResult> {
    private static final long serialVersionUID = 407193676876654596L;

    /* Properties */
    private String authCode;

    private String pspReference;

    private String resultCode;

    private String refusalReason;

    /* Constructors */
    public AdyenPaymentResultDto(final PaymentResultStatus status, final String authCode, final String pspReference, final String resultCode, final String refusalReason) {
        super(PaymentProviderType.ADYEN, status);
        this.authCode = authCode;
        this.pspReference = pspReference;
        this.resultCode = resultCode;
        this.refusalReason = refusalReason;
    }

    public AdyenPaymentResultDto() {
        super(PaymentProviderType.ADYEN);
    }

    /* Properties getters and setters */
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(final String resultCode) {
        this.resultCode = resultCode;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(final String refusalReason) {
        this.refusalReason = refusalReason;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final AdyenPaymentResult paymentResult) {
        super.updateDomainEntityProperties(paymentResult);
        paymentResult.setAuthCode(getAuthCode());
        paymentResult.setPspReference(getPspReference());
        paymentResult.setResultCode(getResultCode());
        paymentResult.setRefusalReason(getRefusalReason());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentResultDto)) {
            return false;
        }
        final AdyenPaymentResultDto that = (AdyenPaymentResultDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getAuthCode(), that.getAuthCode());
        builder.append(this.getPspReference(), that.getPspReference());
        builder.append(this.getResultCode(), that.getResultCode());
        builder.append(this.getRefusalReason(), that.getRefusalReason());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getAuthCode());
        builder.append(this.getPspReference());
        builder.append(this.getResultCode());
        builder.append(this.getRefusalReason());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("authCode", this.getAuthCode());
        builder.append("pspReference", this.getPspReference());
        builder.append("resultCode", this.getResultCode());
        builder.append("refusalReason", this.getRefusalReason());
        return builder.build();
    }
}
