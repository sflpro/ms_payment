package com.sfl.pms.services.payment.encryption.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/21/15
 * Time: 1:13 AM
 */
public class PaymentCardEncryptionInformationDto implements Serializable {
    private static final long serialVersionUID = 409407384152739850L;

    /* Properties */
    private String expiryMonth;

    private String expiryYear;

    private String number;

    private String holderName;

    private String cvc;

    /* Constructors */
    public PaymentCardEncryptionInformationDto() {
    }

    public PaymentCardEncryptionInformationDto(final String expiryMonth, final String expiryYear, final String number, final String holderName, final String cvc) {
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.number = number;
        this.holderName = holderName;
        this.cvc = cvc;
    }

    /* Properties getters and setters */
    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(final String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(final String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(final String holderName) {
        this.holderName = holderName;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(final String cvc) {
        this.cvc = cvc;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentCardEncryptionInformationDto)) {
            return false;
        }
        final PaymentCardEncryptionInformationDto that = (PaymentCardEncryptionInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getExpiryMonth(), that.getExpiryMonth());
        builder.append(getExpiryYear(), that.getExpiryYear());
        builder.append(getHolderName(), that.getHolderName());
        builder.append(getNumber(), that.getNumber());
        builder.append(getCvc(), that.getCvc());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getExpiryMonth());
        builder.append(getExpiryYear());
        builder.append(getHolderName());
        builder.append(getNumber());
        builder.append(getCvc());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("expiryMonth", getExpiryMonth());
        builder.append("expiryYear", getExpiryYear());
        builder.append("holderName", getHolderName());
        builder.append("number", "***");
        builder.append("cvc", "***");
        return builder.build();
    }
}
