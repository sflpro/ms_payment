package com.sfl.pms.services.payment.common.model;

import com.sfl.pms.services.common.model.AbstractDomainUuIdAwareEntityModel;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
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
 * Date: 12/24/14
 * Time: 11:32 PM
 */
@Entity
@Table(name = "payment", indexes = {@Index(name = "IDX_payment_last_state", columnList = "last_state"), @Index(name = "IDX_payment_store_payment_method", columnList = "store_payment_method"), @Index(name = "IDX_payment_created", columnList = "created"), @Index(name = "IDX_payment_provider_type", columnList = "payment_provider_type")})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Payment extends AbstractDomainUuIdAwareEntityModel {
    private static final long serialVersionUID = 19731195327911276L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_type", nullable = false)
    private PaymentProviderType paymentProviderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmed_payment_method_type", nullable = true)
    private PaymentMethodType confirmedPaymentMethodType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", unique = false, nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private PaymentProcessingChannel paymentProcessingChannel;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_method_surcharge", nullable = false)
    private BigDecimal paymentMethodSurcharge;

    @Column(name = "payment_total_amount", nullable = false)
    private BigDecimal paymentTotalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "last_state", nullable = false)
    private PaymentState lastState;

    @OneToMany(mappedBy = "payment", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaymentStateChangeHistoryRecord> stateChangeHistoryRecords;

    @OneToMany(mappedBy = "payment", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaymentResult> paymentResults;

    @Column(name = "client_ip_address", nullable = true)
    private String clientIpAddress;

    @Column(name = "store_payment_method", nullable = false)
    private boolean storePaymentMethod;

    /* Constructors */
    public Payment() {
        initializeDefaults();
    }

    public Payment(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public PaymentType getType() {
        return type;
    }

    public void setType(final PaymentType type) {
        this.type = type;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    public PaymentMethodType getConfirmedPaymentMethodType() {
        return confirmedPaymentMethodType;
    }

    public void setConfirmedPaymentMethodType(final PaymentMethodType confirmedPaymentMethodType) {
        this.confirmedPaymentMethodType = confirmedPaymentMethodType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaymentMethodSurcharge() {
        return paymentMethodSurcharge;
    }

    public void setPaymentMethodSurcharge(final BigDecimal paymentMethodSurcharge) {
        this.paymentMethodSurcharge = paymentMethodSurcharge;
    }

    public BigDecimal getPaymentTotalAmount() {
        return paymentTotalAmount;
    }

    public void setPaymentTotalAmount(final BigDecimal totalPaymentAmount) {
        this.paymentTotalAmount = totalPaymentAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public PaymentState getLastState() {
        return lastState;
    }

    public void setLastState(final PaymentState lastState) {
        this.lastState = lastState;
    }

    public Set<PaymentStateChangeHistoryRecord> getStateChangeHistoryRecords() {
        return stateChangeHistoryRecords;
    }

    public void setStateChangeHistoryRecords(final Set<PaymentStateChangeHistoryRecord> stateChangeHistoryRecords) {
        this.stateChangeHistoryRecords = stateChangeHistoryRecords;
    }

    public Set<PaymentResult> getPaymentResults() {
        return paymentResults;
    }

    public void setPaymentResults(final Set<PaymentResult> paymentResults) {
        this.paymentResults = paymentResults;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }


    public PaymentProcessingChannel getPaymentProcessingChannel() {
        return paymentProcessingChannel;
    }

    public void setPaymentProcessingChannel(final PaymentProcessingChannel paymentProcessingChannel) {
        this.paymentProcessingChannel = paymentProcessingChannel;
    }

    public boolean isStorePaymentMethod() {
        return storePaymentMethod;
    }

    public void setStorePaymentMethod(final boolean storePaymentMethod) {
        this.storePaymentMethod = storePaymentMethod;
    }

    /* Public utility methods */
    public PaymentStateChangeHistoryRecord updatePaymentState(final PaymentState paymentState, final String information) {
        Assert.notNull(paymentState, "Payment state should not be null");
        // Create new payment state history entry
        final PaymentStateChangeHistoryRecord historyRecord = new PaymentStateChangeHistoryRecord();
        historyRecord.setInitialState(getLastState());
        historyRecord.setUpdatedState(paymentState);
        historyRecord.setInformation(information);
        historyRecord.setPayment(this);
        // Add new history entry
        getStateChangeHistoryRecords().add(historyRecord);
        // Update last state
        setLastState(paymentState);
        setUpdated(new Date());
        return historyRecord;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.stateChangeHistoryRecords = new LinkedHashSet<>();
        this.paymentResults = new LinkedHashSet<>();
        // Update last state
        updatePaymentState(PaymentState.CREATED, null);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        final Payment that = (Payment) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.getConfirmedPaymentMethodType(), that.getConfirmedPaymentMethodType());
        builder.append(this.getLastState(), that.getLastState());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.isStorePaymentMethod(), that.isStorePaymentMethod());
        builder.append(getIdOrNull(this.getCustomer()), getIdOrNull(that.getCustomer()));
        builder.append(getIdOrNull(this.getPaymentProcessingChannel()), getIdOrNull(that.getPaymentProcessingChannel()));
        builder.append(getDoubleValueOrNull(this.getAmount()), getDoubleValueOrNull(that.getAmount()));
        builder.append(getDoubleValueOrNull(this.getPaymentMethodSurcharge()), getDoubleValueOrNull(that.getPaymentMethodSurcharge()));
        builder.append(getDoubleValueOrNull(this.getPaymentTotalAmount()), getDoubleValueOrNull(that.getPaymentTotalAmount()));
        builder.append(this.getCurrency(), that.getCurrency());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getPaymentProviderType());
        builder.append(this.getConfirmedPaymentMethodType());
        builder.append(this.getLastState());
        builder.append(this.getClientIpAddress());
        builder.append(this.isStorePaymentMethod());
        builder.append(getIdOrNull(this.getCustomer()));
        builder.append(getIdOrNull(this.getPaymentProcessingChannel()));
        builder.append(getDoubleValueOrNull(this.getAmount()));
        builder.append(getDoubleValueOrNull(this.getPaymentMethodSurcharge()));
        builder.append(getDoubleValueOrNull(this.getPaymentTotalAmount()));
        builder.append(this.getCurrency());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        builder.append("confirmedPaymentMethodType", this.getConfirmedPaymentMethodType());
        builder.append("lastState", this.getLastState());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("storePaymentMethod", this.isStorePaymentMethod());
        builder.append("customer", getIdOrNull(this.getCustomer()));
        builder.append("paymentProcessingChannel", getIdOrNull(this.getPaymentProcessingChannel()));
        builder.append("amount", this.getAmount());
        builder.append("paymentMethodSurcharge", this.getPaymentMethodSurcharge());
        builder.append("paymentTotalAmount", this.getPaymentTotalAmount());
        builder.append("currency", this.getCurrency());
        return builder.build();
    }

}
