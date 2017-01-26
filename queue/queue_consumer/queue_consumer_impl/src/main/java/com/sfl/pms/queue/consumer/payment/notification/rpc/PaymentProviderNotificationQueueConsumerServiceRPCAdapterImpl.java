package com.sfl.pms.queue.consumer.payment.notification.rpc;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.queue.amqp.model.payment.notification.PaymentProviderNotificationRequestRPCTransferModel;
import com.sfl.pms.queue.amqp.rpc.RPCCallType;
import com.sfl.pms.queue.amqp.rpc.impl.AbstractRPCServiceAdapterImpl;
import com.sfl.pms.queue.amqp.rpc.message.RPCMethodHandler;
import com.sfl.pms.queue.consumer.payment.notification.PaymentProviderNotificationProcessingQueueConsumerService;
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
@Component("paymentProviderNotificationQueueConsumerServiceRPCAdapter")
public class PaymentProviderNotificationQueueConsumerServiceRPCAdapterImpl extends AbstractRPCServiceAdapterImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationQueueConsumerServiceRPCAdapterImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationProcessingQueueConsumerService paymentProviderNotificationProcessingQueueConsumerService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public PaymentProviderNotificationQueueConsumerServiceRPCAdapterImpl() {
        LOGGER.debug("Initializing sms notification queue consumer service rpc adapter");
    }


    @Override
    public void afterPropertiesSet() {
        addMethodHandler(new RPCMethodHandler<PaymentProviderNotificationRequestRPCTransferModel>(RPCCallType.START_PAYMENT_PROVIDER_NOTIFICATION_REQUEST_PROCESSING.getCallIdentifier(), PaymentProviderNotificationRequestRPCTransferModel.class) {
            @Override
            public Object executeMethod(final Object parameter) {
                final PaymentProviderNotificationRequestRPCTransferModel notificationRPCTransferModel = (PaymentProviderNotificationRequestRPCTransferModel) parameter;
                /* Process consumer service call */
                persistenceUtilityService.runInPersistenceSession(() -> {
                    paymentProviderNotificationProcessingQueueConsumerService.processPaymentProviderNotificationRequest(notificationRPCTransferModel.getNotificationRequestId());
                });
                return notificationRPCTransferModel;
            }
        });
    }
}
