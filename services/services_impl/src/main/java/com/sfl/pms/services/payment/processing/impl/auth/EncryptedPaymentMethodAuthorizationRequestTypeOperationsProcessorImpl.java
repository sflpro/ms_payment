package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.group.GroupPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 4:08 PM
 */
@Component
public class EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl extends AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl implements EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl.class);

    /* Dependencies */
    @Autowired
    private GroupPaymentMethodDefinitionService groupPaymentMethodDefinitionService;

    /* Constructor */
    public EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl() {
        LOGGER.debug("Initializing provided payment method authorization request type operations processor");
    }

    @Nonnull
    @Override
    public PaymentProcessingChannelDto<? extends PaymentProcessingChannel> createPaymentProcessingChannelDto(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        assertAuthorizationRequest(authorizationRequest);
        final CustomerEncryptedPaymentMethodAuthorizationRequest encryptedAuthorizationRequest = (CustomerEncryptedPaymentMethodAuthorizationRequest) authorizationRequest;
        // Create processing channel DTO
        final EncryptedPaymentMethodProcessingChannelDto processingChannelDto = new EncryptedPaymentMethodProcessingChannelDto(encryptedAuthorizationRequest.getPaymentProviderIntegrationType(), encryptedAuthorizationRequest.getEncryptedData());
        LOGGER.debug("Created payment processing channel DTO for authorization request - {}. Processing channel DTO - {}", encryptedAuthorizationRequest, processingChannelDto);
        return processingChannelDto;
    }

    @Nonnull
    @Override
    public CustomerPaymentMethodAuthorizationPaymentDto createPaymentDto(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        assertAuthorizationRequest(authorizationRequest);
        final CustomerEncryptedPaymentMethodAuthorizationRequest encryptedAuthorizationRequest = (CustomerEncryptedPaymentMethodAuthorizationRequest) authorizationRequest;
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(encryptedAuthorizationRequest.getPaymentMethodGroup(), encryptedAuthorizationRequest.getCurrency(), encryptedAuthorizationRequest.getPaymentProviderType());
        // Create DTO
        return createPaymentDto(authorizationRequest, encryptedAuthorizationRequest.getCurrency(), paymentMethodDefinition.getAuthorizationSurcharge());
    }

    /* Utility methods */
    private void assertAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        Assert.notNull(authorizationRequest, "Authorization request should not be null");
        Assert.isInstanceOf(CustomerEncryptedPaymentMethodAuthorizationRequest.class, authorizationRequest);
    }

    /* Properties getters and setters */
    public GroupPaymentMethodDefinitionService getGroupPaymentMethodDefinitionService() {
        return groupPaymentMethodDefinitionService;
    }

    public void setGroupPaymentMethodDefinitionService(final GroupPaymentMethodDefinitionService groupPaymentMethodDefinitionService) {
        this.groupPaymentMethodDefinitionService = groupPaymentMethodDefinitionService;
    }
}
