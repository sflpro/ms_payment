package com.sfl.pms.externalclients.payment.adyen.model.recurring;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 4:59 PM
 */
public final class RecurringContractCardDetailsModel extends AbstractRecurringContractDetailsModel {

    private static final long serialVersionUID = -2991427978404266419L;

    /* Properties */
    private final String cardHolderName;

    private final String cardNumber;

    private final Integer expiryYear;

    private final Integer expiryMonth;

    /* Constructors */
    private RecurringContractCardDetailsModel() {
        super();
        this.cardHolderName = null;
        this.cardNumber = null;
        this.expiryYear = null;
        this.expiryMonth = null;
    }

    private RecurringContractCardDetailsModel(final RecurringContractCardDetailsBuilder builder) {
        super(builder);
        this.cardHolderName = builder.cardHolderName;
        this.cardNumber = builder.cardNumber;
        this.expiryMonth = builder.expiryMonth;
        this.expiryYear = builder.expiryYear;
    }

    /* Create builder */
    public static RecurringContractCardDetailsBuilder newBuilder() {
        return new RecurringContractCardDetailsBuilder();
    }

    /* Getters and setters */
    public String getCardHolderName() {
        return cardHolderName;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurringContractCardDetailsModel)) {
            return false;
        }
        RecurringContractCardDetailsModel that = (RecurringContractCardDetailsModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(cardHolderName, that.getCardHolderName());
        builder.append(cardNumber, that.getCardNumber());
        builder.append(expiryYear, that.getExpiryYear());
        builder.append(expiryMonth, that.getExpiryMonth());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(cardHolderName);
        builder.append(cardNumber);
        builder.append(expiryYear);
        builder.append(expiryMonth);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("cardHolderName", cardHolderName);
        builder.append("cardNumber", cardNumber);
        builder.append("expiryYear", expiryYear);
        builder.append("expiryMonth", expiryMonth);
        return builder.build();
    }

    /* Inner classes */
    public static final class RecurringContractCardDetailsBuilder extends AbstractRecurringContractDetailsBuilder<RecurringContractCardDetailsBuilder> {

        /* Builder properties */
        private String cardNumber;

        private String cardHolderName;

        private Integer expiryYear;

        private Integer expiryMonth;

        /* Constructors */
        private RecurringContractCardDetailsBuilder() {
        }

        public RecurringContractCardDetailsBuilder cardHolderName(final String cardHolderName) {
            this.cardHolderName = cardHolderName;
            return this;
        }

        public RecurringContractCardDetailsBuilder cardNumber(final String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public RecurringContractCardDetailsBuilder expiryYear(final Integer expiryYear) {
            this.expiryYear = expiryYear;
            return this;
        }

        public RecurringContractCardDetailsBuilder expiryYearFromString(final String expiryYear) {
            this.expiryYear = Integer.parseInt(expiryYear);
            return this;
        }

        public RecurringContractCardDetailsBuilder expiryMonth(final Integer expiryMonth) {
            this.expiryMonth = expiryMonth;
            return this;
        }

        public RecurringContractCardDetailsBuilder expiryMonthFromString(final String expiryMonth) {
            this.expiryMonth = Integer.parseInt(expiryMonth);
            return this;
        }

        public RecurringContractCardDetailsModel build() {
            return new RecurringContractCardDetailsModel(this);
        }
    }
}
