package com.sfl.pms.services.payment.customer.method.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
public class StartCustomerPaymentMethodRemovalRequestProcessingEvent implements ApplicationEvent {

    /* Properties */
    private final Long removalRequestId;

    /* Constructors */
    public StartCustomerPaymentMethodRemovalRequestProcessingEvent(final Long removalRequestId) {
        Assert.notNull(removalRequestId, "Authorization request should not be null");
        this.removalRequestId = removalRequestId;
    }

    /* Properties getters and setters */
    public Long getRemovalRequestId() {
        return removalRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartCustomerPaymentMethodRemovalRequestProcessingEvent)) {
            return false;
        }
        final StartCustomerPaymentMethodRemovalRequestProcessingEvent that = (StartCustomerPaymentMethodRemovalRequestProcessingEvent) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getRemovalRequestId(), that.getRemovalRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getRemovalRequestId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("removalRequestId", getRemovalRequestId());
        return builder.build();
    }
}
