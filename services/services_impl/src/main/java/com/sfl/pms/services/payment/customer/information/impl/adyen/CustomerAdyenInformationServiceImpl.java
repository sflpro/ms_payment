package com.sfl.pms.services.payment.customer.information.impl.adyen;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.persistence.repositories.payment.customer.information.adyen.CustomerAdyenInformationRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.information.adyen.CustomerAdyenInformationService;
import com.sfl.pms.services.payment.customer.information.impl.AbstractCustomerPaymentProviderInformationServiceImpl;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
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
 * Date: 1/10/15
 * Time: 12:11 PM
 */
@Service
public class CustomerAdyenInformationServiceImpl extends AbstractCustomerPaymentProviderInformationServiceImpl<CustomerAdyenInformation> implements CustomerAdyenInformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAdyenInformationServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerAdyenInformationRepository customerAdyenInformationRepository;

    @Autowired
    private CustomerService customerService;

    /* Constructors */
    public CustomerAdyenInformationServiceImpl() {
        LOGGER.debug("Initializing customer Adyen information service");
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerAdyenInformation getOrCreateCustomerPaymentProviderInformation(@Nonnull final Long customerId) {
        Assert.notNull(customerId, "Customer id should not be null");
        LOGGER.debug("Getting customer Adyen information , customer id - {}", customerId);
        final Customer customer = customerService.getCustomerById(customerId);
        CustomerAdyenInformation information = customerAdyenInformationRepository.findByCustomerAndType(customer, PaymentProviderType.ADYEN);
        if (information == null) {
            LOGGER.debug("No customer Adyen information exists for customer with id - {}. Creating it.", customerId);
            information = new CustomerAdyenInformation();
            information.setShopperReference(customer.getUuId());
            information.setShopperEmail(customer.getEmail());
            information.setCustomer(customer);
            // Persist entity
            information = customerAdyenInformationRepository.save(information);
            LOGGER.debug("Successfully creates customer Adyen information for customer with id - {}. Customer Adyen information - {}", customer.getId(), information);
        } else {
            LOGGER.debug("Customer Adyen information already exists, using it. Customer id - {}, Adyen information - {}", customerId, information);
        }
        return information;
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentProviderInformationRepository<CustomerAdyenInformation> getRepository() {
        return customerAdyenInformationRepository;
    }

    @Override
    protected Class<CustomerAdyenInformation> getInstanceClass() {
        return CustomerAdyenInformation.class;
    }

    /* Properties getters and setters */
    public CustomerAdyenInformationRepository getCustomerAdyenInformationRepository() {
        return customerAdyenInformationRepository;
    }

    public void setCustomerAdyenInformationRepository(final CustomerAdyenInformationRepository customerAdyenInformationRepository) {
        this.customerAdyenInformationRepository = customerAdyenInformationRepository;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(final CustomerService customerService) {
        this.customerService = customerService;
    }
}
