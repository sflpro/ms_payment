package com.sfl.pms.api.internal.facade.notification.exception;

import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 9/3/15
 * Time: 1:28 PM
 */
public class InvalidPaymentProviderNotificationsTokenException extends SecurityException {

    private static final long serialVersionUID = 6549223918269504348L;

    /* Properties */
    private final PaymentProviderType paymentProviderType;

    private final String providedNotificationsToken;

    private final String requiredNotificationsToken;

    /* Constructors */
    public InvalidPaymentProviderNotificationsTokenException(final PaymentProviderType paymentProviderType, final String providedNotificationsToken, final String requiredNotificationsToken) {
        super("Received invalid notifications token for payment provider - " + paymentProviderType + ", received token - " + providedNotificationsToken + ", expected token - " + requiredNotificationsToken);
        this.paymentProviderType = paymentProviderType;
        this.providedNotificationsToken = providedNotificationsToken;
        this.requiredNotificationsToken = requiredNotificationsToken;
    }

    /* Properties */
    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public String getProvidedNotificationsToken() {
        return providedNotificationsToken;
    }

    public String getRequiredNotificationsToken() {
        return requiredNotificationsToken;
    }
}
