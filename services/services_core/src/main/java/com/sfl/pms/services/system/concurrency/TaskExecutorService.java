package com.sfl.pms.services.system.concurrency;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/16/15
 * Time: 12:24 PM
 */
public interface TaskExecutorService {

    /**
     * Execute task after asynchronously
     *
     * @param runnable
     * @param runInPersistenceContext
     */
    void executeTaskAsynchronously(@Nonnull final Runnable runnable, @Nonnull final boolean runInPersistenceContext);
}
