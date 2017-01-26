package com.sfl.pms.services.payment.customer.method.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 5:08 PM
 */
public abstract class CustomerPaymentMethodDto<T extends CustomerPaymentMethod> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = -7741454335015225495L;

    /* Properties */
    private PaymentMethodType paymentMethodType;

    private final CustomerPaymentMethodType type;

    /* Constructors */
    public CustomerPaymentMethodDto(final CustomerPaymentMethodType type) {
        this.type = type;
    }

    public CustomerPaymentMethodDto(final CustomerPaymentMethodType type, final PaymentMethodType paymentMethodType) {
        this(type);
        this.paymentMethodType = paymentMethodType;
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public CustomerPaymentMethodType getType() {
        return type;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T paymentMethod) {
        paymentMethod.setPaymentMethodType(getPaymentMethodType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodDto)) {
            return false;
        }
        final CustomerPaymentMethodDto that = (CustomerPaymentMethodDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getType(), that.getType());
        builder.append(getPaymentMethodType(), that.getPaymentMethodType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getType());
        builder.append(getPaymentMethodType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", getType());
        builder.append("paymentMethodType", getPaymentMethodType());
        return builder.build();
    }

}
