package com.sfl.pms.services.payment.processing.dto.redirect;

import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 11:28 PM
 */
public class PaymentRedirectProcessingInformationDto extends PaymentProcessingResultDetailedInformationDto {

    private static final long serialVersionUID = -1686826921709894455L;

    /* Properties */
    private final String redirectUrl;

    /* Constructors */
    public PaymentRedirectProcessingInformationDto(final Long paymentId, final String redirectUrl) {
        super(paymentId);
        this.redirectUrl = redirectUrl;
    }

    /* Properties getters and setters */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentRedirectProcessingInformationDto)) {
            return false;
        }
        final PaymentRedirectProcessingInformationDto that = (PaymentRedirectProcessingInformationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRedirectUrl(), that.getRedirectUrl());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRedirectUrl());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("redirectUrl", this.getRedirectUrl());
        return builder.build();
    }
}
