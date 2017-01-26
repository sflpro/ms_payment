package com.sfl.pms.services.payment.notification.model;

import com.sfl.pms.services.common.model.AbstractDomainUuIdAwareEntityModel;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:19 AM
 */
@Entity
@Table(name = "payment_provider_notification_request")
public class PaymentProviderNotificationRequest extends AbstractDomainUuIdAwareEntityModel {

    private static final long serialVersionUID = 3159582836910011003L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private PaymentProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PaymentProviderNotificationRequestState state;

    @Column(name = "raw_content", nullable = false, length = 5000)
    private String rawContent;

    @Column(name = "client_ip_address", nullable = true)
    private String clientIpAddress;

    /* Constructors */
    public PaymentProviderNotificationRequest() {
        initializeDefaults();
    }

    public PaymentProviderNotificationRequest(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
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

    public PaymentProviderNotificationRequestState getState() {
        return state;
    }

    public void setState(final PaymentProviderNotificationRequestState state) {
        this.state = state;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        this.state = PaymentProviderNotificationRequestState.CREATED;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderNotificationRequest)) {
            return false;
        }
        final PaymentProviderNotificationRequest that = (PaymentProviderNotificationRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getProviderType(), that.getProviderType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getRawContent(), that.getRawContent());
        builder.append(this.getState(), that.getState());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getProviderType());
        builder.append(this.getClientIpAddress());
        builder.append(this.getRawContent());
        builder.append(this.getState());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("providerType", this.getProviderType());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("rawContent", this.getRawContent());
        builder.append("state", this.getState());
        return builder.build();
    }
}
