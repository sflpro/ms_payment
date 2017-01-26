package com.sfl.pms.queue.consumer.payment.customer.method.rpc;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.queue.amqp.model.payment.customer.method.PaymentMethodAuthorizationRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.amqp.rpc.impl.AbstractRPCServiceAdapterImpl;
import com.sfl.pms.queue.amqp.rpc.message.RPCMethodHandler;
import com.sfl.pms.queue.consumer.payment.customer.method.PaymentMethodAuthorizationRequestProcessingConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/15
 * Time: 11:26 PM
 */
@Component("paymentMethodAuthorizationRequestProcessingConsumerServiceRPCAdapter")
public class PaymentMethodAuthorizationRequestProcessingConsumerServiceRPCAdapterImpl extends AbstractRPCServiceAdapterImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodAuthorizationRequestProcessingConsumerServiceRPCAdapterImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentMethodAuthorizationRequestProcessingConsumerService paymentMethodAuthorizationRequestProcessingConsumerService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public PaymentMethodAuthorizationRequestProcessingConsumerServiceRPCAdapterImpl() {
        LOGGER.debug("Initializing payment method authorization request processing consumer service RPC Adapter");
    }

    @Override
    public void afterPropertiesSet() {
        addMethodHandler(new RPCMethodHandler<PaymentMethodAuthorizationRequestRPCTransferModel>(RPCCallType.START_PAYMENT_METHOD_AUTHORIZATION_PROCESSING.getCallIdentifier(), PaymentMethodAuthorizationRequestRPCTransferModel.class) {
            @Override
            public Object executeMethod(final Object parameter) {
                final PaymentMethodAuthorizationRequestRPCTransferModel rpcHandler = (PaymentMethodAuthorizationRequestRPCTransferModel) parameter;
                persistenceUtilityService.runInPersistenceSession(() -> {
                    paymentMethodAuthorizationRequestProcessingConsumerService.processPaymentMethodAuthorizationRequest(rpcHandler.getAuthorizationRequestId());
                });
                return rpcHandler;
            }
        });
    }

    /* Properties getters and setters */
    public PaymentMethodAuthorizationRequestProcessingConsumerService getPaymentMethodAuthorizationRequestProcessingConsumerService() {
        return paymentMethodAuthorizationRequestProcessingConsumerService;
    }

    public void setPaymentMethodAuthorizationRequestProcessingConsumerService(final PaymentMethodAuthorizationRequestProcessingConsumerService paymentMethodAuthorizationRequestProcessingConsumerService) {
        this.paymentMethodAuthorizationRequestProcessingConsumerService = paymentMethodAuthorizationRequestProcessingConsumerService;
    }

    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }
}
