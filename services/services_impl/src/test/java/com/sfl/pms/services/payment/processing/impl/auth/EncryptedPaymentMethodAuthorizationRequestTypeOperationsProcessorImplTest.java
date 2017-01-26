package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.group.GroupPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 4:16 PM
 */
public class EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest extends AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest {

    /* Test subject and mocks */
    @TestSubject
    private EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor = new EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl();

    @Mock
    private GroupPaymentMethodDefinitionService groupPaymentMethodDefinitionService;

    /* Constructors */
    public EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImplTest() {
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
            encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor.createPaymentProcessingChannelDto(null);
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
        final CustomerProvidedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor.createPaymentProcessingChannelDto(authorizationRequest);
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
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> processingChannelDto = encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor.createPaymentProcessingChannelDto(authorizationRequest);
        assertNotNull(processingChannelDto);
        assertTrue(processingChannelDto instanceof EncryptedPaymentMethodProcessingChannelDto);
        final EncryptedPaymentMethodProcessingChannelDto providedPaymentMethodProcessingChannelDto = (EncryptedPaymentMethodProcessingChannelDto) processingChannelDto;
        assertEquals(authorizationRequest.getEncryptedData(), providedPaymentMethodProcessingChannelDto.getEncryptedPaymentMethodInformation());
        Assert.assertEquals(authorizationRequest.getPaymentProviderIntegrationType(), providedPaymentMethodProcessingChannelDto.getPaymentProviderIntegrationType());
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentDto() {
        // Test data
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        final GroupPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createGroupPaymentMethodDefinition();
        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(EasyMock.eq(authorizationRequest.getPaymentMethodGroup()), EasyMock.eq(authorizationRequest.getCurrency()), EasyMock.eq(authorizationRequest.getPaymentProviderType()))).andReturn(paymentMethodDefinition).once();
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
        return encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
    }

    @Override
    protected CustomerPaymentMethodAuthorizationRequest getInstanceWithInvalidType() {
        return getServicesImplTestHelper().createCustomerProvidedPaymentMethodAuthorizationRequest();
    }

    @Override
    protected CustomerPaymentMethodAuthorizationRequest getInstance(final PaymentMethodGroupType paymentMethodGroupType) {
        final CustomerEncryptedPaymentMethodAuthorizationRequest authorizationRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authorizationRequest.setPaymentMethodGroup(paymentMethodGroupType);
        return authorizationRequest;
    }
}
