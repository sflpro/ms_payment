package com.sfl.pms.services.payment.customer.information.impl;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.persistence.repositories.payment.customer.information.CustomerPaymentProviderInformationRepository;
import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import org.easymock.Mock;
import org.easymock.TestSubject;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:21 PM
 */
public class CustomerPaymentProviderInformationServiceImplTest extends AbstractCustomerPaymentProviderInformationServiceImplTest<CustomerPaymentProviderInformation> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentProviderInformationServiceImpl customerPaymentProviderInformationService = new CustomerPaymentProviderInformationServiceImpl();

    @Mock
    private CustomerPaymentProviderInformationRepository customerPaymentProviderInformationRepository;

    /* Constructors */
    public CustomerPaymentProviderInformationServiceImplTest() {
    }

    @Override
    protected AbstractCustomerPaymentProviderInformationService<CustomerPaymentProviderInformation> getService() {
        return customerPaymentProviderInformationService;
    }

    @Override
    protected AbstractCustomerPaymentProviderInformationRepository<CustomerPaymentProviderInformation> getRepository() {
        return customerPaymentProviderInformationRepository;
    }

    @Override
    protected CustomerPaymentProviderInformation getInstance() {
        return getServicesImplTestHelper().createCustomerAdyenInformation();
    }

    @Override
    protected Class<CustomerPaymentProviderInformation> getInstanceClass() {
        return CustomerPaymentProviderInformation.class;
    }

}
