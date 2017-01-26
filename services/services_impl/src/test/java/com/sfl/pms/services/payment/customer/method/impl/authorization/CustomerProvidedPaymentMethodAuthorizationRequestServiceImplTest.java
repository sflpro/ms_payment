package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.CustomerProvidedPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerProvidedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 1:32 PM
 */
public class CustomerProvidedPaymentMethodAuthorizationRequestServiceImplTest extends AbstractCustomerPaymentMethodAuthorizationRequestServiceImplTest<CustomerProvidedPaymentMethodAuthorizationRequest> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerProvidedPaymentMethodAuthorizationRequestServiceImpl customerProvidedPaymentMethodAuthorizationRequestService = new CustomerProvidedPaymentMethodAuthorizationRequestServiceImpl();

    @Mock
    private CustomerProvidedPaymentMethodAuthorizationRequestRepository customerProvidedPaymentMethodAuthorizationRequestRepository;

    @Mock
    private CustomerService customerService;

    /* Constructors */
    public CustomerProvidedPaymentMethodAuthorizationRequestServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentMethodAuthorizationRequestWithInvalidArguments() {
        // Test data
        final Long customerId = 1L;
        final CustomerProvidedPaymentMethodAuthorizationRequestDto authorizationRequestDto = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequestDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(null, authorizationRequestDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerProvidedPaymentMethodAuthorizationRequestDto(null, authorizationRequestDto.getPaymentProviderIntegrationType(), authorizationRequestDto.getClientIpAddress(), authorizationRequestDto.getPaymentMethodType(), authorizationRequestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerProvidedPaymentMethodAuthorizationRequestDto(authorizationRequestDto.getPaymentProviderType(), null, authorizationRequestDto.getClientIpAddress(), authorizationRequestDto.getPaymentMethodType(), authorizationRequestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerProvidedPaymentMethodAuthorizationRequestDto(authorizationRequestDto.getPaymentProviderType(), authorizationRequestDto.getPaymentProviderIntegrationType(), authorizationRequestDto.getClientIpAddress(), null, authorizationRequestDto.getCurrency()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, new CustomerProvidedPaymentMethodAuthorizationRequestDto(authorizationRequestDto.getPaymentProviderType(), authorizationRequestDto.getPaymentProviderIntegrationType(), authorizationRequestDto.getClientIpAddress(), authorizationRequestDto.getPaymentMethodType(), null));
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
        final Long customerId = 1L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerProvidedPaymentMethodAuthorizationRequestDto authorizationRequestDto = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequestDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerProvidedPaymentMethodAuthorizationRequestRepository.save(isA(CustomerProvidedPaymentMethodAuthorizationRequest.class))).andAnswer(() -> {
            final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = (CustomerProvidedPaymentMethodAuthorizationRequest) getCurrentArguments()[0];
            return authorizationRequest;
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customerId, authorizationRequestDto);
        getServicesImplTestHelper().assertCustomerProvidedPaymentMethodAuthorizationRequest(authorizationRequest, authorizationRequestDto);
        assertNotNull(authorizationRequest.getCustomer());
        assertEquals(customer, authorizationRequest.getCustomer());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerProvidedPaymentMethodAuthorizationRequest> getRepository() {
        return customerProvidedPaymentMethodAuthorizationRequestRepository;
    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerProvidedPaymentMethodAuthorizationRequest> getService() {
        return customerProvidedPaymentMethodAuthorizationRequestService;
    }

    @Override
    protected CustomerProvidedPaymentMethodAuthorizationRequest getInstance() {
        return getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
    }

    @Override
    protected Class<CustomerProvidedPaymentMethodAuthorizationRequest> getInstanceClass() {
        return CustomerProvidedPaymentMethodAuthorizationRequest.class;
    }
}
