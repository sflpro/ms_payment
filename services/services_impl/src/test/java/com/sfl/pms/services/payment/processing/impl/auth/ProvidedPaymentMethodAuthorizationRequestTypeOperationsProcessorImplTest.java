package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.ProvidedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.individual.IndividualPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 4:16 PM
 */
public class ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest extends AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest {

    /* Test subject and mocks */
    @TestSubject
    private ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl providedPaymentMethodAuthorizationRequestTypeOperationsProcessor = new ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl();

    @Mock
    private IndividualPaymentMethodDefinitionService individualPaymentMethodDefinitionService;

    /* Constructors */
    public ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentProcessingChannelDtoWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            providedPaymentMethodAuthorizationRequestTypeOperationsProcessor.createPaymentProcessingChannelDto(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProcessingChannelDtoWithInvalidRequestType() {
        // Test data
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            providedPaymentMethodAuthorizationRequestTypeOperationsProcessor.createPaymentProcessingChannelDto(authorizationRequest);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentProcessingChannelDto() {
        // Test data
        final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> processingChannelDto = providedPaymentMethodAuthorizationRequestTypeOperationsProcessor.createPaymentProcessingChannelDto(authorizationRequest);
        assertNotNull(processingChannelDto);
        assertTrue(processingChannelDto instanceof ProvidedPaymentMethodProcessingChannelDto);
        final ProvidedPaymentMethodProcessingChannelDto providedPaymentMethodProcessingChannelDto = (ProvidedPaymentMethodProcessingChannelDto) processingChannelDto;
        assertEquals(authorizationRequest.getPaymentMethodType(), providedPaymentMethodProcessingChannelDto.getPaymentMethodType());
        assertEquals(authorizationRequest.getPaymentProviderIntegrationType(), providedPaymentMethodProcessingChannelDto.getPaymentProviderIntegrationType());
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentDto() {
        // Test data
        final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createIndividualPaymentMethodDefinition();
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(eq(authorizationRequest.getPaymentMethodType()), eq(authorizationRequest.getCurrency()), eq(authorizationRequest.getPaymentProviderType()))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getPaymentMethodAuthorizationRequestTypeOperationsProcessor().createPaymentDto(authorizationRequest);
        assertPaymentDto(paymentDto, authorizationRequest, paymentMethodDefinition.getAuthorizationSurcharge());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected PaymentMethodAuthorizationRequestTypeOperationsProcessor getPaymentMethodAuthorizationRequestTypeOperationsProcessor() {
        return providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
    }

    @Override
    protected CustomerPaymentMethodAuthorizationRequest getInstanceWithInvalidType() {
        return getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
    }

    @Override
    protected CustomerPaymentMethodAuthorizationRequest getInstance(final PaymentMethodGroupType paymentMethodGroupType) {
        final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
        if (paymentMethodGroupType.equals(PaymentMethodGroupType.BANK_TRANSFER)) {
            authorizationRequest.setPaymentMethodType(PaymentMethodType.IDEAL);
        } else {
            authorizationRequest.setPaymentMethodType(PaymentMethodType.MASTER_CARD);
        }
        return authorizationRequest;
    }
}
