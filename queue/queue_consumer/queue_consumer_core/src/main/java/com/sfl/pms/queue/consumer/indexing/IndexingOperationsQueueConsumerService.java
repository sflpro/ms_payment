package com.sfl.pms.queue.consumer.indexing;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/12/14
 * Time: 10:52 PM
 */
public interface IndexingOperationsQueueConsumerService {

    /**
     * Creates or updates search index for car wash station for provided id
     *
     * @param carWashStationId
     * @param indexName
     */
    void createOrUpdateIndexEntryForCarWashStation(@Nonnull final Long carWashStationId, @Nonnull final String indexName);

    /**
     * Updates rewarded loyalty points for customer
     *
     * @param customerId
     * @param stationIds
     * @param indexName
     */
    void createOrUpdateRewardedLoyaltyPointsIndexEntryForCustomer(@Nonnull final Long customerId, @Nonnull Set<Long> stationIds, @Nonnull final String indexName);
}
