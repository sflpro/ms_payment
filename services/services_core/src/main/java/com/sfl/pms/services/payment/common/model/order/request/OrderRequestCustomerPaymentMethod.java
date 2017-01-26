package com.sfl.pms.services.payment.common.model.order.request;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 11:47 AM
 */
@Entity
@DiscriminatorValue(value = "CUSTOMER_PAYMENT_METHOD")
public class OrderRequestCustomerPaymentMethod extends OrderRequestPaymentMethod {

    private static final long serialVersionUID = -915007037773560797L;

    /* Properties */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_payment_method_id", nullable = true, unique = false)
    private CustomerPaymentMethod customerPaymentMethod;

    /* Constructors */
    public OrderRequestCustomerPaymentMethod() {
        setType(OrderRequestPaymentMethodType.CUSTOMER_PAYMENT_METHOD);
    }

    /* Properties getters and setters */
    public CustomerPaymentMethod getCustomerPaymentMethod() {
        return customerPaymentMethod;
    }

    public void setCustomerPaymentMethod(final CustomerPaymentMethod customerPaymentMethod) {
        this.customerPaymentMethod = customerPaymentMethod;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestCustomerPaymentMethod)) {
            return false;
        }
        final OrderRequestCustomerPaymentMethod that = (OrderRequestCustomerPaymentMethod) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getIdOrNull(this.getCustomerPaymentMethod()), getIdOrNull(that.getCustomerPaymentMethod()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(this.getCustomerPaymentMethod()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("customerPaymentMethod", getIdOrNull(this.getCustomerPaymentMethod()));
        return builder.build();
    }
}
