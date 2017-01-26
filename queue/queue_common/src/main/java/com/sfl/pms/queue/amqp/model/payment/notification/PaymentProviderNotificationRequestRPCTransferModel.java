package com.sfl.pms.queue.amqp.model.payment.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/10/15
 * Time: 4:28 PM
 */
public class PaymentProviderNotificationRequestRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = -7442648754340921086L;

    /* Properties */
    @JsonProperty(value = "notificationRequestId", required = true)
    private Long notificationRequestId;

    /* Constructors */
    public PaymentProviderNotificationRequestRPCTransferModel(final Long notificationRequestId) {
        this.notificationRequestId = notificationRequestId;
    }

    public PaymentProviderNotificationRequestRPCTransferModel() {
    }

    /* Properties getters and setters */
    public Long getNotificationRequestId() {
        return notificationRequestId;
    }

    public void setNotificationRequestId(final Long notificationRequestId) {
        this.notificationRequestId = notificationRequestId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderNotificationRequestRPCTransferModel)) {
            return false;
        }
        final PaymentProviderNotificationRequestRPCTransferModel that = (PaymentProviderNotificationRequestRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getNotificationRequestId(), that.getNotificationRequestId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getNotificationRequestId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("notificationRequestId", getNotificationRequestId());
        return builder.build();
    }
}
