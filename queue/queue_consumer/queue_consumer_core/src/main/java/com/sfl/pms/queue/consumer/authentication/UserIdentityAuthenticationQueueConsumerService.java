package com.sfl.pms.queue.consumer.authentication;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/18/15
 * Time: 6:40 PM
 */
public interface UserIdentityAuthenticationQueueConsumerService {

    /**
     * Start user identity authentication processing event
     *
     * @param userIdentityAuthenticationId
     */
    void processUserIdentityAuthenticationEvent(@Nonnull final Long userIdentityAuthenticationId);
}
