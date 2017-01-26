package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.repositories.payment.notification.PaymentProviderNotificationRequestRepository;
import com.sfl.pms.services.payment.notification.dto.PaymentProviderNotificationRequestDto;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationRequestNotFoundForIdException;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationRequestStateNotAllowedException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:33 AM
 */
public class PaymentProviderNotificationRequestServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderNotificationRequestServiceImpl paymentProviderNotificationRequestService = new PaymentProviderNotificationRequestServiceImpl();

    @Mock
    private PaymentProviderNotificationRequestRepository paymentProviderNotificationRequestRepository;

    /* Constructors */
    public PaymentProviderNotificationRequestServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentProviderNotificationRequestStateWithInvalidArguments() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationRequestState state = PaymentProviderNotificationRequestState.PROCESSING;
        final Set<PaymentProviderNotificationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(PaymentProviderNotificationRequestState.CREATED));
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(null, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(requestId, null, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(requestId, state, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentProviderNotificationRequestStateWithNotExistingRequestId() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationRequestState state = PaymentProviderNotificationRequestState.PROCESSING;
        final Set<PaymentProviderNotificationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(PaymentProviderNotificationRequestState.CREATED));
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestRepository.findByIdWithPessimisticWriteLock(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationRequestNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderNotificationNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentProviderNotificationRequestStateNotAllowedState() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationRequestState state = PaymentProviderNotificationRequestState.PROCESSING;
        final PaymentProviderNotificationRequest request = getServicesImplTestHelper().createPaymentProviderNotificationRequest();
        final PaymentProviderNotificationRequestState initialState = request.getState();
        final MutableHolder<PaymentProviderNotificationRequestState> notAllowedStateHolder = new MutableHolder<>(null);
        Arrays.asList(PaymentProviderNotificationRequestState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                notAllowedStateHolder.setValue(currentState);
            }
        });
        final Set<PaymentProviderNotificationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(notAllowedStateHolder.getValue()));
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestRepository.findByIdWithPessimisticWriteLock(eq(requestId))).andReturn(request).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationRequestStateNotAllowedException ex) {
            // Expected
            assertPaymentProviderNotificationRequestStateNotAllowedException(ex, state, initialState, allowedStates);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentProviderNotificationRequestState() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationRequestState state = PaymentProviderNotificationRequestState.PROCESSING;
        final PaymentProviderNotificationRequest request = getServicesImplTestHelper().createPaymentProviderNotificationRequest();
        final PaymentProviderNotificationRequestState initialState = request.getState();
        final Set<PaymentProviderNotificationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(initialState));
        final Date requestUpdated = request.getUpdated();
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestRepository.findByIdWithPessimisticWriteLock(eq(requestId))).andReturn(request).once();
        expect(paymentProviderNotificationRequestRepository.save(isA(PaymentProviderNotificationRequest.class))).andAnswer(() -> (PaymentProviderNotificationRequest) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProviderNotificationRequest result = paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(requestId, state, allowedStates);
        assertNotNull(result);
        assertEquals(state, result.getState());
        assertTrue(result.getUpdated().compareTo(requestUpdated) >= 0 && requestUpdated != result.getUpdated());
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderNotificationRequestByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderNotificationRequestByIdWithNotExistingId() {
        // Test data
        final Long requestId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestRepository.findOne(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(requestId);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationRequestNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderNotificationNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderNotificationRequestById() {
        // Test data
        final Long requestId = 1L;
        final PaymentProviderNotificationRequest request = getServicesImplTestHelper().createPaymentProviderNotificationRequest();
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestRepository.findOne(eq(requestId))).andReturn(request).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProviderNotificationRequest result = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(requestId);
        assertEquals(request, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProviderNotificationRequestWithInvalidArguments() {
        // Test data
        final PaymentProviderNotificationRequestDto requestDto = getServicesImplTestHelper().createPaymentProviderNotificationRequestDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationRequestService.createPaymentProviderNotificationRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            paymentProviderNotificationRequestService.createPaymentProviderNotificationRequest(new PaymentProviderNotificationRequestDto(null, requestDto.getRawContent(), requestDto.getClientIpAddress()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            paymentProviderNotificationRequestService.createPaymentProviderNotificationRequest(new PaymentProviderNotificationRequestDto(requestDto.getProviderType(), null, requestDto.getClientIpAddress()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProviderNotificationRequest() {
        // Test data
        final PaymentProviderNotificationRequestDto requestDto = getServicesImplTestHelper().createPaymentProviderNotificationRequestDto();
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestRepository.save(isA(PaymentProviderNotificationRequest.class))).andAnswer(() -> (PaymentProviderNotificationRequest) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProviderNotificationRequest request = paymentProviderNotificationRequestService.createPaymentProviderNotificationRequest(requestDto);
        getServicesImplTestHelper().assertPaymentProviderNotificationRequest(request, requestDto);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertPaymentProviderNotificationNotFoundForIdException(final PaymentProviderNotificationRequestNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(PaymentProviderNotificationRequest.class, ex.getEntityClass());
    }

    private void assertPaymentProviderNotificationRequestStateNotAllowedException(final PaymentProviderNotificationRequestStateNotAllowedException ex, final PaymentProviderNotificationRequestState requiredState, final PaymentProviderNotificationRequestState requestState, final Set<PaymentProviderNotificationRequestState> allowedStates) {
        assertEquals(requestState, ex.getRequestState());
        assertEquals(requiredState, ex.getRequiredState());
        assertEquals(allowedStates, ex.getAllowedStates());
    }

}
