package com.sfl.pms.core.api.internal.model.payment;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/20/16
 * Time: 11:25 AM
 */
public enum PaymentStateClientType {
    CREATED, STARTED_PROCESSING, GENERATED_REDIRECT_URL, FAILED, REFUSED, PAID, PENDING, CANCELLED, RECEIVED;
}
