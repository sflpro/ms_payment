package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:29 PM
 */
@Service
public class CustomerPaymentMethodAuthorizationRequestServiceImpl extends AbstractCustomerPaymentMethodAuthorizationRequestServiceImpl<CustomerPaymentMethodAuthorizationRequest> implements CustomerPaymentMethodAuthorizationRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAuthorizationRequestServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestRepository customerPaymentMethodAuthorizationRequestRepository;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestServiceImpl() {
        LOGGER.debug("Initializing customer payment method authorization request service");
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerPaymentMethodAuthorizationRequest> getRepository() {
        return customerPaymentMethodAuthorizationRequestRepository;
    }

    @Override
    protected Class<CustomerPaymentMethodAuthorizationRequest> getInstanceClass() {
        return CustomerPaymentMethodAuthorizationRequest.class;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestRepository getCustomerPaymentMethodAuthorizationRequestRepository() {
        return customerPaymentMethodAuthorizationRequestRepository;
    }

    public void setCustomerPaymentMethodAuthorizationRequestRepository(final CustomerPaymentMethodAuthorizationRequestRepository customerPaymentMethodAuthorizationRequestRepository) {
        this.customerPaymentMethodAuthorizationRequestRepository = customerPaymentMethodAuthorizationRequestRepository;
    }
}
