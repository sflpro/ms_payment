package com.sfl.pms.services.payment.method.model.adyen;

import org.springframework.util.Assert;

import java.util.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/23/14
 * Time: 11:33 AM
 */
public enum AdyenPaymentMethodType {
    AMERICAN_EXPRESS(null, new String[]{"amex"}),
    MAESTRO(null, new String[]{"maestro"}),
    MASTER_CARD(null, new String[]{"mc", "bijcard"}),
    VISA(null, new String[]{"visa"}),
    MISTER_CASH(null, new String[]{"bcmc"}),
    DINERS_CLUB(null, new String[]{"diners"}),
    DISCOVER(null, new String[]{"discover"}),
    IDEAL("sepadirectdebit", new String[]{"ideal"}),
    SEPA_DIRECT_DEBIT(null, new String[]{"sepadirectdebit"});

    /* Static mapping of code with enum values */
    private static final Map<String, AdyenPaymentMethodType> CODE_TO_ENUM_MAPPING;

    static {
        final Map<String, AdyenPaymentMethodType> tempMapping = new HashMap<>();
        for (final AdyenPaymentMethodType paymentMethodType : AdyenPaymentMethodType.values()) {
            paymentMethodType.getCodes().forEach(currentCode -> {
                tempMapping.put(currentCode, paymentMethodType);
            });
        }
        // Publish map
        CODE_TO_ENUM_MAPPING = Collections.unmodifiableMap(tempMapping);
    }


    /* Properties */
    private final Set<String> codes;

    private final String recurringPaymentBrand;

    /* Constructor */
    AdyenPaymentMethodType(final String recurringPaymentBrand, final String[] codes) {
        this.codes = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(codes)));
        this.recurringPaymentBrand = recurringPaymentBrand;
    }

    /* Properties getters and setters */
    public Set<String> getCodes() {
        return codes;
    }

    public String getRecurringPaymentBrand() {
        return recurringPaymentBrand;
    }

    /* Utility methods */
    public static AdyenPaymentMethodType getAdyenPaymentMethodTypeForCode(final String code) {
        Assert.notNull(code, "Adyen payment method code should not be null");
        final AdyenPaymentMethodType paymentMethodType = CODE_TO_ENUM_MAPPING.get(code);
        if (paymentMethodType == null) {
            throw new IllegalArgumentException("Unknown Adyen payment method code - " + code);
        }
        return paymentMethodType;
    }
}
