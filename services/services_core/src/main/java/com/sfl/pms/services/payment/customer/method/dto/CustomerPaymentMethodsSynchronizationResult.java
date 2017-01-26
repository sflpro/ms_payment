package com.sfl.pms.services.payment.customer.method.dto;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/27/15
 * Time: 6:14 PM
 */
public class CustomerPaymentMethodsSynchronizationResult implements Serializable {

    private static final long serialVersionUID = 41165942114754729L;

    /* Properties */
    private final List<CustomerPaymentMethod> createdCustomerPaymentMethods;

    private final List<CustomerPaymentMethod> deactivatedCustomerPaymentMethods;


    public CustomerPaymentMethodsSynchronizationResult(final List<CustomerPaymentMethod> createdCustomerPaymentMethods, final List<CustomerPaymentMethod> deactivatedCustomerPaymentMethods) {
        this.createdCustomerPaymentMethods = createdCustomerPaymentMethods;
        this.deactivatedCustomerPaymentMethods = deactivatedCustomerPaymentMethods;
    }

    /* Properties getters and setters */
    public List<CustomerPaymentMethod> getCreatedCustomerPaymentMethods() {
        return createdCustomerPaymentMethods;
    }

    public List<CustomerPaymentMethod> getDeactivatedCustomerPaymentMethods() {
        return deactivatedCustomerPaymentMethods;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodsSynchronizationResult)) {
            return false;
        }
        final CustomerPaymentMethodsSynchronizationResult that = (CustomerPaymentMethodsSynchronizationResult) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(this.getCreatedCustomerPaymentMethods(), that.getCreatedCustomerPaymentMethods());
        builder.append(this.getDeactivatedCustomerPaymentMethods(), that.getDeactivatedCustomerPaymentMethods());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getCreatedCustomerPaymentMethods());
        builder.append(this.getDeactivatedCustomerPaymentMethods());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("createdCustomerPaymentMethods", this.getCreatedCustomerPaymentMethods());
        builder.append("deactivatedCustomerPaymentMethods", this.getDeactivatedCustomerPaymentMethods());
        return builder.toString();
    }
}
