package com.sfl.pms.services.payment.processing.dto.order;

import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 3:32 PM
 */
public class OrderPaymentProcessingResultDetailedInformationDto extends PaymentProcessingResultDetailedInformationDto {

    private static final long serialVersionUID = -6065188026883182200L;

    /* Constructors */
    public OrderPaymentProcessingResultDetailedInformationDto(final Long paymentId) {
        super(paymentId);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProcessingResultDetailedInformationDto)) {
            return false;
        }
        final OrderPaymentProcessingResultDetailedInformationDto that = (OrderPaymentProcessingResultDetailedInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        return builder.build();
    }
}
