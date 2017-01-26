package com.sfl.pms.services.payment.common.event.order;

import com.sfl.pms.services.system.event.model.ApplicationEvent;
import com.sfl.pms.services.system.event.model.ApplicationEventListener;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 3:59 PM
 */
public abstract class StartOrderPaymentRequestProcessingCommandEventListenerAdapter implements ApplicationEventListener {

    /* Constructors */
    public StartOrderPaymentRequestProcessingCommandEventListenerAdapter() {
    }

    @Nonnull
    @Override
    public boolean subscribed(@Nonnull final ApplicationEvent applicationEvent) {
        return (applicationEvent instanceof StartOrderPaymentRequestProcessingCommandEvent);
    }

    @Override
    public void process(@Nonnull final ApplicationEvent applicationEvent) {
        processOrderPaymentRequestCreatedEvent((StartOrderPaymentRequestProcessingCommandEvent) applicationEvent);
    }

    /* Abstract methods */
    protected abstract void processOrderPaymentRequestCreatedEvent(final StartOrderPaymentRequestProcessingCommandEvent event);
}
