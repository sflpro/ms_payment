package com.sfl.pms.services.payment.customer.method.dto.adyen;

import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 12:04 PM
 */
public class CustomerPaymentMethodAdyenInformationDto extends CustomerPaymentMethodProviderInformationDto<CustomerPaymentMethodAdyenInformation> {
    private static final long serialVersionUID = -6149209456864565669L;
    /* Properties */
    private String recurringDetailReference;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformationDto() {
        super(PaymentProviderType.ADYEN);
    }

    public CustomerPaymentMethodAdyenInformationDto(final String recurringDetailReference) {
        this();
        this.recurringDetailReference = recurringDetailReference;
    }

    /* Properties getters and setters */
    public String getRecurringDetailReference() {
        return recurringDetailReference;
    }

    public void setRecurringDetailReference(final String recurringDetailReference) {
        this.recurringDetailReference = recurringDetailReference;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final CustomerPaymentMethodAdyenInformation providerInformation) {
        super.updateDomainEntityProperties(providerInformation);
        providerInformation.setRecurringDetailReference(getRecurringDetailReference());
    }

    @Override
    public String getPaymentProviderIdentifierForPaymentMethod() {
        return recurringDetailReference;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAdyenInformationDto)) {
            return false;
        }
        final CustomerPaymentMethodAdyenInformationDto that = (CustomerPaymentMethodAdyenInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRecurringDetailReference(), that.getRecurringDetailReference());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRecurringDetailReference());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("recurringDetailReference", this.getRecurringDetailReference());
        return builder.build();
    }
}
