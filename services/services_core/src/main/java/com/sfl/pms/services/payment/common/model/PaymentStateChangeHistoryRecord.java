package com.sfl.pms.services.payment.common.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:15 AM
 */
@Entity
@Table(name = "payment_state_change_history_record")
public class PaymentStateChangeHistoryRecord extends AbstractDomainEntityModel {
    private static final long serialVersionUID = -5743477436288381125L;

    /* Properties */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "initial_state", nullable = true)
    private PaymentState initialState;

    @Enumerated(EnumType.STRING)
    @Column(name = "updated_state", nullable = false)
    private PaymentState updatedState;

    @Column(name = "information", nullable = true, length = 2000)
    private String information;

    /* Constructors */
    public PaymentStateChangeHistoryRecord() {
    }

    /* Properties getters and setters */
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(final Payment payment) {
        this.payment = payment;
    }

    public PaymentState getInitialState() {
        return initialState;
    }

    public void setInitialState(final PaymentState initialState) {
        this.initialState = initialState;
    }

    public PaymentState getUpdatedState() {
        return updatedState;
    }

    public void setUpdatedState(final PaymentState updatedState) {
        this.updatedState = updatedState;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(final String information) {
        this.information = information;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentStateChangeHistoryRecord)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final PaymentStateChangeHistoryRecord that = (PaymentStateChangeHistoryRecord) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getIdOrNull(getPayment()), getIdOrNull(that.getPayment()));
        builder.append(getInitialState(), that.getInitialState());
        builder.append(getUpdatedState(), that.getUpdatedState());
        builder.append(getInformation(), that.getInformation());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(getPayment()));
        builder.append(getInitialState());
        builder.append(getUpdatedState());
        builder.append(getInformation());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("payment", getIdOrNull(getPayment()));
        builder.append("initialState", getInitialState());
        builder.append("updatedState", getUpdatedState());
        builder.append("information", getInformation());
        return builder.build();
    }
}
