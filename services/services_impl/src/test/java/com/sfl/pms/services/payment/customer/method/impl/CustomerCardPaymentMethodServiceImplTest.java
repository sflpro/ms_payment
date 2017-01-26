package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.AbstractCustomerPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerCardPaymentMethodRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodRepository;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerCardPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAlreadyBoundAuthorizationRequestException;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodInvalidCustomerException;
import com.sfl.pms.services.payment.customer.method.impl.provider.CustomerPaymentMethodProviderInformationHandler;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:48 PM
 */
public class CustomerCardPaymentMethodServiceImplTest extends AbstractCustomerPaymentMethodServiceImplTest<CustomerCardPaymentMethod> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerCardPaymentMethodServiceImpl customerCardPaymentMethodService = new CustomerCardPaymentMethodServiceImpl();

    @Mock
    private CustomerCardPaymentMethodRepository customerCardPaymentMethodRepository;

    @Mock
    private CustomerPaymentMethodProviderInformationHandler customerPaymentMethodAdyenInformationHandler;

    @Mock
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    @Mock
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    /* Constructors */
    public CustomerCardPaymentMethodServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreateCustomerPaymentMethodWithNullArguments() {
        // Test data
        final Long customerId = 1l;
        final Long authorizationRequestId = 2l;
        final CustomerCardPaymentMethodDto customerCardPaymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(null, authorizationRequestId, customerCardPaymentMethodDto, informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, null, informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, customerCardPaymentMethodDto, null);
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
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodAdyenInformationDto informationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerCardPaymentMethodDto(null, paymentMethodDto.getExpiryMonth(), paymentMethodDto.getExpiryYear(), paymentMethodDto.getNumberTail(), paymentMethodDto.getHolderName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerCardPaymentMethodDto(PaymentMethodType.IDEAL, paymentMethodDto.getExpiryMonth(), paymentMethodDto.getExpiryYear(), paymentMethodDto.getNumberTail(), paymentMethodDto.getHolderName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerCardPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), 0, paymentMethodDto.getExpiryYear(), paymentMethodDto.getNumberTail(), paymentMethodDto.getHolderName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerCardPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), paymentMethodDto.getExpiryMonth(), 0, paymentMethodDto.getNumberTail(), paymentMethodDto.getHolderName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerCardPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), paymentMethodDto.getExpiryMonth(), paymentMethodDto.getExpiryYear(), null, paymentMethodDto.getHolderName()), informationDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, new CustomerCardPaymentMethodDto(paymentMethodDto.getPaymentMethodType(), paymentMethodDto.getExpiryMonth(), paymentMethodDto.getExpiryYear(), paymentMethodDto.getNumberTail(), null), informationDto);
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
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
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
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, informationDto);
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
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
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
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, informationDto);
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
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
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
            customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, informationDto);
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
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
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
        expect(customerCardPaymentMethodRepository.save(isA(CustomerCardPaymentMethod.class))).andAnswer(new IAnswer<CustomerCardPaymentMethod>() {
            @Override
            public CustomerCardPaymentMethod answer() throws Throwable {
                return (CustomerCardPaymentMethod) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerCardPaymentMethod paymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, authorizationRequestId, paymentMethodDto, providerInformationDto);
        getServicesImplTestHelper().assertCustomerCardPaymentMethod(paymentMethod, paymentMethodDto);
        assertTrue(paymentMethod.getProviderInformation() instanceof CustomerPaymentMethodAdyenInformation);
        getServicesImplTestHelper().assertCustomerPaymentMethodAdyenInformation((CustomerPaymentMethodAdyenInformation) paymentMethod.getProviderInformation(), providerInformationDto);
        assertEquals(authorizationRequest, paymentMethod.getAuthorizationRequest());
        Assert.assertEquals(customer, paymentMethod.getCustomer());
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
        final CustomerCardPaymentMethodDto paymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
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
        expect(customerCardPaymentMethodRepository.save(isA(CustomerCardPaymentMethod.class))).andAnswer(new IAnswer<CustomerCardPaymentMethod>() {
            @Override
            public CustomerCardPaymentMethod answer() throws Throwable {
                return (CustomerCardPaymentMethod) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerCardPaymentMethod paymentMethod = customerCardPaymentMethodService.createCustomerPaymentMethod(customerId, null, paymentMethodDto, providerInformationDto);
        getServicesImplTestHelper().assertCustomerCardPaymentMethod(paymentMethod, paymentMethodDto);
        assertTrue(paymentMethod.getProviderInformation() instanceof CustomerPaymentMethodAdyenInformation);
        getServicesImplTestHelper().assertCustomerPaymentMethodAdyenInformation((CustomerPaymentMethodAdyenInformation) paymentMethod.getProviderInformation(), providerInformationDto);
        assertEquals(null, paymentMethod.getAuthorizationRequest());
        Assert.assertEquals(customer, paymentMethod.getCustomer());
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
    protected CustomerCardPaymentMethod getInstance() {
        return getServicesImplTestHelper().createCustomerCardPaymentMethod();
    }

    @Override
    protected Class<CustomerCardPaymentMethod> getInstanceClass() {
        return CustomerCardPaymentMethod.class;
    }

    @Override
    protected AbstractCustomerPaymentMethodService<CustomerCardPaymentMethod> getService() {
        return customerCardPaymentMethodService;
    }

    @Override
    protected AbstractCustomerPaymentMethodRepository<CustomerCardPaymentMethod> getRepository() {
        return customerCardPaymentMethodRepository;
    }

}
