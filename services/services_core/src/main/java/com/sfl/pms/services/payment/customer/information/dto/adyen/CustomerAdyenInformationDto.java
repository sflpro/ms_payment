package com.sfl.pms.services.payment.customer.information.dto.adyen;

import com.sfl.pms.services.payment.customer.information.dto.CustomerPaymentProviderInformationDto;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:45 AM
 */
public class CustomerAdyenInformationDto extends CustomerPaymentProviderInformationDto<CustomerAdyenInformation> {
    private static final long serialVersionUID = -1774850904890881697L;

    /* Properties */
    private String shopperReference;

    private String shopperEmail;


    /* Constructors */
    public CustomerAdyenInformationDto(final String shopperReference, final String shopperEmail) {
        this();
        this.shopperReference = shopperReference;
        this.shopperEmail = shopperEmail;
    }

    public CustomerAdyenInformationDto() {
        super(PaymentProviderType.ADYEN);
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

    @Override
    public void updateDomainEntityProperties(final CustomerAdyenInformation information) {
        super.updateDomainEntityProperties(information);
        information.setShopperEmail(getShopperEmail());
        information.setShopperReference(getShopperReference());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerAdyenInformationDto)) {
            return false;
        }
        final CustomerAdyenInformationDto that = (CustomerAdyenInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getShopperEmail(), that.getShopperEmail());
        builder.append(getShopperReference(), that.getShopperReference());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getShopperEmail());
        builder.append(getShopperReference());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("shopperEmail", getShopperEmail());
        builder.append("shopperReference", getShopperReference());
        return builder.build();
    }
}
