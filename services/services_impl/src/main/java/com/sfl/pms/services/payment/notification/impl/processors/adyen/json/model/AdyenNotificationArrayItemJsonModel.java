package com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 2:27 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdyenNotificationArrayItemJsonModel implements Serializable {

    private static final long serialVersionUID = -6484773414258762390L;

    /* Properties */
    @JsonProperty("NotificationRequestItem")
    private AdyenNotificationRequestItemJsonModel notificationRequestItem;

    /* Constructors */
    public AdyenNotificationArrayItemJsonModel() {
    }

    public AdyenNotificationArrayItemJsonModel(final AdyenNotificationRequestItemJsonModel notificationRequestItem) {
        this.notificationRequestItem = notificationRequestItem;
    }

    /* Properties getters and setters */
    public AdyenNotificationRequestItemJsonModel getNotificationRequestItem() {
        return notificationRequestItem;
    }

    public void setNotificationRequestItem(final AdyenNotificationRequestItemJsonModel notificationRequestItem) {
        this.notificationRequestItem = notificationRequestItem;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenNotificationArrayItemJsonModel)) {
            return false;
        }
        final AdyenNotificationArrayItemJsonModel that = (AdyenNotificationArrayItemJsonModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getNotificationRequestItem(), that.getNotificationRequestItem());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getNotificationRequestItem());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("notificationRequestItem", this.getNotificationRequestItem());
        return builder.build();
    }
}
