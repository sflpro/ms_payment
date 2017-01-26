package com.sfl.pms.scheduler.jobs.payment.method;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/6/15
 * Time: 1:22 PM
 */
public interface CustomerPaymentMethodsSynchronizationJob {

    /**
     * Start Adyen saved customer payment methods synchronization
     */
    void synchronizeAdyenCustomerPaymentMethods();
}
