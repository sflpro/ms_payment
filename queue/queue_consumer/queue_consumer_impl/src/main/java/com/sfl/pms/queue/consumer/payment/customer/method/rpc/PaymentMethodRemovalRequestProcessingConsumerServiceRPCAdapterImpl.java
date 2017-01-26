package com.sfl.pms.queue.consumer.payment.customer.method.rpc;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.queue.amqp.model.payment.customer.method.PaymentMethodRemovalRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.amqp.rpc.impl.AbstractRPCServiceAdapterImpl;
import com.sfl.pms.queue.amqp.rpc.message.RPCMethodHandler;
import com.sfl.pms.queue.consumer.payment.customer.method.PaymentMethodRemovalRequestProcessingConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 2:09 PM
 */
@Component("paymentMethodRemovalRequestProcessingConsumerServiceRPCAdapter")
public class PaymentMethodRemovalRequestProcessingConsumerServiceRPCAdapterImpl extends AbstractRPCServiceAdapterImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodRemovalRequestProcessingConsumerServiceRPCAdapterImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentMethodRemovalRequestProcessingConsumerService paymentMethodRemovalRequestProcessingConsumerService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public PaymentMethodRemovalRequestProcessingConsumerServiceRPCAdapterImpl() {
        LOGGER.debug("Initializing payment method removal request processing consumer service RPC Adapter");
    }

    @Override
    public void afterPropertiesSet() {
        addMethodHandler(new RPCMethodHandler<PaymentMethodRemovalRequestRPCTransferModel>(RPCCallType.START_PAYMENT_METHOD_REMOVAL_PROCESSING.getCallIdentifier(), PaymentMethodRemovalRequestRPCTransferModel.class) {
            @Override
            public Object executeMethod(final Object parameter) {
                final PaymentMethodRemovalRequestRPCTransferModel rpcHandler = (PaymentMethodRemovalRequestRPCTransferModel) parameter;
                persistenceUtilityService.runInPersistenceSession(() -> {
                    paymentMethodRemovalRequestProcessingConsumerService.processPaymentMethodRemovalRequest(rpcHandler.getPaymentMethodId());
                });
                return rpcHandler;
            }
        });
    }

    /* Properties getters and setters */
    public PaymentMethodRemovalRequestProcessingConsumerService getPaymentMethodRemovalRequestProcessingConsumerService() {
        return paymentMethodRemovalRequestProcessingConsumerService;
    }

    public void setPaymentMethodRemovalRequestProcessingConsumerService(final PaymentMethodRemovalRequestProcessingConsumerService paymentMethodRemovalRequestProcessingConsumerService) {
        this.paymentMethodRemovalRequestProcessingConsumerService = paymentMethodRemovalRequestProcessingConsumerService;
    }

    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }
}
