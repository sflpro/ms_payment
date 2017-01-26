package com.sfl.pms.queue.producer.payment.redirect;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 6:12 PM
 */
public interface PaymentProviderRedirectResultProcessingQueueProducerService {

    /**
     * Process payment provider redirect result
     *
     * @param redirectResultId
     */
    void processPaymentProviderRedirectResultProcessingEvent(@Nonnull final Long redirectResultId);
}
