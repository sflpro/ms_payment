package com.sfl.pms.services.payment.redirect.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/10/15
 * Time: 4:28 PM
 */
public abstract class StartPaymentProviderRedirectResultProcessingEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public StartPaymentProviderRedirectResultProcessingEventListenerAdapter() {
    }


    @Override
    public boolean subscribed(@Nonnull final ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof StartPaymentProviderRedirectResultProcessingEvent);
    }

    @Override
    public void process(@Nonnull final ApplicationEvent applicationEvent) {
        final StartPaymentProviderRedirectResultProcessingEvent event = (StartPaymentProviderRedirectResultProcessingEvent) applicationEvent;
        processStartPaymentProviderRedirectResultProcessingEvent(event);
    }

    /* Abstract methods */
    protected abstract void processStartPaymentProviderRedirectResultProcessingEvent(@Nonnull final StartPaymentProviderRedirectResultProcessingEvent event);
}
