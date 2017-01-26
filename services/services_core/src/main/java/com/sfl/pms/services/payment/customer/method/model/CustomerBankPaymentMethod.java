package com.sfl.pms.services.payment.customer.method.model;

import com.sfl.pms.services.country.model.CountryCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/3/15
 * Time: 4:11 PM
 */
@Entity
@DiscriminatorValue(value = "BANK")
@Table(name = "payment_method_customer_bank")
public class CustomerBankPaymentMethod extends CustomerPaymentMethod {

    private static final long serialVersionUID = -636161747696669885L;

    /* Properties */
    @Column(name = "bank_account_number", nullable = true)
    private String bankAccountNumber;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "country_code", nullable = false)
    private CountryCode countryCode;

    @Column(name = "iban", nullable = true)
    private String iban;

    @Column(name = "bic", nullable = true)
    private String bic;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    /* Constructors */
    public CustomerBankPaymentMethod() {
        initializeDefaults();
    }

    public CustomerBankPaymentMethod(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(final String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(final String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(final String bic) {
        this.bic = bic;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        setType(CustomerPaymentMethodType.BANK);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerBankPaymentMethod)) {
            return false;
        }
        final CustomerBankPaymentMethod that = (CustomerBankPaymentMethod) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getBankAccountNumber(), that.getBankAccountNumber());
        builder.append(this.getBankName(), that.getBankName());
        builder.append(this.getCountryCode(), that.getCountryCode());
        builder.append(this.getIban(), that.getIban());
        builder.append(this.getBic(), that.getBic());
        builder.append(this.getOwnerName(), that.getOwnerName());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getBankAccountNumber());
        builder.append(this.getBankName());
        builder.append(this.getCountryCode());
        builder.append(this.getIban());
        builder.append(this.getBic());
        builder.append(this.getOwnerName());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("bankAccountNumber", this.getBankAccountNumber());
        builder.append("bankName", this.getBankName());
        builder.append("countryCode", this.getCountryCode());
        builder.append("iban", this.getIban());
        builder.append("bic", this.getBic());
        builder.append("ownerName", this.getOwnerName());
        return builder.build();
    }
}
