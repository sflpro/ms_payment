package com.sfl.pms.externalclients.payment.adyen.model.payment;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 5:40 PM
 */
public class DynamicZeroValuePaymentModel extends AbstractPaymentAmountModel {

    private static final long serialVersionUID = -6168794407733891777L;

    /* Constructors */

    /**
     * Create payment model with zero value
     * Currency code must be provided
     *
     * @param amountCurrency currency code
     */
    public DynamicZeroValuePaymentModel(final String amountCurrency) {
        super(amountCurrency, BigDecimal.ZERO);
    }


    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DynamicZeroValuePaymentModel)) {
            return false;
        }
        DynamicZeroValuePaymentModel that = (DynamicZeroValuePaymentModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        return builder.build();
    }
}
