package com.sfl.pms.core.api.internal.model.order.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 2:12 PM
 */
public class CreateOrderPaymentResponse extends AbstractResponseModel {

    private static final long serialVersionUID = -5964988045208009613L;

    /* Properties */
    @JsonProperty("orderPaymentRequestUuId")
    private String orderPaymentRequestUuId;

    /* Constructors */
    public CreateOrderPaymentResponse() {
    }

    public CreateOrderPaymentResponse(final String orderPaymentRequestUuId) {
        this.orderPaymentRequestUuId = orderPaymentRequestUuId;
    }

    /* Properties getters and setters */
    public String getOrderPaymentRequestUuId() {
        return orderPaymentRequestUuId;
    }

    public void setOrderPaymentRequestUuId(final String orderPaymentRequestUuId) {
        this.orderPaymentRequestUuId = orderPaymentRequestUuId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrderPaymentResponse)) {
            return false;
        }
        final CreateOrderPaymentResponse that = (CreateOrderPaymentResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getOrderPaymentRequestUuId(), that.getOrderPaymentRequestUuId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getOrderPaymentRequestUuId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("orderPaymentRequestUuId", this.getOrderPaymentRequestUuId());
        return builder.build();
    }
}
