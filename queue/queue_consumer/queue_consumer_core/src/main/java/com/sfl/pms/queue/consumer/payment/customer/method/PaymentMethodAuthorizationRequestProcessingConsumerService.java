package com.sfl.pms.queue.consumer.payment.customer.method;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 11:10 PM
 */
public interface PaymentMethodAuthorizationRequestProcessingConsumerService {

    /**
     * Process payment method authorization request created event
     *
     * @param paymentMethodAuthorizationRequestId
     */
    void processPaymentMethodAuthorizationRequest(@Nonnull final Long paymentMethodAuthorizationRequestId);
}
