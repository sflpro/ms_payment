package com.sfl.pms.services.payment.processing.impl.adyen;

import com.sfl.pms.externalclients.payment.adyen.communicator.AdyenApiCommunicator;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.DisableContractResultModel;
import com.sfl.pms.externalclients.payment.adyen.model.request.DisableRecurringContractRequest;
import com.sfl.pms.externalclients.payment.adyen.model.response.DisableRecurringContractResponse;
import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.auth.CustomerPaymentMethodAuthorizationPaymentService;
import com.sfl.pms.services.payment.common.dto.adyen.AdyenPaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import com.sfl.pms.services.payment.common.order.OrderPaymentService;
import com.sfl.pms.services.payment.customer.information.adyen.CustomerAdyenInformationService;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;
import com.sfl.pms.services.payment.customer.method.adyen.CustomerPaymentMethodAdyenInformationService;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.processing.impl.PaymentProviderOperationsProcessor;
import com.sfl.pms.services.payment.provider.adyen.AdyenPaymentProviderIntegrationService;
import com.sfl.pms.services.payment.provider.dto.AdyenRedirectUrlGenerationDto;
import com.sfl.pms.services.payment.provider.model.adyen.AdyenRecurringContractType;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 9:51 PM
 */
public class AdyenPaymentSpecificOperationsProcessorImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private AdyenPaymentOperationsProcessorImpl adyenPaymentOperationsProcessor = new AdyenPaymentOperationsProcessorImpl();

    @Mock
    private AdyenApiCommunicator adyenApiCommunicator;

    @Mock
    private CustomerAdyenInformationService customerAdyenInformationService;

    @Mock
    private CustomerPaymentMethodAdyenInformationService customerPaymentMethodAdyenInformationService;

    @Mock
    private CustomerPaymentMethodAuthorizationPaymentService customerPaymentMethodAuthorizationPaymentService;

    @Mock
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    @Mock
    private OrderPaymentService orderPaymentService;

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private AdyenPaymentProviderIntegrationService adyenPaymentProviderIntegrationService;

    /* Constructors */
    public AdyenPaymentSpecificOperationsProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testGeneratePaymentRedirectUrlWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenPaymentOperationsProcessor.generatePaymentRedirectUrl(null, false);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGeneratePaymentRedirectUrlWithRecurringContract() {
        // Test
        final Long paymentId = 1L;
        final AdyenRedirectUrlGenerationDto urlGenerationDto = getServicesImplTestHelper().createAdyenRedirectUrlGenerationDto();
        final String redirectUrl = "http://adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Test data
        expect(adyenPaymentProviderIntegrationService.createAdyenRedirectGenerationDtoForPayment(eq(paymentId), EasyMock.eq(AdyenRecurringContractType.RECURRING))).andReturn(urlGenerationDto).once();
        expect(adyenPaymentProviderIntegrationService.generatePaymentProviderRedirectUrlForDto(eq(urlGenerationDto))).andReturn(redirectUrl).once();
        // Replay
        replayAll();
        // Run test scenario
        String result = adyenPaymentOperationsProcessor.generatePaymentRedirectUrl(paymentId, true);
        assertEquals(redirectUrl, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGeneratePaymentRedirectUrlWithoutRecurringContract() {
        // Test
        final Long paymentId = 1L;
        final AdyenRedirectUrlGenerationDto urlGenerationDto = getServicesImplTestHelper().createAdyenRedirectUrlGenerationDto();
        final String redirectUrl = "http://adyen.com/pay.shtml";
        // Reset
        resetAll();
        // Test data
        expect(adyenPaymentProviderIntegrationService.createAdyenRedirectGenerationDtoForPayment(eq(paymentId), eq(AdyenRecurringContractType.NONE))).andReturn(urlGenerationDto).once();
        expect(adyenPaymentProviderIntegrationService.generatePaymentProviderRedirectUrlForDto(eq(urlGenerationDto))).andReturn(redirectUrl).once();
        // Replay
        replayAll();
        // Run test scenario
        String result = adyenPaymentOperationsProcessor.generatePaymentRedirectUrl(paymentId, false);
        assertEquals(redirectUrl, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentResultAlreadyExistsWithInvalidArguments() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(null, paymentResultDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentResultAlreadyExistsWhenItAlreadyExists() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Create new Adyen payment result with same status, authCode, pspReference and result code
        final AdyenPaymentResult adyenPaymentResult = new AdyenPaymentResult();
        adyenPaymentResult.setResultCode(paymentResultDto.getResultCode());
        adyenPaymentResult.setPspReference(paymentResultDto.getPspReference());
        adyenPaymentResult.setAuthCode(paymentResultDto.getAuthCode());
        adyenPaymentResult.setStatus(paymentResultDto.getStatus());
        payment.getPaymentResults().add(adyenPaymentResult);
        // Reset
        resetAll();
        // Expectations
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, paymentResultDto);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentResultAlreadyExistsWithDifferentResultCode() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Create new Adyen payment result with same status, authCode, pspReference and result code
        final AdyenPaymentResult adyenPaymentResult = new AdyenPaymentResult();
        adyenPaymentResult.setResultCode(paymentResultDto.getResultCode() + "_u");
        adyenPaymentResult.setPspReference(paymentResultDto.getPspReference());
        adyenPaymentResult.setAuthCode(paymentResultDto.getAuthCode());
        adyenPaymentResult.setStatus(paymentResultDto.getStatus());
        payment.getPaymentResults().add(adyenPaymentResult);
        // Reset
        resetAll();
        // Expectations
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, paymentResultDto);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentResultAlreadyExistsWithDifferentPspReference() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Create new Adyen payment result with same status, authCode, pspReference and result code
        final AdyenPaymentResult adyenPaymentResult = new AdyenPaymentResult();
        adyenPaymentResult.setResultCode(paymentResultDto.getResultCode());
        adyenPaymentResult.setPspReference(paymentResultDto.getPspReference() + "_u");
        adyenPaymentResult.setAuthCode(paymentResultDto.getAuthCode());
        adyenPaymentResult.setStatus(paymentResultDto.getStatus());
        payment.getPaymentResults().add(adyenPaymentResult);
        // Reset
        resetAll();
        // Expectations
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, paymentResultDto);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentResultAlreadyExistsWithDifferentAuthCode() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Create new Adyen payment result with same status, authCode, pspReference and result code
        final AdyenPaymentResult adyenPaymentResult = new AdyenPaymentResult();
        adyenPaymentResult.setResultCode(paymentResultDto.getResultCode());
        adyenPaymentResult.setPspReference(paymentResultDto.getPspReference());
        adyenPaymentResult.setAuthCode(paymentResultDto.getAuthCode() + "_u");
        adyenPaymentResult.setStatus(paymentResultDto.getStatus());
        payment.getPaymentResults().add(adyenPaymentResult);
        // Reset
        resetAll();
        // Expectations
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, paymentResultDto);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentResultAlreadyExistsWithDifferentStatus() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final AdyenPaymentResultDto paymentResultDto = getServicesImplTestHelper().createAdyenPaymentResultDto();
        // Create new Adyen payment result with same status, authCode, pspReference and result code
        final AdyenPaymentResult adyenPaymentResult = new AdyenPaymentResult();
        adyenPaymentResult.setResultCode(paymentResultDto.getResultCode());
        adyenPaymentResult.setPspReference(paymentResultDto.getPspReference());
        adyenPaymentResult.setAuthCode(paymentResultDto.getAuthCode());
        adyenPaymentResult.setStatus(getAnyPaymentResultExcept(paymentResultDto.getStatus()));
        payment.getPaymentResults().add(adyenPaymentResult);
        // Reset
        resetAll();
        // Expectations
        expect(persistenceUtilityService.initializeAndUnProxy(isA(Object.class))).andAnswer(() -> getCurrentArguments()[0]).anyTimes();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = adyenPaymentOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, paymentResultDto);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodRemovalWithInvalidArguments() {
        /* Test data */
        /* Reset mocks */
        resetAll();
        /* Replay mocks */
        replayAll();
        /* Run test case */
        try {
            adyenPaymentOperationsProcessor.processCustomerPaymentMethodRemoval(null);
            fail("Exception will be thrown");
        } catch (final IllegalArgumentException e) {
            //Exception
        }
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodRemoval() {
        /* Test data */
        final Long customerId = 1l;
        final Long customerPaymentMethodId = 1l;
        final Long paymentMethodProviderInformationId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod();
        customerPaymentMethod.setId(customerPaymentMethodId);
        final CustomerPaymentMethodAdyenInformation customerPaymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        customerPaymentMethodAdyenInformation.setId(paymentMethodProviderInformationId);
        customerPaymentMethodAdyenInformation.setCustomerPaymentMethod(customerPaymentMethod);
        customerPaymentMethod.setCustomer(customer);
        final CustomerAdyenInformation customerAdyenInformation = getServicesImplTestHelper().createCustomerAdyenInformation();
        customerAdyenInformation.setCustomer(customer);
        /* Create disable recurring contract response */
        final DisableRecurringContractResponse disableRecurringContractResponse = createDisableRecurringContractResponse();
        /* Reset mocks */
        resetAll();
        /* Register expectations */
        expect(customerPaymentMethodAdyenInformationService.getCustomerPaymentMethodProviderInformationById(eq(paymentMethodProviderInformationId))).andReturn(customerPaymentMethodAdyenInformation).once();
        expect(customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(EasyMock.eq(customer.getId()))).andReturn(customerAdyenInformation).once();
        expect(adyenApiCommunicator.disableRecurringContract(isA(DisableRecurringContractRequest.class))).andAnswer(new IAnswer<DisableRecurringContractResponse>() {
            @Override
            public DisableRecurringContractResponse answer() {
                final DisableRecurringContractRequest disableRecurringContractRequest = (DisableRecurringContractRequest) getCurrentArguments()[0];
                assertDisableRecurringContractRequest(disableRecurringContractRequest, customerPaymentMethodAdyenInformation, customerAdyenInformation);
                return disableRecurringContractResponse;
            }
        }).once();
        /* Replay mocks */
        replayAll();
        /* Run test case */
        final PaymentProviderOperationsProcessor.CustomerPaymentMethodRemovalData result = adyenPaymentOperationsProcessor.processCustomerPaymentMethodRemoval(paymentMethodProviderInformationId);
        assertNotNull(result);
        assertEquals(result.getStatus(), disableRecurringContractResponse.getDisableContractResult().getStatus());
        verifyAll();
    }

    /* Utility methods */
    private PaymentResultStatus getAnyPaymentResultExcept(final PaymentResultStatus paymentResultStatus) {
        for (final PaymentResultStatus currentStatus : PaymentResultStatus.values()) {
            if (!currentStatus.equals(paymentResultStatus)) {
                return currentStatus;
            }
        }
        return null;
    }

    private DisableRecurringContractResponse createDisableRecurringContractResponse() {
        final DisableContractResultModel disableContractResultModel = new DisableContractResultModel("status of disabling");
        final DisableRecurringContractResponse disableRecurringContractResponse = new DisableRecurringContractResponse(disableContractResultModel);
        return disableRecurringContractResponse;
    }

    private void assertDisableRecurringContractRequest(final DisableRecurringContractRequest disableRecurringContractRequest, final CustomerPaymentMethodAdyenInformation customerPaymentMethodAdyenInformation, final CustomerAdyenInformation customerAdyenInformation) {
        assertNotNull(disableRecurringContractRequest);
        assertEquals(disableRecurringContractRequest.getContractReference(), customerPaymentMethodAdyenInformation.getRecurringDetailReference());
        assertEquals(disableRecurringContractRequest.getShopperReference(), customerAdyenInformation.getShopperReference());
    }
}
