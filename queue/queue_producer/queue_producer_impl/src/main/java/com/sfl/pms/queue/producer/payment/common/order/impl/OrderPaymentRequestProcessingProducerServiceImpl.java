package com.sfl.pms.queue.producer.payment.common.order.impl;

import com.sfl.pms.queue.amqp.model.payment.common.order.OrderPaymentRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.producer.connector.AmqpConnectorService;
import com.sfl.pms.queue.producer.connector.AmqpResponseHandler;
import com.sfl.pms.queue.producer.payment.common.order.OrderPaymentRequestProcessingProducerService;
import com.sfl.pms.services.payment.common.event.order.StartOrderPaymentRequestProcessingCommandEvent;
import com.sfl.pms.services.payment.common.event.order.StartOrderPaymentRequestProcessingCommandEventListenerAdapter;
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
 * Date: 2/4/15
 * Time: 4:23 PM
 */
@Service
public class OrderPaymentRequestProcessingProducerServiceImpl implements OrderPaymentRequestProcessingProducerService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentRequestProcessingProducerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    @Autowired
    private AmqpConnectorService amqpConnectorService;

    /* Constructors */
    public OrderPaymentRequestProcessingProducerServiceImpl() {
        LOGGER.debug("Initializing order payment request processing producer service");
    }

    @Override
    public void afterPropertiesSet() {
        applicationEventDistributionService.subscribe(new CustomerPaymentMethodAuthorizationRequestCreatedEventListener());
    }

    @Override
    public void processOrderPaymentRequestProcessingEvent(@Nonnull final Long orderPaymentRequestId) {
        Assert.notNull(orderPaymentRequestId, "Order payment request ID should not be null");
        LOGGER.debug("Processing order payment request with id - {}", orderPaymentRequestId);
        amqpConnectorService.publishMessage(RPCCallType.START_ORDER_PAYMENT_REQUEST_PROCESSING, new OrderPaymentRequestRPCTransferModel(orderPaymentRequestId), OrderPaymentRequestRPCTransferModel.class, new OrderPaymentRequestRPCResponseHandler());
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
    private class CustomerPaymentMethodAuthorizationRequestCreatedEventListener extends StartOrderPaymentRequestProcessingCommandEventListenerAdapter {

        /* Constructors */
        public CustomerPaymentMethodAuthorizationRequestCreatedEventListener() {
        }

        @Override
        protected void processOrderPaymentRequestCreatedEvent(final StartOrderPaymentRequestProcessingCommandEvent event) {
            OrderPaymentRequestProcessingProducerServiceImpl.this.processOrderPaymentRequestProcessingEvent(event.getOrderPaymentRequestId());
        }
    }

    private static class OrderPaymentRequestRPCResponseHandler implements AmqpResponseHandler<OrderPaymentRequestRPCTransferModel> {

        private final StopWatch stopWatch;

        /* Constructors */
        public OrderPaymentRequestRPCResponseHandler() {
            this.stopWatch = new StopWatch();
            stopWatch.start();
        }

        @Override
        public void handleResponse(@Nonnull final OrderPaymentRequestRPCTransferModel responseModel) {
            stopWatch.stop();
            LOGGER.debug("Finalized order payment round trip, response model - {}, duration - {}", responseModel, stopWatch.getTime());

        }
    }
}
