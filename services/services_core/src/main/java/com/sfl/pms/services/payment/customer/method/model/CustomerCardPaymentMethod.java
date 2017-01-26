package com.sfl.pms.services.payment.customer.method.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 8:11 PM
 */
@Entity
@DiscriminatorValue(value = "CARD")
@Table(name = "payment_method_customer_card")
public class CustomerCardPaymentMethod extends CustomerPaymentMethod {
    private static final long serialVersionUID = 8114393228464268394L;

    /* Properties */
    @Column(name = "expiry_month", nullable = false)
    private int expiryMonth;

    @Column(name = "expiry_year", nullable = false)
    private int expiryYear;

    @Column(name = "number_tail", nullable = false)
    private String numberTail;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    /* Constrictors */
    public CustomerCardPaymentMethod() {
        initializeDefaults();
    }

    public CustomerCardPaymentMethod(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(final int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(final int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getNumberTail() {
        return numberTail;
    }

    public void setNumberTail(final String numberTail) {
        this.numberTail = numberTail;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(final String holderName) {
        this.holderName = holderName;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        setType(CustomerPaymentMethodType.CARD);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerCardPaymentMethod)) {
            return false;
        }
        final CustomerCardPaymentMethod that = (CustomerCardPaymentMethod) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getExpiryMonth(), that.getExpiryMonth());
        builder.append(this.getExpiryYear(), that.getExpiryYear());
        builder.append(this.getNumberTail(), that.getNumberTail());
        builder.append(this.getHolderName(), that.getHolderName());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getExpiryMonth());
        builder.append(this.getExpiryYear());
        builder.append(this.getNumberTail());
        builder.append(this.getHolderName());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("expiryMonth", this.getExpiryMonth());
        builder.append("expiryYear", this.getExpiryYear());
        builder.append("numberTails", this.getNumberTail());
        builder.append("holderName", this.getHolderName());
        return builder.build();
    }
}
