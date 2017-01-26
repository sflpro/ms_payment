package com.sfl.pms.services.payment.common.removal;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.encryption.adyen.AdyenPaymentCardEncryptionService;
import com.sfl.pms.services.payment.encryption.dto.PaymentCardEncryptionInformationDto;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 4/13/15
 * Time: 4:53 PM
 */
public class CustomerPaymentMethodRemovalProcessingServiceIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodRemovalProcessingService customerPaymentMethodRemovalProcessingService;

    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;

    @Autowired
    private AdyenPaymentCardEncryptionService adyenPaymentCardEncryptionService;

    /* Constructors */
    public CustomerPaymentMethodRemovalProcessingServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testProcessCustomerPaymentMethodRemovalRequest() {
        /* Create test data */
        if (!isEnablePaymentIntegrationTests()) {
            return;
        }
        /* Prepare data */
        final Customer customer = getServicesTestHelper().createCustomer();
        /* Create customer card payment method */
        CustomerPaymentMethod customerPaymentMethod = createCustomerPaymentMethod(customer, getServicesTestHelper().createMasterCardInformation());
        /* Flush, clear and remove payment method */
        flushAndClear();
        customerPaymentMethod = customerPaymentMethodService.removeCustomerPaymentMethod(customerPaymentMethod.getId());
        assertNotNull(customerPaymentMethod.getRemoved());
        /* Flush, clear and process payment method removal from payment provider */
        flushAndClear();
        customerPaymentMethodRemovalProcessingService.processCustomerPaymentMethodRemovalRequest(customerPaymentMethod.getId());
    }


    /* Utility methods */
    private CustomerPaymentMethod createCustomerPaymentMethod(final Customer customer, final PaymentCardEncryptionInformationDto encryptionInformationDto) {
        final String adyenCardEncryptedData = encryptCardData(encryptionInformationDto);
        final Pair<CustomerCardPaymentMethod, Long> paymentMethodAddingResult = getServicesTestHelper().createCustomerEncryptedCardPaymentMethodWithPaymentProvider(customer, adyenCardEncryptedData);
        CustomerCardPaymentMethod customerCardPaymentMethod = paymentMethodAddingResult.getKey();
        return customerCardPaymentMethod;
    }

    private String encryptCardData(final PaymentCardEncryptionInformationDto encryptionInformationDto) {
        return adyenPaymentCardEncryptionService.encryptPaymentCardInformation(encryptionInformationDto);
    }
}
