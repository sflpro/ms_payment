package com.sfl.pms.core.api.internal.model.notification.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractRequestModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import com.sfl.pms.core.api.internal.model.provider.PaymentProviderClientType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/18/16
 * Time: 3:54 PM
 */
public class CreatePaymentProviderNotificationRequest extends AbstractRequestModel {

    private static final long serialVersionUID = 8263071127817154681L;

    /* Properties */
    @JsonProperty("rawContent")
    private String rawContent;

    @JsonProperty("notificationsToken")
    private String notificationsToken;

    @JsonProperty("paymentProviderType")
    private PaymentProviderClientType paymentProviderType;

    @JsonProperty("clientIpAddress")
    private String clientIpAddress;

    /* Constructors */
    public CreatePaymentProviderNotificationRequest() {
    }

    /* Properties getters and setters */
    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(final String rawContent) {
        this.rawContent = rawContent;
    }

    public String getNotificationsToken() {
        return notificationsToken;
    }

    public void setNotificationsToken(final String notificationsToken) {
        this.notificationsToken = notificationsToken;
    }

    public PaymentProviderClientType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderClientType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    /* Utility methods */
    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (getPaymentProviderType() == null) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_NOTIFICATION_MISSING_PROVIDER_TYPE));
        }
        if (StringUtils.isBlank(getRawContent())) {
            errors.add(new ErrorResponseModel(ErrorType.PAYMENT_PROVIDER_NOTIFICATION_MISSING_RAW_CONTENT));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatePaymentProviderNotificationRequest)) {
            return false;
        }
        final CreatePaymentProviderNotificationRequest that = (CreatePaymentProviderNotificationRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRawContent(), that.getRawContent());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.getNotificationsToken(), that.getNotificationsToken());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRawContent());
        builder.append(this.getPaymentProviderType());
        builder.append(this.getClientIpAddress());
        builder.append(this.getNotificationsToken());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("rawContent", this.getRawContent());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("notificationsToken", this.getNotificationsToken());
        return builder.build();
    }
}
