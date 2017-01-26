package com.sfl.pms.services.order.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 12:17 PM
 */
@Entity
@Table(name = "orders", indexes = {@Index(name = "IDX_orders_last_state", columnList = "last_state")})
public class Order extends AbstractDomainEntityModel {

    private static final long serialVersionUID = -2751915037290600194L;

    /* Properties */
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "payment_total_with_vat", nullable = false)
    private BigDecimal paymentTotalWithVat;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_state", nullable = false)
    private OrderState lastState;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderStateChangeHistoryRecord> stateChangeHistoryRecords;

    @OneToOne(mappedBy = "order", optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private OrderPaymentChannel paymentChannel;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderPayment> payments;

    /* Constructors */
    public Order() {
        initializeDefaults();
    }

    /* Getters and setters */

    public String getUuId() {
        return uuId;
    }

    public void setUuId(final String uuId) {
        this.uuId = uuId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getPaymentTotalWithVat() {
        return paymentTotalWithVat;
    }

    public void setPaymentTotalWithVat(final BigDecimal paymentTotalWithVat) {
        this.paymentTotalWithVat = paymentTotalWithVat;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public OrderState getLastState() {
        return lastState;
    }

    public void setLastState(final OrderState lastState) {
        this.lastState = lastState;
    }

    public Set<OrderStateChangeHistoryRecord> getStateChangeHistoryRecords() {
        return stateChangeHistoryRecords;
    }

    public void setStateChangeHistoryRecords(final Set<OrderStateChangeHistoryRecord> stateChangeHistoryRecords) {
        this.stateChangeHistoryRecords = stateChangeHistoryRecords;
    }

    public OrderPaymentChannel getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(final OrderPaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Set<OrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(final Set<OrderPayment> payments) {
        this.payments = payments;
    }

    /* Public utility methods */
    public OrderStateChangeHistoryRecord updateOrderState(final OrderState orderState) {
        Assert.notNull(orderState, "Order state should not be null");
        // Create new order state history entry
        final OrderStateChangeHistoryRecord historyRecord = new OrderStateChangeHistoryRecord();
        historyRecord.setInitialState(getLastState());
        historyRecord.setUpdatedState(orderState);
        historyRecord.setOrder(this);
        // Add new history entry
        getStateChangeHistoryRecords().add(historyRecord);
        // Update last state
        setLastState(orderState);
        setUpdated(new Date());
        return historyRecord;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.stateChangeHistoryRecords = new LinkedHashSet<>();
        this.payments = new LinkedHashSet<>();
        // Set default created state
        updateOrderState(OrderState.CREATED);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        final Order that = (Order) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getUuId(), that.getUuId());
        builder.append(getLastState(), that.getLastState());
        builder.append(getIdOrNull(getCustomer()), getIdOrNull(that.getCustomer()));
        builder.append(getDoubleValueOrNull(getPaymentTotalWithVat()), getDoubleValueOrNull(that.getPaymentTotalWithVat()));
        builder.append(getCurrency(), that.getCurrency());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getUuId());
        builder.append(getLastState());
        builder.append(getIdOrNull(getCustomer()));
        builder.append(getDoubleValueOrNull(getPaymentTotalWithVat()));
        builder.append(getCurrency());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("uuId", getUuId());
        builder.append("lastState", getLastState());
        builder.append("customer", getIdOrNull(getCustomer()));
        builder.append("paymentTotalWithVat", getPaymentTotalWithVat());
        builder.append("currency", getCurrency());
        return builder.build();
    }
}
