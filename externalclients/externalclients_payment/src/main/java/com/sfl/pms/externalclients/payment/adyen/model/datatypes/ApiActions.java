package com.sfl.pms.externalclients.payment.adyen.model.datatypes;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 6:38 PM
 */
public enum ApiActions {
    /* Payment */
    PAYMENT_AUTHORIZE("Payment.authorise"),
    /* Recurring */
    RECURRING_LIST_DETAILS("Recurring.listRecurringDetails"),
    RECURRING_DISABLE("Recurring.disable");

    private String actionName;

    ApiActions(final String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }
}
