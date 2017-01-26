package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodProviderInformationRepository;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodProviderInformationService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:50 AM
 */
public abstract class AbstractCustomerPaymentMethodProviderInformationServiceImplTest<T extends CustomerPaymentMethodProviderInformation> extends AbstractServicesUnitTest {

    /* Constructors */
    public AbstractCustomerPaymentMethodProviderInformationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetCustomerPaymentMethodProviderInformationByIdWithInvalidArguments() {
        /* Test data */
        /* Reset mocks */
        resetAll();
        /* Replay mocks */
        replayAll();
        /* run test cases */
        try {
            getService().getCustomerPaymentMethodProviderInformationById(null);
            fail("Exception must be thrown");
        } catch (IllegalArgumentException e) {
            //exception
        }
        verifyAll();
    }

    @Test
    public void testGetCustomerPaymentMethodProviderInformationById() {
        /* Test data */
        final Long paymentMethodProviderInformationId = 1l;
        final T customerPaymentMethodProviderInformation = createNewInstance();
        /* Reset mocks */
        resetAll();
        /* register expectations */
        expect(getRepository().findOne(eq(paymentMethodProviderInformationId))).andReturn(customerPaymentMethodProviderInformation).once();
        /* Replay mocks */
        replayAll();
        /* run test cases */
        final T result = getService().getCustomerPaymentMethodProviderInformationById(paymentMethodProviderInformationId);
        assertNotNull(result);
        assertEquals(result, customerPaymentMethodProviderInformation);
        verifyAll();
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodProviderInformationService<T> getService();

    protected abstract AbstractCustomerPaymentMethodProviderInformationRepository<T> getRepository();

    protected abstract T createNewInstance();
}
