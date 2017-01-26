package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/6/15
 * Time: 4:57 PM
 */
public class PaymentResultAlreadyExistsForNotificationException extends ServicesRuntimeException {

    private static final long serialVersionUID = 5437996594904609246L;

    /* Properties */
    private final Long notificationId;

    private final Long existingPaymentResultId;

    /* Constructors */
    public PaymentResultAlreadyExistsForNotificationException(final Long notificationId, final Long existingPaymentResultId) {
        super("Payment result with id - " + existingPaymentResultId + " already exists for notification with id - " + notificationId);
        this.notificationId = notificationId;
        this.existingPaymentResultId = existingPaymentResultId;
    }

    /* Properties getters and setters */
    public Long getNotificationId() {
        return notificationId;
    }

    public Long getExistingPaymentResultId() {
        return existingPaymentResultId;
    }
}
