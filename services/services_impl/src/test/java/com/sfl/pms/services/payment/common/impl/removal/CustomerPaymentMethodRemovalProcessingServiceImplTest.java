package com.sfl.pms.services.payment.common.impl.removal;

import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.processing.impl.PaymentProviderOperationsProcessor;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.Date;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 4:37 PM
 */
public class CustomerPaymentMethodRemovalProcessingServiceImplTest extends AbstractServicesUnitTest {

    /* Test subjects and mocks */
    @TestSubject
    private CustomerPaymentMethodRemovalProcessingServiceImpl customerPaymentMethodRemovalProcessingService = new CustomerPaymentMethodRemovalProcessingServiceImpl();

    @Mock
    private PaymentProviderOperationsProcessor adyenPaymentProviderOperationsProcessor;

    @Mock
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public CustomerPaymentMethodRemovalProcessingServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testProcessCustomerPaymentMethodRemovalRequestWithInvalidArguments() {
        /* Test data */
        /* Reset mocks */
        resetAll();
        /* Replay mocks */
        replayAll();
        /* Run test cases */
        try {
            customerPaymentMethodRemovalProcessingService.processCustomerPaymentMethodRemovalRequest(null);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            // Exception
        }
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodRemovalWhenPaymentMethodIsNotRemoved() {
        /* Test data */
        final Long paymentMethodRemovalRequestId = 1l;
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod();
        /* Reset mocks */
        resetAll();
        /* Register expectations */
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(paymentMethodRemovalRequestId))).andReturn(customerPaymentMethod).once();
        /* Replay mocks */
        replayAll();
        /* Run test cases */
        try {
            customerPaymentMethodRemovalProcessingService.processCustomerPaymentMethodRemovalRequest(paymentMethodRemovalRequestId);
            fail("Exception will be thrown");
        } catch (IllegalArgumentException e) {
            // Exception
        }
        verifyAll();
    }

    @Test
    public void testProcessCustomerPaymentMethodRemoval() {
        /* Test data */
        final Long paymentMethodRemovalRequestId = 1l;
        final Long customerPaymentMethodProviderInformationId = 1l;
        final CustomerPaymentMethodProviderInformation customerPaymentMethodProviderInformation = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformation();
        final CustomerPaymentMethod customerPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod();
        customerPaymentMethod.setProviderInformation(customerPaymentMethodProviderInformation);
        customerPaymentMethod.setRemoved(new Date());
        customerPaymentMethod.setId(paymentMethodRemovalRequestId);
        customerPaymentMethodProviderInformation.setId(customerPaymentMethodProviderInformationId);
        final PaymentProviderOperationsProcessor.CustomerPaymentMethodRemovalData customerPaymentMethodRemovalData = new PaymentProviderOperationsProcessor.CustomerPaymentMethodRemovalData("SUCCESS");
        /* Reset mocks */
        resetAll();
        /* Register expectations */
        expect(customerPaymentMethodService.getCustomerPaymentMethodById(eq(paymentMethodRemovalRequestId))).andReturn(customerPaymentMethod).once();
        expect(adyenPaymentProviderOperationsProcessor.processCustomerPaymentMethodRemoval(eq(customerPaymentMethodProviderInformationId))).andReturn(customerPaymentMethodRemovalData).once();
        /* Replay mocks */
        replayAll();
        /* Run test cases */
        customerPaymentMethodRemovalProcessingService.processCustomerPaymentMethodRemovalRequest(paymentMethodRemovalRequestId);
        verifyAll();
    }
}
