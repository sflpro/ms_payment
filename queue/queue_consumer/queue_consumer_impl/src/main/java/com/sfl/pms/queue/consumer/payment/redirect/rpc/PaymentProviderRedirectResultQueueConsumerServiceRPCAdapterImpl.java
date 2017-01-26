package com.sfl.pms.queue.consumer.payment.redirect.rpc;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.queue.amqp.model.payment.redirect.PaymentProviderRedirectResultRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.amqp.rpc.impl.AbstractRPCServiceAdapterImpl;
import com.sfl.pms.queue.amqp.rpc.message.RPCMethodHandler;
import com.sfl.pms.queue.consumer.payment.redirect.PaymentProviderRedirectResultProcessingQueueConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 2/11/15
 * Time: 5:28 PM
 */
@Component("paymentProviderRedirectResultQueueConsumerServiceRPCAdapter")
public class PaymentProviderRedirectResultQueueConsumerServiceRPCAdapterImpl extends AbstractRPCServiceAdapterImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderRedirectResultQueueConsumerServiceRPCAdapterImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderRedirectResultProcessingQueueConsumerService paymentProviderRedirectResultProcessingQueueConsumerService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public PaymentProviderRedirectResultQueueConsumerServiceRPCAdapterImpl() {
        LOGGER.debug("Initializing payment provider redirect request queue consumer service rpc adapter");
    }


    @Override
    public void afterPropertiesSet() {
        addMethodHandler(new RPCMethodHandler<PaymentProviderRedirectResultRPCTransferModel>(RPCCallType.START_PAYMENT_PROVIDER_REDIRECT_RESULT_PROCESSING.getCallIdentifier(), PaymentProviderRedirectResultRPCTransferModel.class) {
            @Override
            public Object executeMethod(final Object parameter) {
                final PaymentProviderRedirectResultRPCTransferModel redirectResultRPCTransferModel = (PaymentProviderRedirectResultRPCTransferModel) parameter;
                /* Process consumer service call */
                persistenceUtilityService.runInPersistenceSession(() -> {
                    paymentProviderRedirectResultProcessingQueueConsumerService.processPaymentProviderRedirectResult(redirectResultRPCTransferModel.getRedirectResultId());
                });
                return redirectResultRPCTransferModel;
            }
        });
    }
}
