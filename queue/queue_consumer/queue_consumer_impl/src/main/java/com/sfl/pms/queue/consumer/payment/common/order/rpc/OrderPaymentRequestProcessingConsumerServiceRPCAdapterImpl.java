package com.sfl.pms.queue.consumer.payment.common.order.rpc;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.queue.amqp.model.payment.common.order.OrderPaymentRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.amqp.rpc.impl.AbstractRPCServiceAdapterImpl;
import com.sfl.pms.queue.amqp.rpc.message.RPCMethodHandler;
import com.sfl.pms.queue.consumer.payment.common.order.OrderPaymentRequestProcessingConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 5:55 PM
 */
@Component("orderPaymentRequestProcessingConsumerServiceRPCAdapter")
public class OrderPaymentRequestProcessingConsumerServiceRPCAdapterImpl extends AbstractRPCServiceAdapterImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentRequestProcessingConsumerServiceRPCAdapterImpl.class);

    /* Dependencies */
    @Autowired
    private OrderPaymentRequestProcessingConsumerService orderPaymentRequestProcessingConsumerService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public OrderPaymentRequestProcessingConsumerServiceRPCAdapterImpl() {
        LOGGER.debug("Initializing order payment request processing consumer service RPC adapter");
    }

    @Override
    public void afterPropertiesSet() {
        addMethodHandler(new RPCMethodHandler<OrderPaymentRequestRPCTransferModel>(RPCCallType.START_ORDER_PAYMENT_REQUEST_PROCESSING.getCallIdentifier(), OrderPaymentRequestRPCTransferModel.class) {
            @Override
            public Object executeMethod(final Object parameter) {
                final OrderPaymentRequestRPCTransferModel rpcHandler = (OrderPaymentRequestRPCTransferModel) parameter;
                persistenceUtilityService.runInPersistenceSession(() -> {
                    orderPaymentRequestProcessingConsumerService.processOrderPaymentRequest(rpcHandler.getOrderPaymentRequestId());
                });
                return rpcHandler;
            }
        });
    }

    /* Properties getters and setters */
    public OrderPaymentRequestProcessingConsumerService getOrderPaymentRequestProcessingConsumerService() {
        return orderPaymentRequestProcessingConsumerService;
    }

    public void setOrderPaymentRequestProcessingConsumerService(final OrderPaymentRequestProcessingConsumerService orderPaymentRequestProcessingConsumerService) {
        this.orderPaymentRequestProcessingConsumerService = orderPaymentRequestProcessingConsumerService;
    }

    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }
}
