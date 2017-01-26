package com.sfl.pms.services.payment.customer.method.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 10:30 PM
 */
public class StartCustomerPaymentMethodAuthorizationRequestProcessingEvent implements ApplicationEvent {

    /* Properties */
    private final Long authorizationRequestId;

    /* Constructors */
    public StartCustomerPaymentMethodAuthorizationRequestProcessingEvent(final Long authorizationRequestId) {
        Assert.notNull(authorizationRequestId, "Authorization request should not be null");
        this.authorizationRequestId = authorizationRequestId;
    }

    /* Properties getters and setters */
    public Long getAuthorizationRequestId() {
        return authorizationRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartCustomerPaymentMethodAuthorizationRequestProcessingEvent)) {
            return false;
        }
        final StartCustomerPaymentMethodAuthorizationRequestProcessingEvent that = (StartCustomerPaymentMethodAuthorizationRequestProcessingEvent) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getAuthorizationRequestId(), that.getAuthorizationRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getAuthorizationRequestId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("authorizationRequestId", getAuthorizationRequestId());
        return builder.build();
    }
}
