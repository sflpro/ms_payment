package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.repositories.payment.notification.AbstractPaymentProviderNotificationRepository;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationRequestService;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationNotFoundForIdException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 9:51 AM
 */
public abstract class AbstractPaymentProviderNotificationServiceImplTest<T extends PaymentProviderNotification> extends AbstractServicesUnitTest {

    /* Mocks  */
    @Mock
    private PaymentProviderNotificationRequestService paymentProviderNotificationRequestService;

    @Mock
    private PaymentService paymentService;

    /* Constructors */
    public AbstractPaymentProviderNotificationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentForNotificationWithInvalidArguments() {
        // Test data
        final Long paymentId = 1L;
        final Long notificationId = 2L;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentForNotification(null, paymentId);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            getService().updatePaymentForNotification(notificationId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentForNotificationWithNotExistingNotificationId() {
        // Test data
        final Long paymentId = 1L;
        final Long notificationId = 2L;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(notificationId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().updatePaymentForNotification(notificationId, paymentId);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderNotificationNotFoundForIdException(ex, notificationId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentForNotification() {
        // Test data
        final Long paymentId = 1L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final Long notificationId = 2L;
        final T notification = getInstance();
        notification.setId(notificationId);
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(notificationId))).andReturn(notification).once();
        expect(paymentService.getPaymentById(eq(paymentId))).andReturn(payment).once();
        expect(getRepository().save(isA(getInstanceClass()))).andAnswer(() -> (T) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().updatePaymentForNotification(notificationId, paymentId);
        assertEquals(payment, result.getPayment());
        // Verify
        verifyAll();
    }


    @Test
    public void testGetPaymentProviderNotificationByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderNotificationById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderNotificationByIdWithNotExistingId() {
        // Test data
        final Long notificationId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(notificationId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderNotificationById(notificationId);
            fail("Exception should be thrown");
        } catch (final PaymentProviderNotificationNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderNotificationNotFoundForIdException(ex, notificationId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderNotificationById() {
        // Test data
        final Long notificationId = 1L;
        final T notification = getInstance();
        notification.setId(notificationId);
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(notificationId))).andReturn(notification).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentProviderNotificationById(notificationId);
        assertEquals(notification, result);
        // Verify
        verifyAll();
    }

    /* Abstract methods */
    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();

    protected abstract AbstractPaymentProviderNotificationRepository<T> getRepository();

    protected abstract AbstractPaymentProviderNotificationService<T> getService();

    /* Utility methods */
    protected PaymentProviderNotificationRequestService getPaymentProviderNotificationRequestService() {
        return paymentProviderNotificationRequestService;
    }

    protected void assertPaymentProviderNotificationNotFoundForIdException(final PaymentProviderNotificationNotFoundForIdException ex, final Long notificationId) {
        Assert.assertEquals(notificationId, ex.getId());
        Assert.assertEquals(getInstanceClass(), ex.getEntityClass());
    }
}
