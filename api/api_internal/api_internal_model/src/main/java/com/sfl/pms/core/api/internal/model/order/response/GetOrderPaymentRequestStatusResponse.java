package com.sfl.pms.core.api.internal.model.order.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractResponseModel;
import com.sfl.pms.core.api.internal.model.order.OrderPaymentRequestStateClientType;
import com.sfl.pms.core.api.internal.model.order.OrderStateClientType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/20/16
 * Time: 11:18 AM
 */
public class GetOrderPaymentRequestStatusResponse extends AbstractResponseModel {

    private static final long serialVersionUID = 8757700804997785324L;

    /* Properties */
    @JsonProperty("orderPaymentRequestUuId")
    private String orderPaymentRequestUuId;

    @JsonProperty("orderUuId")
    private String orderUuId;

    @JsonProperty("paymentUuId")
    private String paymentUuId;

    @JsonProperty("paymentRedirectUrl")
    private String paymentRedirectUrl;

    @JsonProperty("requestState")
    private OrderPaymentRequestStateClientType requestState;

    @JsonProperty("orderState")
    private OrderStateClientType orderState;

    /* Constructors */
    public GetOrderPaymentRequestStatusResponse() {
    }

    /* Properties getters and setters */
    public String getOrderUuId() {
        return orderUuId;
    }

    public void setOrderUuId(final String orderUuId) {
        this.orderUuId = orderUuId;
    }

    public String getOrderPaymentRequestUuId() {
        return orderPaymentRequestUuId;
    }

    public void setOrderPaymentRequestUuId(final String orderPaymentRequestUuId) {
        this.orderPaymentRequestUuId = orderPaymentRequestUuId;
    }

    public String getPaymentRedirectUrl() {
        return paymentRedirectUrl;
    }

    public void setPaymentRedirectUrl(final String paymentRedirectUrl) {
        this.paymentRedirectUrl = paymentRedirectUrl;
    }

    public OrderPaymentRequestStateClientType getRequestState() {
        return requestState;
    }

    public void setRequestState(final OrderPaymentRequestStateClientType requestState) {
        this.requestState = requestState;
    }

    public OrderStateClientType getOrderState() {
        return orderState;
    }

    public void setOrderState(final OrderStateClientType orderState) {
        this.orderState = orderState;
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
        if (!(o instanceof GetOrderPaymentRequestStatusResponse)) {
            return false;
        }
        final GetOrderPaymentRequestStatusResponse that = (GetOrderPaymentRequestStatusResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getOrderPaymentRequestUuId(), that.getOrderPaymentRequestUuId());
        builder.append(this.getOrderUuId(), that.getOrderUuId());
        builder.append(this.getPaymentUuId(), that.getPaymentUuId());
        builder.append(this.getPaymentRedirectUrl(), that.getPaymentRedirectUrl());
        builder.append(this.getRequestState(), that.getRequestState());
        builder.append(this.getOrderState(), that.getOrderState());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getOrderPaymentRequestUuId());
        builder.append(this.getOrderUuId());
        builder.append(this.getPaymentUuId());
        builder.append(this.getPaymentRedirectUrl());
        builder.append(this.getRequestState());
        builder.append(this.getOrderState());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("orderPaymentRequestUuId", this.getOrderPaymentRequestUuId());
        builder.append("orderUuId", this.getOrderUuId());
        builder.append("paymentUuId", this.getPaymentUuId());
        builder.append("paymentRedirectUrl", this.getPaymentRedirectUrl());
        builder.append("requestState", this.getRequestState());
        builder.append("orderState", this.getOrderState());
        return builder.build();
    }
}
