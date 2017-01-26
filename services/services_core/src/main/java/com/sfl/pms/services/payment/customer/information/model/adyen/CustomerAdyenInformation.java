package com.sfl.pms.services.payment.customer.information.model.adyen;

import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/23/14
 * Time: 11:08 AM
 */
@Entity
@DiscriminatorValue(value = "ADYEN")
@Table(name = "payment_customer_adyen_information")
public class CustomerAdyenInformation extends CustomerPaymentProviderInformation {
    private static final long serialVersionUID = -3750044640633206415L;

    /* Properties */
    @Column(name = "shopper_reference", nullable = false, unique = false)
    private String shopperReference;

    @Column(name = "shopper_email", nullable = false, unique = false)
    private String shopperEmail;

    /* Constructors */
    public CustomerAdyenInformation() {
        setType(PaymentProviderType.ADYEN);
    }

    /* Properties getters and setters */
    public String getShopperReference() {
        return shopperReference;
    }

    public void setShopperReference(final String shopperReference) {
        this.shopperReference = shopperReference;
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(final String shopperEmail) {
        this.shopperEmail = shopperEmail;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerAdyenInformation)) {
            return false;
        }
        final CustomerAdyenInformation that = (CustomerAdyenInformation) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getShopperReference(), that.getShopperReference());
        builder.append(this.getShopperEmail(), that.getShopperEmail());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getShopperReference());
        builder.append(this.getShopperEmail());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("shopperReference", this.getShopperReference());
        builder.append("shopperEmail", this.getShopperEmail());
        return builder.build();
    }
}
