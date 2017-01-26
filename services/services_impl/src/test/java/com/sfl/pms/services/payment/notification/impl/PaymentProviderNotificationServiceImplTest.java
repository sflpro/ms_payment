package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.repositories.payment.notification.AbstractPaymentProviderNotificationRepository;
import com.sfl.pms.persistence.repositories.payment.notification.PaymentProviderNotificationRepository;
import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationNotFoundForIdException;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationStateNotAllowedException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;
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
 * Time: 9:56 AM
 */
public class PaymentProviderNotificationServiceImplTest extends AbstractPaymentProviderNotificationServiceImplTest<PaymentProviderNotification> {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderNotificationServiceImpl paymentProviderNotificationService = new PaymentProviderNotificationServiceImpl();

    @Mock
    private PaymentProviderNotificationRepository paymentProviderNotificationRepository;

    /* Constructors */
    public PaymentProviderNotificationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentProviderNotificationStateWithInvalidArguments() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationState state = PaymentProviderNotificationState.PROCESSING;
        final Set<PaymentProviderNotificationState> allowedStates = new LinkedHashSet<>(Arrays.asList(PaymentProviderNotificationState.CREATED));
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationService.updatePaymentProviderNotificationState(null, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            paymentProviderNotificationService.updatePaymentProviderNotificationState(requestId, null, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            paymentProviderNotificationService.updatePaymentProviderNotificationState(requestId, state, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentProviderNotificationStateWithNotExistingRequestId() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationState state = PaymentProviderNotificationState.PROCESSING;
        final Set<PaymentProviderNotificationState> allowedStates = new LinkedHashSet<>(Arrays.asList(PaymentProviderNotificationState.CREATED));
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRepository.findByIdWithPessimisticWriteLock(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationService.updatePaymentProviderNotificationState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderNotificationNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentProviderNotificationStateNotAllowedState() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationState state = PaymentProviderNotificationState.PROCESSING;
        final PaymentProviderNotification notification = getInstance();
        final PaymentProviderNotificationState initialState = notification.getState();
        final MutableHolder<PaymentProviderNotificationState> notAllowedStateHolder = new MutableHolder<>(null);
        Arrays.asList(PaymentProviderNotificationState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                notAllowedStateHolder.setValue(currentState);
            }
        });
        final Set<PaymentProviderNotificationState> allowedStates = new LinkedHashSet<>(Arrays.asList(notAllowedStateHolder.getValue()));
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRepository.findByIdWithPessimisticWriteLock(eq(requestId))).andReturn(notification).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationService.updatePaymentProviderNotificationState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationStateNotAllowedException ex) {
            // Expected
            assertPaymentProviderNotificationStateNotAllowedException(ex, state, initialState, allowedStates);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentProviderNotificationState() {
        // Test data
        final Long requestId = 1l;
        final PaymentProviderNotificationState state = PaymentProviderNotificationState.PROCESSING;
        final PaymentProviderNotification notification = getInstance();
        final PaymentProviderNotificationState initialState = notification.getState();
        final Set<PaymentProviderNotificationState> allowedStates = new LinkedHashSet<>(Arrays.asList(initialState));
        final Date requestUpdated = notification.getUpdated();
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRepository.findByIdWithPessimisticWriteLock(eq(requestId))).andReturn(notification).once();
        expect(paymentProviderNotificationRepository.save(isA(PaymentProviderNotification.class))).andAnswer(() -> (PaymentProviderNotification) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProviderNotification result = paymentProviderNotificationService.updatePaymentProviderNotificationState(requestId, state, allowedStates);
        assertNotNull(result);
        assertEquals(state, result.getState());
        assertTrue(result.getUpdated().compareTo(requestUpdated) >= 0 && requestUpdated != result.getUpdated());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected PaymentProviderNotification getInstance() {
        return getServicesImplTestHelper().createAdyenPaymentProviderNotification();
    }

    @Override
    protected Class<PaymentProviderNotification> getInstanceClass() {
        return PaymentProviderNotification.class;
    }

    @Override
    protected AbstractPaymentProviderNotificationRepository<PaymentProviderNotification> getRepository() {
        return paymentProviderNotificationRepository;
    }

    @Override
    protected AbstractPaymentProviderNotificationService<PaymentProviderNotification> getService() {
        return paymentProviderNotificationService;
    }

    private void assertPaymentProviderNotificationStateNotAllowedException(final PaymentProviderNotificationStateNotAllowedException ex, final PaymentProviderNotificationState requiredState, final PaymentProviderNotificationState requestState, final Set<PaymentProviderNotificationState> allowedStates) {
        assertEquals(requestState, ex.getRequestState());
        assertEquals(requiredState, ex.getRequiredState());
        assertEquals(allowedStates, ex.getAllowedStates());
    }

}
