package com.sfl.pms.services.payment.customer.method.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
public abstract class CustomerPaymentMethodRemovalRequestCreatedEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public CustomerPaymentMethodRemovalRequestCreatedEventListenerAdapter() {
    }

    @Override
    public boolean subscribed(@Nonnull ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof StartCustomerPaymentMethodRemovalRequestProcessingEvent);
    }

    @Override
    public void process(@Nonnull ApplicationEvent applicationEvent) {
        processPaymentMethodRemovalRequestEvent((StartCustomerPaymentMethodRemovalRequestProcessingEvent) applicationEvent);
    }

    /* Abstract methods */
    protected abstract void processPaymentMethodRemovalRequestEvent(@Nonnull final StartCustomerPaymentMethodRemovalRequestProcessingEvent startCustomerPaymentMethodRemovalRequestProcessingEvent);
}
