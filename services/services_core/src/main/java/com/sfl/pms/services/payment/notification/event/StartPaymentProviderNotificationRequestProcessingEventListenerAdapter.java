package com.sfl.pms.services.payment.notification.event;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/10/15
 * Time: 4:28 PM
 */
public abstract class StartPaymentProviderNotificationRequestProcessingEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public StartPaymentProviderNotificationRequestProcessingEventListenerAdapter() {
    }


    @Override
    public boolean subscribed(@Nonnull final ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof StartPaymentProviderNotificationRequestProcessingEvent);
    }

    @Override
    public void process(@Nonnull final ApplicationEvent applicationEvent) {
        final StartPaymentProviderNotificationRequestProcessingEvent event = (StartPaymentProviderNotificationRequestProcessingEvent) applicationEvent;
        processStartPaymentProviderNotificationRequestProcessingEvent(event);
    }

    /* Abstract methods */
    protected abstract void processStartPaymentProviderNotificationRequestProcessingEvent(final StartPaymentProviderNotificationRequestProcessingEvent event);
}
