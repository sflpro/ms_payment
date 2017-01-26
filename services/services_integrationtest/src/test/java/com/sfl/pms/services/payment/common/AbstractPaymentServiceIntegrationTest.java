package com.sfl.pms.services.payment.common;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.common.dto.adyen.AdyenPaymentResultDto;
import com.sfl.pms.services.payment.common.exception.PaymentResultAlreadyExistsForNotificationException;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.redirect.adyen.AdyenRedirectResultService;
import com.sfl.pms.services.payment.redirect.model.adyen.AdyenRedirectResult;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:33 PM
 */
@Ignore
public abstract class AbstractPaymentServiceIntegrationTest<T extends Payment> extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    @Autowired
    private PaymentProviderNotificationService paymentProviderNotificationService;

    @Autowired
    private AdyenRedirectResultService adyenRedirectResultService;

    /* Constructors */
    public AbstractPaymentServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentByUuId() {
        // Prepare data
        final T payment = getInstance();
        // Load and assert
        T result = getService().getPaymentByUuId(payment.getUuId());
        assertEquals(payment, result);
        // Flush, clear, reload and assert
        flushAndClear();
        result = getService().getPaymentByUuId(payment.getUuId());
        assertEquals(payment, result);
    }

    @Test
    public void testGetPaymentById() {
        // Prepare data
        final T payment = getInstance();
        T result = getService().getPaymentById(payment.getId());
        assertEquals(payment, result);
        // Flush, reload and assert
        flushAndClear();
        result = getService().getPaymentById(payment.getId());
        assertEquals(payment, result);
    }

    @Test
    public void testCreatePaymentResultForPayment() {
        // Prepare data
        T payment = getInstance();
        assertEquals(0, payment.getPaymentResults().size());
        final AdyenPaymentResultDto paymentResultDto = getServicesTestHelper().createAdyenPaymentResultDto();
        final PaymentProviderNotification notification = getServicesTestHelper().createAdyenPaymentProviderNotification();
        AdyenRedirectResult adyenRedirectResult = getServicesTestHelper().createAdyenRedirectResult();
        adyenRedirectResult = adyenRedirectResultService.updatePaymentForRedirectResult(adyenRedirectResult.getId(), payment.getId());
        paymentProviderNotificationService.updatePaymentForNotification(notification.getId(), payment.getId());
        flushAndClear();
        // Create payment result
        PaymentResult paymentResult = getService().createPaymentResultForPayment(payment.getId(), notification.getId(), adyenRedirectResult.getId(), paymentResultDto);
        assertAdyenPaymentResult(paymentResult, notification, adyenRedirectResult, paymentResultDto);
        // Flush, reload and try again
        flushAndClear();
        payment = getService().getPaymentById(payment.getId());
        assertAdyenPaymentResult(payment.getPaymentResults().iterator().next(), notification, adyenRedirectResult, paymentResultDto);
    }

    @Test
    public void testCreatePaymentResultForPaymentWhenNotificationIsAssociatedWithAnotherResult() {
        // Prepare data
        T payment = getInstance();
        assertEquals(0, payment.getPaymentResults().size());
        final AdyenPaymentResultDto paymentResultDto = getServicesTestHelper().createAdyenPaymentResultDto();
        final PaymentProviderNotification notification = getServicesTestHelper().createAdyenPaymentProviderNotification();
        paymentProviderNotificationService.updatePaymentForNotification(notification.getId(), payment.getId());
        flushAndClear();
        // Create payment result
        PaymentResult paymentResult = getService().createPaymentResultForPayment(payment.getId(), notification.getId(), null, paymentResultDto);
        assertNotNull(paymentResult);
        // Flush, reload and try again
        flushAndClear();
        // Try to crate new payment result
        try {
            getService().createPaymentResultForPayment(payment.getId(), notification.getId(), null, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final PaymentResultAlreadyExistsForNotificationException ex) {
            // Expected
        }
    }

    /* Uti;ity methods */
    private void assertAdyenPaymentResult(final PaymentResult paymentResult, final PaymentProviderNotification notification, final AdyenRedirectResult adyenRedirectResult, final AdyenPaymentResultDto paymentResultDto) {
        assertNotNull(paymentResult);
        final PaymentResult unProxiedPaymentResult = persistenceUtilityService.initializeAndUnProxy(paymentResult);
        assertTrue(unProxiedPaymentResult instanceof AdyenPaymentResult);
        final AdyenPaymentResult adyenPaymentResult = (AdyenPaymentResult) unProxiedPaymentResult;
        getServicesTestHelper().assertAdyenPaymentResult(adyenPaymentResult, paymentResultDto);
        assertNotNull(paymentResult.getNotification());
        assertEquals(notification.getId(), paymentResult.getNotification().getId());
        assertNotNull(paymentResult.getRedirectResult());
        assertEquals(adyenRedirectResult.getId(), paymentResult.getRedirectResult().getId());
    }

    /* Abstract methods */
    protected abstract AbstractPaymentService<T> getService();

    protected abstract T getInstance();
}
