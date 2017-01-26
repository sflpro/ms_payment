package com.sfl.pms.core.api.internal.model.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractRequestModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import com.sfl.pms.core.api.internal.model.customer.CustomerModel;
import com.sfl.pms.core.api.internal.model.order.OrderModel;
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
 * Date: 1/19/16
 * Time: 2:08 PM
 */
public class CreateOrderRequest extends AbstractRequestModel {

    private static final long serialVersionUID = -5457703892605553780L;

    /* Properties */
    @JsonProperty("customer")
    private CustomerModel customer;

    @JsonProperty("order")
    private OrderModel order;

    @JsonProperty("paymentMethod")
    private OrderRequestPaymentMethodModel paymentMethod;

    @JsonProperty("storePaymentMethod")
    private Boolean storePaymentMethod;

    @JsonProperty("clientIpAddress")
    private String clientIpAddress;

    /* Constructors */
    public CreateOrderRequest() {
    }

    public CreateOrderRequest(final CustomerModel customer,
                              final OrderModel order,
                              final OrderRequestPaymentMethodModel paymentMethod,
                              final Boolean storePaymentMethod,
                              final String clientIpAddress) {
        this.customer = customer;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.storePaymentMethod = storePaymentMethod;
        this.clientIpAddress = clientIpAddress;
    }

    /* Properties getters and setters */
    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(final CustomerModel customer) {
        this.customer = customer;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(final OrderModel order) {
        this.order = order;
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
        if (customer == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_PAYMENT_MISSING_CUSTOMER));
        } else {
            errors.addAll(customer.validateRequiredFields());
        }
        if (order == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_PAYMENT_MISSING_ORDER));
        } else {
            errors.addAll(order.validateRequiredFields());
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
        builder.append(this.getCustomer(), that.getCustomer());
        builder.append(this.getOrder(), that.getOrder());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getStorePaymentMethod(), that.getStorePaymentMethod());
        builder.append(this.getPaymentMethod(), that.getPaymentMethod());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getCustomer());
        builder.append(this.getOrder());
        builder.append(this.getClientIpAddress());
        builder.append(this.getStorePaymentMethod());
        builder.append(this.getPaymentMethod());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("customer", this.getCustomer());
        builder.append("order", this.getOrder());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("storePaymentMethod", this.getStorePaymentMethod());
        builder.append("paymentMethod", this.getPaymentMethod());
        return builder.build();
    }
}
