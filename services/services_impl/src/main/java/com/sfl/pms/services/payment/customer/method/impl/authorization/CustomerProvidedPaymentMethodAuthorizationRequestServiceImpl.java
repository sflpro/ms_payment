package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.CustomerProvidedPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerProvidedPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerProvidedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 1:24 PM
 */
@Service
public class CustomerProvidedPaymentMethodAuthorizationRequestServiceImpl extends AbstractCustomerPaymentMethodAuthorizationRequestServiceImpl<CustomerProvidedPaymentMethodAuthorizationRequest> implements CustomerProvidedPaymentMethodAuthorizationRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerProvidedPaymentMethodAuthorizationRequestServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerProvidedPaymentMethodAuthorizationRequestRepository customerProvidedPaymentMethodAuthorizationRequestRepository;

    @Autowired
    private CustomerService customerService;

    /* Constructors */
    public CustomerProvidedPaymentMethodAuthorizationRequestServiceImpl() {
        LOGGER.debug("Initializing customer provided payment method authorization request service");
    }

    @Nonnull
    @Override
    public CustomerProvidedPaymentMethodAuthorizationRequest createPaymentMethodAuthorizationRequest(@Nonnull final Long customerId, @Nonnull final CustomerProvidedPaymentMethodAuthorizationRequestDto requestDto) {
        Assert.notNull(customerId, "Customer id should not be null");
        assertProvidedAuthorizationRequestDto(requestDto);
        LOGGER.debug("Creating new customer Provided payment method, customer id - {}, request DTO - {}", customerId, requestDto);
        // Load customer
        final Customer customer = customerService.getCustomerById(customerId);
        // Create new payment method request
        CustomerProvidedPaymentMethodAuthorizationRequest request = new CustomerProvidedPaymentMethodAuthorizationRequest(true);
        // Set properties
        request.setCustomer(customer);
        requestDto.updateDomainEntityProperties(request);
        // Persist entity
        request = customerProvidedPaymentMethodAuthorizationRequestRepository.save(request);
        LOGGER.debug("Successfully created new payment method authorization request for customer with id - {}, request - {}", customer.getId(), request);
        return request;
    }

    /* Utility methods */
    private void assertProvidedAuthorizationRequestDto(final CustomerProvidedPaymentMethodAuthorizationRequestDto requestDto) {
        assertAuthorizationRequestDto(requestDto);
        Assert.notNull(requestDto.getPaymentMethodType(), "Payment method type in request DTO should not be null");
    }

    @Override
    protected AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerProvidedPaymentMethodAuthorizationRequest> getRepository() {
        return customerProvidedPaymentMethodAuthorizationRequestRepository;
    }

    @Override
    protected Class<CustomerProvidedPaymentMethodAuthorizationRequest> getInstanceClass() {
        return CustomerProvidedPaymentMethodAuthorizationRequest.class;
    }

    /* Properties getters and setters */
    public CustomerProvidedPaymentMethodAuthorizationRequestRepository getCustomerProvidedPaymentMethodAuthorizationRequestRepository() {
        return customerProvidedPaymentMethodAuthorizationRequestRepository;
    }

    public void setCustomerProvidedPaymentMethodAuthorizationRequestRepository(final CustomerProvidedPaymentMethodAuthorizationRequestRepository customerProvidedPaymentMethodAuthorizationRequestRepository) {
        this.customerProvidedPaymentMethodAuthorizationRequestRepository = customerProvidedPaymentMethodAuthorizationRequestRepository;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }
}
