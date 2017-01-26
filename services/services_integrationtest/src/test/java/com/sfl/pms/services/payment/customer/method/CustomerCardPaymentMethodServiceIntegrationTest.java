package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.dto.CustomerCardPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAlreadyBoundAuthorizationRequestException;
import com.sfl.pms.services.payment.customer.method.exception.adyen.AdyenRecurringDetailReferenceAlreadyUsedException;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 5:38 PM
 */
public class CustomerCardPaymentMethodServiceIntegrationTest extends AbstractCustomerPaymentMethodServiceIntegrationTest<CustomerCardPaymentMethod> {

    /* Dependencies */
    @Autowired
    private CustomerCardPaymentMethodService customerCardPaymentMethodService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public CustomerCardPaymentMethodServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testCreateCustomerPaymentMethodWithoutAuthorizationRequest() {
        // Prepare data
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final Customer customer = getServicesTestHelper().createCustomer();
        flushAndClear();
        // Create payment method
        CustomerCardPaymentMethod paymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), null, paymentMethodDto, adyenInformationDto);
        assertPaymentMethod(paymentMethod, paymentMethodDto, adyenInformationDto, customer, null);
        flushAndClear();
        paymentMethod = customerCardPaymentMethodService.getCustomerPaymentMethodById(paymentMethod.getId());
        assertPaymentMethod(paymentMethod, paymentMethodDto, adyenInformationDto, customer, null);
    }

    @Test
    public void testCreateCustomerPaymentMethodWithAuthorizationRequest() {
        // Prepare data
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        final Customer customer = authorizationRequest.getCustomer();
        flushAndClear();
        // Create payment method
        CustomerCardPaymentMethod paymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), authorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
        assertPaymentMethod(paymentMethod, paymentMethodDto, adyenInformationDto, customer, authorizationRequest);
        flushAndClear();
        paymentMethod = customerCardPaymentMethodService.getCustomerPaymentMethodById(paymentMethod.getId());
        assertPaymentMethod(paymentMethod, paymentMethodDto, adyenInformationDto, customer, authorizationRequest);
    }

    @Test
    public void testCreateCustomerPaymentMethodWhenAuthorizationRequestIsAlreadyUsed() {
        // Prepare data
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        final Customer customer = authorizationRequest.getCustomer();
        flushAndClear();
        // Create payment method
        CustomerCardPaymentMethod paymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), authorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
        adyenInformationDto.setRecurringDetailReference(adyenInformationDto.getRecurringDetailReference() + "_updated");
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), authorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAlreadyBoundAuthorizationRequestException ex) {
            // Expected
        }
        // Flush and try again
        flushAndClear();
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), authorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAlreadyBoundAuthorizationRequestException ex) {
            // Expected
        }
    }

    @Test
    public void testCreateCustomerPaymentMethodWhenRecurringDetailsReferenceIsAlreadyUsed() {
        // Prepare data
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        final CustomerPaymentMethodAuthorizationRequest secondAuthorizationRequest = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest(authorizationRequest.getCustomer(), getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequestDto());
        final Customer customer = authorizationRequest.getCustomer();
        flushAndClear();
        // Create payment method
        CustomerCardPaymentMethod paymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), authorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), secondAuthorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
            fail("Exception should be thrown");
        } catch (final AdyenRecurringDetailReferenceAlreadyUsedException ex) {
            // Expected
        }
        // Flush and try again
        flushAndClear();
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customer.getId(), secondAuthorizationRequest.getId(), paymentMethodDto, adyenInformationDto);
            fail("Exception should be thrown");
        } catch (final AdyenRecurringDetailReferenceAlreadyUsedException ex) {
            // Expected
        }
    }

    /* Utility methods */
    private void assertPaymentMethod(final CustomerCardPaymentMethod paymentMethod, final CustomerCardPaymentMethodDto paymentMethodDto, final CustomerPaymentMethodAdyenInformationDto adyenInformationDto, final Customer customer, final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        getServicesTestHelper().assertCustomerCardPaymentMethod(paymentMethod, paymentMethodDto);
        assertNotNull(paymentMethod.getProviderInformation());
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = (CustomerPaymentMethodAdyenInformation) persistenceUtilityService.initializeAndUnProxy(paymentMethod.getProviderInformation());
        getServicesTestHelper().assertCustomerPaymentMethodAdyenInformation(paymentMethodAdyenInformation, adyenInformationDto);
        // Assert customer
        assertNotNull(paymentMethod.getCustomer());
        Assert.assertEquals(customer.getId(), paymentMethod.getCustomer().getId());
        // Assert authorization request
        if (authorizationRequest == null) {
            assertNull(paymentMethod.getAuthorizationRequest());
        } else {
            assertNotNull(paymentMethod.getAuthorizationRequest());
            assertEquals(authorizationRequest.getId(), paymentMethod.getAuthorizationRequest().getId());
        }
    }

    @Override
    protected AbstractCustomerPaymentMethodService<CustomerCardPaymentMethod> getService() {
        return customerCardPaymentMethodService;
    }

    @Override
    protected CustomerCardPaymentMethod getInstance(final Customer customer) {
        return getServicesTestHelper().createCustomerCardPaymentMethod(customer);
    }
}