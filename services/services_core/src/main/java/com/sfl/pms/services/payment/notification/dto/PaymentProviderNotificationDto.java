package com.sfl.pms.services.payment.notification.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:19 PM
 */
public abstract class PaymentProviderNotificationDto<T extends PaymentProviderNotification> extends AbstractDomainEntityModelDto<T> {

    private static final long serialVersionUID = -5477098255162732468L;

    /* Properties */
    private final PaymentProviderType type;

    private String rawContent;

    private String clientIpAddress;

    /* Constructors */
    public PaymentProviderNotificationDto(final PaymentProviderType type, final String rawContent, final String clientIpAddress) {
        this(type);
        this.rawContent = rawContent;
        this.clientIpAddress = clientIpAddress;
    }

    public PaymentProviderNotificationDto(final PaymentProviderType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(final String rawContent) {
        this.rawContent = rawContent;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T notification) {
        Assert.notNull(notification, "Notification should not be null");
        notification.setClientIpAddress(getClientIpAddress());
        notification.setRawContent(getRawContent());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderNotificationDto)) {
            return false;
        }
        final PaymentProviderNotificationDto that = (PaymentProviderNotificationDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getRawContent(), that.getRawContent());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getClientIpAddress());
        builder.append(this.getRawContent());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("rawContent", this.getRawContent());
        return builder.build();
    }

}
