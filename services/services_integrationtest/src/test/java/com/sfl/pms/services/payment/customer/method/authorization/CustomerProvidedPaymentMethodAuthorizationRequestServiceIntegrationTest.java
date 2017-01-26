package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerProvidedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 1:57 PM
 */
public class CustomerProvidedPaymentMethodAuthorizationRequestServiceIntegrationTest extends AbstractCustomerPaymentMethodAuthorizationRequestServiceIntegrationTest<CustomerProvidedPaymentMethodAuthorizationRequest> {

    /* Dependencies */
    @Autowired
    private CustomerProvidedPaymentMethodAuthorizationRequestService customerProvidedPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public CustomerProvidedPaymentMethodAuthorizationRequestServiceIntegrationTest() {
    }

    @Test
    public void testCreatePaymentMethodAuthorizationRequest() {
        // Prepare data
        final CustomerProvidedPaymentMethodAuthorizationRequestDto authorizationRequestDto = getServicesTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequestDto();
        final Customer customer = getServicesTestHelper().createCustomer();
        flushAndClear();
        // Create authorization request
        CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = customerProvidedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customer.getId(), authorizationRequestDto);
        assertAuthorizationRequest(authorizationRequest, authorizationRequestDto, customer);
        // Flush, reload and assert again
        flushAndClear();
        authorizationRequest = customerProvidedPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(authorizationRequest.getId());
        assertAuthorizationRequest(authorizationRequest, authorizationRequestDto, customer);
    }

    /* Utility methods */
    private void assertAuthorizationRequest(final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest, final CustomerProvidedPaymentMethodAuthorizationRequestDto authorizationRequestDto, final Customer customer) {
        getServicesTestHelper().assertCustomerPaymentMethodAuthorizationRequest(authorizationRequest, authorizationRequestDto);
        // Assert customer
        assertNotNull(authorizationRequest.getCustomer());
        assertEquals(customer.getId(), authorizationRequest.getCustomer().getId());
    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerProvidedPaymentMethodAuthorizationRequest> getService() {
        return customerProvidedPaymentMethodAuthorizationRequestService;
    }

    @Override
    protected CustomerProvidedPaymentMethodAuthorizationRequest getInstance() {
        return getServicesTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
    }


}
