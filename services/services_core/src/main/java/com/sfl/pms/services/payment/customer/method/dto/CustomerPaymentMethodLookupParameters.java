package com.sfl.pms.services.payment.customer.method.dto;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 3/5/15
 * Time: 2:49 PM
 */
public class CustomerPaymentMethodLookupParameters implements Serializable {

    private static final long serialVersionUID = -1203622706461546239L;

    /* Properties */
    private CustomerPaymentMethodType type;

    private Long customerId;

    private Boolean excludeRemoved;

    /* Constructors */
    public CustomerPaymentMethodLookupParameters() {
    }

    /* Getters and setters */
    public CustomerPaymentMethodType getType() {
        return type;
    }

    public CustomerPaymentMethodLookupParameters setType(final CustomerPaymentMethodType type) {
        this.type = type;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerPaymentMethodLookupParameters setCustomerId(final Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Boolean getExcludeRemoved() {
        return excludeRemoved;
    }

    public CustomerPaymentMethodLookupParameters setExcludeRemoved(final boolean excludeRemoved) {
        this.excludeRemoved = excludeRemoved;
        return this;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodLookupParameters)) {
            return false;
        }
        final CustomerPaymentMethodLookupParameters that = (CustomerPaymentMethodLookupParameters) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getType(), that.getType());
        builder.append(getExcludeRemoved(), that.getExcludeRemoved());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getType());
        builder.append(getExcludeRemoved());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("type", getType());
        builder.append("excludeRemoved", getExcludeRemoved());
        return builder.build();
    }
}
