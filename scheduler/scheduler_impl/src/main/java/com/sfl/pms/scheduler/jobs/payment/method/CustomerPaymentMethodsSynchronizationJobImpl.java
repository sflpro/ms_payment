package com.sfl.pms.scheduler.jobs.payment.method;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentSearchParameters;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodsSynchronizationService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.system.concurrency.impl.SynchronExecutorService;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.apache.commons.lang.mutable.MutableLong;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.mutable.MutableInt;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/6/15
 * Time: 1:32 PM
 */
@Component(value = "customerPaymentMethodsSynchronizationJob")
public class CustomerPaymentMethodsSynchronizationJobImpl implements CustomerPaymentMethodsSynchronizationJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodsSynchronizationJobImpl.class);

    /* Constants */
    private static final int CUSTOMERS_BATCH_SIZE = 500;

    private static final int CUSTOMERS_GROUP_SIZE = 10;

    private static final int SYNCHRONIZATION_THREAD_POOL_SIZE = 10;

    private static final long MAXIMUM_SECONDS_TO_WAIT_FOR_EXECUTOR_SHUTDOWN_IN_SECONDS = 2 * 60;

    private static final int PAYMENTS_AGE_IN_DAYS_TARGET_FOR_PROCESSING = 10;

    /* Dependencies */

    /* Configuration */
    @Value("#{appProperties['jobs.payment.methods.synchronization.concurrent.enabled']}")
    private boolean concurrentProcessingEnabled;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    @Autowired
    private CustomerPaymentMethodsSynchronizationService customerPaymentMethodsSynchronizationService;

    @Autowired
    private PaymentService paymentService;

    /* Constructors */
    public CustomerPaymentMethodsSynchronizationJobImpl() {
        LOGGER.debug("Initializing customer payment methods synchronization job");
        this.concurrentProcessingEnabled = true;
    }

    @Override
    public void synchronizeAdyenCustomerPaymentMethods() {
        // Synchronize Adyen payment methods
        synchronizeCustomerPaymentMethods(PaymentProviderType.ADYEN, PAYMENTS_AGE_IN_DAYS_TARGET_FOR_PROCESSING);
    }

    /* Utility methods */
    public void synchronizeCustomerPaymentMethods(final PaymentProviderType paymentProviderType, final int processPaymentsCreatedDaysAgo) {
        LOGGER.debug("Starting customers payment methods synchronization job, payment provider type - {}, orders age in days - {}", paymentProviderType, processPaymentsCreatedDaysAgo);
        // Create executor service
        final ExecutorService executorService = initializeExecutorService(SYNCHRONIZATION_THREAD_POOL_SIZE);
        try {
            final CustomersSynchronizationAggregatedCounts aggregatedCounts = synchronizeCustomerPaymentMethods(executorService, paymentProviderType, processPaymentsCreatedDaysAgo);
            LOGGER.debug("Customers payment methods synchronization result is - {}", aggregatedCounts);
        } finally {
            try {
                executorService.shutdown();
                executorService.awaitTermination(MAXIMUM_SECONDS_TO_WAIT_FOR_EXECUTOR_SHUTDOWN_IN_SECONDS, TimeUnit.SECONDS);
            } catch (final Exception ex) {
                LOGGER.error("Error occurred during thread pool termination", ex);
            }
        }
    }

    protected CustomersSynchronizationAggregatedCounts synchronizeCustomerPaymentMethods(final ExecutorService executorService, final PaymentProviderType paymentProviderType, final int processPaymentsCreatedDaysAgo) {
        // Grab total count of customers
        final PaymentSearchParameters parameters = createPaymentSearchParameters(paymentProviderType, processPaymentsCreatedDaysAgo);
        final Long totalCustomersCount = paymentService.getCustomersCountForPaymentSearchParameters(parameters);
        LOGGER.debug("Total of - {} customers are found target for payment methods synchronization", totalCustomersCount);
        final MutableLong startFrom = new MutableLong(0);
        final MutableInt batchesCount = new MutableInt(0);
        final CustomersSynchronizationAggregatedCounts aggregatedCounts = new CustomersSynchronizationAggregatedCounts(totalCustomersCount);
        // Load batch of customers
        List<Long> customersBatch = getCustomersBatchWithPersistenceSession(parameters, startFrom.longValue());
        while (customersBatch.size() != 0) {
            // Divide customers batch into groups and start synchronization
            divideCustomersBatchIntoGroupsAndStartSynchronization(customersBatch, paymentProviderType, aggregatedCounts, executorService);
            // Increment batches count
            batchesCount.increment();
            startFrom.setValue(batchesCount.intValue() * CUSTOMERS_BATCH_SIZE);
            if (startFrom.longValue() > totalCustomersCount) {
                break;
            }
            customersBatch = getCustomersBatchWithPersistenceSession(parameters, startFrom.longValue());
        }
        return aggregatedCounts;
    }

    private PaymentSearchParameters createPaymentSearchParameters(final PaymentProviderType paymentProviderType, final int processPaymentsCreatedDaysAgo) {
        final DateTime currentMidNightDate = new DateTime().withTimeAtStartOfDay();
        final PaymentSearchParameters parameters = new PaymentSearchParameters();
        parameters.setStorePaymentMethod(Boolean.TRUE);
        parameters.setPaymentState(PaymentState.PAID);
        parameters.setPaymentProviderType(paymentProviderType);
        parameters.setCreatedAfterDate(currentMidNightDate.minusDays(processPaymentsCreatedDaysAgo).toDate());
        return parameters;
    }

    private List<Long> getCustomersBatchWithPersistenceSession(final PaymentSearchParameters searchParameters, final long startFrom) {
        final MutableHolder<List<Long>> mutableHolder = new MutableHolder<>(null);
        persistenceUtilityService.runInPersistenceSession(() -> {
            final List<Long> customerIds = paymentService.getCustomersForPaymentSearchParameters(searchParameters, startFrom, CUSTOMERS_BATCH_SIZE);
            mutableHolder.setValue(customerIds);
        });
        return mutableHolder.getValue();
    }

    private void divideCustomersBatchIntoGroupsAndStartSynchronization(final List<Long> customersBatch, final PaymentProviderType paymentProviderType, final CustomersSynchronizationAggregatedCounts aggregatedCounts, final ExecutorService executorService) {
        final List<Future<CustomersSynchronizationBatchResult>> submittedFutures = new ArrayList<>();
        // Divide customer ids into groups
        List<Long> customersGroup = new ArrayList<>();
        for (final Long customerId : customersBatch) {
            customersGroup.add(customerId);
            if (customersGroup.size() >= CUSTOMERS_GROUP_SIZE) {
                // Create new synchronization runner
                final CustomerSynchronizationCommand customerSynchronizationCommand = new CustomerSynchronizationCommand(customersGroup, paymentProviderType);
                final Future<CustomersSynchronizationBatchResult> customersGroupFuture = executorService.submit(customerSynchronizationCommand);
                submittedFutures.add(customersGroupFuture);
                // Create new list for group
                customersGroup = new ArrayList<>();
            }
        }
        // Check if list is not empty the submit one more future
        if (customersGroup.size() != 0) {
            final CustomerSynchronizationCommand customerSynchronizationCommand = new CustomerSynchronizationCommand(customersGroup, paymentProviderType);
            final Future<CustomersSynchronizationBatchResult> stationsGroupFuture = executorService.submit(customerSynchronizationCommand);
            submittedFutures.add(stationsGroupFuture);
        }
        // Wait for submitted futures to finalize
        for (final Future<CustomersSynchronizationBatchResult> future : submittedFutures) {
            try {
                final CustomersSynchronizationBatchResult customersSynchronizationBatchResult = future.get();
                aggregatedCounts.addSuccessfulCustomersCount(customersSynchronizationBatchResult.getSuccessfulCustomerIds().size());
                aggregatedCounts.addFailedCustomersCount(customersSynchronizationBatchResult.getFailedCustomerIds().size());
            } catch (final Exception ex) {
                LOGGER.error("Error occurred while waiting for customers group future to finalize.", ex);
            }
        }
    }

    private ExecutorService initializeExecutorService(final int threadPoolSize) {
        LOGGER.debug("Initializing fixed thread pool, thread pool size - {}", threadPoolSize);
        final ExecutorService executorService;
        if (concurrentProcessingEnabled) {
            executorService = Executors.newFixedThreadPool(threadPoolSize);
        } else {
            executorService = SynchronExecutorService.createExecutorService();
        }
        return executorService;
    }

    /* Properties getters and setters */

    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }

    public boolean isConcurrentProcessingEnabled() {
        return concurrentProcessingEnabled;
    }

    public void setConcurrentProcessingEnabled(final boolean concurrentProcessingEnabled) {
        this.concurrentProcessingEnabled = concurrentProcessingEnabled;
    }

    public CustomerPaymentMethodsSynchronizationService getCustomerPaymentMethodsSynchronizationService() {
        return customerPaymentMethodsSynchronizationService;
    }

    public void setCustomerPaymentMethodsSynchronizationService(final CustomerPaymentMethodsSynchronizationService customerPaymentMethodsSynchronizationService) {
        this.customerPaymentMethodsSynchronizationService = customerPaymentMethodsSynchronizationService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /* Inner classes */
    private class CustomerSynchronizationCommand implements Callable<CustomersSynchronizationBatchResult> {

        private final List<Long> customersIds;

        private final PaymentProviderType paymentProviderType;

        public CustomerSynchronizationCommand(final List<Long> customersIds, final PaymentProviderType paymentProviderType) {
            this.customersIds = customersIds;
            this.paymentProviderType = paymentProviderType;
        }

        @Override
        public CustomersSynchronizationBatchResult call() {
            // Create synchronization aggregated result
            final CustomersSynchronizationBatchResult result = new CustomersSynchronizationBatchResult();
            persistenceUtilityService.runInPersistenceSession(() -> {
                for (final Long customerId : customersIds) {
                    try {
                        LOGGER.debug("Starting customer payment methods synchronization for customer with id - {}, payment provider type - {}", customerId, paymentProviderType);
                        final CustomerPaymentMethodsSynchronizationResult synchronizationResult = customerPaymentMethodsSynchronizationService.synchronizeCustomerPaymentMethods(customerId, paymentProviderType);
                        LOGGER.debug("Successfully completed customer payment methods synchronization for customer with id - {}, payment provider type - {}, synchronization result - {}", customerId, paymentProviderType, synchronizationResult);
                        result.addSuccessfulCustomerId(customerId);
                    } catch (final Exception ex) {
                        LOGGER.error("Error occurred while synchronizing payment methods for customer with id - {}, payment provider type - {}", customerId, paymentProviderType, ex);
                        result.addFailedCustomerId(customerId);
                    }
                }
            });
            return result;
        }
    }

    private static class CustomersSynchronizationBatchResult {

        /* Properties */
        private final List<Long> successfulCustomerIds;

        private final List<Long> failedCustomerIds;

        public CustomersSynchronizationBatchResult() {
            this.successfulCustomerIds = new ArrayList<>();
            this.failedCustomerIds = new ArrayList<>();
        }

        public void addSuccessfulCustomerId(final Long customerId) {
            this.successfulCustomerIds.add(customerId);
        }

        public void addFailedCustomerId(final Long customerId) {
            this.failedCustomerIds.add(customerId);
        }

        public List<Long> getSuccessfulCustomerIds() {
            return successfulCustomerIds;
        }

        public List<Long> getFailedCustomerIds() {
            return failedCustomerIds;
        }
    }

    protected static class CustomersSynchronizationAggregatedCounts {

        private final MutableLong successfulCustomersCount;

        private final MutableLong failedCustomersCount;

        private final long expectedTotalCount;

        public CustomersSynchronizationAggregatedCounts(final long expectedTotalCount) {
            this.successfulCustomersCount = new MutableLong(0);
            this.failedCustomersCount = new MutableLong(0);
            this.expectedTotalCount = expectedTotalCount;
        }

        public MutableLong getSuccessfulCustomersCount() {
            return successfulCustomersCount;
        }

        public MutableLong getFailedCustomersCount() {
            return failedCustomersCount;
        }

        public void addSuccessfulCustomersCount(final int count) {
            successfulCustomersCount.add(count);
        }

        public void addFailedCustomersCount(final int count) {
            failedCustomersCount.add(count);
        }

        public long getExpectedTotalCount() {
            return expectedTotalCount;
        }

        @Override
        public String toString() {
            final ToStringBuilder builder = new ToStringBuilder(this);
            builder.append("successfulCustomersCount", successfulCustomersCount);
            builder.append("failedCustomersCount", failedCustomersCount);
            builder.append("expectedTotalCount", expectedTotalCount);
            return builder.toString();
        }
    }
}
