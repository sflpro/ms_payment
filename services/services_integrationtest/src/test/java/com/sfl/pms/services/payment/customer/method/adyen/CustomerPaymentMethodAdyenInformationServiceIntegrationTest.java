package com.sfl.pms.services.payment.customer.method.adyen;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodProviderInformationService;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodProviderInformationServiceIntegrationTest;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:53 AM
 */
public class CustomerPaymentMethodAdyenInformationServiceIntegrationTest extends AbstractCustomerPaymentMethodProviderInformationServiceIntegrationTest<CustomerPaymentMethodAdyenInformation> {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAdyenInformationService customerPaymentMethodAdyenInformationService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformationServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentMethodInformationRecurringDetailReference() {
        // Prepare data
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerCardPaymentMethod paymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest(), getServicesTestHelper().createCustomerCardPaymentMethodDto(), adyenInformationDto);
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = (CustomerPaymentMethodAdyenInformation) persistenceUtilityService.initializeAndUnProxy(paymentMethod.getProviderInformation());
        final String recurringDetailReference = adyenInformationDto.getRecurringDetailReference();
        final String updatedRecurringDetailsReference = recurringDetailReference + "_updated";
        flushAndClear();
        // Update recurring details reference
        CustomerPaymentMethodAdyenInformation result = customerPaymentMethodAdyenInformationService.updatePaymentMethodInformationRecurringDetailReference(paymentMethodAdyenInformation.getId(), updatedRecurringDetailsReference);
        assertEquals(updatedRecurringDetailsReference, result.getRecurringDetailReference());
        // Flush, clear, reload and assert
        flushAndClear();
        result = customerPaymentMethodAdyenInformationService.getCustomerPaymentMethodProviderInformationById(result.getId());
        assertEquals(updatedRecurringDetailsReference, result.getRecurringDetailReference());
    }

    @Test
    public void testGetPaymentMethodInformationByRecurringDetailReference() {
        // Prepare data
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerCardPaymentMethod paymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest(), getServicesTestHelper().createCustomerCardPaymentMethodDto(), adyenInformationDto);
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = (CustomerPaymentMethodAdyenInformation) persistenceUtilityService.initializeAndUnProxy(paymentMethod.getProviderInformation());
        final String recurringDetailReference = adyenInformationDto.getRecurringDetailReference();
        // Run test scenario
        CustomerPaymentMethodAdyenInformation result = customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(recurringDetailReference);
        assertEquals(paymentMethodAdyenInformation, result);
        // Flush, reload and assert again
        flushAndClear();
        result = customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(recurringDetailReference);
        assertEquals(paymentMethodAdyenInformation, result);
    }

    @Test
    public void testCheckIfPaymentMethodInformationExistsForRecurringDetailReferenceWhenItExists() {
        // Prepare data
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerCardPaymentMethod paymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest(), getServicesTestHelper().createCustomerCardPaymentMethodDto(), adyenInformationDto);
        final String recurringDetailReference = adyenInformationDto.getRecurringDetailReference();
        // Run test scenario
        boolean result = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(recurringDetailReference);
        assertTrue(result);
        // Flush, clear and check again
        result = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(recurringDetailReference);
        assertTrue(result);
    }

    @Test
    public void testCheckIfPaymentMethodInformationExistsForRecurringDetailReferenceWhenItDoesNotExist() {
        // Prepare data
        final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerCardPaymentMethod paymentMethod = getServicesTestHelper().createCustomerCardPaymentMethod(getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest(), getServicesTestHelper().createCustomerCardPaymentMethodDto(), adyenInformationDto);
        final String recurringDetailReference = adyenInformationDto.getRecurringDetailReference() + "_not_existing";
        // Run test scenario
        boolean result = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(recurringDetailReference);
        assertFalse(result);
        // Flush, clear and check again
        result = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(recurringDetailReference);
        assertFalse(result);
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentMethodProviderInformationService<CustomerPaymentMethodAdyenInformation> getService() {
        return customerPaymentMethodAdyenInformationService;
    }
}
