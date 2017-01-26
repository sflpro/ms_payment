package com.sfl.pms.services.payment.common.auth;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.AbstractPaymentServiceIntegrationTest;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.exception.auth.CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.common.model.channel.EncryptedPaymentMethodProcessingChannel;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:40 PM
 */
public class CustomerPaymentMethodAuthorizationPaymentServiceIntegrationTest extends AbstractPaymentServiceIntegrationTest<CustomerPaymentMethodAuthorizationPayment> {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationPaymentService customerPaymentMethodAuthorizationPaymentService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentServiceIntegrationTest() {
    }

    @Test
    public void testCreatePayment() {
        // Prepare data
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        final EncryptedPaymentMethodProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = new EncryptedPaymentMethodProcessingChannelDto(PaymentProviderIntegrationType.API, authorizationRequest.getEncryptedData());
        flushAndClear();
        // Create payment
        CustomerPaymentMethodAuthorizationPayment payment = customerPaymentMethodAuthorizationPaymentService.createPayment(authorizationRequest.getId(), paymentDto, encryptedPaymentMethodProcessingChannelDto);
        assertPayment(payment, authorizationRequest, paymentDto, encryptedPaymentMethodProcessingChannelDto);
        // Flush, reload and assert again
        flushAndClear();
        payment = customerPaymentMethodAuthorizationPaymentService.getPaymentById(payment.getId());
        assertPayment(payment, authorizationRequest, paymentDto, encryptedPaymentMethodProcessingChannelDto);
    }

    @Test
    public void testCreatePaymentWhenRequestAlreadyHasAssociatedPayment() {
        // Prepare data
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        final EncryptedPaymentMethodProcessingChannelDto encryptedPaymentMethodProcessingChannelDto = new EncryptedPaymentMethodProcessingChannelDto(PaymentProviderIntegrationType.API, authorizationRequest.getEncryptedData());
        flushAndClear();
        // Create payment
        final CustomerPaymentMethodAuthorizationPayment payment = customerPaymentMethodAuthorizationPaymentService.createPayment(authorizationRequest.getId(), paymentDto, encryptedPaymentMethodProcessingChannelDto);
        assertNotNull(payment);
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authorizationRequest.getId(), paymentDto, encryptedPaymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException ex) {
            // Expected
        }
        // Flush and try again
        flushAndClear();
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authorizationRequest.getId(), paymentDto, encryptedPaymentMethodProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException ex) {
            // Expected
        }
    }

    /* Utility methods */
    private void assertPayment(final CustomerPaymentMethodAuthorizationPayment payment, final CustomerPaymentMethodAuthorizationRequest authorizationRequest, final CustomerPaymentMethodAuthorizationPaymentDto paymentDto, final EncryptedPaymentMethodProcessingChannelDto paymentMethodProcessingChannelDto) {
        getServicesTestHelper().assertCustomerPaymentMethodAuthorizationPayment(payment, paymentDto);
        getServicesTestHelper().assertPaymentLastState(payment, PaymentState.CREATED, null, 1);
        assertNotNull(payment.getAuthorizationRequest());
        assertEquals(authorizationRequest.getId(), payment.getAuthorizationRequest().getId());
        assertNotNull(payment.getCustomer());
        assertEquals(authorizationRequest.getCustomer().getId(), payment.getCustomer().getId());
        getServicesTestHelper().assertPaymentLastState(payment, PaymentState.CREATED, null, 1);
        // Assert payment processing channel
        assertNotNull(payment.getPaymentProcessingChannel());
        final PaymentProcessingChannel paymentProcessingChannel = persistenceUtilityService.initializeAndUnProxy(payment.getPaymentProcessingChannel());
        assertTrue(paymentProcessingChannel instanceof EncryptedPaymentMethodProcessingChannel);
        final EncryptedPaymentMethodProcessingChannel encryptedPaymentMethodProcessingChannel = (EncryptedPaymentMethodProcessingChannel) paymentProcessingChannel;
        getServicesTestHelper().assertEncryptedPaymentMethodProcessingChannel(encryptedPaymentMethodProcessingChannel, paymentMethodProcessingChannelDto);
    }

    @Override
    protected AbstractPaymentService<CustomerPaymentMethodAuthorizationPayment> getService() {
        return customerPaymentMethodAuthorizationPaymentService;
    }

    @Override
    protected CustomerPaymentMethodAuthorizationPayment getInstance() {
        return getServicesTestHelper().createCustomerPaymentMethodAuthorizationPayment();
    }

}
