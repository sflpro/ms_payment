package com.sfl.pms.services.system.concurrency.impl;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.system.concurrency.TaskExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/16/15
 * Time: 12:24 PM
 */
@Service
public class TaskExecutorServiceImpl implements TaskExecutorService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutorServiceImpl.class);

    /* Constants */
    private static final int MAX_THREADS_COUNT = 25;

    /* Dependencies */
    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    private ExecutorService executorService;

    /* Constructors */
    public TaskExecutorServiceImpl() {
        LOGGER.debug("Initializing task executor service");
    }

    @Override
    public void afterPropertiesSet() {
        // Initializing scheduled task executor service
        initializeExecutorService();
    }

    @Override
    public void executeTaskAsynchronously(@Nonnull final Runnable runnable, @Nonnull final boolean runInPersistenceContext) {
        Assert.notNull(runnable, "Runnable task should not be null");
        LOGGER.debug("Executing task, run in persistence context - {}", runInPersistenceContext);
        executorService.submit(new TaskExecutorDecorator(runnable, runInPersistenceContext));

    }

    /* Utility methods */
    private void initializeExecutorService() {
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS_COUNT);
    }

    /* Properties getters and setters */
    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    /* Inner classes */
    private class TaskExecutorDecorator implements Runnable {

        /* Properties */
        private final Runnable runnable;

        private final boolean runInPersistenceContext;

        public TaskExecutorDecorator(final Runnable runnable, final boolean runInPersistenceContext) {
            this.runnable = runnable;
            this.runInPersistenceContext = runInPersistenceContext;
        }

        @Override
        public void run() {
            if (runInPersistenceContext) {
                persistenceUtilityService.runInPersistenceSession(runnable);
            } else {
                runnable.run();
            }
        }
    }
}
