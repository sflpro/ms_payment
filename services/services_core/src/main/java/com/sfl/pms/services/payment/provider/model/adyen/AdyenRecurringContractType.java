package com.sfl.pms.services.payment.provider.model.adyen;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/2/15
 * Time: 4:11 PM
 */
public enum AdyenRecurringContractType {
    RECURRING("RECURRING"), ONE_CLICK("ONECLICK"), NONE(null);

    /* Properties */
    private final String code;

    private AdyenRecurringContractType(final String code) {
        this.code = code;
    }

    /* Properties getters and setters */
    public String getCode() {
        return code;
    }
}
