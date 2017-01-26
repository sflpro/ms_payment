package com.sfl.pms.services.payment.processing.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 3:28 PM
 */
public abstract class PaymentProcessingResultDetailedInformationDto implements Serializable {

    private static final long serialVersionUID = -9126430375702844925L;

    /* Properties */
    private final Long paymentId;

    /* Constructors */
    public PaymentProcessingResultDetailedInformationDto(final Long paymentId) {
        this.paymentId = paymentId;
    }

    /* Properties getters and setters */
    public Long getPaymentId() {
        return paymentId;
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
        final PaymentProcessingResultDetailedInformationDto that = (PaymentProcessingResultDetailedInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getPaymentId(), that.getPaymentId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getPaymentId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("paymentId", getPaymentId());
        return builder.build();
    }
}
