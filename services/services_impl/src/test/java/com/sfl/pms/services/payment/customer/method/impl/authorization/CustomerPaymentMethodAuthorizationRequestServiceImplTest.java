package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.easymock.TestSubject;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:47 PM
 */
public class CustomerPaymentMethodAuthorizationRequestServiceImplTest extends AbstractCustomerPaymentMethodAuthorizationRequestServiceImplTest<CustomerPaymentMethodAuthorizationRequest> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodAuthorizationRequestServiceImpl customerPaymentMethodAuthorizationRequestService = new CustomerPaymentMethodAuthorizationRequestServiceImpl();

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestServiceImplTest() {
    }

    /* Test methods */

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerPaymentMethodAuthorizationRequest> getRepository() {
        return getCustomerPaymentMethodAuthorizationRequestRepository();
    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerPaymentMethodAuthorizationRequest> getService() {
        return customerPaymentMethodAuthorizationRequestService;
    }

    @Override
    protected CustomerPaymentMethodAuthorizationRequest getInstance() {
        return getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
    }

    @Override
    protected Class<CustomerPaymentMethodAuthorizationRequest> getInstanceClass() {
        return CustomerPaymentMethodAuthorizationRequest.class;
    }
}
