package com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 12:46 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdyenNotificationJsonModel implements Serializable {

    private static final long serialVersionUID = 1414412748146305190L;

    /* Properties */
    @JsonProperty("live")
    private Boolean live;

    @JsonProperty("notificationItems")
    private List<AdyenNotificationArrayItemJsonModel> notificationItems;

    /* Constructors */
    public AdyenNotificationJsonModel() {
        this.notificationItems = new ArrayList<>();
    }

    /* Properties getters and setters */
    public Boolean getLive() {
        return live;
    }

    public void setLive(final Boolean live) {
        this.live = live;
    }

    public List<AdyenNotificationArrayItemJsonModel> getNotificationItems() {
        return notificationItems;
    }

    public void setNotificationItems(final List<AdyenNotificationArrayItemJsonModel> notificationItems) {
        this.notificationItems = notificationItems;
    }

    /* utility methods */
    public AdyenNotificationRequestItemJsonModel getFirstAdyenNotificationRequestItemJsonModel() {
        if (notificationItems.size() > 0) {
            return notificationItems.get(0).getNotificationRequestItem();
        }
        return null;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenNotificationJsonModel)) {
            return false;
        }
        final AdyenNotificationJsonModel that = (AdyenNotificationJsonModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getLive(), that.getLive());
        builder.append(this.getNotificationItems(), that.getNotificationItems());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getLive());
        builder.append(this.getNotificationItems());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("live", this.getLive());
        builder.append("notificationItems", this.getNotificationItems());
        return builder.build();
    }
}
