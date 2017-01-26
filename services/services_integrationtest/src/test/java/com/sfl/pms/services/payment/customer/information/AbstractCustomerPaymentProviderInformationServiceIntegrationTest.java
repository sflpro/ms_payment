package com.sfl.pms.services.payment.customer.information;

import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:31 PM
 */
public abstract class AbstractCustomerPaymentProviderInformationServiceIntegrationTest<T extends CustomerPaymentProviderInformation> extends AbstractServiceIntegrationTest {

    /* Constructors */
    public AbstractCustomerPaymentProviderInformationServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerProviderInformationById() {
        // Prepare data
        final T information = getInstance();
        T result = getService().getCustomerPaymentProviderInformationById(information.getId());
        assertEquals(information, result);
        // Flush, reload and assert
        flushAndClear();
        result = getService().getCustomerPaymentProviderInformationById(information.getId());
        assertEquals(information, result);
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentProviderInformationService<T> getService();

    protected abstract T getInstance();

}
