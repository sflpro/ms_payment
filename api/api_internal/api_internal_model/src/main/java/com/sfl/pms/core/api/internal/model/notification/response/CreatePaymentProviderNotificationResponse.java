package com.sfl.pms.core.api.internal.model.notification.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/18/16
 * Time: 4:06 PM
 */
public class CreatePaymentProviderNotificationResponse extends AbstractResponseModel {

    private static final long serialVersionUID = 85509303571567016L;

    /* Properties */
    @JsonProperty("paymentProviderNotificationRequestUuId")
    private String paymentProviderNotificationRequestUuId;

    /* Constructors */
    public CreatePaymentProviderNotificationResponse() {
    }

    public CreatePaymentProviderNotificationResponse(final String paymentProviderNotificationRequestUuId) {
        this.paymentProviderNotificationRequestUuId = paymentProviderNotificationRequestUuId;
    }

    /* Properties getters and setters */
    public String getPaymentProviderNotificationRequestUuId() {
        return paymentProviderNotificationRequestUuId;
    }

    public void setPaymentProviderNotificationRequestUuId(final String paymentProviderNotificationRequestUuId) {
        this.paymentProviderNotificationRequestUuId = paymentProviderNotificationRequestUuId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatePaymentProviderNotificationResponse)) {
            return false;
        }
        final CreatePaymentProviderNotificationResponse that = (CreatePaymentProviderNotificationResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentProviderNotificationRequestUuId(), that.getPaymentProviderNotificationRequestUuId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentProviderNotificationRequestUuId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentProviderNotificationRequestUuId", this.getPaymentProviderNotificationRequestUuId());
        return builder.build();
    }
}
