package com.sfl.pms.services.payment.redirect;

import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResultState;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 1:16 PM
 */
public class PaymentProviderRedirectResultServiceIntegrationTest extends AbstractPaymentProviderRedirectResultServiceIntegrationTest<PaymentProviderRedirectResult> {

    /* Dependencies */
    @Autowired
    private PaymentProviderRedirectResultService paymentProviderRedirectResultService;

    /* Constructors */
    public PaymentProviderRedirectResultServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentProviderRedirectResultState() {
        // Grab instance
        PaymentProviderRedirectResult redirectResult = getInstance();
        final PaymentProviderRedirectResultState initialState = redirectResult.getState();
        final MutableHolder<PaymentProviderRedirectResultState> newStateHolder = new MutableHolder<>(null);
        Arrays.asList(PaymentProviderRedirectResultState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                newStateHolder.setValue(currentState);
            }
        });
        final PaymentProviderRedirectResultState newState = newStateHolder.getValue();
        flushAndClear();
        redirectResult = paymentProviderRedirectResultService.updatePaymentProviderRedirectResultState(redirectResult.getId(), newState, new HashSet<>(Arrays.asList(initialState)));
        assertEquals(newState, redirectResult.getState());
        // FLush, reload and assert
        flushAndClear();
        redirectResult = paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(redirectResult.getId());
        assertEquals(newState, redirectResult.getState());
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderRedirectResultService<PaymentProviderRedirectResult> getService() {
        return paymentProviderRedirectResultService;
    }

    @Override
    protected PaymentProviderRedirectResult getInstance() {
        return getServicesTestHelper().createAdyenRedirectResult();
    }

    @Override
    protected Class<PaymentProviderRedirectResult> getInstanceClass() {
        return PaymentProviderRedirectResult.class;
    }
}
