package com.sfl.pms.api.internal.facade.order.impl;

import com.sfl.pms.api.internal.facade.test.AbstractFacadeUnitTest;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import com.sfl.pms.core.api.internal.model.common.result.ResultResponseModel;
import com.sfl.pms.core.api.internal.model.customer.CustomerModel;
import com.sfl.pms.core.api.internal.model.order.OrderModel;
import com.sfl.pms.core.api.internal.model.order.OrderPaymentRequestStateClientType;
import com.sfl.pms.core.api.internal.model.order.OrderRequestRedirectPaymentMethodModel;
import com.sfl.pms.core.api.internal.model.order.OrderStateClientType;
import com.sfl.pms.core.api.internal.model.order.request.CreateOrderRequest;
import com.sfl.pms.core.api.internal.model.order.request.GetOrderPaymentRequestStatusRequest;
import com.sfl.pms.core.api.internal.model.order.request.RePayOrderRequest;
import com.sfl.pms.core.api.internal.model.order.response.CreateOrderPaymentResponse;
import com.sfl.pms.core.api.internal.model.order.response.GetOrderPaymentRequestStatusResponse;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.dto.CustomerDto;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.dto.OrderDto;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.order.model.OrderState;
import com.sfl.pms.services.payment.common.dto.order.request.OrderPaymentRequestDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestRedirectPaymentMethodDto;
import com.sfl.pms.services.payment.common.event.order.StartOrderPaymentRequestProcessingCommandEvent;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequestState;
import com.sfl.pms.services.payment.common.order.request.OrderPaymentRequestService;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.system.event.ApplicationEventDistributionService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 3:14 PM
 */
