package com.sfl.pms.services.order.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 2/19/15
 * Time: 1:58 PM
 */
public abstract class OrderCreatedEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public OrderCreatedEventListenerAdapter() {
    }

    @Override
    public boolean subscribed(@Nonnull final ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof OrderCreatedEvent);
    }

    @Override
    public void process(@Nonnull final ApplicationEvent applicationEvent) {
        processOrderCreatedEvent((OrderCreatedEvent) applicationEvent);
    }

    /* Abstract methods */
    protected abstract void processOrderCreatedEvent(@Nonnull final OrderCreatedEvent orderCreatedEvent);
}
