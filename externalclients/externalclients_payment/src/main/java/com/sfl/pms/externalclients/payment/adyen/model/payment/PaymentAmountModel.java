package com.sfl.pms.externalclients.payment.adyen.model.payment;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 1/12/15
 * Time: 7:37 PM
 */
public class PaymentAmountModel extends AbstractPaymentAmountModel {

    private static final long serialVersionUID = 7671517757216037998L;

    /* Constructors */
    public PaymentAmountModel(final String amountCurrency, final BigDecimal amountValue) {
        super(amountCurrency, amountValue);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAmountModel)) {
            return false;
        }
        PaymentAmountModel that = (PaymentAmountModel) o;
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
