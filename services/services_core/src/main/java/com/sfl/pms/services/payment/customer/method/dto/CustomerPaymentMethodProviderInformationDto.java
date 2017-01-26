package com.sfl.pms.services.payment.customer.method.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 11:59 AM
 */
public abstract class CustomerPaymentMethodProviderInformationDto<T extends CustomerPaymentMethodProviderInformation> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = 7879323506625226921L;

    /* Properties */
    private PaymentProviderType type;

    /* Constructors */
    public CustomerPaymentMethodProviderInformationDto() {
    }

    public CustomerPaymentMethodProviderInformationDto(final PaymentProviderType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    protected void setType(final PaymentProviderType type) {
        this.type = type;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T providerInformation) {

    }

    /* Abstract methods */
    public abstract String getPaymentProviderIdentifierForPaymentMethod();

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodProviderInformationDto)) {
            return false;
        }
        final CustomerPaymentMethodProviderInformationDto that = (CustomerPaymentMethodProviderInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getType(), that.getType());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getType());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", getType());
        return builder.build();
    }
}
