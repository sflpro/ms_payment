package com.sfl.pms.core.api.internal.model.order.request;

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
 * Date: 1/20/16
 * Time: 10:53 AM
 */
public class GetOrderPaymentRequestStatusRequest extends AbstractRequestModel {

    private static final long serialVersionUID = 3554837621571954373L;

    /* Dependencies */
    @JsonProperty("orderPaymentRequestUuid")
    private String orderPaymentRequestUuid;

    /* Constructors */
    public GetOrderPaymentRequestStatusRequest() {
    }

    public GetOrderPaymentRequestStatusRequest(final String orderPaymentRequestUuid) {
        this.orderPaymentRequestUuid = orderPaymentRequestUuid;
    }

    /* Properties getters and setters */
    public String getOrderPaymentRequestUuid() {
        return orderPaymentRequestUuid;
    }

    public void setOrderPaymentRequestUuid(final String orderPaymentRequestUuid) {
        this.orderPaymentRequestUuid = orderPaymentRequestUuid;
    }

    /* Validation methods */
    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(getOrderPaymentRequestUuid())) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_PAYMENT_REQUEST_MISSING_UUID));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrderPaymentRequestStatusRequest)) {
            return false;
        }
        final GetOrderPaymentRequestStatusRequest that = (GetOrderPaymentRequestStatusRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getOrderPaymentRequestUuid(), that.getOrderPaymentRequestUuid());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getOrderPaymentRequestUuid());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("orderPaymentRequestUuid", this.getOrderPaymentRequestUuid());
        return builder.build();
    }
}
