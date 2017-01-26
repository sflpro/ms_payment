package com.sfl.pms.services.order.external;

import com.sfl.pms.services.order.model.OrderState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/22/16
 * Time: 6:11 PM
 */
public interface OrderStateMutationExternalNotifierEventProcessingService {

    /**
     * Processes order state updated event
     *
     * @param orderId
     * @param orderState
     */
    void processOrderStateUpdatedEvent(@Nonnull final Long orderId, @Nonnull final OrderState orderState, @Nullable final String paymentUuid);
}
