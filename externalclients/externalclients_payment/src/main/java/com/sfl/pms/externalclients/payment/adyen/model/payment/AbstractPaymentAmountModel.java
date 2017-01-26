package com.sfl.pms.externalclients.payment.adyen.model.payment;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 2:53 PM
 */
public abstract class AbstractPaymentAmountModel extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = -1720164631145991802L;

    /* Constants */
    private static final int DEFAULT_MINOR_UNITS_MULTIPLIER = 100;

    /* Properties */
    private final String amountCurrency;

    private final BigDecimal amountValue;

    private final int minorUnitsMultiplier;


    /* Constructors */
    /* Default */
    public AbstractPaymentAmountModel(final String amountCurrency, final BigDecimal amountValue) {
        this(amountCurrency, amountValue, DEFAULT_MINOR_UNITS_MULTIPLIER);
    }

    public AbstractPaymentAmountModel(final String amountCurrency, final BigDecimal amountValue, final int minorUnitsMultiplier) {
        Assert.hasText(amountCurrency);
        Assert.notNull(amountValue);
        this.amountCurrency = amountCurrency;
        this.amountValue = amountValue;
        this.minorUnitsMultiplier = minorUnitsMultiplier;
    }


    /* Getters and setters */
    public String getAmountCurrency() {
        return amountCurrency;
    }

    public BigDecimal getAmountValue() {
        return amountValue;
    }

    public int getMinorUnitsMultiplier() {
        return minorUnitsMultiplier;
    }

    /* Public interface methods */
    public long getPaymentAmountInMinorUnits() {
        final BigDecimal amountInMinorUnits = getAmountValue().multiply(BigDecimal.valueOf(getMinorUnitsMultiplier()));
        return amountInMinorUnits.longValue();
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractPaymentAmountModel)) {
            return false;
        }
        AbstractPaymentAmountModel that = (AbstractPaymentAmountModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(amountCurrency, that.getAmountCurrency());
        builder.append(amountValue.doubleValue(), that.getAmountValue().doubleValue());
        builder.append(minorUnitsMultiplier, that.getMinorUnitsMultiplier());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(amountCurrency);
        builder.append(amountValue.doubleValue());
        builder.append(minorUnitsMultiplier);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("amountCurrency", amountCurrency);
        builder.append("amountValue", amountValue.doubleValue());
        builder.append("minorUnitsMultiplier", minorUnitsMultiplier);
        return builder.build();
    }
}
