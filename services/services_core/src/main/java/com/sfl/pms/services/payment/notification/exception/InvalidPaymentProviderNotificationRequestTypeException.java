package com.sfl.pms.services.payment.notification.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 3:30 PM
 */
public class InvalidPaymentProviderNotificationRequestTypeException extends ServicesRuntimeException {

    private static final long serialVersionUID = 8167807158855043433L;

    /* Properties */
    private final Long notificationRequestId;

    private final PaymentProviderType notificationRequestProviderType;

    private final PaymentProviderType requiredProviderType;

    /* Constructors */
    public InvalidPaymentProviderNotificationRequestTypeException(final Long notificationRequestId, final PaymentProviderType notificationRequestProviderType, final PaymentProviderType requiredProviderType) {
        super("Payment provider notification request with id - " + notificationRequestId + " has provider type of - " + notificationRequestProviderType + ", required provider type - " + requiredProviderType);
        this.notificationRequestId = notificationRequestId;
        this.notificationRequestProviderType = notificationRequestProviderType;
        this.requiredProviderType = requiredProviderType;
    }

    /* Properties getters and setters */
    public Long getNotificationRequestId() {
        return notificationRequestId;
    }

    public PaymentProviderType getNotificationRequestProviderType() {
        return notificationRequestProviderType;
    }

    public PaymentProviderType getRequiredProviderType() {
        return requiredProviderType;
    }
}
