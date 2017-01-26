package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAuthorizationRequestStateNotAllowedException;
import com.sfl.pms.services.payment.customer.method.exception.PaymentMethodAuthorizationRequestNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.exception.PaymentMethodAuthorizationRequestNotFoundForUuIdException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.easymock.Mock;
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
 * Date: 12/29/14
 * Time: 8:43 PM
 */
public abstract class AbstractCustomerPaymentMethodAuthorizationRequestServiceImplTest<T extends CustomerPaymentMethodAuthorizationRequest> extends AbstractServicesUnitTest {

    /* Mocks */
    @Mock
    private CustomerPaymentMethodAuthorizationRequestRepository customerPaymentMethodAuthorizationRequestRepository;

    /* Constructors */
    public AbstractCustomerPaymentMethodAuthorizationRequestServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentMethodAuthorizationRequestRedirectUrlWithInvalidArguments() {
        // Test data
        final Long requestId = 1L;
        final String redirectUrl = "http://live.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentMethodAuthorizationRequestRedirectUrl(null, redirectUrl);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            getService().updatePaymentMethodAuthorizationRequestRedirectUrl(requestId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestRedirectUrlWithNotExistingId() {
        // Test data
        final Long requestId = 1L;
        final String redirectUrl = "http://live.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentMethodAuthorizationRequestRedirectUrl(requestId, redirectUrl);
            fail("Exception should be thrown");
        } catch (final PaymentMethodAuthorizationRequestNotFoundForIdException ex) {
            // Expected
            assertPaymentMethodAuthorizationRequestNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestRedirectUrl() {
        // Test data
        final Long requestId = 1L;
        final T authorizationRequest = getInstance();
        authorizationRequest.setId(requestId);
        final String redirectUrl = "http://live.adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(requestId))).andReturn(authorizationRequest).once();
        expect(getRepository().save(isA(getInstanceClass()))).andAnswer(() -> (T) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().updatePaymentMethodAuthorizationRequestRedirectUrl(requestId, redirectUrl);
        assertNotNull(result);
        assertEquals(redirectUrl, result.getPaymentRedirectUrl());
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodAuthorizationRequestByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestByUuIdWithNotExistingUuId() {
        // Expected
        final String uuId = "not_existing_uuid";
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(uuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodAuthorizationRequestByUuId(uuId);
            fail("Exception should be thrown");
        } catch (final PaymentMethodAuthorizationRequestNotFoundForUuIdException ex) {
            // Expected
            assertPaymentMethodAuthorizationRequestNotFoundForUuIdException(ex, uuId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestByUuId() {
        // Expected
        final T authorizationRequest = getInstance();
        final String uuId = authorizationRequest.getUuId();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(uuId))).andReturn(authorizationRequest).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentMethodAuthorizationRequestByUuId(uuId);
        assertEquals(authorizationRequest, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestStateWithInvalidArguments() {
        // Test data
        final Long requestId = 1l;
        final CustomerPaymentMethodAuthorizationRequestState state = CustomerPaymentMethodAuthorizationRequestState.PROCESSING;
        final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.CREATED));
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentMethodAuthorizationRequestState(null, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            getService().updatePaymentMethodAuthorizationRequestState(requestId, null, allowedStates);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            getService().updatePaymentMethodAuthorizationRequestState(requestId, state, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestStateWithNotExistingRequestId() {
        // Test data
        final Long requestId = 1l;
        final CustomerPaymentMethodAuthorizationRequestState state = CustomerPaymentMethodAuthorizationRequestState.PROCESSING;
        final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.CREATED));
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestRepository.findByIdWithWriteLockFlushedAndFreshData(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentMethodAuthorizationRequestState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final PaymentMethodAuthorizationRequestNotFoundForIdException ex) {
            // Expected
            assertPaymentMethodAuthorizationRequestNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestStateWithNotAllowedState() {
        // Test data
        final Long requestId = 1l;
        final CustomerPaymentMethodAuthorizationRequestState state = CustomerPaymentMethodAuthorizationRequestState.PROCESSING;
        final T request = getInstance();
        final CustomerPaymentMethodAuthorizationRequestState initialState = request.getState();
        final MutableHolder<CustomerPaymentMethodAuthorizationRequestState> notAllowedStateHolder = new MutableHolder<>(null);
        Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                notAllowedStateHolder.setValue(currentState);
            }
        });
        final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(notAllowedStateHolder.getValue()));
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestRepository.findByIdWithWriteLockFlushedAndFreshData(eq(requestId))).andReturn(request).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentMethodAuthorizationRequestState(requestId, state, allowedStates);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAuthorizationRequestStateNotAllowedException ex) {
            // Expected
            assertCustomerPaymentMethodAuthorizationRequestStateNotAllowedException(ex, state, initialState, allowedStates);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestState() {
        // Test data
        final Long requestId = 1l;
        final CustomerPaymentMethodAuthorizationRequestState state = CustomerPaymentMethodAuthorizationRequestState.PROCESSING;
        final T request = getInstance();
        final CustomerPaymentMethodAuthorizationRequestState initialState = request.getState();
        final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates = new LinkedHashSet<>(Arrays.asList(initialState));
        final Date requestUpdated = request.getUpdated();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestRepository.findByIdWithWriteLockFlushedAndFreshData(eq(requestId))).andReturn(request).once();
        expect(customerPaymentMethodAuthorizationRequestRepository.saveAndFlush(isA(getInstanceClass()))).andAnswer(() -> (T) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodAuthorizationRequest result = getService().updatePaymentMethodAuthorizationRequestState(requestId, state, allowedStates);
        assertNotNull(result);
        assertEquals(state, result.getState());
        assertTrue(result.getUpdated().compareTo(requestUpdated) >= 0 && requestUpdated != result.getUpdated());
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodAuthorizationRequestById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestByIdWithNotExistingRequestId() {
        // Test data
        final Long requestId = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(requestId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentMethodAuthorizationRequestById(requestId);
            fail("Exception should be thrown");
        } catch (final PaymentMethodAuthorizationRequestNotFoundForIdException ex) {
            // Expected
            assertPaymentMethodAuthorizationRequestNotFoundForIdException(ex, requestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestById() {
        // Test data
        final Long requestId = 1l;
        final T request = getInstance();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(requestId))).andReturn(request).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentMethodAuthorizationRequestById(requestId);
        assertEquals(request, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertPaymentMethodAuthorizationRequestNotFoundForUuIdException(final PaymentMethodAuthorizationRequestNotFoundForUuIdException ex, final String uuId) {
        assertEquals(uuId, ex.getUuId());
        assertEquals(getInstanceClass(), ex.getAuthorizationRequestClass());
    }

    private void assertPaymentMethodAuthorizationRequestNotFoundForIdException(final PaymentMethodAuthorizationRequestNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    private void assertCustomerPaymentMethodAuthorizationRequestStateNotAllowedException(final CustomerPaymentMethodAuthorizationRequestStateNotAllowedException ex, final CustomerPaymentMethodAuthorizationRequestState requiredState, final CustomerPaymentMethodAuthorizationRequestState requestState, final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates) {
        assertEquals(requestState, ex.getRequestState());
        assertEquals(requiredState, ex.getRequiredState());
        assertEquals(allowedStates, ex.getAllowedStates());
    }

    public CustomerPaymentMethodAuthorizationRequestRepository getCustomerPaymentMethodAuthorizationRequestRepository() {
        return customerPaymentMethodAuthorizationRequestRepository;
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodAuthorizationRequestRepository<T> getRepository();

    protected abstract AbstractCustomerPaymentMethodAuthorizationRequestService<T> getService();

    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();

}
