package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 10:18 PM
 */
public class CustomerEncryptedPaymentMethodAuthorizationRequestServiceIntegrationTest extends AbstractCustomerPaymentMethodAuthorizationRequestServiceIntegrationTest<CustomerEncryptedPaymentMethodAuthorizationRequest> {

    /* Dependencies */
    @Autowired
    private CustomerEncryptedPaymentMethodAuthorizationRequestService customerEncryptedPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public CustomerEncryptedPaymentMethodAuthorizationRequestServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentMethodAuthorizationRequest() {
        // Prepare data
        final CustomerEncryptedPaymentMethodAuthorizationRequestDto requestDto = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequestDto();
        final Customer customer = getServicesTestHelper().createCustomer();
        flushAndClear();
        // Create new request
        CustomerEncryptedPaymentMethodAuthorizationRequest request = customerEncryptedPaymentMethodAuthorizationRequestService.createPaymentMethodAuthorizationRequest(customer.getId(), requestDto);
        assertAuthorizationRequest(request, customer, requestDto);
        // Flush , reload and assert again
        flushAndClear();
        request = customerEncryptedPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(request.getId());
        assertAuthorizationRequest(request, customer, requestDto);
    }

    /* Utility methods */
    private void assertAuthorizationRequest(final CustomerEncryptedPaymentMethodAuthorizationRequest request, final Customer customer, final CustomerEncryptedPaymentMethodAuthorizationRequestDto requestDto) {
        getServicesTestHelper().assertCustomerEncryptedPaymentMethodAuthorizationRequest(request, requestDto);
        assertNotNull(request.getCustomer());
        assertEquals(customer.getId(), request.getCustomer().getId());

    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerEncryptedPaymentMethodAuthorizationRequest> getService() {
        return customerEncryptedPaymentMethodAuthorizationRequestService;
    }

    @Override
    protected CustomerEncryptedPaymentMethodAuthorizationRequest getInstance() {
        return getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
    }

}
