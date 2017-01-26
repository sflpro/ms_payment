package com.sfl.pms.services.payment.redirect.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/10/15
 * Time: 4:28 PM
 */
public class StartPaymentProviderRedirectResultProcessingEvent implements ApplicationEvent {

    /* Properties */
    private final Long paymentProviderRedirectResultId;

    /* Constructors */
    public StartPaymentProviderRedirectResultProcessingEvent(final Long paymentProviderRedirectResultId) {
        Assert.notNull(paymentProviderRedirectResultId, "Payment provider notification request id should not be null");
        this.paymentProviderRedirectResultId = paymentProviderRedirectResultId;
    }

    /* Properties getters and setters */
    public Long getPaymentProviderRedirectResultId() {
        return paymentProviderRedirectResultId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartPaymentProviderRedirectResultProcessingEvent)) {
            return false;
        }
        final StartPaymentProviderRedirectResultProcessingEvent that = (StartPaymentProviderRedirectResultProcessingEvent) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getPaymentProviderRedirectResultId(), that.getPaymentProviderRedirectResultId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getPaymentProviderRedirectResultId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("paymentProviderRedirectResultId", getPaymentProviderRedirectResultId());
        return builder.build();
    }
}

