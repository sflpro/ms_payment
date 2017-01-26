package com.sfl.pms.services.payment.notification.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:37 AM
 */
public class PaymentProviderNotificationRequestDto extends AbstractDomainEntityModelDto<PaymentProviderNotificationRequest> {

    private static final long serialVersionUID = -5231046088567383820L;

    /* Properties */
    private PaymentProviderType providerType;

    private String rawContent;

    private String clientIpAddress;

    /* Constructors */
    public PaymentProviderNotificationRequestDto(final PaymentProviderType providerType, final String rawContent, final String clientIpAddress) {
        this.providerType = providerType;
        this.rawContent = rawContent;
        this.clientIpAddress = clientIpAddress;
    }

    public PaymentProviderNotificationRequestDto() {
    }

    /* Properties getters and setters */
    public PaymentProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(final PaymentProviderType providerType) {
        this.providerType = providerType;
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
    public void updateDomainEntityProperties(final PaymentProviderNotificationRequest request) {
        Assert.notNull(request, "Payment provider notification request should not be null");
        request.setClientIpAddress(getClientIpAddress());
        request.setProviderType(getProviderType());
        request.setRawContent(getRawContent());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderNotificationRequestDto)) {
            return false;
        }
        final PaymentProviderNotificationRequestDto that = (PaymentProviderNotificationRequestDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getProviderType(), that.getProviderType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getRawContent(), that.getRawContent());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getProviderType());
        builder.append(this.getClientIpAddress());
        builder.append(this.getRawContent());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("providerType", this.getProviderType());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("rawContent", this.getRawContent());
        return builder.build();
    }
}
