package com.sfl.pms.queue.producer.payment.customer.method;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
public interface PaymentMethodRemovalRequestProcessingProducerService {

    /**
     * Process payment method removal request created event
     *
     * @param paymentMethodRemovalRequestId
     */
    void processPaymentMethodRemovalRequestCreatedEvent(@Nonnull final Long paymentMethodRemovalRequestId);
}
