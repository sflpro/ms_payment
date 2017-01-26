package com.sfl.pms.core.api.internal.model.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractRequestModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import com.sfl.pms.core.api.internal.model.order.OrderRequestPaymentMethodModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/20/16
 * Time: 3:48 PM
 */
public class RePayOrderRequest extends AbstractRequestModel {
    private static final long serialVersionUID = -5457703892605553780L;

    /* Properties */
    @JsonProperty("orderUuId")
    private String orderUuId;

    @JsonProperty("paymentMethod")
    private OrderRequestPaymentMethodModel paymentMethod;

    @JsonProperty("storePaymentMethod")
    private Boolean storePaymentMethod;

    @JsonProperty("clientIpAddress")
    private String clientIpAddress;

    /* Constructors */
    public RePayOrderRequest() {
    }

    public RePayOrderRequest(final String orderUuId,
                             final OrderRequestPaymentMethodModel paymentMethod,
                             final Boolean storePaymentMethod,
                             final String clientIpAddress) {
        this.orderUuId = orderUuId;
        this.paymentMethod = paymentMethod;
        this.storePaymentMethod = storePaymentMethod;
        this.clientIpAddress = clientIpAddress;
    }

    /* Properties getters and setters */
    public String getOrderUuId() {
        return orderUuId;
    }

    public void setOrderUuId(final String orderUuId) {
        this.orderUuId = orderUuId;
    }

    public Boolean getStorePaymentMethod() {
        return storePaymentMethod;
    }

    public void setStorePaymentMethod(final Boolean storePaymentMethod) {
        this.storePaymentMethod = storePaymentMethod;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public OrderRequestPaymentMethodModel getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final OrderRequestPaymentMethodModel paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /* Validation methods */
    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (orderUuId == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_MISSING_UUID));
        }
        if (paymentMethod == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_PAYMENT_MISSING_METHOD));
        } else {
            errors.addAll(paymentMethod.validateRequiredFields());
        }
        if (storePaymentMethod == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_PAYMENT_MISSING_STORE_METHOD));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrderRequest)) {
            return false;
        }
        final CreateOrderRequest that = (CreateOrderRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getOrderUuId(), that.getOrder());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getStorePaymentMethod(), that.getStorePaymentMethod());
        builder.append(this.getPaymentMethod(), that.getPaymentMethod());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getOrderUuId());
        builder.append(this.getClientIpAddress());
        builder.append(this.getStorePaymentMethod());
        builder.append(this.getPaymentMethod());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("orderUuId", this.getOrderUuId());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("storePaymentMethod", this.getStorePaymentMethod());
        builder.append("paymentMethod", this.getPaymentMethod());
        return builder.build();
    }
}
