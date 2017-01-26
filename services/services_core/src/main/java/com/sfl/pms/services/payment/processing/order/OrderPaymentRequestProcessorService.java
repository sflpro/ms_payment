package com.sfl.pms.services.payment.processing.order;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 3:18 PM
 */
public interface OrderPaymentRequestProcessorService {

    /**
     * Creates and processes order payment
     *
     * @param orderPaymentRequestId
     * @return orderId
     */
    @Nonnull
    Long processOrderPaymentRequest(@Nonnull final Long orderPaymentRequestId);
}
