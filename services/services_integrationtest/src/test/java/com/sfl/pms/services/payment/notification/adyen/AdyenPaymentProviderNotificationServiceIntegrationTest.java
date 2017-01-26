package com.sfl.pms.services.payment.notification.adyen;

import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationServiceIntegrationTest;
import com.sfl.pms.services.payment.notification.dto.adyen.AdyenPaymentProviderNotificationDto;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:14 AM
 */
public class AdyenPaymentProviderNotificationServiceIntegrationTest extends AbstractPaymentProviderNotificationServiceIntegrationTest<AdyenPaymentProviderNotification> {

    /* Dependencies */
    @Autowired
    private AdyenPaymentProviderNotificationService adyenPaymentProviderNotificationService;

    /* Constructors */
    public AdyenPaymentProviderNotificationServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentProviderNotification() {
        // Prepare data
        final AdyenPaymentProviderNotificationDto notificationDto = getServicesTestHelper().createAdyenPaymentProviderNotificationDto();
        final PaymentProviderNotificationRequest request = getServicesTestHelper().createPaymentProviderNotificationRequest();
        flushAndClear();
        // Create notification
        AdyenPaymentProviderNotification notification = adyenPaymentProviderNotificationService.createPaymentProviderNotification(request.getId(), notificationDto);
        assertPaymentProviderNotification(notification, notificationDto, request);
        // Flush, clear, reload and assert again
        flushAndClear();
        notification = adyenPaymentProviderNotificationService.getPaymentProviderNotificationById(notification.getId());
        assertPaymentProviderNotification(notification, notificationDto, request);
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderNotificationService<AdyenPaymentProviderNotification> getService() {
        return adyenPaymentProviderNotificationService;
    }

    @Override
    protected Class<AdyenPaymentProviderNotification> getInstanceClass() {
        return AdyenPaymentProviderNotification.class;
    }

    @Override
    protected AdyenPaymentProviderNotification getInstance() {
        return getServicesTestHelper().createAdyenPaymentProviderNotification();
    }

    private void assertPaymentProviderNotification(final AdyenPaymentProviderNotification notification, AdyenPaymentProviderNotificationDto notificationDto, final PaymentProviderNotificationRequest request) {
        getServicesTestHelper().assertAdyenPaymentProviderNotification(notification, notificationDto);
        assertNotNull(notification.getRequest());
        assertEquals(request.getId(), notification.getRequest().getId());
    }
}
