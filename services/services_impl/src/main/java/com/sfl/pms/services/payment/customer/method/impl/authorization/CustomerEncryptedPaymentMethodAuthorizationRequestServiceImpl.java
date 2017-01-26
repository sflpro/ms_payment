package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:35 PM
 */
@Service
public class CustomerEncryptedPaymentMethodAuthorizationRequestServiceImpl extends AbstractCustomerPaymentMethodAuthorizationRequestServiceImpl<CustomerEncryptedPaymentMethodAuthorizationRequest> implements CustomerEncryptedPaymentMethodAuthorizationRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEncryptedPaymentMethodAuthorizationRequestServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerEncryptedPaymentMethodAuthorizationRequestRepository customerEncryptedPaymentMethodAuthorizationRequestRepository;

    @Autowired
    private CustomerService customerService;

    /* Constructors */
    public CustomerEncryptedPaymentMethodAuthorizationRequestServiceImpl() {
        LOGGER.debug("Initializing customer Adyen payment method authorization request service");
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerEncryptedPaymentMethodAuthorizationRequest createPaymentMethodAuthorizationRequest(@Nonnull final Long customerId, @Nonnull final CustomerEncryptedPaymentMethodAuthorizationRequestDto requestDto) {
        Assert.notNull(customerId, "Customer id should not be null");
        assertEncryptedAuthorizationRequestDto(requestDto);
        LOGGER.debug("Creating new customer Encrypted payment method, customer id - {}, request DTO - {}", customerId, requestDto);
        // Load customer
        final Customer customer = customerService.getCustomerById(customerId);
        // Create new payment method request
        CustomerEncryptedPaymentMethodAuthorizationRequest request = new CustomerEncryptedPaymentMethodAuthorizationRequest(true);
        // Set properties
        request.setCustomer(customer);
        requestDto.updateDomainEntityProperties(request);
        // Persist entity
        request = customerEncryptedPaymentMethodAuthorizationRequestRepository.save(request);
        LOGGER.debug("Successfully created new payment method authorization request for customer with id - {}, request - {}", customer.getId(), request);
        return request;
    }


    /* Utility methods */
    private void assertEncryptedAuthorizationRequestDto(final CustomerEncryptedPaymentMethodAuthorizationRequestDto requestDto) {
        assertAuthorizationRequestDto(requestDto);
        Assert.notNull(requestDto.getPaymentMethodGroup(), "Payment method group in request DTO should not be null");
        Assert.notNull(requestDto.getEncryptedData(), "Encrypted data in request DTO should not be null");
    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerEncryptedPaymentMethodAuthorizationRequest> getRepository() {
        return customerEncryptedPaymentMethodAuthorizationRequestRepository;
    }

    @Override
    protected Class<CustomerEncryptedPaymentMethodAuthorizationRequest> getInstanceClass() {
        return CustomerEncryptedPaymentMethodAuthorizationRequest.class;
    }

    /* Properties getters and setters */
    public CustomerEncryptedPaymentMethodAuthorizationRequestRepository getCustomerEncryptedPaymentMethodAuthorizationRequestRepository() {
        return customerEncryptedPaymentMethodAuthorizationRequestRepository;
    }

    public void setCustomerEncryptedPaymentMethodAuthorizationRequestRepository(final CustomerEncryptedPaymentMethodAuthorizationRequestRepository customerEncryptedPaymentMethodAuthorizationRequestRepository) {
        this.customerEncryptedPaymentMethodAuthorizationRequestRepository = customerEncryptedPaymentMethodAuthorizationRequestRepository;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }
}
