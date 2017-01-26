package com.sfl.pms.core.api.internal.model.order;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sfl.pms.core.api.internal.model.common.AbstractApiModel;
import com.sfl.pms.core.api.internal.model.common.request.ValidatableRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 2:15 PM
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderRequestRedirectPaymentMethodModel.class, name = "REDIRECT_PAYMENT_METHOD")
})
public abstract class OrderRequestPaymentMethodModel extends AbstractApiModel implements ValidatableRequest {

    private static final long serialVersionUID = 5455781958616770L;

    /* Properties */
    private final OrderRequestPaymentMethodClientType type;

    /* Constructors */
    public OrderRequestPaymentMethodModel(final OrderRequestPaymentMethodClientType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public OrderRequestPaymentMethodClientType getType() {
        return type;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestPaymentMethodModel)) {
            return false;
        }
        final OrderRequestPaymentMethodModel that = (OrderRequestPaymentMethodModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getType(), that.getType());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getType());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", getType());
        return builder.build();
    }
}
