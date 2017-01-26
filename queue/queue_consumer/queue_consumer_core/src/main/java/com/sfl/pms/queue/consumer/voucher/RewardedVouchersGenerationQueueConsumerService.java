package com.sfl.pms.queue.consumer.voucher;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/16/15
 * Time: 1:26 AM
 */
public interface RewardedVouchersGenerationQueueConsumerService {

    /**
     * Processes rewarded vouchers generation request
     *
     * @param rewardedVouchersGenerationRequestId
     */
    void processRewardedVouchersGenerationRequest(@Nonnull final Long rewardedVouchersGenerationRequestId);
}
