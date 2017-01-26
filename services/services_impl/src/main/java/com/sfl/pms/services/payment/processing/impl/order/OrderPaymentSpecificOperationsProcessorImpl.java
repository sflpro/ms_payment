package com.sfl.pms.services.payment.processing.impl.order;

import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.dto.OrderStateChangeDto;
import com.sfl.pms.services.order.dto.OrderStateChangeHistoryRecordDto;
import com.sfl.pms.services.order.dto.payment.provider.OrderProviderPaymentChannelDto;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.PaymentType;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.order.OrderPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.impl.AbstractPaymentTypeSpecificOperationsProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 10:49 AM
 */
@Component("orderPaymentSpecificOperationsProcessor")
public class OrderPaymentSpecificOperationsProcessorImpl extends AbstractPaymentTypeSpecificOperationsProcessorImpl implements OrderPaymentSpecificOperationsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentSpecificOperationsProcessorImpl.class);

    /* Dependencies */
    @Autowired
    private OrderService orderService;

    /* Constructors */
    public OrderPaymentSpecificOperationsProcessorImpl() {
        LOGGER.debug("Initializing order payment specific operations processor");
    }

    @Nonnull
    @Override
    public PaymentProcessingResultDetailedInformationDto processPaymentResult(@Nonnull final Payment payment, @Nonnull final PaymentResult paymentResult) {
        assertPayment(payment);
        final OrderPayment orderPayment = (OrderPayment) payment;
        final Long orderId = orderPayment.getOrder().getId();
        if (paymentResult != null) {
            LOGGER.debug("Successfully set payment result for payment with id - {}, payment result - {}", payment.getId(), paymentResult);
            if (paymentResult.getStatus().getPaymentState().isInterpretAsPaid()) {
                LOGGER.debug("Payment with id - {} created for order with id - {} was authorized by payment provider. Payment result - {}", payment.getId(), orderId, paymentResult);
                // Update payment state
                updatePaymentState(payment.getId(), paymentResult.getStatus().getPaymentState(), null);
                // Load order, check that order is not paid
                final boolean isOrderPaid = orderService.checkIfOrderIsPaid(orderId);
                if (!isOrderPaid) {
                    // Update state on order
                    markOrderAsPaid(orderId, payment.getId());
                }
                // Schedule asynchronous payment methods synchronization
                if (payment.isStorePaymentMethod()) {
                    schedulePaymentMethodsSynchronizationTask(payment.getCustomer().getId(), payment.getPaymentProviderType());
                }
            } else {
                LOGGER.debug("Payment with id - {} created for order with id - {} was refused by payment provider. Payment result - {}", payment.getId(), orderId, paymentResult);
                // Update payment state
                updatePaymentState(payment.getId(), paymentResult.getStatus().getPaymentState(), null);
                // Update state on order
                updateOrderState(orderId, OrderState.PAYMENT_FAILED, payment.getUuId());
            }
        } else {
            // Update state on order
            updateOrderState(orderId, OrderState.PAYMENT_FAILED, payment.getUuId());
            // Update payment state
            updatePaymentState(payment.getId(), PaymentState.FAILED, null);
        }
        return new OrderPaymentProcessingResultDetailedInformationDto(payment.getId());
    }

    @Override
    public PaymentProcessingResultDetailedInformationDto processDuplicatePaymentResult(@Nonnull final Payment payment, @Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        LOGGER.debug("Do not process duplicate result payment result for payment - {} , payment result - {}", payment, paymentResultDto);
        return null;
    }


    /* Utility methods */
    private void assertPayment(final Payment payment) {
        assertPaymentNotNull(payment);
        Assert.isInstanceOf(OrderPayment.class, payment, "Payment should be type of OrderPayment");
    }

    private void assertPaymentNotNull(final Payment payment) {
        Assert.notNull(payment, "Payment should not be null");
    }

    private void updateOrderState(final Long orderId, final OrderState orderState, final String paymentUuid) {
        final OrderStateChangeDto orderStateChangeDto = new OrderStateChangeDto();
        orderStateChangeDto.setOrderId(orderId);
        orderStateChangeDto.setOrderStateChangeHistoryRecordDto(new OrderStateChangeHistoryRecordDto(orderState));
        orderStateChangeDto.setPaymentUuid(paymentUuid);
        orderService.updateOrderState(orderStateChangeDto);
    }

    private void markOrderAsPaid(final Long orderId, final Long orderPaymentId) {
        orderService.markOrderPaid(orderId, new OrderProviderPaymentChannelDto(orderPaymentId));
    }

    @Override
    protected PaymentType getPaymentType() {
        return PaymentType.ORDER;
    }

    /* Properties getters and setters */
    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(final OrderService orderService) {
        this.orderService = orderService;
    }
}
