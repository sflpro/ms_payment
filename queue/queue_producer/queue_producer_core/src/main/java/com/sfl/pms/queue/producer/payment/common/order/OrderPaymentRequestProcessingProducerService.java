package com.sfl.pms.queue.producer.payment.common.order;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 4:22 PM
 */
public interface OrderPaymentRequestProcessingProducerService {

    /**
     * Process order payment request
     *
     * @param orderPaymentRequestId
     */
    void processOrderPaymentRequestProcessingEvent(@Nonnull final Long orderPaymentRequestId);
}
