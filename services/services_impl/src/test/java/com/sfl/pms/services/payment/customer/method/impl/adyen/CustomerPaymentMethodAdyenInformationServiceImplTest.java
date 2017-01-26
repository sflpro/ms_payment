package com.sfl.pms.services.payment.customer.method.impl.adyen;

import com.sfl.pms.persistence.repositories.payment.customer.method.adyen.CustomerPaymentMethodAdyenInformationRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodProviderInformationRepository;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodProviderInformationService;
import com.sfl.pms.services.payment.customer.method.exception.adyen.AdyenRecurringDetailReferenceAlreadyUsedException;
import com.sfl.pms.services.payment.customer.method.exception.adyen.PaymentMethodAdyenInformationNotFoundForDetailReferenceException;
import com.sfl.pms.services.payment.customer.method.exception.adyen.PaymentMethodAdyenInformationNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.impl.AbstractCustomerPaymentMethodProviderInformationServiceImplTest;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:39 AM
 */
@SuppressWarnings("unchecked")
public class CustomerPaymentMethodAdyenInformationServiceImplTest extends AbstractCustomerPaymentMethodProviderInformationServiceImplTest<CustomerPaymentMethodAdyenInformation> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodAdyenInformationServiceImpl customerPaymentMethodAdyenInformationService = new CustomerPaymentMethodAdyenInformationServiceImpl();

    @Mock
    private CustomerPaymentMethodAdyenInformationRepository customerPaymentMethodAdyenInformationRepository;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentMethodInformationRecurringDetailReferenceWithInvalidArguments() {
        // Test data
        final Long informationId = 1L;
        final String recurringDetailsReference = "HGYFIYTFRUDFURTFOFITDITRYDRUYEDR";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationService.updatePaymentMethodInformationRecurringDetailReference(null, recurringDetailsReference);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAdyenInformationService.updatePaymentMethodInformationRecurringDetailReference(informationId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodInformationRecurringDetailReferenceWithExistingDetailsReference() {
        // Test data
        final Long informationId = 1L;
        final String recurringDetailsReference = "HGYFIYTFRUDFURTFOFITDITRYDRUYEDR";
        final CustomerPaymentMethodAdyenInformation adyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        adyenInformation.setId(informationId);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailsReference))).andReturn(adyenInformation).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationService.updatePaymentMethodInformationRecurringDetailReference(informationId, recurringDetailsReference);
            fail("Exception should be thrown");
        } catch (final AdyenRecurringDetailReferenceAlreadyUsedException ex) {
            // Expected
            assertAdyenRecurringDetailReferenceAlreadyUsedException(ex, informationId, recurringDetailsReference);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodInformationRecurringDetailReferenceWithNotExistingId() {
        // Test data
        final Long informationId = 1L;
        final String recurringDetailsReference = "HGYFIYTFRUDFURTFOFITDITRYDRUYEDR";
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailsReference))).andReturn(null).once();
        expect(customerPaymentMethodAdyenInformationRepository.findOne(eq(informationId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationService.updatePaymentMethodInformationRecurringDetailReference(informationId, recurringDetailsReference);
            fail("Exception should be thrown");
        } catch (final PaymentMethodAdyenInformationNotFoundForIdException ex) {
            // Expected
            assertPaymentMethodAdyenInformationNotFoundForIdException(ex, informationId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testUpdatePaymentMethodInformationRecurringDetailReference() {
        // Test data
        final Long informationId = 1L;
        final CustomerPaymentMethodAdyenInformation adyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        adyenInformation.setId(informationId);
        final String recurringDetailsReference = "HGYFIYTFRUDFURTFOFITDITRYDRUYEDR";
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailsReference))).andReturn(null).once();
        expect(customerPaymentMethodAdyenInformationRepository.findOne(eq(informationId))).andReturn(adyenInformation).once();
        expect(customerPaymentMethodAdyenInformationRepository.save(isA(CustomerPaymentMethodAdyenInformation.class))).andAnswer(() -> (CustomerPaymentMethodAdyenInformation) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodAdyenInformation result = customerPaymentMethodAdyenInformationService.updatePaymentMethodInformationRecurringDetailReference(informationId, recurringDetailsReference);
        assertNotNull(result);
        assertEquals(recurringDetailsReference, result.getRecurringDetailReference());
        // Verify
        verifyAll();
    }


    @Test
    public void testGetPaymentMethodInformationByRecurringDetailReferenceWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(null);
            fail("exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodInformationByRecurringDetailReferenceWithNotExistingReference() {
        // Test data
        final String recurringDetailReference = "not_existing_detail_reference";
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailReference))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(recurringDetailReference);
            fail("exception should be thrown");
        } catch (final PaymentMethodAdyenInformationNotFoundForDetailReferenceException ex) {
            // Expected
            assertPaymentMethodAdyenInformationNotFoundForDetailReferenceException(ex, recurringDetailReference);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodInformationByRecurringDetailReference() {
        // Test data
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        final String recurringDetailReference = paymentMethodAdyenInformation.getRecurringDetailReference();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailReference))).andReturn(paymentMethodAdyenInformation).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodAdyenInformation result = customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(recurringDetailReference);
        assertEquals(paymentMethodAdyenInformation, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodInformationExistsForRecurringDetailReferenceWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodInformationExistsForRecurringDetailReferenceWhenItExists() {
        // Test data
        final String recurringDetailReference = "JHGV-IUIY-POK8-8UY9";
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation(getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto());
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailReference))).andReturn(paymentMethodAdyenInformation).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(recurringDetailReference);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodInformationExistsForRecurringDetailReferenceWhenItDoesNotExist() {
        // Test data
        final String recurringDetailReference = "JHGV-IUIY-POK8-8UY9";
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(eq(recurringDetailReference))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(recurringDetailReference);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    protected void assertPaymentMethodAdyenInformationNotFoundForDetailReferenceException(final PaymentMethodAdyenInformationNotFoundForDetailReferenceException ex, final String recurringDetailReference) {
        assertEquals(recurringDetailReference, ex.getRecurringDetailReference());
    }

    private void assertPaymentMethodAdyenInformationNotFoundForIdException(final PaymentMethodAdyenInformationNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(CustomerPaymentMethodAdyenInformation.class, ex.getEntityClass());
    }

    private void assertAdyenRecurringDetailReferenceAlreadyUsedException(final AdyenRecurringDetailReferenceAlreadyUsedException ex, final Long existingInformationId, final String reference) {
        assertEquals(existingInformationId, ex.getAdyenPaymentMethodInformationId());
        assertEquals(reference, ex.getRecurringDetailReference());
    }

    @Override
    protected AbstractCustomerPaymentMethodProviderInformationService getService() {
        return customerPaymentMethodAdyenInformationService;
    }

    @Override
    protected AbstractCustomerPaymentMethodProviderInformationRepository getRepository() {
        return customerPaymentMethodAdyenInformationRepository;
    }

    @Override
    protected CustomerPaymentMethodAdyenInformation createNewInstance() {
        return getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
    }
}
