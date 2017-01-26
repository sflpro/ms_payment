package com.sfl.pms.core.api.internal.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractApiModel;
import com.sfl.pms.core.api.internal.model.common.request.ValidatableRequest;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 2:00 PM
 */
public class CustomerModel extends AbstractApiModel implements ValidatableRequest {

    private static final long serialVersionUID = 4904205883958210912L;

    /* Properties */
    @JsonProperty("uuId")
    private String uuId;

    @JsonProperty("email")
    private String email;

    /* Constructors */
    public CustomerModel() {
    }

    public CustomerModel(final String uuId, final String email) {
        this.uuId = uuId;
        this.email = email;
    }

    /* Properties getters and setters */
    public String getUuId() {
        return uuId;
    }

    public void setUuId(final String uuId) {
        this.uuId = uuId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    /* Validation methods */
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(getUuId())) {
            errors.add(new ErrorResponseModel(ErrorType.CUSTOMER_MISSING_UUID));
        }
        if (StringUtils.isBlank(getEmail())) {
            errors.add(new ErrorResponseModel(ErrorType.CUSTOMER_MISSING_EMAIL));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerModel)) {
            return false;
        }
        final CustomerModel that = (CustomerModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getEmail(), that.getEmail());
        builder.append(this.getUuId(), that.getUuId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getEmail());
        builder.append(this.getUuId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("email", this.getEmail());
        builder.append("uuId", this.getUuId());
        return builder.build();
    }
}
