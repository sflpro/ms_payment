package com.sfl.pms.core.api.internal.model.redirect.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractResponseModel;
import com.sfl.pms.core.api.internal.model.redirect.PaymentProviderRedirectResultStateClientType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/22/16
 * Time: 1:18 PM
 */
public class GetPaymentProviderRedirectResultStatusResponse extends AbstractResponseModel {

    private static final long serialVersionUID = -8717101063626049773L;

    /* Properties */
    @JsonProperty("redirectResultUuId")
    private String redirectResultUuId;

    @JsonProperty("state")
    private PaymentProviderRedirectResultStateClientType state;

    @JsonProperty("paymentUuId")
    private String paymentUuId;

    /* Constructors */
    public GetPaymentProviderRedirectResultStatusResponse() {
    }

    /* Properties getters and setters */
    public String getRedirectResultUuId() {
        return redirectResultUuId;
    }

    public void setRedirectResultUuId(final String redirectResultUuId) {
        this.redirectResultUuId = redirectResultUuId;
    }

    public PaymentProviderRedirectResultStateClientType getState() {
        return state;
    }

    public void setState(final PaymentProviderRedirectResultStateClientType state) {
        this.state = state;
    }

    public String getPaymentUuId() {
        return paymentUuId;
    }

    public void setPaymentUuId(final String paymentUuId) {
        this.paymentUuId = paymentUuId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetPaymentProviderRedirectResultStatusResponse)) {
            return false;
        }
        final GetPaymentProviderRedirectResultStatusResponse that = (GetPaymentProviderRedirectResultStatusResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRedirectResultUuId(), that.getRedirectResultUuId());
        builder.append(this.getState(), that.getState());
        builder.append(this.getPaymentUuId(), that.getPaymentUuId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRedirectResultUuId());
        builder.append(this.getState());
        builder.append(this.getPaymentUuId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("redirectResultUuId", this.getRedirectResultUuId());
        builder.append("state", this.getState());
        builder.append("paymentUuId", this.getPaymentUuId());
        return builder.build();
    }
}
