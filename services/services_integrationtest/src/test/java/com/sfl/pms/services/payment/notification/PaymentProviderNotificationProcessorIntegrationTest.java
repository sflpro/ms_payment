package com.sfl.pms.services.payment.notification;

import com.sfl.pms.externalclients.payment.adyen.model.datatypes.AdyenPaymentStatus;
import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.common.order.OrderPaymentService;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.notification.dto.PaymentProviderNotificationRequestDto;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/30/15
 * Time: 11:33 AM
 */
public class PaymentProviderNotificationProcessorIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationProcessingService paymentProviderNotificationProcessingService;

    @Autowired
    private PaymentProviderNotificationRequestService paymentProviderNotificationRequestService;

    @Autowired
    private PaymentProviderNotificationService paymentProviderNotificationService;

    @Autowired
    private OrderPaymentService orderPaymentService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;


    /* Constructors */
    public PaymentProviderNotificationProcessorIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderNotificationRequestWhenPaymentIsNotYetPaid() {
        // Prepare data
        OrderPayment payment = getServicesTestHelper().createOrderPayment();
        assertEquals(PaymentState.CREATED, payment.getLastState());
        final String authCode = "98765";
        final String pspReference = "87756454678909087768723484534";
        final String notificationRawContent = getServicesTestHelper().getAdyenNotificationRawContent(payment.getUuId(), authCode, pspReference);
        final PaymentProviderNotificationRequestDto requestDto = getServicesTestHelper().createPaymentProviderNotificationRequestDto();
        requestDto.setRawContent(notificationRawContent);
        // Create notification result
        PaymentProviderNotificationRequest notificationRequest = getServicesTestHelper().createPaymentProviderNotificationRequest(requestDto);
        flushAndClear();
        // Start notifications processing
        final List<Long> notificationIds = paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(notificationRequest.getId());
        assertEquals(1, notificationIds.size());
        PaymentProviderNotification notification = paymentProviderNotificationService.getPaymentProviderNotificationById(notificationIds.get(0));
        notification = persistenceUtilityService.initializeAndUnProxy(notification);
        assertTrue(notification instanceof AdyenPaymentProviderNotification);
        // Assert notification
        final AdyenPaymentProviderNotification adyenPaymentProviderNotification = (AdyenPaymentProviderNotification) notification;
        assertEquals(notificationRequest.getId(), notification.getRequest().getId());
        assertNotNull(notification.getPayment());
        assertEquals(payment.getId(), notification.getPayment().getId());
        // Reload notification request and assert states
        notificationRequest = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(notificationRequest.getId());
        assertEquals(PaymentProviderNotificationRequestState.PROCESSED, notificationRequest.getState());
        assertEquals(PaymentProviderNotificationState.PROCESSED, notification.getState());
        // Reload payment and check if it is paid
        payment = orderPaymentService.getPaymentById(payment.getId());
        assertEquals(PaymentState.PAID, payment.getLastState());
        // Grab payment result
        assertEquals(1, payment.getPaymentResults().size());
        PaymentResult paymentResult = payment.getPaymentResults().iterator().next();
        paymentResult = persistenceUtilityService.initializeAndUnProxy(paymentResult);
        // Check that payment result is paid
        assertEquals(PaymentResultStatus.PAID, paymentResult.getStatus());
        assertEquals(PaymentMethodType.getPaymentMethodTypeForAdyenPaymentMethod(adyenPaymentProviderNotification.getPaymentMethodType()), payment.getConfirmedPaymentMethodType());
        // Check that result is associated with notification
        assertNotNull(paymentResult.getNotification());
        assertEquals(notification.getId(), paymentResult.getNotification().getId());
        // Check Adyen payment result specific fields
        assertTrue(paymentResult instanceof AdyenPaymentResult);
        final AdyenPaymentResult adyenPaymentResult = (AdyenPaymentResult) paymentResult;
        assertEquals(authCode, adyenPaymentResult.getAuthCode());
        assertEquals(pspReference, adyenPaymentResult.getPspReference());
        assertEquals(AdyenPaymentStatus.AUTHORISED.getResult(), adyenPaymentResult.getResultCode());
    }

    @Test
    public void testProcessPaymentProviderNotificationRequestWhenPaymentAlreadyPaidAndNotificationHasSamePaymentInformation() {
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        // Prepare data
        OrderPayment payment = getServicesTestHelper().createOrderPaymentAndPayUsingAdyen();
        assertEquals(PaymentState.PAID, payment.getLastState());
        assertEquals(1, payment.getPaymentResults().size());
        PaymentResult initialPaymentResult = payment.getPaymentResults().iterator().next();
        initialPaymentResult = persistenceUtilityService.initializeAndUnProxy(initialPaymentResult);
        assertTrue(initialPaymentResult instanceof AdyenPaymentResult);
        final AdyenPaymentResult adyenPaymentResult = (AdyenPaymentResult) initialPaymentResult;
        assertNull(initialPaymentResult.getNotification());
        // Check that payment result is paid
        assertEquals(PaymentResultStatus.PAID, initialPaymentResult.getStatus());
        // Create Adyen notification
        final String notificationRawContent = getServicesTestHelper().getAdyenNotificationRawContent(payment.getUuId(), adyenPaymentResult.getAuthCode(), adyenPaymentResult.getPspReference());
        final PaymentProviderNotificationRequestDto requestDto = getServicesTestHelper().createPaymentProviderNotificationRequestDto();
        requestDto.setRawContent(notificationRawContent);
        // Create notification result
        PaymentProviderNotificationRequest notificationRequest = getServicesTestHelper().createPaymentProviderNotificationRequest(requestDto);
        flushAndClear();
        // Start notifications processing
        final List<Long> notificationIds = paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(notificationRequest.getId());
        assertEquals(1, notificationIds.size());
        PaymentProviderNotification notification = paymentProviderNotificationService.getPaymentProviderNotificationById(notificationIds.get(0));
        notification = persistenceUtilityService.initializeAndUnProxy(notification);
        assertTrue(notification instanceof AdyenPaymentProviderNotification);
        // Assert notification
        final AdyenPaymentProviderNotification adyenPaymentProviderNotification = (AdyenPaymentProviderNotification) notification;
        assertEquals(notificationRequest.getId(), notification.getRequest().getId());
        assertNotNull(notification.getPayment());
        assertEquals(payment.getId(), notification.getPayment().getId());
        // Reload notification request and assert states
        notificationRequest = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(notificationRequest.getId());
        assertEquals(PaymentProviderNotificationRequestState.PROCESSED, notificationRequest.getState());
        assertEquals(PaymentProviderNotificationState.PROCESSED, notification.getState());
        // Reload payment and check if it is paid
        payment = orderPaymentService.getPaymentById(payment.getId());
        assertEquals(PaymentState.PAID, payment.getLastState());
        assertEquals(PaymentMethodType.getPaymentMethodTypeForAdyenPaymentMethod(adyenPaymentProviderNotification.getPaymentMethodType()), payment.getConfirmedPaymentMethodType());
        // Grab payment result
        assertEquals(1, payment.getPaymentResults().size());
        PaymentResult paymentResult = payment.getPaymentResults().iterator().next();
        // Check that payment result is not changed
        assertEquals(initialPaymentResult.getId(), paymentResult.getId());
    }

    @Test
    public void testProcessPaymentProviderNotificationRequestWhenPaymentAlreadyPaidAndNotificationHasDifferentPaymentInformation() {
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        // Prepare data
        OrderPayment payment = getServicesTestHelper().createOrderPaymentAndPayUsingAdyen();
        assertEquals(PaymentState.PAID, payment.getLastState());
        assertEquals(1, payment.getPaymentResults().size());
        PaymentResult initialPaymentResult = payment.getPaymentResults().iterator().next();
        initialPaymentResult = persistenceUtilityService.initializeAndUnProxy(initialPaymentResult);
        assertTrue(initialPaymentResult instanceof AdyenPaymentResult);
        final AdyenPaymentResult adyenPaymentResult = (AdyenPaymentResult) initialPaymentResult;
        assertNull(initialPaymentResult.getNotification());
        // Check that payment result is paid
        assertEquals(PaymentResultStatus.PAID, initialPaymentResult.getStatus());
        // Create Adyen notification
        final String authCode = adyenPaymentResult.getAuthCode() + "_u";
        final String pspReference = adyenPaymentResult.getPspReference() + "_u";
        final String notificationRawContent = getServicesTestHelper().getAdyenNotificationRawContent(payment.getUuId(), authCode, pspReference);
        final PaymentProviderNotificationRequestDto requestDto = getServicesTestHelper().createPaymentProviderNotificationRequestDto();
        requestDto.setRawContent(notificationRawContent);
        // Create notification result
        PaymentProviderNotificationRequest notificationRequest = getServicesTestHelper().createPaymentProviderNotificationRequest(requestDto);
        flushAndClear();
        // Start notifications processing
        final List<Long> notificationIds = paymentProviderNotificationProcessingService.processPaymentProviderNotificationRequest(notificationRequest.getId());
        assertEquals(1, notificationIds.size());
        PaymentProviderNotification notification = paymentProviderNotificationService.getPaymentProviderNotificationById(notificationIds.get(0));
        notification = persistenceUtilityService.initializeAndUnProxy(notification);
        assertTrue(notification instanceof AdyenPaymentProviderNotification);
        // Assert notification
        final AdyenPaymentProviderNotification adyenPaymentProviderNotification = (AdyenPaymentProviderNotification) notification;
        assertEquals(notificationRequest.getId(), notification.getRequest().getId());
        assertNotNull(notification.getPayment());
        assertEquals(payment.getId(), notification.getPayment().getId());
        // Reload notification request and assert states
        notificationRequest = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(notificationRequest.getId());
        assertEquals(PaymentProviderNotificationRequestState.PROCESSED, notificationRequest.getState());
        assertEquals(PaymentProviderNotificationState.PROCESSED, notification.getState());
        // Reload payment and check if it is paid
        payment = orderPaymentService.getPaymentById(payment.getId());
        assertEquals(PaymentState.PAID, payment.getLastState());
        assertEquals(PaymentMethodType.getPaymentMethodTypeForAdyenPaymentMethod(adyenPaymentProviderNotification.getPaymentMethodType()), payment.getConfirmedPaymentMethodType());
        // Check payment results
        assertEquals(2, payment.getPaymentResults().size());
        // Assert 2 payment results , one for notification and one for initial transaction
        PaymentResult paymentTransactionResult = null;
        PaymentResult paymentNotificationResult = null;
        for (final PaymentResult currentPaymentResult : payment.getPaymentResults()) {
            if (currentPaymentResult.getId().equals(initialPaymentResult.getId())) {
                paymentTransactionResult = currentPaymentResult;
            } else {
                paymentNotificationResult = currentPaymentResult;
            }
        }
        // Assert transaction payment results
        assertNotNull(paymentTransactionResult);
        // Assert notification payment result
        assertNotNull(paymentNotificationResult);
        // Check that payment result is paid
        assertEquals(PaymentResultStatus.PAID, paymentNotificationResult.getStatus());
        // Check that result is associated with notification
        assertNotNull(paymentNotificationResult.getNotification());
        assertEquals(notification.getId(), paymentNotificationResult.getNotification().getId());
        // Check Adyen payment result specific fields
        assertTrue(paymentNotificationResult instanceof AdyenPaymentResult);
        final AdyenPaymentResult adyenPaymentNotificationResult = (AdyenPaymentResult) paymentNotificationResult;
        assertEquals(authCode, adyenPaymentNotificationResult.getAuthCode());
        assertEquals(pspReference, adyenPaymentNotificationResult.getPspReference());
        assertEquals(AdyenPaymentStatus.AUTHORISED.getResult(), adyenPaymentNotificationResult.getResultCode());
    }
}
