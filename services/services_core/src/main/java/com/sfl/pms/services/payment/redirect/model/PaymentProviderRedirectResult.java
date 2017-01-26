package com.sfl.pms.services.payment.redirect.model;

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
 * Date: 7/11/15
 * Time: 11:19 AM
 */
@Entity
@Table(name = "payment_redirect_result")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentProviderRedirectResult extends AbstractDomainUuIdAwareEntityModel {

    private static final long serialVersionUID = -1532546454823051176L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentProviderType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PaymentProviderRedirectResultState state;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = true, unique = false)
    private Payment payment;

    /* Constructors */
    public PaymentProviderRedirectResult() {
        initializeDefaults();
    }

    public PaymentProviderRedirectResult(final boolean generateUuId) {
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

    public PaymentProviderRedirectResultState getState() {
        return state;
    }

    public void setState(final PaymentProviderRedirectResultState state) {
        this.state = state;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(final Payment payment) {
        this.payment = payment;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.state = PaymentProviderRedirectResultState.CREATED;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderRedirectResult)) {
            return false;
        }
        final PaymentProviderRedirectResult that = (PaymentProviderRedirectResult) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getState(), that.getState());
        builder.append(getIdOrNull(this.getPayment()), getIdOrNull(that.getPayment()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getState());
        builder.append(getIdOrNull(this.getPayment()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("state", this.getState());
        builder.append("payment", getIdOrNull(this.getPayment()));
        return builder.build();
    }
}
