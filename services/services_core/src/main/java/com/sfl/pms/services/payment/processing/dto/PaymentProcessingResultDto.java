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
public class PaymentProcessingResultDto implements Serializable {

    private static final long serialVersionUID = -9126430375702844925L;

    /* Properties */
    private final PaymentProcessingResultDetailedInformationDto informationDto;

    private final PaymentProcessingResultType resultType;

    /* Constructors */
    public PaymentProcessingResultDto(PaymentProcessingResultDetailedInformationDto informationDto, final PaymentProcessingResultType resultType) {
        this.informationDto = informationDto;
        this.resultType = resultType;
    }

    /* Properties getters and setters */

    public PaymentProcessingResultDetailedInformationDto getInformationDto() {
        return informationDto;
    }

    public PaymentProcessingResultType getResultType() {
        return resultType;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProcessingResultDto)) {
            return false;
        }
        final PaymentProcessingResultDto that = (PaymentProcessingResultDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getResultType(), that.getResultType());
        builder.append(getInformationDto(), that.getInformationDto());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getResultType());
        builder.append(getInformationDto());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("resultType", getResultType());
        builder.append("informationDto", getInformationDto());
        return builder.build();
    }
}
