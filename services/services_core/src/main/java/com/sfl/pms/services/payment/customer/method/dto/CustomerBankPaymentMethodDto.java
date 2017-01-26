package com.sfl.pms.services.payment.customer.method.dto;

import com.sfl.pms.services.country.model.CountryCode;
import com.sfl.pms.services.payment.customer.method.model.CustomerBankPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/3/15
 * Time: 4:30 PM
 */
public class CustomerBankPaymentMethodDto extends CustomerPaymentMethodDto<CustomerBankPaymentMethod> {

    private static final long serialVersionUID = -2274832876811548913L;

    /* Properties */
    private String bankAccountNumber;

    private String bankName;

    private CountryCode countryCode;

    private String iban;

    private String bic;

    private String ownerName;

    /* Constructors */
    public CustomerBankPaymentMethodDto(final PaymentMethodType paymentMethodType, final String bankAccountNumber, final String bankName, final CountryCode countryCode, final String iban, final String bic, final String ownerName) {
        super(CustomerPaymentMethodType.BANK, paymentMethodType);
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.countryCode = countryCode;
        this.iban = iban;
        this.bic = bic;
        this.ownerName = ownerName;
    }

    public CustomerBankPaymentMethodDto() {
        super(CustomerPaymentMethodType.BANK);
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

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final CustomerBankPaymentMethod paymentMethod) {
        super.updateDomainEntityProperties(paymentMethod);
        paymentMethod.setBankAccountNumber(getBankAccountNumber());
        paymentMethod.setCountryCode(getCountryCode());
        paymentMethod.setIban(getIban());
        paymentMethod.setBankName(getBankName());
        paymentMethod.setOwnerName(getOwnerName());
        paymentMethod.setBic(getBic());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerBankPaymentMethodDto)) {
            return false;
        }
        final CustomerBankPaymentMethodDto that = (CustomerBankPaymentMethodDto) o;
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
