package com.sfl.pms.services.payment.common.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 5:50 PM
 */
@Entity
@Table(name = "payment_result")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentResult extends AbstractDomainEntityModel {
    private static final long serialVersionUID = 5184427976170250583L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentResultStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentProviderType type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false, unique = false)
    private Payment payment;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = true, unique = false)
    private PaymentProviderNotification notification;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "redirect_result_id", nullable = true, unique = false)
    private PaymentProviderRedirectResult redirectResult;

    /* Constructors */
    public PaymentResult() {
    }

    /* Properties getters and setters */
    public PaymentResultStatus getStatus() {
        return status;
    }

    public void setStatus(final PaymentResultStatus status) {
        this.status = status;
    }

    public PaymentProviderType getType() {
        return type;
    }

    public void setType(final PaymentProviderType type) {
        this.type = type;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(final Payment payment) {
        this.payment = payment;
    }

    public PaymentProviderNotification getNotification() {
        return notification;
    }

    public void setNotification(final PaymentProviderNotification notification) {
        this.notification = notification;
    }

    public PaymentProviderRedirectResult getRedirectResult() {
        return redirectResult;
    }

    public void setRedirectResult(final PaymentProviderRedirectResult redirectResult) {
        this.redirectResult = redirectResult;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentResult)) {
            return false;
        }
        final PaymentResult that = (PaymentResult) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getStatus(), that.getStatus());
        builder.append(getIdOrNull(this.getPayment()), getIdOrNull(that.getPayment()));
        builder.append(getIdOrNull(this.getNotification()), getIdOrNull(that.getNotification()));
        builder.append(getIdOrNull(this.getRedirectResult()), getIdOrNull(that.getRedirectResult()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getStatus());
        builder.append(getIdOrNull(this.getPayment()));
        builder.append(getIdOrNull(this.getNotification()));
        builder.append(getIdOrNull(this.getRedirectResult()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("status", this.getStatus());
        builder.append("payment", getIdOrNull(this.getPayment()));
        builder.append("notification", getIdOrNull(this.getNotification()));
        builder.append("redirectResult", getIdOrNull(this.getRedirectResult()));
        return builder.build();
    }
}
