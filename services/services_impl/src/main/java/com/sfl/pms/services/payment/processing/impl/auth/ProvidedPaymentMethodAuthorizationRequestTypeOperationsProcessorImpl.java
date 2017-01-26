package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.ProvidedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.method.individual.IndividualPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
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
public class ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl extends AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl implements ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl.class);

    /* Dependencies */
    @Autowired
    private IndividualPaymentMethodDefinitionService individualPaymentMethodDefinitionService;

    /* Constructor */
    public ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl() {
        LOGGER.debug("Initializing provided payment method authorization request type operations processor");
    }

    @Nonnull
    @Override
    public PaymentProcessingChannelDto<? extends PaymentProcessingChannel> createPaymentProcessingChannelDto(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        assertAuthorizationRequest(authorizationRequest);
        final CustomerProvidedPaymentMethodAuthorizationRequest providedAuthorizationRequest = (CustomerProvidedPaymentMethodAuthorizationRequest) authorizationRequest;
        // Create processing channel DTO
        final ProvidedPaymentMethodProcessingChannelDto processingChannelDto = new ProvidedPaymentMethodProcessingChannelDto(providedAuthorizationRequest.getPaymentProviderIntegrationType(), providedAuthorizationRequest.getPaymentMethodType());
        LOGGER.debug("Created payment processing channel DTO for authorization request - {}. Processing channel DTO - {}", providedAuthorizationRequest, processingChannelDto);
        return processingChannelDto;
    }

    @Nonnull
    @Override
    public CustomerPaymentMethodAuthorizationPaymentDto createPaymentDto(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        assertAuthorizationRequest(authorizationRequest);
        final CustomerProvidedPaymentMethodAuthorizationRequest providedAuthorizationRequest = (CustomerProvidedPaymentMethodAuthorizationRequest) authorizationRequest;
        final PaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(providedAuthorizationRequest.getPaymentMethodType(), providedAuthorizationRequest.getCurrency(), providedAuthorizationRequest.getPaymentProviderType());
        // Create DTO
        return createPaymentDto(authorizationRequest, providedAuthorizationRequest.getCurrency(), paymentMethodDefinition.getAuthorizationSurcharge());
    }

    /* Utility methods */
    private void assertAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        Assert.notNull(authorizationRequest, "Authorization request should not be null");
        Assert.isInstanceOf(CustomerProvidedPaymentMethodAuthorizationRequest.class, authorizationRequest);
    }

    /* Properties getters and setters */
    public IndividualPaymentMethodDefinitionService getIndividualPaymentMethodDefinitionService() {
        return individualPaymentMethodDefinitionService;
    }

    public void setIndividualPaymentMethodDefinitionService(final IndividualPaymentMethodDefinitionService individualPaymentMethodDefinitionService) {
        this.individualPaymentMethodDefinitionService = individualPaymentMethodDefinitionService;
    }
}
