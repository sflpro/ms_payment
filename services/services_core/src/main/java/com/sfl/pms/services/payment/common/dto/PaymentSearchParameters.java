package com.sfl.pms.services.payment.common.dto;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/5/15
 * Time: 1:43 PM
 */
public class PaymentSearchParameters implements Serializable {

    private static final long serialVersionUID = -2428230137982534587L;

    /* Properties */
    private PaymentState paymentState;

    private Boolean storePaymentMethod;

    private Date createdAfterDate;

    private Date createdBeforeDate;

    private PaymentProviderType paymentProviderType;

    /* Constructors */
    public PaymentSearchParameters() {
    }

    /* Properties getters and setters */
    public PaymentState getPaymentState() {
        return paymentState;
    }

    public PaymentSearchParameters setPaymentState(final PaymentState paymentState) {
        this.paymentState = paymentState;
        return this;
    }

    public Boolean getStorePaymentMethod() {
        return storePaymentMethod;
    }

    public PaymentSearchParameters setStorePaymentMethod(final Boolean storePaymentMethod) {
        this.storePaymentMethod = storePaymentMethod;
        return this;
    }

    public Date getCreatedAfterDate() {
        return AbstractDomainEntityModel.cloneDateIfNotNull(createdAfterDate);
    }

    public PaymentSearchParameters setCreatedAfterDate(final Date createdAfterDate) {
        this.createdAfterDate = AbstractDomainEntityModel.cloneDateIfNotNull(createdAfterDate);
        return this;
    }

    public Date getCreatedBeforeDate() {
        return AbstractDomainEntityModel.cloneDateIfNotNull(createdBeforeDate);
    }

    public PaymentSearchParameters setCreatedBeforeDate(final Date createdBeforeDate) {
        this.createdBeforeDate = AbstractDomainEntityModel.cloneDateIfNotNull(createdBeforeDate);
        return this;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public PaymentSearchParameters setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
        return this;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentSearchParameters)) {
            return false;
        }
        final PaymentSearchParameters that = (PaymentSearchParameters) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getPaymentState(), that.getPaymentState());
        builder.append(this.getStorePaymentMethod(), that.getStorePaymentMethod());
        builder.append(this.getCreatedAfterDate(), that.getCreatedAfterDate());
        builder.append(this.getCreatedBeforeDate(), that.getCreatedBeforeDate());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getPaymentState());
        builder.append(this.getStorePaymentMethod());
        builder.append(this.getCreatedAfterDate());
        builder.append(this.getCreatedBeforeDate());
        builder.append(this.getPaymentProviderType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("paymentState", this.getPaymentState());
        builder.append("storePaymentMethod", this.getStorePaymentMethod());
        builder.append("createdAfterDate", this.getCreatedAfterDate());
        builder.append("createdBeforeDate", this.getCreatedBeforeDate());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        return builder.build();
    }
}
