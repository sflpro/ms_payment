package com.sfl.pms.services.payment.customer.information.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:39 AM
 */
public abstract class CustomerPaymentProviderInformationDto<T extends CustomerPaymentProviderInformation> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = 6626871208375077316L;

    /* Properties */
    private final PaymentProviderType type;

    /* Constructors */
    public CustomerPaymentProviderInformationDto(final PaymentProviderType paymentProviderType) {
        this.type = paymentProviderType;
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T information) {
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentProviderInformationDto)) {
            return false;
        }
        final CustomerPaymentProviderInformationDto that = (CustomerPaymentProviderInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getType(), that.getType());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getType());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("type", getType());
        return builder.build();
    }
}
