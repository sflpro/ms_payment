package com.sfl.pms.services.payment.customer.information.impl.adyen;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.persistence.repositories.payment.customer.information.adyen.CustomerAdyenInformationRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.dto.adyen.CustomerAdyenInformationDto;
import com.sfl.pms.services.payment.customer.information.impl.AbstractCustomerPaymentProviderInformationServiceImplTest;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 12:26 PM
 */
public class CustomerAdyenInformationServiceImplTest extends AbstractCustomerPaymentProviderInformationServiceImplTest<CustomerAdyenInformation> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerAdyenInformationServiceImpl customerAdyenInformationService = new CustomerAdyenInformationServiceImpl();

    @Mock
    private CustomerAdyenInformationRepository customerAdyenInformationRepository;

    @Mock
    private CustomerService customerService;

    /* Constructors */
    public CustomerAdyenInformationServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetOrCreateCustomerPaymentProviderInformationWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Expectations
        // Run test scenario
        try {
            customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrCreateCustomerPaymentProviderInformationWhenItAlreadyExists() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerAdyenInformation information = getServicesImplTestHelper().createCustomerAdyenInformation();
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerAdyenInformationRepository.findByCustomerAndType(eq(customer), eq(PaymentProviderType.ADYEN))).andReturn(information).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerAdyenInformation result = customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(customerId);
        assertEquals(information, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrCreateCustomerPaymentProviderInformationWhenItDoesNotExist() {
        // Test data
        final Long customerId = 1l;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerAdyenInformationDto informationDto = new CustomerAdyenInformationDto(customer.getUuId(), customer.getEmail());
        // Reset
        resetAll();
        // Expectations
        expect(customerService.getCustomerById(eq(customerId))).andReturn(customer).once();
        expect(customerAdyenInformationRepository.findByCustomerAndType(eq(customer), eq(PaymentProviderType.ADYEN))).andReturn(null).once();
        expect(customerAdyenInformationRepository.save(isA(CustomerAdyenInformation.class))).andAnswer(new IAnswer<CustomerAdyenInformation>() {
            @Override
            public CustomerAdyenInformation answer() throws Throwable {
                return (CustomerAdyenInformation) getCurrentArguments()[0];
            }
        }).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerAdyenInformation result = customerAdyenInformationService.getOrCreateCustomerPaymentProviderInformation(customerId);
        getServicesImplTestHelper().assertCustomerAdyenInformation(result, informationDto);
        assertEquals(customer, result.getCustomer());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected AbstractCustomerPaymentProviderInformationService<CustomerAdyenInformation> getService() {
        return customerAdyenInformationService;
    }

    @Override
    protected AbstractCustomerPaymentProviderInformationRepository<CustomerAdyenInformation> getRepository() {
        return customerAdyenInformationRepository;
    }

    @Override
    protected CustomerAdyenInformation getInstance() {
        return getServicesImplTestHelper().createCustomerAdyenInformation();
    }

    @Override
    protected Class<CustomerAdyenInformation> getInstanceClass() {
        return CustomerAdyenInformation.class;
    }


}
