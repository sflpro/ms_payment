package com.sfl.pms.externalclients.payment.adyen.model.datatypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/7/15
 * Time: 3:36 PM
 */
public enum AdyenPaymentNotificationEventType {
    AUTHORIZATION("AUTHORISATION"), CANCELLATION("CANCELLATION"), REFUND("REFUND"), CANCEL_OR_REFUND("CANCEL_OR_REFUND"), CAPTURE("CAPTURE"), REFUNDED_REVERSED("REFUNDED_REVERSED"), CAPTURE_FAILED("CAPTURE_FAILED"), REFUND_FAILED("REFUND_FAILED"), REQUEST_FOR_INFORMATION("REQUEST_FOR_INFORMATION"), NOTIFICATION_OF_CHARGEBACK("NOTIFICATION_OF_CHARGEBACK"), ADVICE_OF_DEBIT("ADVICE_OF_DEBIT"), CHARGEBACK("CHARGEBACK"), CHARGEBACK_REVERSED("CHARGEBACK_REVERSED"), REPORT_AVAILABLE("REPORT_AVAILABLE");

    /* Constants */
    private static final Map<String, AdyenPaymentNotificationEventType> CODE_TO_TYPE_MAPPING;

    static {
        final Map<String, AdyenPaymentNotificationEventType> tempMapping = new HashMap<>();
        for (final AdyenPaymentNotificationEventType eventType : AdyenPaymentNotificationEventType.values()) {
            tempMapping.put(eventType.getEventCode(), eventType);
        }
        // Publish map
        CODE_TO_TYPE_MAPPING = Collections.unmodifiableMap(tempMapping);
    }

    public static AdyenPaymentNotificationEventType getEventTypeForCode(final String eventCode) {
        return CODE_TO_TYPE_MAPPING.get(eventCode);
    }


    /* Properties */
    private final String eventCode;

    private AdyenPaymentNotificationEventType(final String eventCode) {
        this.eventCode = eventCode;
    }

    /* Properties getters and setters */
    public String getEventCode() {
        return eventCode;
    }
}