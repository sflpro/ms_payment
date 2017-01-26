package com.sfl.pms.services.payment.common.model;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 5:51 PM
 */
public enum PaymentResultStatus {
    PAID(true, false, PaymentState.PAID), REFUSED(false, false, PaymentState.REFUSED), FAILED(false, false, PaymentState.FAILED), PENDING(true, true, PaymentState.PENDING), CANCELLED(false, false, PaymentState.CANCELLED), ERROR(false, false, PaymentState.FAILED), RECEIVED(true, true, PaymentState.RECEIVED);

    private final PaymentState paymentState;

    /* Constructors */
    private PaymentResultStatus(final boolean interpretAsPaid, final boolean isTemporaryState, final PaymentState paymentState) {
        this.paymentState = paymentState;
    }

    /* Properties getters and setters */

    public PaymentState getPaymentState() {
        return paymentState;
    }

}
