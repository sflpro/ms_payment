package com.sfl.pms.core.api.internal.model.redirect.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 11:45 AM
 */
public class CreateAdyenRedirectResultResponse extends AbstractResponseModel {

    private static final long serialVersionUID = 1051828365933821110L;

    /* Properties */
    @JsonProperty("paymentProviderRedirectResultUuId")
    private String paymentProviderRedirectResultUuId;

    /* Constructors */
    public CreateAdyenRedirectResultResponse() {
    }

    public CreateAdyenRedirectResultResponse(final String paymentProviderRedirectResultUuId) {
        this.paymentProviderRedirectResultUuId = paymentProviderRedirectResultUuId;
    }

    /* Properties getters and setters */
    public String getPaymentProviderRedirectResultUuId() {
        return paymentProviderRedirectResultUuId;
    }

    public void setPaymentProviderRedirectResultUuId(final String paymentProviderRedirectResultUuId) {
        this.paymentProviderRedirectResultUuId = paymentProviderRedirectResultUuId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateAdyenRedirectResultResponse)) {
            return false;
        }
        final CreateAdyenRedirectResultResponse that = (CreateAdyenRedirectResultResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentProviderRedirectResultUuId(), that.getPaymentProviderRedirectResultUuId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentProviderRedirectResultUuId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentProviderRedirectResultUuId", this.getPaymentProviderRedirectResultUuId());
        return builder.build();
    }
}
