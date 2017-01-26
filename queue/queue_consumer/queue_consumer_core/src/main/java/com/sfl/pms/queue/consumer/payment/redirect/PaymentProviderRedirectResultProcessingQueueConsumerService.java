package com.sfl.pms.queue.consumer.payment.redirect;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 7:34 PM
 */
public interface PaymentProviderRedirectResultProcessingQueueConsumerService {

    /**
     * Process payment provider redirect result
     *
     * @param redirectResultId
     */
    void processPaymentProviderRedirectResult(@Nonnull final Long redirectResultId);
}
