package com.sfl.pms.services.payment.method.model;

import com.sfl.pms.services.payment.method.model.adyen.AdyenPaymentMethodType;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/23/14
 * Time: 10:48 AM
 */
public enum PaymentMethodType {
    AMERICAN_EXPRESS(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.AMERICAN_EXPRESS),
    MAESTRO(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.MAESTRO),
    MASTER_CARD(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.MASTER_CARD),
    VISA(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.VISA),
    MISTER_CASH(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.MISTER_CASH),
    DINERS_CLUB(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.DINERS_CLUB),
    DISCOVER(PaymentMethodGroupType.CARD, AdyenPaymentMethodType.DISCOVER),
    IDEAL(PaymentMethodGroupType.BANK_TRANSFER, AdyenPaymentMethodType.IDEAL),
    SEPA_DIRECT_DEBIT(PaymentMethodGroupType.BANK_TRANSFER, AdyenPaymentMethodType.SEPA_DIRECT_DEBIT);

    /* Static mapping of Adyen payment method type with payment method */
    private static final Map<AdyenPaymentMethodType, PaymentMethodType> ADYEN_TO_ENUM_MAPPING;

    static {
        final Map<AdyenPaymentMethodType, PaymentMethodType> tempMapping = new HashMap<>();
        // Loop through values and create mapping
        for (final PaymentMethodType paymentMethodType : PaymentMethodType.values()) {
            if (paymentMethodType.getAdyenPaymentMethod() != null) {
                tempMapping.put(paymentMethodType.getAdyenPaymentMethod(), paymentMethodType);
            }
        }
        // Publish map
        ADYEN_TO_ENUM_MAPPING = Collections.unmodifiableMap(tempMapping);
    }

    /* Properties */
    private final PaymentMethodGroupType group;

    private final AdyenPaymentMethodType adyenPaymentMethod;

    /* Constructor */
    PaymentMethodType(final PaymentMethodGroupType group, final AdyenPaymentMethodType adyenPaymentMethod) {
        this.group = group;
        this.adyenPaymentMethod = adyenPaymentMethod;
    }

    /* Properties getters and setters */
    public PaymentMethodGroupType getGroup() {
        return group;
    }

    public AdyenPaymentMethodType getAdyenPaymentMethod() {
        return adyenPaymentMethod;
    }

    /* Utility methods */
    public static PaymentMethodType getPaymentMethodTypeForAdyenPaymentMethod(final AdyenPaymentMethodType adyenPaymentMethodType) {
        Assert.notNull(adyenPaymentMethodType, "Adyen payment method type should not be null");
        final PaymentMethodType paymentMethodType = ADYEN_TO_ENUM_MAPPING.get(adyenPaymentMethodType);
        if (paymentMethodType == null) {
            throw new IllegalArgumentException("Unknown Adyen payment method type - " + adyenPaymentMethodType);
        }
        return paymentMethodType;
    }

}
