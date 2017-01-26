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
 * Time: 1:31 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdyenNotificationAmountJsonModel implements Serializable {
    private static final long serialVersionUID = 4606602898655124006L;

    /* Properties */
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("value")
    private Integer value;

    /* Constructors */
    public AdyenNotificationAmountJsonModel() {
    }

    /* Properties getters and setters */
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(final Integer value) {
        this.value = value;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenNotificationAmountJsonModel)) {
            return false;
        }
        final AdyenNotificationAmountJsonModel that = (AdyenNotificationAmountJsonModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getCurrency(), that.getCurrency());
        builder.append(this.getValue(), that.getValue());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getCurrency());
        builder.append(this.getValue());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("currency", this.getCurrency());
        builder.append("value", this.getValue());
        return builder.build();
    }
}
