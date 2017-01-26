package com.sfl.pms.services.payment.common.model.order.request;

import com.sfl.pms.services.common.model.AbstractDomainUuIdAwareEntityModel;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 12:59 PM
 */
@Entity
@Table(name = "payment_order_request")
public class OrderPaymentRequest extends AbstractDomainUuIdAwareEntityModel {
    private static final long serialVersionUID = 5300076633964668548L;

    /* Properties */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = false)
    private Order order;

    @OneToOne(mappedBy = "orderPaymentRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private OrderRequestPaymentMethod paymentMethod;

    @Column(name = "client_ip_address", nullable = true)
    private String clientIpAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private OrderPaymentRequestState state;

    @Column(name = "store_payment_method", nullable = false)
    private boolean storePaymentMethod;

    @Column(name = "payment_redirect_url", nullable = true, length = 800)
    private String paymentRedirectUrl;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_payment_id", nullable = true, unique = false)
    private OrderPayment orderPayment;

    /* Constructors */
    public OrderPaymentRequest() {
        initializeDefaults();
    }

    public OrderPaymentRequest(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public OrderRequestPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final OrderRequestPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public OrderPaymentRequestState getState() {
        return state;
    }

    public void setState(final OrderPaymentRequestState state) {
        this.state = state;
    }

    public boolean isStorePaymentMethod() {
        return storePaymentMethod;
    }

    public void setStorePaymentMethod(final boolean storePaymentMethod) {
        this.storePaymentMethod = storePaymentMethod;
    }

    public String getPaymentRedirectUrl() {
        return paymentRedirectUrl;
    }

    public void setPaymentRedirectUrl(final String paymentRedirectUrl) {
        this.paymentRedirectUrl = paymentRedirectUrl;
    }

    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(final OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.state = OrderPaymentRequestState.CREATED;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentRequest)) {
            return false;
        }
        final OrderPaymentRequest that = (OrderPaymentRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getState(), that.getState());
        builder.append(this.isStorePaymentMethod(), that.isStorePaymentMethod());
        builder.append(this.getPaymentRedirectUrl(), that.getPaymentRedirectUrl());
        builder.append(getIdOrNull(this.getOrder()), getIdOrNull(that.getOrder()));
        builder.append(getIdOrNull(this.getPaymentMethod()), getIdOrNull(that.getPaymentMethod()));
        builder.append(getIdOrNull(this.getOrderPayment()), getIdOrNull(that.getOrderPayment()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getClientIpAddress());
        builder.append(this.getState());
        builder.append(this.isStorePaymentMethod());
        builder.append(this.getPaymentRedirectUrl());
        builder.append(getIdOrNull(this.getOrder()));
        builder.append(getIdOrNull(this.getPaymentMethod()));
        builder.append(getIdOrNull(this.getOrderPayment()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("state", this.getState());
        builder.append("storePaymentMethod", this.isStorePaymentMethod());
        builder.append("paymentRedirectUrl", this.getPaymentRedirectUrl());
        builder.append("order", getIdOrNull(this.getOrder()));
        builder.append("paymentMethod", getIdOrNull(this.getPaymentMethod()));
        builder.append("orderPayment", getIdOrNull(this.getOrderPayment()));
        return builder.build();
    }
}
