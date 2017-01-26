package com.sfl.pms.services.payment.processing.order;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.payment.common.dto.order.request.OrderPaymentRequestDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestEncryptedPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestRedirectPaymentMethodDto;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequestState;
import com.sfl.pms.services.payment.common.order.request.OrderPaymentRequestService;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.method.dto.group.GroupPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.dto.individual.IndividualPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/17/15
 * Time: 3:44 PM
 */
public class OrderPaymentRequestProcessorServiceIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private OrderPaymentRequestProcessorService orderPaymentRequestProcessorService;

    @Autowired
    private OrderPaymentRequestService orderPaymentRequestService;

    @Autowired
    private OrderService orderService;

    /* Constructors */
    public OrderPaymentRequestProcessorServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testProcessOrderPaymentWithCustomerPaymentMethod() {
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        // Prepare data
        final String email = "dummy_" + UUID.randomUUID().toString() + "@dummy.com";
        final Customer customer = getServicesTestHelper().createCustomer(email);
        Order order = getServicesTestHelper().createOrder(customer);
        flushAndClear();
        // Create customer card payment method
        final Pair<CustomerCardPaymentMethod, Long> paymentMethodAddingResult = getServicesTestHelper().createCustomerEncryptedCardPaymentMethodWithPaymentProvider(customer);
        final CustomerCardPaymentMethod customerCardPaymentMethod = paymentMethodAddingResult.getKey();
        // Create payment method definition
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        paymentMethodDefinitionDto.setPaymentMethodType(customerCardPaymentMethod.getPaymentMethodType());
        paymentMethodDefinitionDto.setPaymentProviderType(customerCardPaymentMethod.getProviderInformation().getType());
        paymentMethodDefinitionDto.setCurrency(order.getCurrency());
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesTestHelper().createIndividualPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        flushAndClear();
        OrderPaymentRequest orderPaymentRequest = getServicesTestHelper().createOrderPaymentRequest(order, customerCardPaymentMethod, getServicesTestHelper().createOrderPaymentRequestDto());
        flushAndClear();
        // Check that order is not paid
        assertNotEquals(OrderState.PAID, order.getLastState());
        // Create and process order payment
        final Long orderId = orderPaymentRequestProcessorService.processOrderPaymentRequest(orderPaymentRequest.getId());
        assertNotNull(orderId);
        // Flush, clear and reload payment
        flushAndClear();
        // Reload order payment request
        orderPaymentRequest = orderPaymentRequestService.getOrderPaymentRequestById(orderPaymentRequest.getId());
        Assert.assertEquals(OrderPaymentRequestState.PROCESSED, orderPaymentRequest.getState());
        // Reload order
        order = orderService.getOrderById(orderId);
        final OrderPayment orderPayment = order.getPayments().iterator().next();
        getServicesTestHelper().assertAuthorizedEncryptedPayment(orderPayment);
        flushAndClear();
        // Reload order
        order = orderService.getOrderById(order.getId());
        assertEquals(OrderState.PAID, order.getLastState());
    }

    @Test
    public void testProcessOrderPaymentWithRedirectPaymentMethod() {
        // Prepare data
        final String email = "dummy_" + UUID.randomUUID().toString() + "@dummy.com";
        final Customer customer = getServicesTestHelper().createCustomer(email);
        Order order = getServicesTestHelper().createOrder(customer);
        flushAndClear();
        // Create Adyen payment settings
        final AdyenPaymentSettings adyenPaymentSettings = getServicesTestHelper().createAdyenPaymentSettings();
        assertNotNull(adyenPaymentSettings);
        // Create order payment request
        final OrderRequestRedirectPaymentMethodDto redirectPaymentMethodDto = getServicesTestHelper().createOrderRequestRedirectPaymentMethodDto();
        redirectPaymentMethodDto.setPaymentMethodType(PaymentMethodType.IDEAL);
        redirectPaymentMethodDto.setPaymentProviderType(PaymentProviderType.ADYEN);
        final OrderPaymentRequestDto orderPaymentRequestDto = getServicesTestHelper().createOrderPaymentRequestDto();
        flushAndClear();
        OrderPaymentRequest orderPaymentRequest = getServicesTestHelper().createOrderPaymentRequest(order, orderPaymentRequestDto, redirectPaymentMethodDto);
        flushAndClear();
        // Create payment method definition
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        paymentMethodDefinitionDto.setPaymentMethodType(redirectPaymentMethodDto.getPaymentMethodType());
        paymentMethodDefinitionDto.setPaymentProviderType(redirectPaymentMethodDto.getPaymentProviderType());
        paymentMethodDefinitionDto.setCurrency(order.getCurrency());
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesTestHelper().createIndividualPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        // Check that order is not paid
        assertNotEquals(OrderState.PAID, order.getLastState());
        // Create and process order payment
        final Long orderId = orderPaymentRequestProcessorService.processOrderPaymentRequest(orderPaymentRequest.getId());
        assertNotNull(orderId);
        // Flush, clear and reload payment
        flushAndClear();
        // Reload order payment request
        orderPaymentRequest = orderPaymentRequestService.getOrderPaymentRequestById(orderPaymentRequest.getId());
        assertEquals(OrderPaymentRequestState.PROCESSED, orderPaymentRequest.getState());
        assertNotNull(orderPaymentRequest.getPaymentRedirectUrl());
        // Reload order
        order = orderService.getOrderById(order.getId());
        assertEquals(OrderState.PAYMENT_PROCESSING_STARTED, order.getLastState());
        // Assert that generated redirect URL is valid
        getServicesTestHelper().assertAdyenIdealRedirectUrlIsCorrect(orderPaymentRequest.getPaymentRedirectUrl());
    }

    @Test
    public void testProcessOrderPaymentWithEncryptedPaymentMethod() {
        // Prepare data
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        // Prepare data
        final String email = "dummy_" + UUID.randomUUID().toString() + "@dummy.com";
        final Customer customer = getServicesTestHelper().createCustomer(email);
        Order order = getServicesTestHelper().createOrder(customer);
        flushAndClear();
        // Create order payment request
        final OrderRequestEncryptedPaymentMethodDto requestEncryptedPaymentMethodDto = getServicesTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        requestEncryptedPaymentMethodDto.setEncryptedData(getServicesTestHelper().createMasterCardEncryptedInformation());
        requestEncryptedPaymentMethodDto.setPaymentProviderType(PaymentProviderType.ADYEN);
        final OrderPaymentRequestDto orderPaymentRequestDto = getServicesTestHelper().createOrderPaymentRequestDto();
        flushAndClear();
        OrderPaymentRequest orderPaymentRequest = getServicesTestHelper().createOrderPaymentRequest(order, orderPaymentRequestDto, requestEncryptedPaymentMethodDto);
        flushAndClear();
        // Create payment method definition
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createGroupPaymentMethodDefinitionDto();
        paymentMethodDefinitionDto.setPaymentMethodGroupType(PaymentMethodGroupType.CARD);
        paymentMethodDefinitionDto.setPaymentProviderType(requestEncryptedPaymentMethodDto.getPaymentProviderType());
        paymentMethodDefinitionDto.setCurrency(order.getCurrency());
        final GroupPaymentMethodDefinition paymentMethodDefinition = getServicesTestHelper().createGroupPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        // Check that order is not paid
        assertNotEquals(OrderState.PAID, order.getLastState());
        // Create and process order payment
        final Long orderId = orderPaymentRequestProcessorService.processOrderPaymentRequest(orderPaymentRequest.getId());
        assertNotNull(orderId);
        // Flush, clear and reload payment
        flushAndClear();
        // Reload order payment request
        orderPaymentRequest = orderPaymentRequestService.getOrderPaymentRequestById(orderPaymentRequest.getId());
        assertEquals(OrderPaymentRequestState.PROCESSED, orderPaymentRequest.getState());
        assertNull(orderPaymentRequest.getPaymentRedirectUrl());
        // Reload order
        order = orderService.getOrderById(order.getId());
        assertEquals(OrderState.PAID, order.getLastState());
    }
}
