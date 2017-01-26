package com.sfl.pms.queue.producer.payment.redirect.impl;

import com.sfl.pms.queue.amqp.model.payment.redirect.PaymentProviderRedirectResultRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.redirect.PaymentProviderRedirectResultProcessingQueueProducerService;
import com.sfl.pms.services.payment.redirect.event.StartPaymentProviderRedirectResultProcessingEvent;
import com.sfl.pms.services.payment.redirect.event.StartPaymentProviderRedirectResultProcessingEventListenerAdapter;
import com.sfl.pms.services.system.event.ApplicationEventDistributionService;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 6:14 PM
 */
@Service
public class PaymentProviderRedirectResultProcessingQueueProducerServiceImpl implements PaymentProviderRedirectResultProcessingQueueProducerService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderRedirectResultProcessingQueueProducerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Autowired
    private AmqpConnectorService amqpConnectorService;


    /* Constructors */
    public PaymentProviderRedirectResultProcessingQueueProducerServiceImpl() {
        LOGGER.debug("Initializing payment provider redirect result processing producer service");
    }

    @Override
    public void afterPropertiesSet() {
        applicationEventDistributionService.subscribe(new StartPaymentProviderRedirectResultProcessingEventListener());
    }

    @Override
    public void processPaymentProviderRedirectResultProcessingEvent(@Nonnull final Long redirectResultId) {
        Assert.notNull(redirectResultId, "Payment provider redirect result id should not be null");
        LOGGER.debug("Processing redirect result processing event, redirect result id - {}", redirectResultId);
        amqpConnectorService.publishMessage(RPCCallType.START_PAYMENT_PROVIDER_REDIRECT_RESULT_PROCESSING, new PaymentProviderRedirectResultRPCTransferModel(redirectResultId), PaymentProviderRedirectResultRPCTransferModel.class, new RedirectResultProcessingEventListenerRPCResponseHandler());
    }

    /* Properties getters and setters */
    public ApplicationEventDistributionService getApplicationEventDistributionService() {
        return applicationEventDistributionService;
    }

    public void setApplicationEventDistributionService(final ApplicationEventDistributionService applicationEventDistributionService) {
        this.applicationEventDistributionService = applicationEventDistributionService;
    }

    public AmqpConnectorService getAmqpConnectorService() {
        return amqpConnectorService;
    }

    public void setAmqpConnectorService(final AmqpConnectorService amqpConnectorService) {
        this.amqpConnectorService = amqpConnectorService;
    }

    /* Inner classes */
    private class StartPaymentProviderRedirectResultProcessingEventListener extends StartPaymentProviderRedirectResultProcessingEventListenerAdapter {

        /* Constructors */
        public StartPaymentProviderRedirectResultProcessingEventListener() {
        }

        @Override
        protected void processStartPaymentProviderRedirectResultProcessingEvent(@Nonnull final StartPaymentProviderRedirectResultProcessingEvent event) {
            PaymentProviderRedirectResultProcessingQueueProducerServiceImpl.this.processPaymentProviderRedirectResultProcessingEvent(event.getPaymentProviderRedirectResultId());
        }
    }

    private static class RedirectResultProcessingEventListenerRPCResponseHandler implements AmqpResponseHandler<PaymentProviderRedirectResultRPCTransferModel> {

        private final StopWatch stopWatch;

        /* Constructors */
        public RedirectResultProcessingEventListenerRPCResponseHandler() {
            this.stopWatch = new StopWatch();
            stopWatch.start();
        }

        @Override
        public void handleResponse(@Nonnull final PaymentProviderRedirectResultRPCTransferModel responseModel) {
            stopWatch.stop();
            LOGGER.debug("Finalized payment provider redirect result processing queue round trip, response model - {}, duration - {}", responseModel, stopWatch.getTime());

        }
    }
}
