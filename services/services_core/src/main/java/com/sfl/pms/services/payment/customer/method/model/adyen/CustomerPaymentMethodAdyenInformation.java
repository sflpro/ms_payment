package com.sfl.pms.services.payment.customer.method.model.adyen;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
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
 * Time: 8:42 PM
 */
@Entity
@DiscriminatorValue(value = "ADYEN")
@Table(name = "payment_method_customer_adyen_info")
public class CustomerPaymentMethodAdyenInformation extends CustomerPaymentMethodProviderInformation {
    private static final long serialVersionUID = -8376131867009306055L;

    /* Properties */
    @Column(name = "recurring_detail_reference", unique = true, nullable = false)
    private String recurringDetailReference;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformation() {
        setType(PaymentProviderType.ADYEN);
    }

    /* Properties getters and setters */

    public String getRecurringDetailReference() {
        return recurringDetailReference;
    }

    public void setRecurringDetailReference(final String recurringDetailReference) {
        this.recurringDetailReference = recurringDetailReference;
    }

    @Override
    public String getPaymentProviderIdentifierForPaymentMethod() {
        return recurringDetailReference;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAdyenInformation)) {
            return false;
        }
        final CustomerPaymentMethodAdyenInformation that = (CustomerPaymentMethodAdyenInformation) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRecurringDetailReference(), that.getRecurringDetailReference());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRecurringDetailReference());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("recurringDetailReference", this.getRecurringDetailReference());
        return builder.build();
    }
}
