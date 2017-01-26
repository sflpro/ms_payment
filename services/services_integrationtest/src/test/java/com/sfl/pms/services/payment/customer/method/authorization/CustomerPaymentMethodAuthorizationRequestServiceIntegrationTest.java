package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 10:16 PM
 */
public class CustomerPaymentMethodAuthorizationRequestServiceIntegrationTest extends AbstractCustomerPaymentMethodAuthorizationRequestServiceIntegrationTest<CustomerPaymentMethodAuthorizationRequest> {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestServiceIntegrationTest() {
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerPaymentMethodAuthorizationRequest> getService() {
        return customerPaymentMethodAuthorizationRequestService;
    }

    @Override
    protected CustomerPaymentMethodAuthorizationRequest getInstance() {
        return getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
    }

}
