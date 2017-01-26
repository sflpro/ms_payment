package com.sfl.pms.queue.producer.payment.customer.method.impl;

import com.sfl.pms.queue.amqp.model.payment.customer.method.PaymentMethodRemovalRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.customer.method.PaymentMethodRemovalRequestProcessingProducerService;
import com.sfl.pms.services.payment.customer.method.event.CustomerPaymentMethodRemovalRequestCreatedEventListenerAdapter;
import com.sfl.pms.services.payment.customer.method.event.StartCustomerPaymentMethodRemovalRequestProcessingEvent;
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
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:45 PM
 */
@Service
public class PaymentMethodRemovalRequestProcessingProducerServiceImpl implements PaymentMethodRemovalRequestProcessingProducerService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodRemovalRequestProcessingProducerService.class);

    /* Dependencies */
    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Autowired
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public PaymentMethodRemovalRequestProcessingProducerServiceImpl() {
        LOGGER.debug("Initializing payment method removal request processing producer service");
    }

    @Override
    public void afterPropertiesSet() {
        applicationEventDistributionService.subscribe(new CustomerPaymentMethodRemovalRequestCreatedEventListener());
    }

    @Override
    public void processPaymentMethodRemovalRequestCreatedEvent(@Nonnull final Long paymentMethodRemovalRequestId) {
        Assert.notNull(paymentMethodRemovalRequestId, "Payment method removal request ID should not be null");
        LOGGER.debug("Processing payment method removal request with id - {}", paymentMethodRemovalRequestId);
        amqpConnectorService.publishMessage(RPCCallType.START_PAYMENT_METHOD_REMOVAL_PROCESSING, new PaymentMethodRemovalRequestRPCTransferModel(paymentMethodRemovalRequestId), PaymentMethodRemovalRequestRPCTransferModel.class, new PaymentMethodRemovalRPCResponseHandler());
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
    private class CustomerPaymentMethodRemovalRequestCreatedEventListener extends CustomerPaymentMethodRemovalRequestCreatedEventListenerAdapter {

        /* Constructors */
        public CustomerPaymentMethodRemovalRequestCreatedEventListener() {
        }

        @Override
        protected void processPaymentMethodRemovalRequestEvent(@Nonnull final StartCustomerPaymentMethodRemovalRequestProcessingEvent startCustomerPaymentMethodRemovalRequestProcessingEvent) {
            PaymentMethodRemovalRequestProcessingProducerServiceImpl.this.processPaymentMethodRemovalRequestCreatedEvent(startCustomerPaymentMethodRemovalRequestProcessingEvent.getRemovalRequestId());
        }
    }

    private static class PaymentMethodRemovalRPCResponseHandler implements AmqpResponseHandler<PaymentMethodRemovalRequestRPCTransferModel> {

        private final StopWatch stopWatch;

        /* Constructors */
        public PaymentMethodRemovalRPCResponseHandler() {
            this.stopWatch = new StopWatch();
            stopWatch.start();
        }

        @Override
        public void handleResponse(@Nonnull final PaymentMethodRemovalRequestRPCTransferModel responseModel) {
            stopWatch.stop();
            LOGGER.debug("Finalized payment method removal round trip, response model - {}, duration - {}", responseModel, stopWatch.getTime());

        }
    }
}
