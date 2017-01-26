package com.sfl.pms.services.payment.redirect;

import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 1:14 PM
 */
@Ignore
public abstract class AbstractPaymentProviderRedirectResultServiceIntegrationTest<T extends PaymentProviderRedirectResult> extends AbstractServiceIntegrationTest {

    /* Constructors */
    public AbstractPaymentProviderRedirectResultServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentForRedirectResult() {
        // Prepare data
        T redirectResult = getInstance();
        assertNull(redirectResult.getPayment());
        final Payment payment = getServicesTestHelper().createOrderPayment();
        flushAndClear();
        // Update payment
        redirectResult = getService().updatePaymentForRedirectResult(redirectResult.getId(), payment.getId());
        assertNotNull(redirectResult);
        assertNotNull(redirectResult.getPayment());
        assertEquals(payment.getId(), redirectResult.getPayment().getId());
        // Flush, reload and assert again
        redirectResult = getService().getPaymentProviderRedirectResultById(redirectResult.getId());
        assertNotNull(redirectResult);
        assertNotNull(redirectResult.getPayment());
        assertEquals(payment.getId(), redirectResult.getPayment().getId());
    }

    @Test
    public void testGetPaymentProviderRedirectResultById() {
        // Create instance
        final T redirectResult = getInstance();
        // Load instance by id and compare
        T result = getService().getPaymentProviderRedirectResultById(redirectResult.getId());
        assertEquals(redirectResult, result);
        // Flush, clear, reload and assert
        result = getService().getPaymentProviderRedirectResultById(redirectResult.getId());
        assertEquals(redirectResult, result);
    }

    @Test
    public void testGetPaymentProviderRedirectResultByUuId() {
        // Create instance
        final T redirectResult = getInstance();
        // Load instance by id and compare
        T result = getService().getPaymentProviderRedirectResultByUuId(redirectResult.getUuId());
        assertEquals(redirectResult, result);
        // Flush, clear, reload and assert
        result = getService().getPaymentProviderRedirectResultByUuId(redirectResult.getUuId());
        assertEquals(redirectResult, result);
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderRedirectResultService<T> getService();

    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();
}
