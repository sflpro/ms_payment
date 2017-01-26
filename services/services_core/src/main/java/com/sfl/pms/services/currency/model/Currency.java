package com.sfl.pms.services.currency.model;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 5:29 PM
 */
public enum Currency {
    EUR("EUR"), USD("USD");

    /* Properties */
    private final String code;

    private Currency(final String code) {
        this.code = code;
    }

    /* Properties getters and setters */
    public String getCode() {
        return code;
    }
}
