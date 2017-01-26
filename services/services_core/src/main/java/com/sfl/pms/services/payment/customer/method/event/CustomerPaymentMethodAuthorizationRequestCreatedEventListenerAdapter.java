package com.sfl.pms.services.payment.customer.method.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 10:51 PM
 */
public abstract class CustomerPaymentMethodAuthorizationRequestCreatedEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestCreatedEventListenerAdapter() {
    }

    @Nonnull
    @Override
    public boolean subscribed(@Nonnull ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof StartCustomerPaymentMethodAuthorizationRequestProcessingEvent);
    }

    @Override
    public void process(@Nonnull ApplicationEvent applicationEvent) {
        processPaymentMethodAuthorizationRequestCreatedEvent((StartCustomerPaymentMethodAuthorizationRequestProcessingEvent) applicationEvent);
    }

    /* Abstract methods */
    protected abstract void processPaymentMethodAuthorizationRequestCreatedEvent(@Nonnull final StartCustomerPaymentMethodAuthorizationRequestProcessingEvent startCustomerPaymentMethodAuthorizationRequestProcessingEvent);
}
