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
 * Date: 1/22/16
 * Time: 11:31 AM
 */
public class GetPaymentProviderRedirectResultStatusRequest extends AbstractRequestModel {

    private static final long serialVersionUID = -5489731657707817120L;

    /* Properties */
    @JsonProperty("redirectResultUuId")
    private String redirectResultUuId;

    /* Constructors */
    public GetPaymentProviderRedirectResultStatusRequest() {
    }

    /* Properties getters and setters */
    public String getRedirectResultUuId() {
        return redirectResultUuId;
    }

    public void setRedirectResultUuId(final String redirectResultUuId) {
        this.redirectResultUuId = redirectResultUuId;
    }

    /* Validation methods */
    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(getRedirectResultUuId())) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_REDIRECT_RESULT_MISSING_UUID));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetPaymentProviderRedirectResultStatusRequest)) {
            return false;
        }
        final GetPaymentProviderRedirectResultStatusRequest that = (GetPaymentProviderRedirectResultStatusRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRedirectResultUuId(), that.getRedirectResultUuId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRedirectResultUuId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("redirectResultUuId", this.getRedirectResultUuId());
        return builder.build();
    }
}
