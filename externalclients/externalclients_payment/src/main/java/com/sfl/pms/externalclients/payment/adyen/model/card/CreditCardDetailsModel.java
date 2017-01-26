package com.sfl.pms.externalclients.payment.adyen.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 6:32 PM
 */
public class CreditCardDetailsModel extends AbstractCardDetailsModel {

    private static final long serialVersionUID = 7706966985225381364L;

    /* Constants */
    private static final int CARD_CODE_MIN = 3;

    private static final int CARD_CODE_MAX = 4;

    /* Properties */
    private final String code;


    public CreditCardDetailsModel(final String number, final String holderName, final String code, final int expiryMonth, final int expiryYear) {
        super(number, holderName, expiryMonth, expiryYear);
        validateCreditCardDetails(code);
        this.code = code;
    }


    /* Utility methods */
    private void validateCreditCardDetails(final String code) {
        Assert.isTrue(StringUtils.length(code) >= CARD_CODE_MIN && StringUtils.length(code) <= CARD_CODE_MAX);
    }

    /* Getters and setters */
    public String getCode() {
        return code;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardDetailsModel)) {
            return false;
        }
        CreditCardDetailsModel that = (CreditCardDetailsModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(code, that.getCode());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(code);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("code", code);
        return builder.build();
    }
}
