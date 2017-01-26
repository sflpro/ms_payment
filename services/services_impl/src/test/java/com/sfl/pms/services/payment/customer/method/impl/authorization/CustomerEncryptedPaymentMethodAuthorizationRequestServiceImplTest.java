package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:50 PM
 */
public class CustomerEncryptedPaymentMethodAuthorizationRequestServiceImplTest extends AbstractCustomerPaymentMethodAuthorizationRequestServiceImplTest<CustomerEncryptedPaymentMethodAuthorizationRequest> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerEncryptedPaymentMethodAuthorizationRequestServiceImpl customerAdyenPaymentMethodAuthorizationRequestService = new CustomerEncryptedPaymentMethodAuthorizationRequestServiceImpl();

    @Mock
    private CustomerEncryptedPaymentMethodAuthorizationRequestRepository customerEncryptedPaymentMethodAuthorizationRequestRepository;

    @Mock
    private CustomerService customerService;

    /* Constructors */
    public CustomerEncryptedPaymentMethodAuthorizationRequestServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentMethodAuthorizationRequestWithInvalidArguments() {
        // Test data
        final Long customerId = 1l;
        final CustomerEncryptedPaymentMethodAuthorizationRequestDto requestDto = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequestDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(null, requestDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerEncryptedPaymentMethodAuthorizationRequestDto(null, requestDto.getPaymentProviderIntegrationType(), requestDto.getClientIpAddress(), requestDto.getPaymentMethodGroup(), requestDto.getEncryptedData(), requestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerEncryptedPaymentMethodAuthorizationRequestDto(requestDto.getPaymentProviderType(), null, requestDto.getClientIpAddress(), requestDto.getPaymentMethodGroup(), requestDto.getEncryptedData(), requestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerEncryptedPaymentMethodAuthorizationRequestDto(requestDto.getPaymentProviderType(), requestDto.getPaymentProviderIntegrationType(), requestDto.getClientIpAddress(), null, requestDto.getEncryptedData(), requestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerEncryptedPaymentMethodAuthorizationRequestDto(requestDto.getPaymentProviderType(), requestDto.getPaymentProviderIntegrationType(), requestDto.getClientIpAddress(), requestDto.getPaymentMethodGroup(), null, requestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerEncryptedPaymentMethodAuthorizationRequestDto(requestDto.getPaymentProviderType(), requestDto.getPaymentProviderIntegrationType(), requestDto.getClientIpAddress(), requestDto.getPaymentMethodGroup(), requestDto.getEncryptedData(), null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentMethodAuthorizationRequest() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        final CustomerEncryptedPaymentMethodAuthorizationRequestDto requestDto = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequestDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerEncryptedPaymentMethodAuthorizationRequestRepository.save(isA(CustomerEncryptedPaymentMethodAuthorizationRequest.class))).andAnswer(new IAnswer<CustomerEncryptedPaymentMethodAuthorizationRequest>() {
            @Override
            public CustomerEncryptedPaymentMethodAuthorizationRequest answer() throws Throwable {
                return (CustomerEncryptedPaymentMethodAuthorizationRequest) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerEncryptedPaymentMethodAuthorizationRequest request = customerAdyenPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, requestDto);
        getServicesImplTestHelper().assertCustomerEncryptedPaymentMethodAuthorizationRequest(request, requestDto);
        Assert.assertEquals(customer, request.getCustomer());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerEncryptedPaymentMethodAuthorizationRequest> getRepository() {
        return customerEncryptedPaymentMethodAuthorizationRequestRepository;
    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerEncryptedPaymentMethodAuthorizationRequest> getService() {
        return customerAdyenPaymentMethodAuthorizationRequestService;
    }

    @Override
    protected CustomerEncryptedPaymentMethodAuthorizationRequest getInstance() {
        return getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
    }

    @Override
    protected Class<CustomerEncryptedPaymentMethodAuthorizationRequest> getInstanceClass() {
        return CustomerEncryptedPaymentMethodAuthorizationRequest.class;
    }
}
