package com.sfl.pms.core.api.internal.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import com.sfl.pms.core.api.internal.model.method.PaymentMethodClientType;
import com.sfl.pms.core.api.internal.model.provider.PaymentProviderClientType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 2:42 PM
 */
public class OrderRequestRedirectPaymentMethodModel extends OrderRequestPaymentMethodModel {

    private static final long serialVersionUID = 8789707953448015078L;

    /* Properties */
    @JsonProperty("paymentMethodType")
    private PaymentMethodClientType paymentMethodType;

    @JsonProperty("paymentProviderType")
    private PaymentProviderClientType paymentProviderType;

    public OrderRequestRedirectPaymentMethodModel() {
        super(OrderRequestPaymentMethodClientType.REDIRECT_PAYMENT_METHOD);
    }

    public OrderRequestRedirectPaymentMethodModel(final OrderRequestPaymentMethodClientType type,
                                                  final PaymentMethodClientType paymentMethodType, final PaymentProviderClientType paymentProviderType) {
        super(type);
        this.paymentMethodType = paymentMethodType;
        this.paymentProviderType = paymentProviderType;
    }

    /* Properties getters and setters */
    public PaymentMethodClientType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodClientType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public PaymentProviderClientType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderClientType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    /* Validation methods */
    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (getPaymentProviderType() == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_PAYMENT_METHOD_MISSING_PROVIDER_TYPE));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestRedirectPaymentMethodModel)) {
            return false;
        }
        final OrderRequestRedirectPaymentMethodModel that = (OrderRequestRedirectPaymentMethodModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getPaymentMethodType(), that.getPaymentMethodType());
        builder.append(getPaymentProviderType(), that.getPaymentProviderType());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getPaymentMethodType());
        builder.append(getPaymentProviderType());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodType", getPaymentMethodType());
        builder.append("paymentProviderType", getPaymentProviderType());
        return builder.build();
    }
}
