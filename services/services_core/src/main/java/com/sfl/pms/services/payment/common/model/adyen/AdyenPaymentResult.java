package com.sfl.pms.services.payment.common.model.adyen;

import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
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
 * Date: 1/13/15
 * Time: 6:03 PM
 */
@Entity
@DiscriminatorValue(value = "ADYEN")
@Table(name = "payment_adyen_result")
public class AdyenPaymentResult extends PaymentResult {
    private static final long serialVersionUID = -7724819558082229132L;

    /* Properties */
    @Column(name = "auth_code", nullable = true)
    private String authCode;

    @Column(name = "psp_reference", nullable = true)
    private String pspReference;

    @Column(name = "result_code", nullable = false)
    private String resultCode;

    @Column(name = "refusal_reason", nullable = true)
    private String refusalReason;

    /* Constructors */
    public AdyenPaymentResult() {
        setType(PaymentProviderType.ADYEN);
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

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenPaymentResult)) {
            return false;
        }
        final AdyenPaymentResult that = (AdyenPaymentResult) o;
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
