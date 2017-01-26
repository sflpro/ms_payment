package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 10:12 PM
 */
@Ignore
public abstract class AbstractCustomerPaymentMethodAuthorizationRequestServiceIntegrationTest<T extends CustomerPaymentMethodAuthorizationRequest> extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public AbstractCustomerPaymentMethodAuthorizationRequestServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentMethodAuthorizationRequestRedirectUrl() {
        // Grab instance
        final T request = getInstance();
        assertNull(request.getPaymentRedirectUrl());
        final String redirectUrl = "https://live.adyen.com/pay.shtml";
        flushAndClear();
        // Update URL
        T result = getService().updatePaymentMethodAuthorizationRequestRedirectUrl(request.getId(), redirectUrl);
        assertNotNull(result);
        assertEquals(redirectUrl, result.getPaymentRedirectUrl());
        // Flush, clear, reload and assert again
        flushAndClear();
        result = getService().getPaymentMethodAuthorizationRequestById(request.getId());
        assertNotNull(result);
        assertEquals(redirectUrl, result.getPaymentRedirectUrl());
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestById() {
        // Grab instance
        final T request = getInstance();
        // FLush, reload and assert
        flushAndClear();
        final T result = getService().getPaymentMethodAuthorizationRequestById(request.getId());
        assertEquals(request, result);
    }

    @Test
    public void testGetPaymentMethodAuthorizationRequestByUuId() {
        // Grab instance
        final T request = getInstance();
        // FLush, reload and assert
        flushAndClear();
        final T result = getService().getPaymentMethodAuthorizationRequestByUuId(request.getUuId());
        assertEquals(request, result);

    }

    @Test
    public void testUpdatePaymentMethodAuthorizationRequestState() {
        // Grab instance
        CustomerPaymentMethodAuthorizationRequest request = getInstance();
        final CustomerPaymentMethodAuthorizationRequestState initialState = request.getState();
        final MutableHolder<CustomerPaymentMethodAuthorizationRequestState> newStateHolder = new MutableHolder<>(null);
        Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                newStateHolder.setValue(currentState);
            }
        });
        final CustomerPaymentMethodAuthorizationRequestState newState = newStateHolder.getValue();
        flushAndClear();
        request = getService().updatePaymentMethodAuthorizationRequestState(request.getId(), newState, new HashSet<>(Arrays.asList(initialState)));
        assertEquals(newState, request.getState());
        // FLush, reload and assert
        flushAndClear();
        request = customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(request.getId());
        assertEquals(newState, request.getState());
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodAuthorizationRequestService<T> getService();

    protected abstract T getInstance();

}
