package com.sfl.pms.services.order.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/30/15
 * Time: 6:09 PM
 */
public abstract class OrderStateChangedEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public OrderStateChangedEventListenerAdapter() {
    }

    @Override
    public boolean subscribed(@Nonnull final ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof OrderStateChangedEvent);
    }

    @Override
    public void process(@Nonnull final ApplicationEvent applicationEvent) {
        processOrderStateChangedEvent((OrderStateChangedEvent) applicationEvent);
    }

    /* Abstract methods */
    protected abstract void processOrderStateChangedEvent(final OrderStateChangedEvent orderPaidEvent);
}
