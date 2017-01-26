package com.sfl.pms.services.payment.customer.information;

import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:34 PM
 */
public class CustomerPaymentProviderInformationServiceIntegrationTest extends AbstractCustomerPaymentProviderInformationServiceIntegrationTest<CustomerPaymentProviderInformation> {

    /* Dependencies */
    @Autowired
    private CustomerPaymentProviderInformationService customerPaymentProviderInformationService;

    /* Constructors */
    public CustomerPaymentProviderInformationServiceIntegrationTest() {
    }

    /* Test methods */

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentProviderInformationService<CustomerPaymentProviderInformation> getService() {
        return customerPaymentProviderInformationService;
    }

    @Override
    protected CustomerPaymentProviderInformation getInstance() {
        return getServicesTestHelper().createCustomerAdyenInformation();
    }

}
