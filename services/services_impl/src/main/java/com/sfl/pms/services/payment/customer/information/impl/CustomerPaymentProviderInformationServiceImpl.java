package com.sfl.pms.services.payment.customer.information.impl;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.persistence.repositories.payment.customer.information.CustomerPaymentProviderInformationRepository;
import com.sfl.pms.services.payment.customer.information.CustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:01 PM
 */
@Service
public class CustomerPaymentProviderInformationServiceImpl extends AbstractCustomerPaymentProviderInformationServiceImpl<CustomerPaymentProviderInformation> implements CustomerPaymentProviderInformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentProviderInformationServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentProviderInformationRepository customerPaymentProviderInformationRepository;

    /* Constructors */
    public CustomerPaymentProviderInformationServiceImpl() {
        LOGGER.debug("Initializing customer payment provider information service");
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentProviderInformationRepository<CustomerPaymentProviderInformation> getRepository() {
        return customerPaymentProviderInformationRepository;
    }

    @Override
    protected Class<CustomerPaymentProviderInformation> getInstanceClass() {
        return CustomerPaymentProviderInformation.class;
    }

    /* Properties getters and setters */
    public CustomerPaymentProviderInformationRepository getCustomerPaymentProviderInformationRepository() {
        return customerPaymentProviderInformationRepository;
    }

    public void setCustomerPaymentProviderInformationRepository(final CustomerPaymentProviderInformationRepository customerPaymentProviderInformationRepository) {
        this.customerPaymentProviderInformationRepository = customerPaymentProviderInformationRepository;
    }
}
