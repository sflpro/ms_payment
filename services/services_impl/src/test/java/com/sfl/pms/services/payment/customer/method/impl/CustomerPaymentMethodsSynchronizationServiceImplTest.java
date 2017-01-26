package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.services.common.sortorder.SortDirection;
import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.CustomerBankPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.CustomerCardPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerBankPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerCardPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerBankPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodType;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;
import com.sfl.pms.services.payment.processing.impl.PaymentProviderOperationsProcessor;
import com.sfl.pms.services.payment.processing.impl.adyen.AdyenPaymentOperationsProcessor;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/15/15
 * Time: 3:31 PM
 */
public class CustomerPaymentMethodsSynchronizationServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodsSynchronizationServiceImpl customerPaymentMethodsSynchronizationService = new CustomerPaymentMethodsSynchronizationServiceImpl();

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerCardPaymentMethodService customerCardPaymentMethodService;

    @Mock
    private CustomerBankPaymentMethodService customerBankPaymentMethodService;

    @Mock
    private AdyenPaymentOperationsProcessor adyenPaymentOperationsProcessor;

    @Mock
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public CustomerPaymentMethodsSynchronizationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testSynchronizeCustomerPaymentMethodsWithInvalidArguments() {
        // Test data
        final Long customerId = 1L;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodsSynchronizationService.synchronizeCustomerPaymentMethods(null, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodsSynchronizationService.synchronizeCustomerPaymentMethods(customerId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testSynchronizeCustomerPaymentMethods() {
        // Test data
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Prepare list of payment provider customer payment methods
        final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentMethodProviderDetailsList = new ArrayList<>();
        paymentMethodProviderDetailsList.addAll(createCustomerCardPaymentMethodProviderInformation(3, 1));
        paymentMethodProviderDetailsList.addAll(createCustomerBankPaymentMethodProviderInformation(3, 2));
        final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentMethodProviderDetailsListForWhichPaymentMethodBeCreated = new ArrayList<>();
        // Create lists of existing payment methods
        final List<CustomerPaymentMethod> existingCustomerPaymentMethodsThatShouldBeLeft = new ArrayList<>();
        for (int i = 0; i < paymentMethodProviderDetailsList.size(); i++) {
            if (i % 2 == 0) {
                existingCustomerPaymentMethodsThatShouldBeLeft.add(createCustomerCardPaymentMethod(paymentMethodProviderDetailsList.get(i).getCustomerPaymentMethodProviderInformationDto().getPaymentProviderIdentifierForPaymentMethod(), i + 10));
            } else {
                paymentMethodProviderDetailsListForWhichPaymentMethodBeCreated.add(paymentMethodProviderDetailsList.get(i));
            }
        }
        final List<CustomerPaymentMethod> existingCustomerPaymentMethodsThatShouldBeRemoved = new ArrayList<>();
        existingCustomerPaymentMethodsThatShouldBeRemoved.add(createCustomerCardPaymentMethod("existing_details_" + 11, 50));
        existingCustomerPaymentMethodsThatShouldBeRemoved.add(createCustomerCardPaymentMethod("existing_details_" + 12, 60));
        existingCustomerPaymentMethodsThatShouldBeRemoved.add(createCustomerBankPaymentMethod("existing_details_" + 13, 70));
        existingCustomerPaymentMethodsThatShouldBeRemoved.add(createCustomerBankPaymentMethod("existing_details_" + 14, 80));
        // Create list of all existing payment method
        final List<CustomerPaymentMethod> allExistingCustomerPaymentMethods = new ArrayList<>();
        allExistingCustomerPaymentMethods.addAll(existingCustomerPaymentMethodsThatShouldBeLeft);
        allExistingCustomerPaymentMethods.addAll(existingCustomerPaymentMethodsThatShouldBeRemoved);
        // Expected customer payment methods lookup parameters
        final CustomerPaymentMethodLookupParameters lookupParameters = new CustomerPaymentMethodLookupParameters();
        lookupParameters.setCustomerId(customerId);
        lookupParameters.setExcludeRemoved(false);
        // Expected customer payment methods search sort direction
        final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification = new SortOrderSpecification<>(CustomerPaymentMethodSortColumn.DATE, SortDirection.ASCENDING);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodService.getCustomerPaymentMethodsForSearchParameters(eq(lookupParameters), eq(sortOrderSpecification), eq(Long.valueOf(0)), eq(Integer.MAX_VALUE))).andReturn(allExistingCustomerPaymentMethods).once();
        expect(adyenPaymentOperationsProcessor.getStoredRecurringPaymentMethods(eq(customerId))).andReturn(paymentMethodProviderDetailsList).once();
        paymentMethodProviderDetailsListForWhichPaymentMethodBeCreated.forEach(providerData -> {
            if (CustomerPaymentMethodType.CARD.equals(providerData.getCustomerPaymentMethodDto().getType())) {
                final CustomerCardPaymentMethodDto customerCardPaymentMethodDto = (CustomerCardPaymentMethodDto) providerData.getCustomerPaymentMethodDto();
                final CustomerCardPaymentMethod customerCardPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod(customerCardPaymentMethodDto);
                final CustomerPaymentMethodAdyenInformation customerPaymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation((CustomerPaymentMethodAdyenInformationDto) providerData.getCustomerPaymentMethodProviderInformationDto());
                customerCardPaymentMethod.setProviderInformation(customerPaymentMethodAdyenInformation);
                expect(customerCardPaymentMethodService.createCustomerPaymentMethod(eq(customerId), isNull(), eq(customerCardPaymentMethodDto), eq(providerData.getCustomerPaymentMethodProviderInformationDto()))).andReturn(customerCardPaymentMethod).once();
            } else if (CustomerPaymentMethodType.BANK.equals(providerData.getCustomerPaymentMethodDto().getType())) {
                final CustomerBankPaymentMethodDto customerBankPaymentMethodDto = (CustomerBankPaymentMethodDto) providerData.getCustomerPaymentMethodDto();
                final CustomerBankPaymentMethod customerBankPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod(customerBankPaymentMethodDto);
                final CustomerPaymentMethodAdyenInformation customerPaymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation((CustomerPaymentMethodAdyenInformationDto) providerData.getCustomerPaymentMethodProviderInformationDto());
                customerBankPaymentMethod.setProviderInformation(customerPaymentMethodAdyenInformation);
                expect(customerBankPaymentMethodService.createCustomerPaymentMethod(eq(customerId), isNull(), eq(customerBankPaymentMethodDto), eq(providerData.getCustomerPaymentMethodProviderInformationDto()))).andReturn(customerBankPaymentMethod).once();
            }
        });
        existingCustomerPaymentMethodsThatShouldBeRemoved.forEach(customerPaymentMethod -> {
            expect(customerPaymentMethodService.removeCustomerPaymentMethod(EasyMock.eq(customerPaymentMethod.getId()))).andReturn(customerPaymentMethod).once();
        });
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodsSynchronizationResult synchronizationResult = customerPaymentMethodsSynchronizationService.synchronizeCustomerPaymentMethods(customerId, paymentProviderType);
        // Assert created customer payment method
        final List<CustomerPaymentMethod> createdPaymentMethods = synchronizationResult.getCreatedCustomerPaymentMethods();
        assertNotNull(createdPaymentMethods);
        assertEquals(paymentMethodProviderDetailsListForWhichPaymentMethodBeCreated.size(), createdPaymentMethods.size());
        paymentMethodProviderDetailsListForWhichPaymentMethodBeCreated.forEach(paymentMethodProviderData -> {
            final MutableHolder<Boolean> matchFound = new MutableHolder<Boolean>(Boolean.FALSE);
            createdPaymentMethods.forEach(customerPaymentMethod -> {
                if (paymentMethodProviderData.getCustomerPaymentMethodProviderInformationDto().getPaymentProviderIdentifierForPaymentMethod().equals(customerPaymentMethod.getProviderInformation().getPaymentProviderIdentifierForPaymentMethod())) {
                    matchFound.setValue(Boolean.TRUE);
                }
            });
            assertTrue(matchFound.getValue().booleanValue());
        });
        // Assert removed customer payment method
        final List<CustomerPaymentMethod> removedPaymentMethods = synchronizationResult.getDeactivatedCustomerPaymentMethods();
        assertNotNull(removedPaymentMethods);
        assertEquals(existingCustomerPaymentMethodsThatShouldBeRemoved.size(), removedPaymentMethods.size());
        existingCustomerPaymentMethodsThatShouldBeRemoved.forEach(customerPaymentMethodToBeRemoved -> {
            final MutableHolder<Boolean> matchFound = new MutableHolder<Boolean>(Boolean.FALSE);
            removedPaymentMethods.forEach(removedPaymentMethod -> {
                if (removedPaymentMethod.getId().equals(customerPaymentMethodToBeRemoved.getId())) {
                    matchFound.setValue(Boolean.TRUE);
                }
            });
            assertTrue(matchFound.getValue().booleanValue());
        });
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private CustomerCardPaymentMethod createCustomerCardPaymentMethod(final String detailsReference, final int index) {
        final CustomerCardPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod();
        customerPaymentMethod.setId(Long.valueOf(index));
        final CustomerPaymentMethodAdyenInformation customerPaymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        customerPaymentMethodAdyenInformation.setRecurringDetailReference(detailsReference);
        customerPaymentMethod.setProviderInformation(customerPaymentMethodAdyenInformation);
        return customerPaymentMethod;
    }

    private CustomerBankPaymentMethod createCustomerBankPaymentMethod(final String detailsReference, final int index) {
        final CustomerBankPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerBankPaymentMethod();
        customerPaymentMethod.setId(Long.valueOf(index));
        final CustomerPaymentMethodAdyenInformation customerPaymentMethodAdyenInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        customerPaymentMethodAdyenInformation.setRecurringDetailReference(detailsReference);
        customerPaymentMethod.setProviderInformation(customerPaymentMethodAdyenInformation);
        return customerPaymentMethod;
    }

    private List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> createCustomerCardPaymentMethodProviderInformation(final int count, final int random) {
        final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentMethodProviderDetails = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final CustomerCardPaymentMethodDto customerCardPaymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
            customerCardPaymentMethodDto.setHolderName(customerCardPaymentMethodDto.getHolderName() + "_" + i);
            final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
            adyenInformationDto.setRecurringDetailReference(adyenInformationDto.getRecurringDetailReference() + "_" + random + "_" + i);
            paymentMethodProviderDetails.add(new PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData(customerCardPaymentMethodDto, adyenInformationDto));
        }
        return paymentMethodProviderDetails;
    }

    private List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> createCustomerBankPaymentMethodProviderInformation(final int count, final int random) {
        final List<PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData> paymentMethodProviderDetails = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final CustomerBankPaymentMethodDto customerCardPaymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
            customerCardPaymentMethodDto.setOwnerName(customerCardPaymentMethodDto.getOwnerName() + "_" + i);
            final CustomerPaymentMethodAdyenInformationDto adyenInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
            adyenInformationDto.setRecurringDetailReference(adyenInformationDto.getRecurringDetailReference() + "_" + random + "_" + i);
            paymentMethodProviderDetails.add(new PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData(customerCardPaymentMethodDto, adyenInformationDto));
        }
        return paymentMethodProviderDetails;
    }
}
