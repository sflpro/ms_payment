package com.sfl.pms.services.payment.notification.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 3:22 PM
 */
public class PaymentProviderNotificationAlreadyExistsForRequestException extends ServicesRuntimeException {

    private static final long serialVersionUID = -6256580690495381945L;

    /* Properties */
    private final Long notificationRequestId;

    private final Long existingNotificationId;

    /* Constructors */
    public PaymentProviderNotificationAlreadyExistsForRequestException(final Long notificationRequestId, final Long existingNotificationId) {
        super("Payment provider notification with id - " + existingNotificationId + " already exists for notification request with id  - " + notificationRequestId);
        this.notificationRequestId = notificationRequestId;
        this.existingNotificationId = existingNotificationId;
    }

    /* Properties getters and setters */
    public Long getNotificationRequestId() {
        return notificationRequestId;
    }

    public Long getExistingNotificationId() {
        return existingNotificationId;
    }
}
