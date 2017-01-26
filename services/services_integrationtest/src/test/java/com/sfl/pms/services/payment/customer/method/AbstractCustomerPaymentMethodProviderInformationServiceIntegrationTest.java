package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:59 AM
 */
@Ignore
public abstract class AbstractCustomerPaymentMethodProviderInformationServiceIntegrationTest<T extends CustomerPaymentMethodProviderInformation> extends AbstractServiceIntegrationTest {

    /* Constructors */
    public AbstractCustomerPaymentMethodProviderInformationServiceIntegrationTest() {
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodProviderInformationService<T> getService();
}
