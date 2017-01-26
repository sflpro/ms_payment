package com.sfl.pms.services.payment.notification.event;

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
public class StartPaymentProviderNotificationRequestProcessingEvent implements ApplicationEvent {

    /* Properties */
    private final Long paymentProviderNotificationRequestId;

    /* Constructors */
    public StartPaymentProviderNotificationRequestProcessingEvent(final Long paymentProviderNotificationRequestId) {
        Assert.notNull(paymentProviderNotificationRequestId, "Payment provider notification request id should not be null");
        this.paymentProviderNotificationRequestId = paymentProviderNotificationRequestId;
    }

    /* Properties getters and setters */
    public Long getPaymentProviderNotificationRequestId() {
        return paymentProviderNotificationRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartPaymentProviderNotificationRequestProcessingEvent)) {
            return false;
        }
        final StartPaymentProviderNotificationRequestProcessingEvent that = (StartPaymentProviderNotificationRequestProcessingEvent) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getPaymentProviderNotificationRequestId(), that.getPaymentProviderNotificationRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getPaymentProviderNotificationRequestId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("paymentProviderNotificationRequestId", getPaymentProviderNotificationRequestId());
        return builder.build();
    }
}

