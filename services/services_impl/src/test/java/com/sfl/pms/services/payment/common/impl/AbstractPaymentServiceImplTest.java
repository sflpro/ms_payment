package com.sfl.pms.services.payment.common.impl;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.persistence.repositories.payment.common.PaymentResultRepository;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.dto.adyen.AdyenPaymentResultDto;
import com.sfl.pms.services.payment.common.exception.*;
import com.sfl.pms.services.payment.common.impl.channel.*;
import com.sfl.pms.services.payment.common.impl.provider.PaymentResultHandler;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.redirect.PaymentProviderRedirectResultService;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.junit.Test;

import java.util.Date;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:20 PM
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPaymentServiceImplTest<T extends Payment> extends AbstractServicesUnitTest {

    /* Mocks */
    @Mock
    private PaymentResultHandler adyenPaymentResultHandler;

    @Mock
    private PaymentResultRepository paymentResultRepository;

    @Mock
    private PaymentProviderNotificationService paymentProviderNotificationService;

    @Mock
    private PaymentProviderRedirectResultService paymentProviderRedirectResultService;

    @Mock
    private CustomerPaymentMethodProcessingChannelHandler customerPaymentMethodProcessingChannelHandler;

    @Mock
    private DeferredPaymentMethodProcessingChannelHandler deferredPaymentMethodProcessingChannelHandler;

    @Mock
    private EncryptedPaymentMethodProcessingChannelHandler encryptedPaymentMethodProcessingChannelHandler;

    @Mock
    private ProvidedPaymentMethodProcessingChannelHandler providedPaymentMethodProcessingChannelHandler;

    /* Constructors */
    public AbstractPaymentServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentByUuIdWithNotExistingUuid() {
        // Test data
        final String uuId = "JHFTYFOTFYFITFOYGYO";
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(uuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentByUuId(uuId);
            fail("Exception should be thrown");
        } catch (final PaymentNotFoundForUuIdException ex) {
            // Expected
            assertPaymentNotFoundForUuIdException(ex, uuId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentByUuId() {
        // Test data
        final String uuId = "JHFTYFOTFYFITFOYGYO";
        final Long paymentId = 1L;
        final T payment = getInstance();
        payment.setId(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findByUuId(eq(uuId))).andReturn(payment).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentByUuId(uuId);
        assertEquals(payment, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithInvalidArguments() {
        // Test data
        final Long paymentId = 1l;
        final Long notificationId = 2L;
        final Long redirectResultId = 3L;
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(null, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithNotExistingPaymentId() {
        // Test data
        final Long paymentId = 1l;
        final Long notificationId = 2L;
        final Long redirectResultId = 3L;
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final PaymentNotFoundForIdException ex) {
            // Expected
            assertPaymentNotFoundForIdException(ex, paymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithNotificationWhichDoesNotHavePayment() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long notificationId = 2L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(null);
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long redirectResultId = 3L;
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final InvalidNotificationForPaymentException ex) {
            // Expected
            assertInvalidNotificationForPaymentException(ex, notificationId, paymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithRedirectResultWhichDoesNotHavePayment() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long notificationId = 2L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(payment);
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long redirectResultId = 3L;
        final PaymentProviderRedirectResult redirectResult = getServicesImplTestHelper().createAdyenRedirectResult();
        redirectResult.setId(redirectResultId);
        redirectResult.setPayment(null);
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        expect(paymentResultRepository.findByNotification(eq(notification))).andReturn(null).once();
        expect(paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(eq(redirectResultId))).andReturn(redirectResult).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final InvalidRedirectResultForPaymentException ex) {
            // Expected
            assertInvalidRedirectResultForPaymentException(ex, redirectResultId, paymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithNotificationWhichHasDifferentPayment() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long secondPaymentId = 2l;
        final T secondPayment = getInstance();
        secondPayment.setId(secondPaymentId);
        final Long notificationId = 3L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(secondPayment);
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long redirectResultId = 4L;
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final InvalidNotificationForPaymentException ex) {
            // Expected
            assertInvalidNotificationForPaymentException(ex, notificationId, paymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithRedirectResultWhichHasDifferentPayment() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long secondPaymentId = 2l;
        final T secondPayment = getInstance();
        secondPayment.setId(secondPaymentId);
        final Long notificationId = 3L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(payment);
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long redirectResultId = 4L;
        final PaymentProviderRedirectResult redirectResult = getServicesImplTestHelper().createAdyenRedirectResult();
        redirectResult.setId(redirectResultId);
        redirectResult.setPayment(secondPayment);
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        expect(paymentResultRepository.findByNotification(eq(notification))).andReturn(null).once();
        expect(paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(eq(redirectResultId))).andReturn(redirectResult).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final InvalidRedirectResultForPaymentException ex) {
            // Expected
            assertInvalidRedirectResultForPaymentException(ex, redirectResultId, paymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithNotificationWhichIsAlreadyAssociatedToResult() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long notificationId = 3L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(payment);
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long existingPaymentResultId = 4L;
        final PaymentResult existingPaymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        existingPaymentResult.setId(existingPaymentResultId);
        final Long redirectResultId = 5L;
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        expect(paymentResultRepository.findByNotification(eq(notification))).andReturn(existingPaymentResult).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final PaymentResultAlreadyExistsForNotificationException ex) {
            // Expected
            assertPaymentResultAlreadyExistsForNotificationException(ex, notificationId, existingPaymentResultId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPaymentWithRedirectResultWhichIsAlreadyAssociatedToResult() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long notificationId = 3L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(payment);
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final Long existingPaymentResultId = 4L;
        final PaymentResult existingPaymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        existingPaymentResult.setId(existingPaymentResultId);
        final Long redirectResultId = 5L;
        final PaymentProviderRedirectResult redirectResult = getServicesImplTestHelper().createAdyenRedirectResult();
        redirectResult.setId(redirectResultId);
        redirectResult.setPayment(payment);
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        expect(paymentResultRepository.findByNotification(eq(notification))).andReturn(null).once();
        expect(paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(eq(redirectResultId))).andReturn(redirectResult).once();
        expect(paymentResultRepository.findByRedirectResult(eq(redirectResult))).andReturn(existingPaymentResult).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final PaymentResultAlreadyExistsForRedirectResultException ex) {
            // Expected
            assertPaymentResultAlreadyExistsForRedirectResultException(ex, redirectResultId, existingPaymentResultId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentResultForPayment() {
        // Test data
        final Long paymentId = 1l;
        final T payment = getInstance();
        payment.setId(paymentId);
        final Long notificationId = 2L;
        final PaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        notification.setPayment(payment);
        final Date paymentUpdateDate = payment.getUpdated();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult(paymentResultDto);
        final Long redirectResultId = 3L;
        final PaymentProviderRedirectResult redirectResult = getServicesImplTestHelper().createAdyenRedirectResult();
        redirectResult.setId(redirectResultId);
        redirectResult.setPayment(payment);
        // Reset
        resetAll();
        // Expectations
        adyenPaymentResultHandler.assertPaymentResultDto(eq(paymentResultDto));
        expectLastCall();
        expect(getRepository().findOne(eq(paymentId))).andReturn(payment).once();
        expect(paymentProviderNotificationService.getPaymentProviderNotificationById(eq(notificationId))).andReturn(notification).once();
        expect(adyenPaymentResultHandler.convertPaymentResultDto(eq(paymentResultDto))).andReturn(paymentResult).once();
        expect(paymentResultRepository.findByNotification(eq(notification))).andReturn(null).once();
        expect(paymentResultRepository.findByRedirectResult(eq(redirectResult))).andReturn(null).once();
        expect(paymentProviderRedirectResultService.getPaymentProviderRedirectResultById(eq(redirectResultId))).andReturn(redirectResult).once();
        expect(getRepository().save(isA(getInstanceClass()))).andAnswer(() -> {
            final T savedPayment = (T) getCurrentArguments()[0];
            assertAdyenPaymentResult(savedPayment.getPaymentResults().iterator().next(), notification, redirectResult, paymentResultDto);
            assertTrue(savedPayment.getUpdated().compareTo(paymentUpdateDate) >= 0 && paymentUpdateDate != savedPayment.getUpdated());
            return savedPayment;
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentResult result = getService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
        assertAdyenPaymentResult(result, notification, redirectResult, paymentResultDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentByIdWithNotExistingId() {
        // Test data
        final Long paymentID = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(paymentID))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentById(paymentID);
            fail("Exception should be thrown");
        } catch (final PaymentNotFoundForIdException ex) {
            // Expected
            assertPaymentNotFoundForIdException(ex, paymentID);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentById() {
        // Test data
        final Long paymentID = 1l;
        final T payment = getInstance();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(paymentID))).andReturn(payment).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentById(paymentID);
        assertNotNull(result);
        assertEquals(payment, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertAdyenPaymentResult(final PaymentResult paymentResult, final PaymentProviderNotification notification, final PaymentProviderRedirectResult redirectResult, final AdyenPaymentResultDto paymentResultDto) {
        assertTrue(paymentResult instanceof AdyenPaymentResult);
        getServicesImplTestHelper().assertAdyenPaymentResult((AdyenPaymentResult) paymentResult, paymentResultDto);
        assertEquals(notification, paymentResult.getNotification());
        assertEquals(redirectResult, paymentResult.getRedirectResult());
    }

    protected void assertPaymentNotFoundForIdException(final PaymentNotFoundForIdException ex, final Long paymentId) {
        assertEquals(paymentId, ex.getId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    private void assertPaymentNotFoundForUuIdException(final PaymentNotFoundForUuIdException ex, final String uuId) {
        assertEquals(uuId, ex.getUuId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    private void assertInvalidNotificationForPaymentException(final InvalidNotificationForPaymentException ex, final Long notificationId, final Long paymentId) {
        assertEquals(notificationId, ex.getNotificationId());
        assertEquals(paymentId, ex.getPaymentId());
    }

    private void assertInvalidRedirectResultForPaymentException(final InvalidRedirectResultForPaymentException ex, final Long redirectResultId, final Long paymentId) {
        assertEquals(redirectResultId, ex.getRedirectResultId());
        assertEquals(paymentId, ex.getPaymentId());
    }

    private void assertPaymentResultAlreadyExistsForNotificationException(final PaymentResultAlreadyExistsForNotificationException ex, final Long notificationId, final Long existingPaymentResultId) {
        assertEquals(notificationId, ex.getNotificationId());
        assertEquals(existingPaymentResultId, ex.getExistingPaymentResultId());
    }

    private void assertPaymentResultAlreadyExistsForRedirectResultException(final PaymentResultAlreadyExistsForRedirectResultException ex, final Long redirectResultId, final Long existingPaymentResultId) {
        assertEquals(redirectResultId, ex.getRedirectResultId());
        assertEquals(existingPaymentResultId, ex.getExistingPaymentResultId());
    }

    protected PaymentProcessingChannelHandler getPaymentProcessingChannelHandler(final PaymentProcessingChannelType paymentProcessingChannelType) {
        switch (paymentProcessingChannelType) {
            case CUSTOMER_PAYMENT_METHOD:
                return customerPaymentMethodProcessingChannelHandler;
            case DEFERRED_PAYMENT_METHOD:
                return deferredPaymentMethodProcessingChannelHandler;
            case ENCRYPTED_PAYMENT_METHOD:
                return encryptedPaymentMethodProcessingChannelHandler;
            case PROVIDED_PAYMENT_METHOD:
                return providedPaymentMethodProcessingChannelHandler;
            default:
                throw new ServicesRuntimeException("Unknown payment processing channel type - " + paymentProcessingChannelType);
        }
    }

    /* Abstract methods */
    protected abstract AbstractPaymentService<T> getService();

    protected abstract AbstractPaymentRepository<T> getRepository();

    protected abstract T getInstance();

    protected abstract Class<T> getInstanceClass();
}
