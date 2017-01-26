package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerBankPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodRepository;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerBankPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAlreadyBoundAuthorizationRequestException;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodInvalidCustomerException;
import com.sfl.pms.services.payment.customer.method.impl.provider.CustomerPaymentMethodProviderInformationHandler;
import com.sfl.pms.services.payment.customer.method.model.CustomerBankPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/3/15
 * Time: 4:46 PM
 */
public class CustomerBankPaymentMethodServiceImplTest extends AbstractCustomerPaymentMethodServiceImplTest<CustomerBankPaymentMethod> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerBankPaymentMethodServiceImpl customerBankPaymentMethodService = new CustomerBankPaymentMethodServiceImpl();

    @Mock
    private CustomerBankPaymentMethodRepository customerBankPaymentMethodRepository;

    @Mock
    private CustomerPaymentMethodProviderInformationHandler customerPaymentMethodAdyenInformationHandler;

    @Mock
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    @Mock
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    /* Constructors */
    public CustomerBankPaymentMethodServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreateCustomerPaymentMethodWithNullArguments() {
        // Test data
        final Long customerId = 1l;
        final Long authorizationRequestId = 2l;
        final CustomerBankPaymentMethodDto customerCardPaymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(null, authorizationRequestId, customerCardPaymentMethodDto, informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, null, informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, customerCardPaymentMethodDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerPaymentMethodWithInvalidPaymentMethodDto() {
        // Test data
        final Long customerId = 1l;
        final Long authorizationRequestId = 2l;
        final CustomerBankPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerBankPaymentMethodDto(null, paymentMethodDto.getBankAccountNumber(), paymentMethodDto.getBankName(), paymentMethodDto.getCountryCode(), paymentMethodDto.getIban(), paymentMethodDto.getBic(), paymentMethodDto.getOwnerName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerBankPaymentMethodDto(PaymentMethodType.MASTER_CARD, paymentMethodDto.getBankAccountNumber(), paymentMethodDto.getBankName(), paymentMethodDto.getCountryCode(), paymentMethodDto.getIban(), paymentMethodDto.getBic(), paymentMethodDto.getOwnerName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerBankPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), paymentMethodDto.getBankAccountNumber(), null, paymentMethodDto.getCountryCode(), paymentMethodDto.getIban(), paymentMethodDto.getBic(), paymentMethodDto.getOwnerName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerBankPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), paymentMethodDto.getBankAccountNumber(), paymentMethodDto.getBankName(), null, paymentMethodDto.getIban(), paymentMethodDto.getBic(), paymentMethodDto.getOwnerName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerBankPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), paymentMethodDto.getBankAccountNumber(), paymentMethodDto.getBankName(), paymentMethodDto.getCountryCode(), paymentMethodDto.getBic(), paymentMethodDto.getIban(), null), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerPaymentMethodWithInvalidProviderInformationDto() {
        // Test data
        final Long customerId = 1l;
        final Long authorizationRequestId = 2l;
        final CustomerBankPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Expectations
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(eq(informationDto));
        expectLastCall().andThrow(new IllegalArgumentException()).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerPaymentMethodWithInvalidCustomer() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long invalidCustomerId = 2l;
        final Customer invalidCustomer = getServicesImplTestHelper().createCustomer();
        invalidCustomer.setId(invalidCustomerId);
        final Long authorizationRequestId = 3l;
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authorizationRequest.setId(authorizationRequestId);
        authorizationRequest.setCustomer(invalidCustomer);
        final CustomerBankPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Expectations
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(eq(informationDto));
        expectLastCall().once();
        expect(getCustomerService().getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(authorizationRequestId))).andReturn(authorizationRequest).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, informationDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodInvalidCustomerException ex) {
            // Expected
            assertCustomerPaymentMethodInvalidCustomerException(ex, customerId, invalidCustomerId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerPaymentMethodWhenAuthorizationRequestIsAlreadyBound() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long authorizationRequestId = 3l;
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authorizationRequest.setId(authorizationRequestId);
        authorizationRequest.setCustomer(customer);
        final CustomerBankPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final Long boundPaymentMethodId = 4l;
        final CustomerCardPaymentMethod boundPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod();
        boundPaymentMethod.setId(boundPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(eq(informationDto));
        expectLastCall().once();
        expect(getCustomerService().getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(authorizationRequestId))).andReturn(authorizationRequest).once();
        expect(customerPaymentMethodRepository.findByAuthorizationRequest(eq(authorizationRequest))).andReturn(boundPaymentMethod).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, informationDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAlreadyBoundAuthorizationRequestException ex) {
            assertCustomerPaymentMethodAlreadyBoundAuthorizationRequestException(ex, boundPaymentMethodId, authorizationRequestId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerPaymentMethodWithAuthorizationRequest() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long authorizationRequestId = 3l;
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authorizationRequest.setId(authorizationRequestId);
        authorizationRequest.setCustomer(customer);
        final CustomerBankPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto providerInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerPaymentMethodAdyenInformation providerInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation(providerInformationDto);
        // Reset
        resetAll();
        // Expectations
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(eq(providerInformationDto));
        expectLastCall().once();
        expect(getCustomerService().getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(authorizationRequestId))).andReturn(authorizationRequest).once();
        expect(customerPaymentMethodRepository.findByAuthorizationRequest(eq(authorizationRequest))).andReturn(null).once();
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationUniqueness(eq(providerInformationDto));
        expectLastCall().once();
        expect(customerPaymentMethodAdyenInformationHandler.convertPaymentMethodProviderInformationDto(eq(providerInformationDto))).andReturn(providerInformation).once();
        expect(customerBankPaymentMethodRepository.save(isA(CustomerBankPaymentMethod.class))).andAnswer(new IAnswer<CustomerBankPaymentMethod>() {
            @Override
            public CustomerBankPaymentMethod answer() throws Throwable {
                return (CustomerBankPaymentMethod) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerBankPaymentMethod paymentMethod = customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, providerInformationDto);
        getServicesImplTestHelper().assertCustomerBankPaymentMethod(paymentMethod, paymentMethodDto);
        assertTrue(paymentMethod.getProviderInformation() instanceof CustomerPaymentMethodAdyenInformation);
        getServicesImplTestHelper().assertCustomerPaymentMethodAdyenInformation((CustomerPaymentMethodAdyenInformation) paymentMethod.getProviderInformation(), providerInformationDto);
        assertEquals(authorizationRequest, paymentMethod.getAuthorizationRequest());
        assertEquals(customer, paymentMethod.getCustomer());
        assertEquals(paymentMethod, paymentMethod.getProviderInformation().getCustomerPaymentMethod());
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerPaymentMethodWithoutAuthorizationRequest() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerBankPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerBankPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto providerInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        final CustomerPaymentMethodAdyenInformation providerInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation(providerInformationDto);
        // Reset
        resetAll();
        // Expectations
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationDto(eq(providerInformationDto));
        expectLastCall().once();
        expect(getCustomerService().getCustomerById(eq(customerId))).andReturn(customer).once();
        customerPaymentMethodAdyenInformationHandler.assertProviderInformationUniqueness(eq(providerInformationDto));
        expectLastCall().once();
        expect(customerPaymentMethodAdyenInformationHandler.convertPaymentMethodProviderInformationDto(eq(providerInformationDto))).andReturn(providerInformation).once();
        expect(customerBankPaymentMethodRepository.save(isA(CustomerBankPaymentMethod.class))).andAnswer(new IAnswer<CustomerBankPaymentMethod>() {
            @Override
            public CustomerBankPaymentMethod answer() throws Throwable {
                return (CustomerBankPaymentMethod) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerBankPaymentMethod paymentMethod = customerBankPaymentMethodService.createCustomerPaymentMethod(customerId, null, paymentMethodDto, providerInformationDto);
        getServicesImplTestHelper().assertCustomerBankPaymentMethod(paymentMethod, paymentMethodDto);
        assertTrue(paymentMethod.getProviderInformation() instanceof CustomerPaymentMethodAdyenInformation);
        getServicesImplTestHelper().assertCustomerPaymentMethodAdyenInformation((CustomerPaymentMethodAdyenInformation) paymentMethod.getProviderInformation(), providerInformationDto);
        assertEquals(null, paymentMethod.getAuthorizationRequest());
        assertEquals(customer, paymentMethod.getCustomer());
        assertEquals(paymentMethod, paymentMethod.getProviderInformation().getCustomerPaymentMethod());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertCustomerPaymentMethodAlreadyBoundAuthorizationRequestException(final CustomerPaymentMethodAlreadyBoundAuthorizationRequestException ex, final Long existingPaymentMethodId, final Long authorizationRequestId) {
        assertEquals(existingPaymentMethodId, ex.getExistingPaymentMethodId());
        assertEquals(authorizationRequestId, ex.getAuthorizationRequestId());
    }

    private void assertCustomerPaymentMethodInvalidCustomerException(final CustomerPaymentMethodInvalidCustomerException ex, final Long providerCustomerId, final Long authorizationRequestCustomerId) {
        assertEquals(providerCustomerId, ex.getProvidedCustomerId());
        assertEquals(authorizationRequestCustomerId, ex.getPaymentMethodCustomerId());
    }

    @Override
    protected CustomerBankPaymentMethod getInstance() {
        return getServicesImplTestHelper().createCustomerBankPaymentMethod();
    }

    @Override
    protected Class<CustomerBankPaymentMethod> getInstanceClass() {
        return CustomerBankPaymentMethod.class;
    }

    @Override
    protected AbstractCustomerPaymentMethodService<CustomerBankPaymentMethod> getService() {
        return customerBankPaymentMethodService;
    }

    @Override
    protected AbstractCustomerPaymentMethodRepository<CustomerBankPaymentMethod> getRepository() {
        return customerBankPaymentMethodRepository;
    }
}
