package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationRequestService;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.AdyenNotificationProcessor;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.apache.commons.lang3.mutable.MutableInt;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 6:57 PM
 */
public class PaymentProviderNotificationProcessorImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderNotificationProcessingServiceImpl paymentProviderNotificationProcessingService = new PaymentProviderNotificationProcessingServiceImpl();

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private AdyenNotificationProcessor adyenNotificationProcessor;

    @Mock
    private PaymentProviderNotificationService paymentProviderNotificationService;

    @Mock
    private PaymentProviderNotificationRequestService paymentProviderNotificationRequestService;

    /* Constructors */
    public PaymentProviderNotificationProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderNotificationRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderNotificationRequest() {
        // Test data
        final Long notificationRequestId = 1L;
        final PaymentProviderNotificationRequest notificationRequest = getServicesImplTestHelper().createPaymentProviderNotificationRequest();
        notificationRequest.setId(notificationRequestId);
        final List<PaymentProviderNotification> notifications = createNotifications(10);
        // Reset
        resetAll();
        // Expectations
        expect(paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(eq(notificationRequestId), EasyMock.eq(PaymentProviderNotificationRequestState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(PaymentProviderNotificationRequestState.CREATED))))).andReturn(notificationRequest).once();
        expect(paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(eq(notificationRequestId))).andReturn(notificationRequest).once();
        expect(adyenNotificationProcessor.createPaymentProviderNotificationForRequest(eq(notificationRequest))).andReturn(notifications).once();
        notifications.forEach(notification -> {
            expect(paymentProviderNotificationService.updatePaymentProviderNotificationState(EasyMock.eq(notification.getId()), EasyMock.eq(PaymentProviderNotificationState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(PaymentProviderNotificationState.CREATED))))).andReturn(notification).once();
            expect(paymentProviderNotificationService.getPaymentProviderNotificationById(EasyMock.eq(notification.getId()))).andReturn(notification).once();
            adyenNotificationProcessor.processPaymentProviderNotification(eq(notification));
            expectLastCall().once();
            expect(paymentProviderNotificationService.updatePaymentProviderNotificationState(EasyMock.eq(notification.getId()), eq(PaymentProviderNotificationState.PROCESSED), eq(new LinkedHashSet<>()))).andReturn(notification).once();
        });
        expect(paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(eq(notificationRequestId), eq(PaymentProviderNotificationRequestState.PROCESSED), eq(new LinkedHashSet<>()))).andReturn(notificationRequest).once();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        // Replay
        replayAll();
        // Run test scenario
        final List<Long> result = paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(notificationRequestId);
        assertEquals(notifications.size(), result.size());
        final MutableInt counter = new MutableInt(0);
        notifications.forEach(notification -> {
            final Long resultId = result.get(counter.intValue());
            Assert.assertEquals(notification.getId(), resultId);
            counter.increment();
        });
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private List<PaymentProviderNotification> createNotifications(final int count) {
        final List<PaymentProviderNotification> notifications = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final AdyenPaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
            notification.setId(Long.valueOf(i));
            notifications.add(notification);
        }
        return notifications;
    }
}
