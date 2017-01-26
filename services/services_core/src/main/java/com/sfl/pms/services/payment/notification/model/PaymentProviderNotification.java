package com.sfl.pms.services.payment.notification.model;

import com.sfl.pms.services.common.model.AbstractDomainUuIdAwareEntityModel;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 6:40 PM
 */
@Entity
@Table(name = "payment_provider_notification")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentProviderNotification extends AbstractDomainUuIdAwareEntityModel {

    private static final long serialVersionUID = 7159911090416660661L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentProviderType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PaymentProviderNotificationState state;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false, unique = false)
    private PaymentProviderNotificationRequest request;

    @Column(name = "raw_content", nullable = false, length = 5000)
    private String rawContent;

    @Column(name = "client_ip_address", nullable = true)
    private String clientIpAddress;

    /* Constructors */
    public PaymentProviderNotification() {
        initializeDefaults();
    }

    public PaymentProviderNotification(boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    public void setType(final PaymentProviderType type) {
        this.type = type;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(final String rawContent) {
        this.rawContent = rawContent;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public PaymentProviderNotificationState getState() {
        return state;
    }

    public void setState(final PaymentProviderNotificationState state) {
        this.state = state;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(final Payment payment) {
        this.payment = payment;
    }

    public PaymentProviderNotificationRequest getRequest() {
        return request;
    }

    public void setRequest(final PaymentProviderNotificationRequest request) {
        this.request = request;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.state = PaymentProviderNotificationState.CREATED;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderNotification)) {
            return false;
        }
        final PaymentProviderNotification that = (PaymentProviderNotification) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getState(), that.getState());
        builder.append(getIdOrNull(this.getPayment()), getIdOrNull(that.getPayment()));
        builder.append(getIdOrNull(this.getRequest()), getIdOrNull(that.getRequest()));
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getRawContent(), that.getRawContent());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getState());
        builder.append(getIdOrNull(this.getPayment()));
        builder.append(getIdOrNull(this.getRequest()));
        builder.append(this.getClientIpAddress());
        builder.append(this.getRawContent());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("state", this.getState());
        builder.append("payment", getIdOrNull(this.getPayment()));
        builder.append("request", getIdOrNull(this.getRequest()));
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("rawContent", this.getRawContent());
        return builder.build();
    }
}
