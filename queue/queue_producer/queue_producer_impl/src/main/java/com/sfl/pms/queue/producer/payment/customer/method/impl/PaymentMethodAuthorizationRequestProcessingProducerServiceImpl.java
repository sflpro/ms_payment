package com.sfl.pms.queue.producer.payment.customer.method.impl;

import com.sfl.pms.queue.amqp.model.payment.customer.method.PaymentMethodAuthorizationRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.customer.method.PaymentMethodAuthorizationRequestProcessingProducerService;
import com.sfl.pms.services.payment.customer.method.event.CustomerPaymentMethodAuthorizationRequestCreatedEventListenerAdapter;
import com.sfl.pms.services.payment.customer.method.event.StartCustomerPaymentMethodAuthorizationRequestProcessingEvent;
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
 * Date: 1/19/15
 * Time: 10:38 PM
 */
@Service
public class PaymentMethodAuthorizationRequestProcessingProducerServiceImpl implements PaymentMethodAuthorizationRequestProcessingProducerService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodAuthorizationRequestProcessingProducerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Autowired
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public PaymentMethodAuthorizationRequestProcessingProducerServiceImpl() {
        LOGGER.debug("Initializing payment method authorization request processing producer service");
    }

    @Override
    public void afterPropertiesSet() {
        applicationEventDistributionService.subscribe(new CustomerPaymentMethodAuthorizationRequestCreatedEventListener());
    }

    @Override
    public void processPaymentMethodAuthorizationRequestCreatedEvent(@Nonnull final Long paymentMethodAuthorizationRequestId) {
        Assert.notNull(paymentMethodAuthorizationRequestId, "Payment method authorization request ID should not be null");
        LOGGER.debug("Processing payment method authorization request with id - {}", paymentMethodAuthorizationRequestId);
        amqpConnectorService.publishMessage(RPCCallType.START_PAYMENT_METHOD_AUTHORIZATION_PROCESSING, new PaymentMethodAuthorizationRequestRPCTransferModel(paymentMethodAuthorizationRequestId), PaymentMethodAuthorizationRequestRPCTransferModel.class, new PaymentMethodAuthorizationRPCResponseHandler());
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
    private class CustomerPaymentMethodAuthorizationRequestCreatedEventListener extends CustomerPaymentMethodAuthorizationRequestCreatedEventListenerAdapter {

        /* Constructors */
        public CustomerPaymentMethodAuthorizationRequestCreatedEventListener() {
        }

        @Override
        protected void processPaymentMethodAuthorizationRequestCreatedEvent(@Nonnull final StartCustomerPaymentMethodAuthorizationRequestProcessingEvent startCustomerPaymentMethodAuthorizationRequestProcessingEvent) {
            PaymentMethodAuthorizationRequestProcessingProducerServiceImpl.this.processPaymentMethodAuthorizationRequestCreatedEvent(startCustomerPaymentMethodAuthorizationRequestProcessingEvent.getAuthorizationRequestId());
        }
    }

    private static class PaymentMethodAuthorizationRPCResponseHandler implements AmqpResponseHandler<PaymentMethodAuthorizationRequestRPCTransferModel> {

        private final StopWatch stopWatch;

        /* Constructors */
        public PaymentMethodAuthorizationRPCResponseHandler() {
            this.stopWatch = new StopWatch();
            stopWatch.start();
        }

        @Override
        public void handleResponse(@Nonnull final PaymentMethodAuthorizationRequestRPCTransferModel responseModel) {
            stopWatch.stop();
            LOGGER.debug("Finalized payment method authorization round trip, response model - {}, duration - {}", responseModel, stopWatch.getTime());

        }
    }
}
