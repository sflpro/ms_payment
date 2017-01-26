package com.sfl.pms.externalclients.payment.adyen.model.card;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 6:10 PM
 */
public class AbstractCardDetailsModel extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 4491898267572165480L;

    /* Constants */
    private static final int EXPIRY_MONTH_MIN = 1;

    private static final int EXPIRY_MONTH_MAX = 12;

    /* Properties */
    private final String number;

    private final String holderName;

    private final int expiryMonth;

    private final int expiryYear;


    /* Constructors */
    public AbstractCardDetailsModel(final String number, final String holderName, final int expiryMonth, final int expiryYear) {
        validateCardDetails(number, holderName, expiryMonth);
        this.number = number;
        this.holderName = holderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    /*  Getters and setters */
    public String getNumber() {
        return number;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public String getHolderName() {
        return holderName;
    }

    /* Utility methods */
    private void validateCardDetails(final String number, final String holderName, final int expiryMonth) {
        Assert.hasText(number);
        Assert.hasText(holderName);
        Assert.isTrue(expiryMonth >= EXPIRY_MONTH_MIN && expiryMonth <= EXPIRY_MONTH_MAX);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractCardDetailsModel)) {
            return false;
        }
        AbstractCardDetailsModel that = (AbstractCardDetailsModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(number, that.getNumber());
        builder.append(expiryMonth, that.getExpiryMonth());
        builder.append(expiryYear, that.getExpiryYear());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(number);
        builder.append(expiryMonth);
        builder.append(expiryYear);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("number", number);
        builder.append("expiryMonth", expiryMonth);
        builder.append("expiryYear", expiryYear);
        return builder.build();
    }
}
