package com.sfl.pms.services.payment.processing.auth;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerProvidedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;
import com.sfl.pms.services.payment.encryption.adyen.AdyenPaymentCardEncryptionService;
import com.sfl.pms.services.payment.encryption.dto.PaymentCardEncryptionInformationDto;
import com.sfl.pms.services.payment.method.dto.individual.IndividualPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.redirect.PaymentRedirectProcessingInformationDto;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/15/15
 * Time: 5:44 PM
 */
public class CustomerPaymentMethodAuthorizationRequestProcessorServiceIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;

    @Autowired
    private AdyenPaymentCardEncryptionService adyenPaymentCardEncryptionService;

    @Autowired
    private CustomerPaymentMethodAuthorizationRequestProcessorService customerPaymentMethodAuthorizationRequestProcessorService;

    @Autowired
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    @Autowired
    private PaymentService paymentService;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestProcessorServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentWithAllPaymentCards() {
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        // Prepare data
        final Customer customer = getServicesTestHelper().createCustomer();
        // Create MasterCard
        createCustomerPaymentMethodAndAssert(customer, getServicesTestHelper().createMasterCardInformation(), PaymentMethodType.MASTER_CARD);
        // Create VisaCard
        createCustomerPaymentMethodAndAssert(customer, getServicesTestHelper().createVisaCardInformation(), PaymentMethodType.VISA);
        // Create Amex card
        createCustomerPaymentMethodAndAssert(customer, getServicesTestHelper().createAmexCardInformation(), PaymentMethodType.AMERICAN_EXPRESS);
        // Create Dinners card
        createCustomerPaymentMethodAndAssert(customer, getServicesTestHelper().createDinersCardInformation(), PaymentMethodType.DINERS_CLUB);
        // Create Discover card
        createCustomerPaymentMethodAndAssert(customer, getServicesTestHelper().createDiscoverCardInformation(), PaymentMethodType.DISCOVER);
        // Flush, clear and load customer payment methods
        flushAndClear();
        final List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getPaymentMethodsForCustomer(customer.getId());
        assertEquals(5, customerPaymentMethods.size());
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentForMasterCard() {
        createPaymentCardAndAssert(getServicesTestHelper().createMasterCardInformation(), PaymentMethodType.MASTER_CARD);
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentForVisaCard() {
        createPaymentCardAndAssert(getServicesTestHelper().createVisaCardInformation(), PaymentMethodType.VISA);
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentForAmexCard() {
        createPaymentCardAndAssert(getServicesTestHelper().createAmexCardInformation(), PaymentMethodType.AMERICAN_EXPRESS);
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentForDinersCard() {
        createPaymentCardAndAssert(getServicesTestHelper().createDinersCardInformation(), PaymentMethodType.DINERS_CLUB);
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentForDiscoverCard() {
        createPaymentCardAndAssert(getServicesTestHelper().createDiscoverCardInformation(), PaymentMethodType.DISCOVER);
    }

    @Test
    public void testProcessCustomerPaymentMethodAuthorizationRequestPaymentForIdealRedirect() throws Exception {
        // Create payment provider settings
        final AdyenPaymentSettings adyenPaymentSettings = getServicesTestHelper().createAdyenPaymentSettings();
        assertNotNull(adyenPaymentSettings);
        // Create customer payment method authorization request
        final CustomerProvidedPaymentMethodAuthorizationRequestDto authorizationRequestDto = getServicesTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequestDto();
        authorizationRequestDto.setPaymentProviderIntegrationType(PaymentProviderIntegrationType.REDIRECT);
        authorizationRequestDto.setPaymentMethodType(PaymentMethodType.IDEAL);
        final Customer customer = getServicesTestHelper().createCustomer();
        CustomerPaymentMethodAuthorizationRequest authorizationRequest = getServicesTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest(customer, authorizationRequestDto);
        flushAndClear();
        // Create individual payment method
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        paymentMethodDefinitionDto.setPaymentMethodType(authorizationRequestDto.getPaymentMethodType());
        paymentMethodDefinitionDto.setPaymentProviderType(authorizationRequestDto.getPaymentProviderType());
        paymentMethodDefinitionDto.setCurrency(Currency.EUR);
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesTestHelper().createIndividualPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        // Process authorization request
        final PaymentProcessingResultDetailedInformationDto detailedInformationDto = customerPaymentMethodAuthorizationRequestProcessorService.processCustomerPaymentMethodAuthorizationRequest(authorizationRequest.getId());
        assertNotNull(detailedInformationDto);
        assertTrue(detailedInformationDto instanceof PaymentRedirectProcessingInformationDto);
        final PaymentRedirectProcessingInformationDto paymentRedirectProcessingInformationDto = (PaymentRedirectProcessingInformationDto) detailedInformationDto;
        assertNotNull(paymentRedirectProcessingInformationDto.getRedirectUrl());
        // Assert payment state
        final Payment payment = paymentService.getPaymentById(detailedInformationDto.getPaymentId());
        assertNotNull(payment);
        Assert.assertEquals(PaymentState.GENERATED_REDIRECT_URL, payment.getLastState());
        // Reload and assert authorization request
        authorizationRequest = customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(authorizationRequest.getId());
        assertEquals(CustomerPaymentMethodAuthorizationRequestState.PROCESSED, authorizationRequest.getState());
        // Reload authorization request and assert URL
        authorizationRequest = customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(authorizationRequest.getId());
        assertNotNull(authorizationRequest.getPaymentRedirectUrl());
        assertEquals(paymentRedirectProcessingInformationDto.getRedirectUrl(), authorizationRequest.getPaymentRedirectUrl());
        // Download content and assert
        getServicesTestHelper().assertAdyenIdealRedirectUrlIsCorrect(paymentRedirectProcessingInformationDto.getRedirectUrl());
    }

    /* Utility methods */
    private void createCustomerPaymentMethodAndAssert(final Customer customer, final PaymentCardEncryptionInformationDto encryptionInformationDto, final PaymentMethodType paymentMethodType) {
        final String adyenCardEncryptedData = encryptCardData(encryptionInformationDto);
        final Pair<CustomerCardPaymentMethod, Long> paymentMethodAddingResult = getServicesTestHelper().createCustomerEncryptedCardPaymentMethodWithPaymentProvider(customer, adyenCardEncryptedData);
        CustomerCardPaymentMethod customerCardPaymentMethod = paymentMethodAddingResult.getKey();
        final Long paymentId = paymentMethodAddingResult.getValue();
        final Payment payment = paymentService.getPaymentById(paymentId);
        // Flush, clear and reload
        flushAndClear();
        CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodService.getCustomerPaymentMethodById(customerCardPaymentMethod.getId());
        customerPaymentMethod = persistenceUtilityService.initializeAndUnProxy(customerPaymentMethod);
        assertTrue(customerPaymentMethod instanceof CustomerCardPaymentMethod);
        customerCardPaymentMethod = (CustomerCardPaymentMethod) customerPaymentMethod;
        // Assert customer card payment method
        getServicesTestHelper().assertCustomerCardPaymentMethod(customerCardPaymentMethod, encryptionInformationDto, paymentMethodType);
        // Grab Adyen information
        CustomerPaymentMethodProviderInformation paymentMethodProviderInformation = customerCardPaymentMethod.getProviderInformation();
        paymentMethodProviderInformation = persistenceUtilityService.initializeAndUnProxy(paymentMethodProviderInformation);
        assertTrue(paymentMethodProviderInformation instanceof CustomerPaymentMethodAdyenInformation);
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = (CustomerPaymentMethodAdyenInformation) paymentMethodProviderInformation;
        assertNotNull(paymentMethodAdyenInformation.getId());
        assertNotNull(paymentMethodAdyenInformation.getRecurringDetailReference());
        // Assert payment
        getServicesTestHelper().assertAuthorizedEncryptedPayment(payment);
    }

    private void createPaymentCardAndAssert(final PaymentCardEncryptionInformationDto encryptionInformationDto, final PaymentMethodType paymentMethodType) {
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        // Prepare data
        final Customer customer = getServicesTestHelper().createCustomer();
        // Create MasterCard
        createCustomerPaymentMethodAndAssert(customer, encryptionInformationDto, paymentMethodType);
        // Flush, clear and load customer payment methods
        flushAndClear();
        final List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodService.getPaymentMethodsForCustomer(customer.getId());
        assertEquals(1, customerPaymentMethods.size());
    }

    private String encryptCardData(final PaymentCardEncryptionInformationDto encryptionInformationDto) {
        return adyenPaymentCardEncryptionService.encryptPaymentCardInformation(encryptionInformationDto);
    }

}
