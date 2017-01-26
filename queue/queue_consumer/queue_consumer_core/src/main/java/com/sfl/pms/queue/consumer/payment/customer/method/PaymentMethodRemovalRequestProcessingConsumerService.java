package com.sfl.pms.queue.consumer.payment.customer.method;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:06 PM
 */
public interface PaymentMethodRemovalRequestProcessingConsumerService {

    /**
     * Process payment method removal request event
     *
     * @param paymentMethodRemovalRequestId
     */
    void processPaymentMethodRemovalRequest(@Nonnull final Long paymentMethodRemovalRequestId);
}
