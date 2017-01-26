package com.sfl.pms.queue.consumer.payment.common.order.impl;

import com.sfl.pms.queue.consumer.payment.common.order.OrderPaymentRequestProcessingConsumerService;
import com.sfl.pms.services.payment.processing.order.OrderPaymentRequestProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 5:04 PM
 */
@Service
@Lazy(false)
public class OrderPaymentRequestProcessingConsumerServiceImpl implements OrderPaymentRequestProcessingConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentRequestProcessingConsumerServiceImpl.class);

    /* Dependencies */
    @Autowired
    private OrderPaymentRequestProcessorService orderPaymentRequestProcessorService;

    /* Constructors */
    public OrderPaymentRequestProcessingConsumerServiceImpl() {
        LOGGER.debug("Initializing order payment request processing consumer service");
    }

    @Override
    public void processOrderPaymentRequest(@Nonnull final Long orderPaymentRequestId) {
        Assert.notNull(orderPaymentRequestId, "Order payment request id should not be null");
        orderPaymentRequestProcessorService.processOrderPaymentRequest(orderPaymentRequestId);
    }

    /* Properties getters and setters */
    public OrderPaymentRequestProcessorService getOrderPaymentRequestProcessorService() {
        return orderPaymentRequestProcessorService;
    }

    public void setOrderPaymentRequestProcessorService(final OrderPaymentRequestProcessorService orderPaymentRequestProcessorService) {
        this.orderPaymentRequestProcessorService = orderPaymentRequestProcessorService;
    }
}
