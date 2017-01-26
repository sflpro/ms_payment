package com.sfl.pms.services.payment.processing.dto.auth;

import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 3:39 PM
 */
public class CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto extends PaymentProcessingResultDetailedInformationDto {

    private static final long serialVersionUID = -3072814683151299374L;

    /* Properties */
    private final List<Long> customerPaymentMethodIds;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto(final Long paymentId, final List<Long> customerPaymentMethodIds) {
        super(paymentId);
        this.customerPaymentMethodIds = customerPaymentMethodIds;
    }

    /* Properties getters and setters */
    public List<Long> getCustomerPaymentMethodIds() {
        return customerPaymentMethodIds;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto)) {
            return false;
        }
        final CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto that = (CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getCustomerPaymentMethodIds(), that.getCustomerPaymentMethodIds());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getCustomerPaymentMethodIds());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("customerPaymentMethodIds", this.getCustomerPaymentMethodIds());
        return builder.build();
    }
}
