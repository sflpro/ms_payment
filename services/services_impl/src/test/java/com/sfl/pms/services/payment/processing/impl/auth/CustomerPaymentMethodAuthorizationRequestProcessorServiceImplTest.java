package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.auth.CustomerPaymentMethodAuthorizationPaymentService;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.ProvidedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.exception.UnknownCustomerPaymentMethodAuthorizationRequestTypeException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestType;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.processing.PaymentProcessorService;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.auth.CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.redirect.PaymentRedirectProcessingInformationDto;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/5/15
 * Time: 3:41 PM
 */
public class CustomerPaymentMethodAuthorizationRequestProcessorServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodAuthorizationRequestProcessorServiceImpl customerPaymentMethodAuthorizationRequestProcessorService = new CustomerPaymentMethodAuthorizationRequestProcessorServiceImpl();

    @Mock
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    @Mock
    private CustomerPaymentMethodAuthorizationPaymentService customerPaymentMethodAuthorizationPaymentService;

    @Mock
    private PaymentProcessorService paymentProcessorService;

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;

    @Mock
    private ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestProcessorServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestWithEncryptedPaymentMethod() {
        /* Test data */
        // Request
        final Long requestId = 1L;
        final CustomerEncryptedPaymentMethodAuthorizationRequest request = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        request.setId(requestId);
        // Payment
        final Long paymentId = 2L;
        final CustomerPaymentMethodAuthorizationPayment authorizationPayment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        authorizationPayment.setId(paymentId);
        // Create processing channel DTO
        final PaymentProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = new EncryptedPaymentMethodProcessingChannelDto(PaymentProviderIntegrationType.API, request.getEncryptedData());
        // Payment DTO
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        // Created payment methods
        final List<Long> createdPaymentMethods = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestState(eq(requestId), eq(CustomerPaymentMethodAuthorizationRequestState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.CREATED, CustomerPaymentMethodAuthorizationRequestState.FAILED))))).andReturn(request).once();
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(requestId))).andReturn(request).times(2);
        expect(customerPaymentMethodAuthorizationPaymentService.createPayment(eq(requestId), eq(paymentDto), eq(encryptedPaymentMethodProcessingChannelDto))).andReturn(authorizationPayment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentProcessorService.processPayment(eq(paymentId))).andReturn(new CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto(paymentId, createdPaymentMethods)).once();
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestState(eq(requestId), eq(CustomerPaymentMethodAuthorizationRequestState.PROCESSED), eq(new LinkedHashSet<>()))).andReturn(request).once();
        expect(getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType()).createPaymentDto(eq(request))).andReturn(paymentDto).once();
        expect(getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType()).createPaymentProcessingChannelDto(eq(request))).andReturn(encryptedPaymentMethodProcessingChannelDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto detailedInformationDto = customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(requestId);
        assertTrue(detailedInformationDto instanceof CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto);
        final CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto paymentMethodsDetailsInformationDto = (CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto) detailedInformationDto;
        final List<Long> result = paymentMethodsDetailsInformationDto.getCustomerPaymentMethodIds();
        assertEquals(createdPaymentMethods, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestWithRedirectPaymentMethod() {
        /* Test data */
        // Request
        final Long requestId = 1L;
        final CustomerProvidedPaymentMethodAuthorizationRequest request = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
        request.setPaymentMethodType(PaymentMethodType.IDEAL);
        request.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.REDIRECT);
        request.setId(requestId);
        // Payment
        final Long paymentId = 2L;
        final CustomerPaymentMethodAuthorizationPayment authorizationPayment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        authorizationPayment.setId(paymentId);
        // Create processing channel DTO
        final PaymentProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = new ProvidedPaymentMethodProcessingChannelDto(request.getPaymentProviderIntegrationType(), request.getPaymentMethodType());
        // Payment DTO
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        // Payment processing result
        final String redirectUrl = "https://live.adyen.com/pay.shtml";
        final PaymentRedirectProcessingInformationDto processingInformationDto = new PaymentRedirectProcessingInformationDto(paymentId, redirectUrl);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestState(eq(requestId), eq(CustomerPaymentMethodAuthorizationRequestState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.CREATED, CustomerPaymentMethodAuthorizationRequestState.FAILED))))).andReturn(request).once();
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(requestId))).andReturn(request).times(2);
        expect(customerPaymentMethodAuthorizationPaymentService.createPayment(eq(requestId), eq(paymentDto), eq(encryptedPaymentMethodProcessingChannelDto))).andReturn(authorizationPayment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentProcessorService.processPayment(eq(paymentId))).andReturn(processingInformationDto).once();
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestRedirectUrl(eq(requestId), eq(redirectUrl))).andReturn(request).once();
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestState(eq(requestId), eq(CustomerPaymentMethodAuthorizationRequestState.PROCESSED), eq(new LinkedHashSet<>()))).andReturn(request).once();
        expect(getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType()).createPaymentDto(eq(request))).andReturn(paymentDto).once();
        expect(getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType()).createPaymentProcessingChannelDto(eq(request))).andReturn(encryptedPaymentMethodProcessingChannelDto).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto detailedInformationDto = customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(requestId);
        assertTrue(detailedInformationDto instanceof PaymentRedirectProcessingInformationDto);
        final PaymentRedirectProcessingInformationDto resultDetailedInformation = (PaymentRedirectProcessingInformationDto) detailedInformationDto;
        assertEquals(processingInformationDto, resultDetailedInformation);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestWhenExceptionIsThrownDuringProcessing() {
        /* Test data */
        // Request
        final Long requestId = 1L;
        final CustomerEncryptedPaymentMethodAuthorizationRequest request = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        request.setId(requestId);
        // Create processing channel DTO
        final PaymentProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = new EncryptedPaymentMethodProcessingChannelDto(PaymentProviderIntegrationType.API, request.getEncryptedData());
        // Payment
        final Long paymentId = 2L;
        final CustomerPaymentMethodAuthorizationPayment authorizationPayment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        authorizationPayment.setId(paymentId);
        // Payment DTO
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestState(eq(requestId), eq(CustomerPaymentMethodAuthorizationRequestState.PROCESSING), eq(new LinkedHashSet<>(Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.CREATED, CustomerPaymentMethodAuthorizationRequestState.FAILED))))).andReturn(request).once();
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(requestId))).andReturn(request).times(2);
        expect(customerPaymentMethodAuthorizationPaymentService.createPayment(eq(requestId), eq(paymentDto), eq(encryptedPaymentMethodProcessingChannelDto))).
                andReturn(authorizationPayment).once();
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentProcessorService.processPayment(eq(paymentId))).andThrow(new ServicesRuntimeException("Testing flow interruption with exception")).once();
        expect(customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestState(eq(requestId), eq(CustomerPaymentMethodAuthorizationRequestState.FAILED), eq(new LinkedHashSet<>()))).andReturn(request).once();
        expect(getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType()).createPaymentProcessingChannelDto(eq(request))).andReturn(encryptedPaymentMethodProcessingChannelDto).once();
        expect(getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType()).createPaymentDto(eq(request))).andReturn(paymentDto).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(requestId);
            fail("Exception should be thrown");
        } catch (final ServicesRuntimeException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private PaymentMethodAuthorizationRequestTypeOperationsProcessor getPaymentMethodAuthorizationRequestTypeOperationsProcessor(final CustomerPaymentMethodAuthorizationRequestType requestType) {
        switch (requestType) {
            case ENCRYPTED_PAYMENT_METHOD:
                return encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
            case PROVIDED_PAYMENT_METHOD:
                return providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
            default: {
                throw new UnknownCustomerPaymentMethodAuthorizationRequestTypeException(requestType);
            }
        }
    }
}
