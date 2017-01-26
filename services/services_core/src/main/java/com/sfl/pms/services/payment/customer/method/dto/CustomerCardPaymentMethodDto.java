package com.sfl.pms.services.payment.customer.method.dto;

import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 5:13 PM
 */
public class CustomerCardPaymentMethodDto extends CustomerPaymentMethodDto<CustomerCardPaymentMethod> {
    private static final long serialVersionUID = -4846317977303740344L;

    /* Properties */
    private int expiryMonth;

    private int expiryYear;

    private String numberTail;

    private String holderName;

    /* Constructors */
    public CustomerCardPaymentMethodDto() {
        super(CustomerPaymentMethodType.CARD);
    }

    public CustomerCardPaymentMethodDto(final PaymentMethodType paymentMethodType, final int expiryMonth, final int expiryYear, final String numberTail, final String holderName) {
        super(CustomerPaymentMethodType.CARD, paymentMethodType);
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.numberTail = numberTail;
        this.holderName = holderName;
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

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final CustomerCardPaymentMethod paymentMethod) {
        super.updateDomainEntityProperties(paymentMethod);
        paymentMethod.setExpiryMonth(getExpiryMonth());
        paymentMethod.setExpiryYear(getExpiryYear());
        paymentMethod.setHolderName(getHolderName());
        paymentMethod.setNumberTail(getNumberTail());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerCardPaymentMethodDto)) {
            return false;
        }
        final CustomerCardPaymentMethodDto that = (CustomerCardPaymentMethodDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getExpiryMonth(), that.getExpiryMonth());
        builder.append(getExpiryYear(), that.getExpiryYear());
        builder.append(getHolderName(), that.getHolderName());
        builder.append(getNumberTail(), that.getNumberTail());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getExpiryMonth());
        builder.append(getExpiryYear());
        builder.append(getHolderName());
        builder.append(getNumberTail());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("expiryMonth", getExpiryMonth());
        builder.append("expiryYear", getExpiryYear());
        builder.append("holderName", getHolderName());
        builder.append("numberTail", getNumberTail());
        return builder.build();
    }
}
