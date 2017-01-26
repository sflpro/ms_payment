package com.sfl.pms.services.payment.common.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/6/15
 * Time: 4:44 PM
 */
public class InvalidNotificationForPaymentException extends ServicesRuntimeException {

    private static final long serialVersionUID = 2090626483638347157L;

    /* Properties */
    private final Long notificationId;

    private final Long paymentId;

    /* Constructors */
    public InvalidNotificationForPaymentException(final Long notificationId, final Long paymentId) {
        super("Notification with id - " + notificationId + " can not be used with payment with id - " + paymentId);
        this.notificationId = notificationId;
        this.paymentId = paymentId;
    }

    /* Properties getters and setters */
    public Long getNotificationId() {
        return notificationId;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}
