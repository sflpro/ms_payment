package com.sfl.pms.services.payment.common.order;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.AbstractPaymentServiceIntegrationTest;
import com.sfl.pms.services.payment.common.dto.channel.CustomerPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.order.OrderPaymentDto;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.channel.CustomerPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:37 PM
 */
public class OrderPaymentServiceIntegrationTest extends AbstractPaymentServiceIntegrationTest<OrderPayment> {

    /* Dependencies */
    @Autowired
    private OrderPaymentService orderPaymentService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public OrderPaymentServiceIntegrationTest() {
    }

    @Test
    public void testCreatePayment() {
        // Prepare data
        final Order order = getServicesTestHelper().createOrder();
        final CustomerPaymentMethod paymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(order.getCustomer());
        final OrderPaymentDto paymentDto = getServicesTestHelper().createOrderPaymentDto();
        final CustomerPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto = getServicesTestHelper().createCustomerPaymentMethodProcessingChannelDto(paymentMethod);
        flushAndClear();
        // Create order payment
        OrderPayment orderPayment = orderPaymentService.createPayment(order.getId(), paymentDto, paymentMethodProcessingChannelDto);
        assertOrderPayment(orderPayment, order, paymentMethod, paymentDto, paymentMethodProcessingChannelDto);
        // Flush, reload and assert again
        orderPayment = orderPaymentService.getPaymentById(orderPayment.getId());
        assertOrderPayment(orderPayment, order, paymentMethod, paymentDto, paymentMethodProcessingChannelDto);
    }

    /* Utility methods */
    private void assertOrderPayment(final OrderPayment orderPayment, final Order order, final CustomerPaymentMethod paymentMethod, final OrderPaymentDto paymentDto, final CustomerPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto) {
        getServicesTestHelper().assertOrderPayment(orderPayment, paymentDto);
        getServicesTestHelper().assertPaymentLastState(orderPayment, PaymentState.CREATED, null, 1);
        assertNotNull(orderPayment.getOrder());
        Assert.assertEquals(order.getId(), orderPayment.getOrder().getId());
        // Grab payment processing channel
        assertNotNull(orderPayment.getPaymentProcessingChannel());
        final PaymentProcessingChannel paymentProcessingChannel = persistenceUtilityService.initializeAndUnProxy(orderPayment.getPaymentProcessingChannel());
        assertTrue(paymentProcessingChannel instanceof CustomerPaymentMethodProcessingChannel);
        final CustomerPaymentMethodProcessingChannel customerPaymentMethodProcessingChannel = (CustomerPaymentMethodProcessingChannel) paymentProcessingChannel;
        assertNotNull(customerPaymentMethodProcessingChannel.getCustomerPaymentMethod());
        Assert.assertEquals(paymentMethod.getId(), customerPaymentMethodProcessingChannel.getCustomerPaymentMethod().getId());

    }

    @Override
    protected AbstractPaymentService<OrderPayment> getService() {
        return orderPaymentService;
    }

    @Override
    protected OrderPayment getInstance() {
        return getServicesTestHelper().createOrderPayment();
    }

}
