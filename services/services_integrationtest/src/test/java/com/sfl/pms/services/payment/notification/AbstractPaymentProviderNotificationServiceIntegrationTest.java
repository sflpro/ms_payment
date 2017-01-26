package com.sfl.pms.services.payment.notification;

import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:02 AM
 */
@Ignore
public abstract class AbstractPaymentProviderNotificationServiceIntegrationTest<T extends PaymentProviderNotification> extends AbstractServiceIntegrationTest {


    /* Constructors */
    public AbstractPaymentProviderNotificationServiceIntegrationTest() {
    }


    /* Test methods */
    @Test
    public void testUpdatePaymentForNotification() {
        // Prepare data
        final T notification = getInstance();
        final Payment payment = getServicesTestHelper().createOrderPayment();
        // Update payment
        T result = getService().updatePaymentForNotification(notification.getId(), payment.getId());
        assertNotNull(result);
        assertNotNull(result.getPayment());
        assertEquals(payment.getId(), result.getPayment().getId());
        // Flush, clear, reload and assert again
        flushAndClear();
        result = getService().getPaymentProviderNotificationById(result.getId());
        assertNotNull(result.getPayment());
        assertEquals(payment.getId(), result.getPayment().getId());
    }

    @Test
    public void testGetPaymentProviderNotificationById() {
        // Prepare data
        final T notification = getInstance();
        // Load and assert
        T result = getService().getPaymentProviderNotificationById(notification.getId());
        assertEquals(notification, result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = getService().getPaymentProviderNotificationById(notification.getId());
        assertEquals(notification, result);
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderNotificationService<T> getService();

    protected abstract Class<T> getInstanceClass();

    protected abstract T getInstance();
}
