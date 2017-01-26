package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.persistence.repositories.payment.common.order.OrderPaymentRequestRepository;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.OrderService;
import com.sfl.pms.services.order.model.Order;
import com.sfl.pms.services.payment.common.dto.order.request.*;
import com.sfl.pms.services.payment.common.exception.order.InvalidOrderPaymentRequestPaymentException;
import com.sfl.pms.services.payment.common.exception.order.OrderPaymentRequestNotFoundForIdException;
import com.sfl.pms.services.payment.common.exception.order.OrderPaymentRequestNotFoundForUuIdException;
import com.sfl.pms.services.payment.common.exception.order.OrderPaymentRequestStateNotAllowedException;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequestState;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethodType;
import com.sfl.pms.services.payment.common.order.OrderPaymentService;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import com.sfl.pms.services.util.mutable.MutableHolder;
import static org.easymock.EasyMock.*;
import org.easymock.Mock;
import org.easymock.TestSubject;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 1:15 PM
 */
public class OrderPaymentRequestServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private OrderPaymentRequestServiceImpl orderPaymentRequestService = new OrderPaymentRequestServiceImpl();

    @Mock
    private OrderPaymentRequestRepository orderPaymentRequestRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderPaymentService orderPaymentService;

    @Mock
    private CustomerPaymentMethodService customerPaymentMethodService;

    @Mock
    private OrderRequestCustomerPaymentMethodDtoHandler orderRequestCustomerPaymentMethodDtoHandler;

    @Mock
    private OrderRequestEncryptedPaymentMethodDtoHandler orderRequestEncryptedPaymentMethodDtoHandler;

    @Mock
    private OrderRequestRedirectPaymentMethodDtoHandler orderRequestRedirectPaymentMethodDtoHandler;

    /* Constructors */
    public OrderPaymentRequestServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdateOrderPaymentRequestPaymentWithInvalidArguments() {
        // Test data
        final Long requestId = 1L;
        final Long paymentId = 2L;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestPayment(null, paymentId);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentRequestService.updateOrderPaymentRequestPayment(requestId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestPaymentWithNotExistingRequestId() {
        // Test data
        final Long requestId = 1L;
        final Long paymentId = 2L;
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestPayment(requestId, paymentId);
            fail("Exception should be thrown");
        } catch (final OrderPaymentRequestNotFoundForIdException ex) {
            // Expected
            assertOrderPaymentRequestNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestPaymentWithDifferentOrders() {
        // Test data
        final Long requestId = 1L;
        final OrderPaymentRequest orderPaymentRequest = getServicesImplTestHelper().createOrderPaymentRequest();
        orderPaymentRequest.setId(requestId);
        final Long requestOrderId = 3L;
        final Order requestOrder = getServicesImplTestHelper().createOrder();
        requestOrder.setId(requestOrderId);
        orderPaymentRequest.setOrder(requestOrder);
        // Create order payment
        final Long paymentId = 2L;
        final OrderPayment orderPayment = getServicesImplTestHelper().createOrderPayment();
        orderPayment.setId(paymentId);
        final Long paymentOrderId = 4L;
        final Order paymentOrder = getServicesImplTestHelper().createOrder();
        paymentOrder.setId(paymentOrderId);
        orderPayment.setOrder(paymentOrder);
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(requestId))).andReturn(orderPaymentRequest).once();
        expect(orderPaymentService.getPaymentById(eq(paymentId))).andReturn(orderPayment).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestPayment(requestId, paymentId);
            fail("Exception should be thrown");
        } catch (final InvalidOrderPaymentRequestPaymentException ex) {
            // Expected
            assertInvalidOrderPaymentRequestPaymentException(ex, requestId, paymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestPayment() {
        // Test data
        final Long requestId = 1L;
        final OrderPaymentRequest orderPaymentRequest = getServicesImplTestHelper().createOrderPaymentRequest();
        orderPaymentRequest.setId(requestId);
        final Long requestOrderId = 3L;
        final Order requestOrder = getServicesImplTestHelper().createOrder();
        requestOrder.setId(requestOrderId);
        orderPaymentRequest.setOrder(requestOrder);
        // Create order payment
        final Long paymentId = 2L;
        final OrderPayment orderPayment = getServicesImplTestHelper().createOrderPayment();
        orderPayment.setId(paymentId);
        orderPayment.setOrder(requestOrder);
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(requestId))).andReturn(orderPaymentRequest).once();
        expect(orderPaymentService.getPaymentById(eq(paymentId))).andReturn(orderPayment).once();
        expect(orderPaymentRequestRepository.save(isA(OrderPaymentRequest.class))).andAnswer(() -> (OrderPaymentRequest) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentRequest result = orderPaymentRequestService.updateOrderPaymentRequestPayment(requestId, paymentId);
        assertNotNull(result);
        assertEquals(orderPayment, result.getOrderPayment());
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestRedirectUrlWithInvalidArguments() {
        // Test data
        final Long orderPaymentRequestId = 1L;
        final String redirectUrl = "http://live.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestRedirectUrl(null, redirectUrl);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentRequestService.updateOrderPaymentRequestRedirectUrl(orderPaymentRequestId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestRedirectUrlWithNotExistingRequestId() {
        // Test data
        final Long orderPaymentRequestId = 1L;
        final String redirectUrl = "http://live.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(orderPaymentRequestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestRedirectUrl(orderPaymentRequestId, redirectUrl);
            fail("Exception should be thrown");
        } catch (final OrderPaymentRequestNotFoundForIdException ex) {
            // Expected
            assertOrderPaymentRequestNotFoundForIdException(ex, orderPaymentRequestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestRedirectUrl() {
        // Test data
        final Long orderPaymentRequestId = 1L;
        final OrderPaymentRequest orderPaymentRequest = getServicesImplTestHelper().createOrderPaymentRequest();
        orderPaymentRequest.setId(orderPaymentRequestId);
        final String redirectUrl = "http://live.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(orderPaymentRequestId))).andReturn(orderPaymentRequest).once();
        expect(orderPaymentRequestRepository.save(isA(OrderPaymentRequest.class))).andAnswer(() -> (OrderPaymentRequest) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentRequest result = orderPaymentRequestService.updateOrderPaymentRequestRedirectUrl(orderPaymentRequestId, redirectUrl);
        assertNotNull(result);
        assertEquals(redirectUrl, result.getPaymentRedirectUrl());
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestStateWithInvalidArguments() {
        // Test data
        final Long requestId = 1l;
        final OrderPaymentRequestState state = OrderPaymentRequestState.PROCESSING;
        final Set<OrderPaymentRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(OrderPaymentRequestState.CREATED));
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestState(null, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentRequestService.updateOrderPaymentRequestState(requestId, null, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentRequestService.updateOrderPaymentRequestState(requestId, state, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestStateWithNotExistingRequestId() {
        // Test data
        final Long requestId = 1l;
        final OrderPaymentRequestState state = OrderPaymentRequestState.PROCESSING;
        final Set<OrderPaymentRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(OrderPaymentRequestState.CREATED));
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findByIdWithWriteLockFlushedAndFreshData(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final OrderPaymentRequestNotFoundForIdException ex) {
            // Expected
            assertOrderPaymentRequestNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestStateNotAllowedState() {
        // Test data
        final Long requestId = 1l;
        final OrderPaymentRequestState state = OrderPaymentRequestState.PROCESSING;
        final OrderPaymentRequest request = getServicesImplTestHelper().createOrderPaymentRequest();
        final OrderPaymentRequestState initialState = request.getState();
        final MutableHolder<OrderPaymentRequestState> notAllowedStateHolder = new MutableHolder<>(null);
        Arrays.asList(OrderPaymentRequestState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                notAllowedStateHolder.setValue(currentState);
            }
        });
        final Set<OrderPaymentRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(notAllowedStateHolder.getValue()));
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findByIdWithWriteLockFlushedAndFreshData(eq(requestId))).andReturn(request).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.updateOrderPaymentRequestState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final OrderPaymentRequestStateNotAllowedException ex) {
            // Expected
            assertOrderPaymentRequestStateNotAllowedException(ex, state, initialState, allowedStates);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdateOrderPaymentRequestState() {
        // Test data
        final Long requestId = 1l;
        final OrderPaymentRequestState state = OrderPaymentRequestState.PROCESSING;
        final OrderPaymentRequest request = getServicesImplTestHelper().createOrderPaymentRequest();
        final OrderPaymentRequestState initialState = request.getState();
        final Set<OrderPaymentRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(initialState));
        final Date requestUpdated = request.getUpdated();
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findByIdWithWriteLockFlushedAndFreshData(eq(requestId))).andReturn(request).once();
        expect(orderPaymentRequestRepository.saveAndFlush(isA(OrderPaymentRequest.class))).andAnswer(() -> (OrderPaymentRequest) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentRequest result = orderPaymentRequestService.updateOrderPaymentRequestState(requestId, state, allowedStates);
        assertNotNull(result);
        assertEquals(state, result.getState());
        assertTrue(result.getUpdated().compareTo(requestUpdated) >= 0 && requestUpdated != result.getUpdated());
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderPaymentRequestWithInvalidArguments() {
        // Test data
        final Long orderId = 1L;
        final OrderPaymentRequestDto orderPaymentRequestDto = getServicesImplTestHelper().createOrderPaymentRequestDto();
        final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> orderRequestPaymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.createOrderPaymentRequest(null, orderPaymentRequestDto, orderRequestPaymentMethodDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentRequestService.createOrderPaymentRequest(orderId, null, orderRequestPaymentMethodDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            orderPaymentRequestService.createOrderPaymentRequest(orderId, orderPaymentRequestDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateOrderPaymentRequestWithCustomerPaymentMethod() {
        // Test data
        final OrderRequestCustomerPaymentMethodDto orderRequestPaymentMethodDto = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethodDto();
        final OrderRequestPaymentMethod orderRequestPaymentMethod = getServicesImplTestHelper().createOrderRequestCustomerPaymentMethod(orderRequestPaymentMethodDto);
        testCreateOrderPaymentRequest(orderRequestPaymentMethodDto, orderRequestPaymentMethod);
    }

    @Test
    public void testCreateOrderPaymentRequestWithEncryptedPaymentMethod() {
        // Test data
        final OrderRequestEncryptedPaymentMethodDto orderRequestPaymentMethodDto = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethodDto();
        final OrderRequestPaymentMethod orderRequestPaymentMethod = getServicesImplTestHelper().createOrderRequestEncryptedPaymentMethod(orderRequestPaymentMethodDto);
        testCreateOrderPaymentRequest(orderRequestPaymentMethodDto, orderRequestPaymentMethod);
    }

    @Test
    public void testCreateOrderPaymentRequestWithRedirectPaymentMethod() {
        // Test data
        final OrderRequestRedirectPaymentMethodDto orderRequestPaymentMethodDto = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethodDto();
        final OrderRequestPaymentMethod orderRequestPaymentMethod = getServicesImplTestHelper().createOrderRequestRedirectPaymentMethod(orderRequestPaymentMethodDto);
        testCreateOrderPaymentRequest(orderRequestPaymentMethodDto, orderRequestPaymentMethod);
    }

    @Test
    public void testGetOrderPaymentRequestByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.getOrderPaymentRequestByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestByUuIdWithNotExistingUuId() {
        // Test data
        final String orderPaymentRequestUuId = "test_uuid";
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findByUuId(eq(orderPaymentRequestUuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.getOrderPaymentRequestByUuId(orderPaymentRequestUuId);
            fail("Exception should be thrown");
        } catch (final OrderPaymentRequestNotFoundForUuIdException ex) {
            // Expected
            assertOrderPaymentRequestNotFoundForUuIdException(ex, orderPaymentRequestUuId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestByUuId() {
        // Test data
        final String orderPaymentRequestUuId = "test_uuid";
        final OrderPaymentRequest orderPaymentRequest = getServicesImplTestHelper().createOrderPaymentRequest();
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findByUuId(eq(orderPaymentRequestUuId))).andReturn(orderPaymentRequest).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentRequest result = orderPaymentRequestService.getOrderPaymentRequestByUuId(orderPaymentRequestUuId);
        assertEquals(orderPaymentRequest, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.getOrderPaymentRequestById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestByIdWithNotExistingId() {
        // Test data
        final Long orderPaymentRequestId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(orderPaymentRequestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            orderPaymentRequestService.getOrderPaymentRequestById(orderPaymentRequestId);
            fail("Exception should be thrown");
        } catch (final OrderPaymentRequestNotFoundForIdException ex) {
            // Expected
            assertOrderPaymentRequestNotFoundForIdException(ex, orderPaymentRequestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrderPaymentRequestById() {
        // Test data
        final Long orderPaymentRequestId = 1L;
        final OrderPaymentRequest orderPaymentRequest = getServicesImplTestHelper().createOrderPaymentRequest();
        // Reset
        resetAll();
        // Expectations
        expect(orderPaymentRequestRepository.findOne(eq(orderPaymentRequestId))).andReturn(orderPaymentRequest).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentRequest result = orderPaymentRequestService.getOrderPaymentRequestById(orderPaymentRequestId);
        assertEquals(orderPaymentRequest, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertOrderPaymentRequestStateNotAllowedException(final OrderPaymentRequestStateNotAllowedException ex, final OrderPaymentRequestState requiredState, final OrderPaymentRequestState requestState, final Set<OrderPaymentRequestState> allowedStates) {
        assertEquals(requestState, ex.getRequestState());
        assertEquals(requiredState, ex.getRequiredState());
        assertEquals(allowedStates, ex.getAllowedStates());
    }

    private void assertOrderPaymentRequestNotFoundForUuIdException(final OrderPaymentRequestNotFoundForUuIdException ex, final String uuId) {
        assertEquals(uuId, ex.getUuId());
        assertEquals(OrderPaymentRequest.class, ex.getEntityClass());
    }

    private void assertOrderPaymentRequestNotFoundForIdException(final OrderPaymentRequestNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(OrderPaymentRequest.class, ex.getEntityClass());
    }

    private void assertInvalidOrderPaymentRequestPaymentException(final InvalidOrderPaymentRequestPaymentException ex, final Long requestId, final Long paymentId) {
        assertEquals(requestId, ex.getOrderPaymentRequestId());
        assertEquals(paymentId, ex.getOrderPaymentId());
    }

    private OrderRequestPaymentMethodDtoHandler getOrderRequestPaymentMethodDtoHandler(final OrderRequestPaymentMethodType type) {
        switch (type) {
            case CUSTOMER_PAYMENT_METHOD:
                return orderRequestCustomerPaymentMethodDtoHandler;
            case ENCRYPTED_PAYMENT_METHOD:
                return orderRequestEncryptedPaymentMethodDtoHandler;
            case REDIRECT_PAYMENT_METHOD:
                return orderRequestRedirectPaymentMethodDtoHandler;
            default: {
                throw new ServicesRuntimeException("Unknown order request payment method type - " + type);
            }
        }
    }

    private void testCreateOrderPaymentRequest(final OrderRequestPaymentMethodDto orderRequestPaymentMethodDto, final OrderRequestPaymentMethod orderRequestPaymentMethod) {
        // Test data
        final Long orderId = 1L;
        final Order order = getServicesImplTestHelper().createOrder();
        order.setId(orderId);
        final OrderPaymentRequestDto orderPaymentRequestDto = getServicesImplTestHelper().createOrderPaymentRequestDto();
        final Long customerId = 4L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        order.setCustomer(customer);
        // Reset
        resetAll();
        // Expectations
        expect(orderService.getOrderById(eq(orderId))).andReturn(order).once();
        expect(orderPaymentRequestRepository.save(isA(OrderPaymentRequest.class))).andAnswer(() -> (OrderPaymentRequest) getCurrentArguments()[0]).once();
        getOrderRequestPaymentMethodDtoHandler(orderRequestPaymentMethodDto.getType()).assertOrderRequestPaymentMethodDto(eq(orderRequestPaymentMethodDto), eq(customer));
        expectLastCall().once();
        expect(getOrderRequestPaymentMethodDtoHandler(orderRequestPaymentMethodDto.getType()).convertOrderRequestPaymentMethodDto(eq(orderRequestPaymentMethodDto), eq(customer))).andReturn(orderRequestPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        final OrderPaymentRequest result = orderPaymentRequestService.createOrderPaymentRequest(orderId, orderPaymentRequestDto, orderRequestPaymentMethodDto);
        getServicesImplTestHelper().assertOrderPaymentRequest(result, orderPaymentRequestDto);
        assertEquals(order, result.getOrder());
        assertEquals(OrderPaymentRequestState.CREATED, result.getState());
        assertEquals(orderRequestPaymentMethod, result.getPaymentMethod());
        // Verify
        verifyAll();
    }
}
