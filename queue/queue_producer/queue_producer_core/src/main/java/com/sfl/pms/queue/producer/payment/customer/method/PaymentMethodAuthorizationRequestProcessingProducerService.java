package com.sfl.pms.queue.producer.payment.customer.method;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 10:36 PM
 */
public interface PaymentMethodAuthorizationRequestProcessingProducerService {

    /**
     * Process payment method authorization request created event
     *
     * @param paymentMethodAuthorizationRequestId
     */
    void processPaymentMethodAuthorizationRequestCreatedEvent(@Nonnull final Long paymentMethodAuthorizationRequestId);
}
