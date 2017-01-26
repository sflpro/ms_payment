package com.sfl.pms.services.payment.customer.information.adyen;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationServiceIntegrationTest;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:37 PM
 */
public class CustomerAdyenInformationServiceIntegrationTest extends AbstractCustomerPaymentProviderInformationServiceIntegrationTest<CustomerAdyenInformation> {

    /* Dependencies */
    @Autowired
    private CustomerAdyenInformationService customerAdyenInformationService;

    /* Constructors */
    public CustomerAdyenInformationServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetOrCreateCustomerPaymentProviderInformationWhenItAlreadyExists() {
        // Prepare data
        final Customer customer = getServicesTestHelper().createCustomer();
        flushAndClear();
        // Create customer Adyen information
        final CustomerAdyenInformation information = customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(customer.getId());
        getServicesTestHelper().assertCustomerAdyenInformation(information, customer);
        // Flush, reload and assert again
        flushAndClear();
        final CustomerAdyenInformation result = customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(customer.getId());
        assertEquals(information, result);
    }

    @Test
    public void testGetOrCreateCustomerPaymentProviderInformationWhenItDoesNotExist() {
        // Prepare data
        final Customer customer = getServicesTestHelper().createCustomer();
        flushAndClear();
        // Create customer Adyen information
        CustomerAdyenInformation information = customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(customer.getId());
        getServicesTestHelper().assertCustomerAdyenInformation(information, customer);
        // Flush, reload and assert again
        flushAndClear();
        information = customerAdyenInformationService.getCustomerPaymentProviderInformationById(information.getId());
        getServicesTestHelper().assertCustomerAdyenInformation(information, customer);
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentProviderInformationService<CustomerAdyenInformation> getService() {
        return customerAdyenInformationService;
    }

    @Override
    protected CustomerAdyenInformation getInstance() {
        return getServicesTestHelper().createCustomerAdyenInformation();
    }

}
