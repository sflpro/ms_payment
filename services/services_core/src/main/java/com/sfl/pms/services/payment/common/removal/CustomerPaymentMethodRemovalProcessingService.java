package com.sfl.pms.services.payment.common.removal;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
public interface CustomerPaymentMethodRemovalProcessingService {

    /**
     * Process customer payment method removal request
     * <strong>payment method ID will be used in place of payment method removal request id for now<strong/>
     *
     * @param paymentMethodRemovalRequestId
     */
    void processCustomerPaymentMethodRemovalRequest(@Nonnull final Long paymentMethodRemovalRequestId);
}
