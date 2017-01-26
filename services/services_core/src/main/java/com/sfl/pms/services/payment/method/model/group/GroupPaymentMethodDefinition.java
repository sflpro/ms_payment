package com.sfl.pms.services.payment.method.model.group;

import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinitionType;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:25 PM
 */
@Entity
@DiscriminatorValue(value = "GROUP")
public class GroupPaymentMethodDefinition extends PaymentMethodDefinition {

    private static final long serialVersionUID = -4351590509644227864L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_group_type", nullable = true)
    private PaymentMethodGroupType paymentMethodGroupType;

    /* Constructors */
    public GroupPaymentMethodDefinition() {
        setType(PaymentMethodDefinitionType.GROUP);
    }

    /* Properties getters and setters */
    public PaymentMethodGroupType getPaymentMethodGroupType() {
        return paymentMethodGroupType;
    }

    public void setPaymentMethodGroupType(final PaymentMethodGroupType paymentMethodType) {
        this.paymentMethodGroupType = paymentMethodType;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupPaymentMethodDefinition)) {
            return false;
        }
        final GroupPaymentMethodDefinition that = (GroupPaymentMethodDefinition) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodGroupType(), that.getPaymentMethodGroupType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodGroupType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodGroupType", this.getPaymentMethodGroupType());
        return builder.build();
    }
}
