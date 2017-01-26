package com.sfl.pms.services.payment.notification.impl.processors.adyen;

import com.sfl.pms.externalclients.payment.adyen.model.datatypes.AdyenPaymentNotificationEventType;
import com.sfl.pms.externalclients.payment.adyen.model.datatypes.AdyenPaymentStatus;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.encryption.EncryptionUtilityService;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.adyen.AdyenPaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.adyen.AdyenPaymentMethodType;
import com.sfl.pms.services.payment.notification.adyen.AdyenPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.dto.adyen.AdyenPaymentProviderNotificationDto;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.AdyenNotificationJsonDeserializer;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationAdditionalDataJsonModel;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationAmountJsonModel;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationJsonModel;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationRequestItemJsonModel;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.adyen.AdyenPaymentProviderNotification;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultType;
import com.sfl.pms.services.payment.processing.dto.order.OrderPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.impl.PaymentResultProcessor;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 12:03 PM
 */
public class AdyenNotificationProcessorImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private AdyenNotificationProcessorImpl adyenNotificationProcessor = new AdyenNotificationProcessorImpl();

    @Mock
    private AdyenPaymentProviderNotificationService adyenPaymentProviderNotificationService;

    @Mock
    private AdyenNotificationJsonDeserializer adyenNotificationJsonDeserializer;

    @Mock
    private EncryptionUtilityService encryptionUtilityService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaymentResultProcessor paymentResultProcessor;

    /* Constructors */
    public AdyenNotificationProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPaymentProviderNotificationWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenNotificationProcessor.processPaymentProviderNotification(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderNotificationWithSuccessfulAuthorizationEventType() {
        // Test data
        final Long notificationId = 1L;
        final AdyenPaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setEventCode(AdyenPaymentNotificationEventType.AUTHORIZATION.getEventCode());
        notification.setId(notificationId);
        notification.setSuccess(true);
        final Long paymentId = 2L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final AdyenPaymentResultDto adyenPaymentResultDto = createSuccessfulAdyenPaymentResultDto(notification);
        final PaymentProcessingResultDetailedInformationDto processingResultDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentByUuId(eq(notification.getMerchantReference()))).andReturn(payment).once();
        expect(adyenPaymentProviderNotificationService.updatePaymentForNotification(eq(notification.getId()), eq(payment.getId()))).andReturn(notification).once();
        expect(paymentResultProcessor.processPaymentResult(eq(payment.getId()), eq(notificationId), isNull(), eq(adyenPaymentResultDto))).andReturn(new PaymentProcessingResultDto(processingResultDto, PaymentProcessingResultType.RESULT_PROCESSED)).once();
        expect(paymentService.updateConfirmedPaymentMethodType(eq(paymentId), EasyMock.eq(PaymentMethodType.getPaymentMethodTypeForAdyenPaymentMethod(notification.getPaymentMethodType())))).andReturn(payment).once();
        // Replay
        replayAll();
        // Run test scenario
        adyenNotificationProcessor.processPaymentProviderNotification(notification);
        // verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderNotificationWithNotSuccessfulAuthorizationEventType() {
        // Test data
        final Long notificationId = 1L;
        final AdyenPaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setEventCode(AdyenPaymentNotificationEventType.AUTHORIZATION.getEventCode());
        notification.setId(notificationId);
        notification.setSuccess(false);
        final Long paymentId = 2L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        final AdyenPaymentResultDto adyenPaymentResultDto = createNotSuccessfulAdyenPaymentResultDto(notification);
        final PaymentProcessingResultDetailedInformationDto processingResultDto = new OrderPaymentProcessingResultDetailedInformationDto(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentByUuId(eq(notification.getMerchantReference()))).andReturn(payment).once();
        expect(adyenPaymentProviderNotificationService.updatePaymentForNotification(eq(notification.getId()), eq(payment.getId()))).andReturn(notification).once();
        expect(paymentResultProcessor.processPaymentResult(eq(payment.getId()), eq(notificationId), isNull(), eq(adyenPaymentResultDto))).andReturn(new PaymentProcessingResultDto(processingResultDto, PaymentProcessingResultType.RESULT_PROCESSED)).once();
        expect(paymentService.updateConfirmedPaymentMethodType(eq(paymentId), eq(PaymentMethodType.getPaymentMethodTypeForAdyenPaymentMethod(notification.getPaymentMethodType())))).andReturn(payment).once();
        // Replay
        replayAll();
        // Run test scenario
        adyenNotificationProcessor.processPaymentProviderNotification(notification);
        // verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentProviderNotificationWithNonAuthorizationEventType() {
        // Test data
        final Long notificationId = 1L;
        final AdyenPaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setEventCode(AdyenPaymentNotificationEventType.CANCELLATION.getEventCode());
        notification.setId(notificationId);
        notification.setSuccess(false);
        final Long paymentId = 2L;
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        payment.setId(paymentId);
        // Reset
        resetAll();
        // Expectations
        expect(paymentService.getPaymentByUuId(eq(notification.getMerchantReference()))).andReturn(payment).once();
        expect(adyenPaymentProviderNotificationService.updatePaymentForNotification(eq(notification.getId()), eq(payment.getId()))).andReturn(notification).once();
        expect(paymentService.updateConfirmedPaymentMethodType(eq(paymentId), eq(PaymentMethodType.getPaymentMethodTypeForAdyenPaymentMethod(notification.getPaymentMethodType())))).andReturn(payment).once();
        // Replay
        replayAll();
        // Run test scenario
        adyenNotificationProcessor.processPaymentProviderNotification(notification);
        // verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProviderNotificationForRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenNotificationProcessor.createPaymentProviderNotificationForRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProviderNotificationForRequest() {
        // Test data
        final Long notificationRequestId = 1L;
        final PaymentProviderNotificationRequest notificationRequest = getServicesImplTestHelper().createPaymentProviderNotificationRequest();
        notificationRequest.setId(notificationRequestId);
        final AdyenNotificationJsonModel notificationJsonModel = getServicesImplTestHelper().createAdyenNotificationJsonModel();
        final AdyenPaymentProviderNotificationDto expectedNotificationDto = buildAdyenNotificationDto(notificationRequest, notificationJsonModel);
        final Long notificationId = 2L;
        final AdyenPaymentProviderNotification notification = getServicesImplTestHelper().createAdyenPaymentProviderNotification();
        notification.setId(notificationId);
        // Reset
        resetAll();
        // Expectations
        expect(adyenNotificationJsonDeserializer.deserializeAdyenNotification(eq(notificationRequest.getRawContent()))).andReturn(notificationJsonModel).once();
        expect(adyenPaymentProviderNotificationService.createPaymentProviderNotification(eq(notificationRequestId), eq(expectedNotificationDto))).andReturn(notification).once();
        // Replay
        replayAll();
        // Run test scenario
        final List<PaymentProviderNotification> result = adyenNotificationProcessor.createPaymentProviderNotificationForRequest(notificationRequest);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(notification, result.get(0));
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private AdyenPaymentProviderNotificationDto buildAdyenNotificationDto(final PaymentProviderNotificationRequest notificationRequest, final AdyenNotificationJsonModel notificationJsonModel) {
        // Deserialize Adyen notification raw content
        final AdyenNotificationRequestItemJsonModel itemJsonModel = notificationJsonModel.getFirstAdyenNotificationRequestItemJsonModel();
        // Create notification DTO
        final AdyenPaymentProviderNotificationDto notificationDto = new AdyenPaymentProviderNotificationDto();
        // Set properties
        notificationDto.setRawContent(notificationRequest.getRawContent());
        notificationDto.setClientIpAddress(notificationRequest.getClientIpAddress());
        // Set JSON properties
        if (itemJsonModel != null) {
            notificationDto.setPspReference(itemJsonModel.getPspReference());
            if (itemJsonModel.getPaymentMethod() != null) {
                notificationDto.setPaymentMethodType(AdyenPaymentMethodType.getAdyenPaymentMethodTypeForCode(itemJsonModel.getPaymentMethod()));
            }
            notificationDto.setMerchantReference(itemJsonModel.getMerchantReference());
            notificationDto.setEventCode(itemJsonModel.getEventCode());
            if (itemJsonModel.getAmount() != null) {
                final AdyenNotificationAmountJsonModel amountJsonModel = itemJsonModel.getAmount();
                notificationDto.setCurrency(Currency.valueOf(amountJsonModel.getCurrency()));
                notificationDto.setAmount(BigDecimal.valueOf(amountJsonModel.getValue()));
            }
            notificationDto.setSuccess(itemJsonModel.getSuccess());
            if (itemJsonModel.getAdditionalData() != null) {
                final AdyenNotificationAdditionalDataJsonModel additionalDataJsonModel = itemJsonModel.getAdditionalData();
                notificationDto.setAuthCode(additionalDataJsonModel.getAuthCode());
            }
        }
        return notificationDto;
    }

    private AdyenPaymentResultDto createSuccessfulAdyenPaymentResultDto(final AdyenPaymentProviderNotification notification) {
        final AdyenPaymentResultDto adyenPaymentResultDto = new AdyenPaymentResultDto();
        adyenPaymentResultDto.setRefusalReason(null);
        adyenPaymentResultDto.setAuthCode(notification.getAuthCode());
        adyenPaymentResultDto.setPspReference(notification.getPspReference());
        adyenPaymentResultDto.setStatus(PaymentResultStatus.PAID);
        adyenPaymentResultDto.setResultCode(AdyenPaymentStatus.AUTHORISED.getResult());
        return adyenPaymentResultDto;
    }

    private AdyenPaymentResultDto createNotSuccessfulAdyenPaymentResultDto(final AdyenPaymentProviderNotification notification) {
        final AdyenPaymentResultDto adyenPaymentResultDto = new AdyenPaymentResultDto();
        adyenPaymentResultDto.setRefusalReason(null);
        adyenPaymentResultDto.setAuthCode(notification.getAuthCode());
        adyenPaymentResultDto.setPspReference(notification.getPspReference());
        adyenPaymentResultDto.setStatus(PaymentResultStatus.REFUSED);
        adyenPaymentResultDto.setResultCode(AdyenPaymentStatus.REFUSED.getResult());
        return adyenPaymentResultDto;
    }
}
