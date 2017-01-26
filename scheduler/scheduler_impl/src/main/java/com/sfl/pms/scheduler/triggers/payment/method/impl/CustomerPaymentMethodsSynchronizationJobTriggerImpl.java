package com.sfl.pms.scheduler.triggers.payment.method.impl;

import com.sfl.pms.scheduler.jobs.payment.method.CustomerPaymentMethodsSynchronizationJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/6/15
 * Time: 3:33 PM
 */
@Component(value = "customerPaymentMethodsSynchronizationJobTrigger")
public class CustomerPaymentMethodsSynchronizationJobTriggerImpl implements CustomerPaymentMethodsSynchronizationJob {


    /* Dependencies */
    @Autowired
    @Qualifier(value = "customerPaymentMethodsSynchronizationJob")
    private CustomerPaymentMethodsSynchronizationJob customerPaymentMethodsSynchronizationJob;


    /* Constructors */
    public CustomerPaymentMethodsSynchronizationJobTriggerImpl() {
    }

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void synchronizeAdyenCustomerPaymentMethods() {
        // Synchronize Adyen payment methods
        customerPaymentMethodsSynchronizationJob.synchronizeAdyenCustomerPaymentMethods();
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodsSynchronizationJob getCustomerPaymentMethodsSynchronizationJob() {
        return customerPaymentMethodsSynchronizationJob;
    }

    public void setCustomerPaymentMethodsSynchronizationJob(final CustomerPaymentMethodsSynchronizationJob customerPaymentMethodsSynchronizationJob) {
        this.customerPaymentMethodsSynchronizationJob = customerPaymentMethodsSynchronizationJob;
    }
}
