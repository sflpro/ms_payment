package com.sfl.pms.externalclients.payment.adyen.model.datatypes;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 2:41 PM
 */
public enum AdyenPaymentStatus {
    AUTHORISED("Authorised"),
    REFUSED("Refused"),
    CANCELLED("Cancelled"),
    PENDING("Pending"),
    RECEIVED("Received"),
    ERROR("Error"),;

    private String result;

    AdyenPaymentStatus(final String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    /* Utility methods */
    public static AdyenPaymentStatus fromString(final String text) {
        if (text != null) {
            for (AdyenPaymentStatus b : AdyenPaymentStatus.values()) {
                if (text.equalsIgnoreCase(b.result)) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
