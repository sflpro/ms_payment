package com.sfl.pms.services.payment.customer.method.impl.provider;

import com.sfl.pms.services.payment.customer.method.adyen.CustomerPaymentMethodAdyenInformationService;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.exception.adyen.AdyenRecurringDetailReferenceAlreadyUsedException;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 5:33 PM
 */
public class CustomerPaymentMethodAdyenInformationHandlerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodAdyenInformationHandlerImpl customerPaymentMethodAdyenInformationHandler = new CustomerPaymentMethodAdyenInformationHandlerImpl();

    @Mock
    private CustomerPaymentMethodAdyenInformationService customerPaymentMethodAdyenInformationService;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformationHandlerImplTest() {
    }

    /* Test methods */
    @Test
    public void testAssertProviderInformationDto() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(new CustomerPaymentMethodAdyenInformationDto(null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentMethodProviderInformationDtoWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationHandler.convertPaymentMethodProviderInformationDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAdyenInformationHandler.convertPaymentMethodProviderInformationDto(new CustomerPaymentMethodAdyenInformationDto(null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testConvertPaymentMethodProviderInformationDto() {
        // Test data
        final CustomerPaymentMethodAdyenInformationDto paymentMethodAdyenInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = (CustomerPaymentMethodAdyenInformation) customerPaymentMethodAdyenInformationHandler.convertPaymentMethodProviderInformationDto(paymentMethodAdyenInformationDto);
        getServicesImplTestHelper().assertCustomerPaymentMethodAdyenInformation(paymentMethodAdyenInformation, paymentMethodAdyenInformationDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertProviderInformationUniquenessWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationHandler.assertProviderInformationUniqueness(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAdyenInformationHandler.assertProviderInformationUniqueness(new CustomerPaymentMethodAdyenInformationDto(null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertProviderInformationUniquenessWhenDetailsReferenceIsAlreadyUsed() {
        // Test data
        final Long providerInformationId = 1l;
        final CustomerPaymentMethodAdyenInformationDto providerInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerPaymentMethodAdyenInformation providerInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation(providerInformationDto);
        providerInformation.setId(providerInformationId);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(eq(providerInformationDto.getRecurringDetailReference()))).andReturn(true).once();
        expect(customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(eq(providerInformationDto.getRecurringDetailReference()))).andReturn(providerInformation).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAdyenInformationHandler.assertProviderInformationUniqueness(providerInformationDto);
            fail("Exception should be thrown");
        } catch (final AdyenRecurringDetailReferenceAlreadyUsedException ex) {
            // Expected
            assertAdyenRecurringDetailReferenceAlreadyUsedException(ex, providerInformationDto.getRecurringDetailReference(), providerInformationId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testAssertProviderInformationUniquenessWhenDetailsReferenceIsNotUsed() {
        // Test data
        final CustomerPaymentMethodAdyenInformationDto providerInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(eq(providerInformationDto.getRecurringDetailReference()))).andReturn(false).once();
        // Replay
        replayAll();
        // Run test scenario
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationUniqueness(providerInformationDto);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertAdyenRecurringDetailReferenceAlreadyUsedException(final AdyenRecurringDetailReferenceAlreadyUsedException ex, final String recurringDetailReference, final Long providerInformationId) {
        assertEquals(recurringDetailReference, ex.getRecurringDetailReference());
        assertEquals(providerInformationId, ex.getAdyenPaymentMethodInformationId());
    }
}
