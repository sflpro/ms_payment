package com.sfl.pms.queue.producer.payment.notification.impl;

import com.sfl.pms.queue.amqp.model.payment.notification.PaymentProviderNotificationRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.notification.PaymentProviderNotificationProcessingQueueProducerService;
import com.sfl.pms.services.payment.notification.event.StartPaymentProviderNotificationRequestProcessingEvent;
import com.sfl.pms.services.payment.notification.event.StartPaymentProviderNotificationRequestProcessingEventListenerAdapter;
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
public class PaymentProviderNotificationProcessingQueueProducerServiceImpl implements PaymentProviderNotificationProcessingQueueProducerService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationProcessingQueueProducerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Autowired
    private AmqpConnectorService amqpConnectorService;


    /* Constructors */
    public PaymentProviderNotificationProcessingQueueProducerServiceImpl() {
        LOGGER.debug("Initializing payment provider notification processing producer service");
    }

    @Override
    public void afterPropertiesSet() {
        applicationEventDistributionService.subscribe(new StartPaymentProviderNotificationRequestProcessingEventListener());
    }

    @Override
    public void processPaymentProviderNotificationRequestProcessingEvent(@Nonnull final Long notificationRequestId) {
        Assert.notNull(notificationRequestId, "Payment provider notification request id should not be null");
        LOGGER.debug("Processing notification request processing event, notification request id - {}", notificationRequestId);
        amqpConnectorService.publishMessage(RPCCallType.START_PAYMENT_PROVIDER_NOTIFICATION_REQUEST_PROCESSING, new PaymentProviderNotificationRequestRPCTransferModel(notificationRequestId), PaymentProviderNotificationRequestRPCTransferModel.class, new NotificationRequestProcessingEventListenerRPCResponseHandler());
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
    private class StartPaymentProviderNotificationRequestProcessingEventListener extends StartPaymentProviderNotificationRequestProcessingEventListenerAdapter {

        /* Constructors */
        public StartPaymentProviderNotificationRequestProcessingEventListener() {
        }

        @Override
        protected void processStartPaymentProviderNotificationRequestProcessingEvent(final StartPaymentProviderNotificationRequestProcessingEvent event) {
            PaymentProviderNotificationProcessingQueueProducerServiceImpl.this.processPaymentProviderNotificationRequestProcessingEvent(event.getPaymentProviderNotificationRequestId());
        }
    }

    private static class NotificationRequestProcessingEventListenerRPCResponseHandler implements AmqpResponseHandler<PaymentProviderNotificationRequestRPCTransferModel> {

        private final StopWatch stopWatch;

        /* Constructors */
        public NotificationRequestProcessingEventListenerRPCResponseHandler() {
            this.stopWatch = new StopWatch();
            stopWatch.start();
        }

        @Override
        public void handleResponse(@Nonnull final PaymentProviderNotificationRequestRPCTransferModel responseModel) {
            stopWatch.stop();
            LOGGER.debug("Finalized payment provider notification request processing queue roundtrip, response model - {}, duration - {}", responseModel, stopWatch.getTime());

        }
    }
}
