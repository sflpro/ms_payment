package com.sfl.pms.services.order.external;

import com.sfl.pms.services.order.model.OrderState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/22/16
 * Time: 5:01 PM
 */
public interface OrderStateMutationExternalNotifierService {

    /**
     * Notifier external party about order state change
     *
     * @param orderUuId
     * @param orderState
     */
    void notifyOrderStateMutation(@Nonnull final String orderUuId, @Nonnull final OrderState orderState, @Nullable final String paymentUuid);
}
