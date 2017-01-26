package com.sfl.pms.services.payment.notification.impl.adyen;

import com.sfl.pms.persistence.repositories.payment.notification.AbstractPaymentProviderNotificationRepository;
import com.sfl.pms.persistence.repositories.payment.notification.PaymentProviderNotificationRepository;
import com.sfl.pms.persistence.repositories.payment.notification.adyen.AdyenPaymentProviderNotificationRepository;
import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.dto.adyen.AdyenPaymentProviderNotificationDto;
import com.sfl.pms.services.payment.notification.impl.AbstractPaymentProviderNotificationServiceImplTest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 9:59 AM
 */
public class AdyenPaymentProviderNotificationServiceImplTest extends AbstractPaymentProviderNotificationServiceImplTest<AdyenPaymentProviderNotification> {

    /* Test subject and mocks */
    @TestSubject
    private AdyenPaymentProviderNotificationServiceImpl adyenPaymentProviderNotificationService = new AdyenPaymentProviderNotificationServiceImpl();

    @Mock
    private AdyenPaymentProviderNotificationRepository adyenPaymentProviderNotificationRepository;

    @Mock
    private PaymentProviderNotificationRepository paymentProviderNotificationRepository;

    /* Constructors */
    public AdyenPaymentProviderNotificationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentProviderNotificationWithInvalidArguments() {
        // Test data
        final Long requestId = 1L;
        final AdyenPaymentProviderNotificationDto notificationDto = getServicesImplTestHelper().createAdyenPaymentProviderNotificationDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenPaymentProviderNotificationService.createPaymentProviderNotification(null, notificationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentProviderNotificationService.createPaymentProviderNotification(requestId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentProviderNotificationService.createPaymentProviderNotification(requestId, new AdyenPaymentProviderNotificationDto(null, notificationDto.getClientIpAddress(), notificationDto.getEventCode(), notificationDto.getAuthCode(), notificationDto.getPspReference(), notificationDto.getSuccess(), notificationDto.getMerchantReference(), notificationDto.getPaymentMethodType(), notificationDto.getCurrency(), notificationDto.getAmount()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProviderNotification() {
        // Test data
        final Long requestId = 1L;
        final PaymentProviderNotificationRequest request = getServicesImplTestHelper().createPaymentProviderNotificationRequest();
        request.setId(requestId);
        final AdyenPaymentProviderNotificationDto notificationDto = getServicesImplTestHelper().createAdyenPaymentProviderNotificationDto();
        // Reset
        resetAll();
        // Expectations
        expect(getPaymentProviderNotificationRequestService().getPaymentProviderNotificationRequestById(eq(requestId))).andReturn(request).once();
        expect(adyenPaymentProviderNotificationRepository.save(isA(AdyenPaymentProviderNotification.class))).andAnswer(() -> (AdyenPaymentProviderNotification) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final AdyenPaymentProviderNotification notification = adyenPaymentProviderNotificationService.createPaymentProviderNotification(requestId, notificationDto);
        getServicesImplTestHelper().assertAdyenPaymentProviderNotification(notification, notificationDto);
        Assert.assertEquals(request, notification.getRequest());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected AdyenPaymentProviderNotification getInstance() {
        return getServicesImplTestHelper().createAdyenPaymentProviderNotification();
    }

    @Override
    protected Class<AdyenPaymentProviderNotification> getInstanceClass() {
        return AdyenPaymentProviderNotification.class;
    }

    @Override
    protected AbstractPaymentProviderNotificationRepository<AdyenPaymentProviderNotification> getRepository() {
        return adyenPaymentProviderNotificationRepository;
    }

    @Override
    protected AbstractPaymentProviderNotificationService<AdyenPaymentProviderNotification> getService() {
        return adyenPaymentProviderNotificationService;
    }
}