public class OrderPaymentFacadeImplTest extends AbstractFacadeUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderPaymentFacadeImpl orderPaymentFacade = new OrderPaymentFacadeImpl();

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderPaymentRequestService orderPaymentRequestService;

    @Mock
    private ApplicationEventDistributionService applicationEventDistributionService;

    /* Constructors */
    public OrderPaymentFacadeImplTest() {
    }

    /* Test methods */
    @Test
    public void testRePayOrderWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentFacade.rePayOrder(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testRePayOrderWithValidationErrorsForEmptyRequest() {
        // Test data
        final RePayOrderRequest rePayOrderRequest = new RePayOrderRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.rePayOrder(rePayOrderRequest);
        assertNotNull(result);
        assertValidationErrors(result.getErrors(), new HashSet<>(Arrays.asList(
                ErrorType.ORDER_MISSING_UUID,
                ErrorType.ORDER_PAYMENT_MISSING_METHOD,
                ErrorType.ORDER_PAYMENT_MISSING_STORE_METHOD)
        ));
        // Verify
        verifyAll();
    }


    @Test
    public void testRePayOrderWithValidationErrorsForEmptyRedirectPaymentMethod() {
        // Request
        final RePayOrderRequest request = getFacadeImplTestHelper().createRePayPaymentRequest();
        request.setPaymentMethod(new OrderRequestRedirectPaymentMethodModel());
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.rePayOrder(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertValidationErrors(result.getErrors(), Collections.singleton(ErrorType.ORDER_PAYMENT_METHOD_MISSING_PROVIDER_TYPE));
        // Verify
        verifyAll();
    }

    @Test
    public void testRePayOrderWithValidationErrorsForAlreadyPaidOrder() {
        // Test data
        final Long orderId = 1L;
        final Order order = getFacadeImplTestHelper().createOrder();
        order.setId(orderId);
        // Request
        final RePayOrderRequest request = getFacadeImplTestHelper().createRePayPaymentRequest();
        final OrderRequestRedirectPaymentMethodModel paymentMethodModel = getFacadeImplTestHelper().createRequestRedirectPaymentMethodModel();
        request.setPaymentMethod(paymentMethodModel);
        // Reset
        resetAll();
        // Expectations
        expect(orderService.getOrderByUuId(eq(request.getOrderUuId()))).andReturn(order).once();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(true).once();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.rePayOrder(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertValidationErrors(result.getErrors(), Collections.singleton(ErrorType.ORDER_PAYMENT_ALREADY_PAID));
        // Verify
        verifyAll();
    }


    @Test
    public void testRePayOrderWithRedirectPaymentMethod() {
        // Test data
        final RePayOrderRequest request = getFacadeImplTestHelper().createRePayPaymentRequest();
        // Order request payment method
        final OrderRequestRedirectPaymentMethodModel requestPaymentMethodModel = getFacadeImplTestHelper().createRequestRedirectPaymentMethodModel();
        request.setPaymentMethod(requestPaymentMethodModel);
        final OrderRequestRedirectPaymentMethodDto orderRequestRedirectPaymentMethodDto = new OrderRequestRedirectPaymentMethodDto(PaymentMethodType.valueOf(requestPaymentMethodModel.getPaymentMethodType().name()), PaymentProviderType.valueOf(requestPaymentMethodModel.getPaymentProviderType().name()));
        // Order
        final Long orderId = 2L;
        final Order order = getFacadeImplTestHelper().createOrder();
        order.setId(orderId);
        // Order payment request
        final OrderPaymentRequestDto orderPaymentRequestDto = new OrderPaymentRequestDto(request.getClientIpAddress(), request.getStorePaymentMethod());
        final Long orderPaymentRequestId = 3L;
        final OrderPaymentRequest orderPaymentRequest = getFacadeImplTestHelper().createOrderPaymentRequest(orderPaymentRequestDto);
        orderPaymentRequest.setId(orderPaymentRequestId);
        // Reset
        resetAll();
        // Expectations
        expect(orderService.getOrderByUuId(eq(request.getOrderUuId()))).andReturn(order).once();
        expect(orderService.checkIfOrderIsPaid(eq(orderId))).andReturn(false).once();
        expect(orderPaymentRequestService.createOrderPaymentRequest(eq(orderId), eq(orderPaymentRequestDto), eq(orderRequestRedirectPaymentMethodDto))).andReturn(orderPaymentRequest).once();
        applicationEventDistributionService.publishAsynchronousEvent(new StartOrderPaymentRequestProcessingCommandEvent(orderPaymentRequestId));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.rePayOrder(request);
        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(0, result.getErrors().size());
        final CreateOrderPaymentResponse response = result.getResponse();
        assertEquals(orderPaymentRequest.getUuId(), response.getOrderPaymentRequestUuId());
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestStatusWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentFacade.getOrderPaymentRequestStatus(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestStatusWithValidationErrors() {
        // Test data
        final GetOrderPaymentRequestStatusRequest request = new GetOrderPaymentRequestStatusRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<GetOrderPaymentRequestStatusResponse> result = orderPaymentFacade.getOrderPaymentRequestStatus(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertEquals(1, result.getErrors().size());
        assertValidationErrors(result.getErrors(), Collections.singleton(ErrorType.ORDER_PAYMENT_REQUEST_MISSING_UUID));
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestStatus() {
        // Test data
        final GetOrderPaymentRequestStatusRequest request = getFacadeImplTestHelper().createGetOrderPaymentRequestStatusRequest();
        // Order payment request
        final Long orderPaymentRequestId = 1L;
        final OrderPaymentRequest orderPaymentRequest = getFacadeImplTestHelper().createOrderPaymentRequest();
        orderPaymentRequest.setId(orderPaymentRequestId);
        orderPaymentRequest.setState(OrderPaymentRequestState.PROCESSED);
        orderPaymentRequest.setPaymentRedirectUrl(UUID.randomUUID().toString());
        // Order
        final OrderState orderState = OrderState.PAID;
        final Long orderId = 2L;
        final Order order = getFacadeImplTestHelper().createOrder();
        order.setId(orderId);
        order.setLastState(orderState);
        orderPaymentRequest.setOrder(order);
        // Create order payment
        final Long orderPaymentId = 3L;
        final OrderPayment orderPayment = getFacadeImplTestHelper().createOrderPayment();
        orderPayment.setId(orderPaymentId);
        orderPaymentRequest.setOrderPayment(orderPayment);
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestService.getOrderPaymentRequestByUuId(eq(request.getOrderPaymentRequestUuid()))).andReturn(orderPaymentRequest).once();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<GetOrderPaymentRequestStatusResponse> result = orderPaymentFacade.getOrderPaymentRequestStatus(request);
        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(0, result.getErrors().size());
        // Grab response
        final GetOrderPaymentRequestStatusResponse response = result.getResponse();
        assertEquals(orderPaymentRequest.getUuId(), response.getOrderPaymentRequestUuId());
        assertEquals(order.getUuId(), response.getOrderUuId());
        assertEquals(OrderStateClientType.valueOf(order.getLastState().name()), response.getOrderState());
        assertEquals(OrderPaymentRequestStateClientType.valueOf(orderPaymentRequest.getState().name()), response.getRequestState());
        assertEquals(orderPaymentRequest.getPaymentRedirectUrl(), response.getPaymentRedirectUrl());
        assertEquals(orderPayment.getUuId(), response.getPaymentUuId());
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentFacade.createOrder(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithValidationErrorsForEmptyRequest() {
        // Request
        final CreateOrderRequest request = new CreateOrderRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.createOrder(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertEquals(4, result.getErrors().size());
        assertValidationErrors(result.getErrors(), new HashSet<>(Arrays.asList(
                ErrorType.ORDER_PAYMENT_MISSING_CUSTOMER,
                ErrorType.ORDER_PAYMENT_MISSING_ORDER,
                ErrorType.ORDER_PAYMENT_MISSING_METHOD,
                ErrorType.ORDER_PAYMENT_MISSING_STORE_METHOD)
        ));
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithValidationErrorsForEmptyCustomer() {
        // Request
        final CreateOrderRequest request = getFacadeImplTestHelper().createCreateOrderPaymentRequest();
        request.setCustomer(new CustomerModel());
        request.setPaymentMethod(getFacadeImplTestHelper().createRequestRedirectPaymentMethodModel());
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.createOrder(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertEquals(2, result.getErrors().size());
        assertValidationErrors(result.getErrors(), new HashSet<>(Arrays.asList(
                ErrorType.CUSTOMER_MISSING_UUID,
                ErrorType.CUSTOMER_MISSING_EMAIL
        )));
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithValidationErrorsForEmptyOrder() {
        // Request
        final CreateOrderRequest request = getFacadeImplTestHelper().createCreateOrderPaymentRequest();
        request.setOrder(new OrderModel());
        request.setPaymentMethod(getFacadeImplTestHelper().createRequestRedirectPaymentMethodModel());
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.createOrder(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertEquals(3, result.getErrors().size());
        assertValidationErrors(result.getErrors(), new HashSet<>(Arrays.asList(
                ErrorType.ORDER_MISSING_UUID,
                ErrorType.ORDER_MISSING_PAYMENT_TOTAL_WITH_VAT,
                ErrorType.ORDER_MISSING_CURRENCY
        )));
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithValidationErrorsForEmptyRedirectPaymentMethod() {
        // Request
        final CreateOrderRequest request = getFacadeImplTestHelper().createCreateOrderPaymentRequest();
        request.setPaymentMethod(new OrderRequestRedirectPaymentMethodModel());
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.createOrder(request);
        assertNotNull(result);
        assertNull(result.getResponse());
        assertEquals(1, result.getErrors().size());
        assertValidationErrors(result.getErrors(), Collections.singleton(ErrorType.ORDER_PAYMENT_METHOD_MISSING_PROVIDER_TYPE));
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderWithRedirectPaymentMethod() {
        // Test data
        final CreateOrderRequest request = getFacadeImplTestHelper().createCreateOrderPaymentRequest();
        // Order request payment method
        final OrderRequestRedirectPaymentMethodModel requestPaymentMethodModel = getFacadeImplTestHelper().createRequestRedirectPaymentMethodModel();
        request.setPaymentMethod(requestPaymentMethodModel);
        final OrderRequestRedirectPaymentMethodDto orderRequestRedirectPaymentMethodDto = new OrderRequestRedirectPaymentMethodDto(PaymentMethodType.valueOf(requestPaymentMethodModel.getPaymentMethodType().name()), PaymentProviderType.valueOf(requestPaymentMethodModel.getPaymentProviderType().name()));
        // Create customer
        final CustomerDto customerDto = new CustomerDto(request.getCustomer().getUuId(), request.getCustomer().getEmail());
        final Long customerId = 1L;
        final Customer customer = getFacadeImplTestHelper().createCustomer(customerDto);
        customer.setId(customerId);
        // Order
        final OrderDto orderDto = new OrderDto(request.getOrder().getUuId(), request.getOrder().getPaymentTotalWithVat(), Currency.valueOf(request.getOrder().getCurrency().name()));
        final Long orderId = 2L;
        final Order order = getFacadeImplTestHelper().createOrder(orderDto);
        order.setId(orderId);
        // Order payment request
        final OrderPaymentRequestDto orderPaymentRequestDto = new OrderPaymentRequestDto(request.getClientIpAddress(), request.getStorePaymentMethod());
        final Long orderPaymentRequestId = 3L;
        final OrderPaymentRequest orderPaymentRequest = getFacadeImplTestHelper().createOrderPaymentRequest(orderPaymentRequestDto);
        orderPaymentRequest.setId(orderPaymentRequestId);
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getOrCreateCustomerForUuId(eq(customerDto))).andReturn(customer).once();
        expect(orderService.createOrder(eq(customerId), eq(orderDto))).andReturn(order).once();
        expect(orderPaymentRequestService.createOrderPaymentRequest(eq(orderId), eq(orderPaymentRequestDto), eq(orderRequestRedirectPaymentMethodDto))).andReturn(orderPaymentRequest).once();
        applicationEventDistributionService.publishAsynchronousEvent(new StartOrderPaymentRequestProcessingCommandEvent(orderPaymentRequestId));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        final ResultResponseModel<CreateOrderPaymentResponse> result = orderPaymentFacade.createOrder(request);
        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(0, result.getErrors().size());
        final CreateOrderPaymentResponse response = result.getResponse();
        assertEquals(orderPaymentRequest.getUuId(), response.getOrderPaymentRequestUuId());
        // Verify
        verifyAll();
    }
}
