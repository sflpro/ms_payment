package com.sfl.pms.externalclients.payment.adyen.model.recurring;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/4/15
 * Time: 10:41 AM
 */
public final class RecurringContractBankDetailsModel extends AbstractRecurringContractDetailsModel {

    private static final long serialVersionUID = -5568440308201721546L;

    /* Properties */
    private final String bankAccountNumber;

    private final String bankLocationId;

    private final String bankName;

    private final String bic;

    private final String countryCode;

    private final String iban;

    private final String ownerName;

    /* Constructors */
    public RecurringContractBankDetailsModel() {
        super();
        this.bankAccountNumber = null;
        this.bankLocationId = null;
        this.bankName = null;
        this.bic = null;
        this.countryCode = null;
        this.iban = null;
        this.ownerName = null;
    }

    private RecurringContractBankDetailsModel(final RecurringContractBankDetailsBuilder builder) {
        super(builder);
        this.bankAccountNumber = builder.bankAccountNumber;
        this.bankLocationId = builder.bankLocationId;
        this.bankName = builder.bankName;
        this.bic = builder.bic;
        this.countryCode = builder.countryCode;
        this.iban = builder.iban;
        this.ownerName = builder.ownerName;
    }

    /* Create builder */
    public static RecurringContractBankDetailsBuilder newBuilder() {
        return new RecurringContractBankDetailsBuilder();
    }

    /* Getters and setters */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getBankLocationId() {
        return bankLocationId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBic() {
        return bic;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getIban() {
        return iban;
    }

    public String getOwnerName() {
        return ownerName;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurringContractBankDetailsModel)) {
            return false;
        }
        RecurringContractBankDetailsModel that = (RecurringContractBankDetailsModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getBankAccountNumber(), that.getBankAccountNumber());
        builder.append(getBankLocationId(), that.getBankLocationId());
        builder.append(getBankName(), that.getBankName());
        builder.append(getBic(), that.getBic());
        builder.append(getCountryCode(), that.getCountryCode());
        builder.append(getIban(), that.getIban());
        builder.append(getOwnerName(), that.getOwnerName());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getBankAccountNumber());
        builder.append(getBankLocationId());
        builder.append(getBankName());
        builder.append(getBic());
        builder.append(getCountryCode());
        builder.append(getIban());
        builder.append(getOwnerName());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("bankAccountNumber", getBankAccountNumber());
        builder.append("bankLocationId", getBankLocationId());
        builder.append("bankName", getBankName());
        builder.append("bic", getBic());
        builder.append("countryCode", getCountryCode());
        builder.append("iban", getIban());
        builder.append("ownerName", getOwnerName());
        return builder.build();
    }

    /* Inner classes */
    public static final class RecurringContractBankDetailsBuilder extends AbstractRecurringContractDetailsBuilder<RecurringContractBankDetailsBuilder> {

        /* Builder properties */
        private String bankAccountNumber;

        private String bankLocationId;

        private String bankName;

        private String bic;

        private String countryCode;

        private String iban;

        private String ownerName;

        /* Constructors */
        private RecurringContractBankDetailsBuilder() {
        }

        public RecurringContractBankDetailsBuilder bankAccountNumber(final String bankAccountNumber) {
            this.bankAccountNumber = bankAccountNumber;
            return this;
        }

        public RecurringContractBankDetailsBuilder bankLocationId(final String bankLocationId) {
            this.bankLocationId = bankLocationId;
            return this;
        }

        public RecurringContractBankDetailsBuilder bankName(final String bankName) {
            this.bankName = bankName;
            return this;
        }

        public RecurringContractBankDetailsBuilder bic(final String bic) {
            this.bic = bic;
            return this;
        }

        public RecurringContractBankDetailsBuilder countryCode(final String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public RecurringContractBankDetailsBuilder iban(final String iban) {
            this.iban = iban;
            return this;
        }

        public RecurringContractBankDetailsBuilder ownerName(final String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public RecurringContractBankDetailsModel build() {
            return new RecurringContractBankDetailsModel(this);
        }

        /* Property getters */
        public String getBankAccountNumber() {
            return bankAccountNumber;
        }

        public String getBankLocationId() {
            return bankLocationId;
        }

        public String getBankName() {
            return bankName;
        }

        public String getBic() {
            return bic;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public String getIban() {
            return iban;
        }

        public String getOwnerName() {
            return ownerName;
        }
    }
}
