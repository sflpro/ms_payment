package com.sfl.pms.services.payment.method.impl.individual;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.persistence.repositories.payment.method.individual.IndividualPaymentMethodDefinitionRepository;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.dto.individual.IndividualPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.exception.individual.IndividualPaymentMethodDefinitionNotFoundForLookupParameters;
import com.sfl.pms.services.payment.method.impl.AbstractPaymentMethodDefinitionServiceImpl;
import com.sfl.pms.services.payment.method.individual.IndividualPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:57 PM
 */
@Service
public class IndividualPaymentMethodDefinitionServiceImpl extends AbstractPaymentMethodDefinitionServiceImpl<IndividualPaymentMethodDefinition> implements IndividualPaymentMethodDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndividualPaymentMethodDefinitionServiceImpl.class);

    /* Dependencies */
    @Autowired
    private IndividualPaymentMethodDefinitionRepository individualPaymentMethodDefinitionRepository;

    /* Constructors */
    public IndividualPaymentMethodDefinitionServiceImpl() {
        LOGGER.debug("Initializing individual payment method definition service");
    }

    @Transactional
    @Override
    public IndividualPaymentMethodDefinition createPaymentMethodDefinition(@Nonnull final IndividualPaymentMethodDefinitionDto methodDefinitionDto) {
        assertIndividualPaymentMethodDefinitionDto(methodDefinitionDto);
        LOGGER.debug("Creating new payment method definition for DTO - {}", methodDefinitionDto);
        assertNoPaymentMethodDefinitionExists(methodDefinitionDto.getPaymentMethodType(), methodDefinitionDto.getCurrency(), methodDefinitionDto.getPaymentProviderType());
        // Create new payment method definition
        IndividualPaymentMethodDefinition paymentMethodDefinition = new IndividualPaymentMethodDefinition();
        methodDefinitionDto.updateDomainEntityProperties(paymentMethodDefinition);
        // Persist payment method definition
        paymentMethodDefinition = individualPaymentMethodDefinitionRepository.save(paymentMethodDefinition);
        LOGGER.debug("Successfully created payment method definition with id - {}, payment method definition - {}", paymentMethodDefinition.getId(), paymentMethodDefinition);
        return paymentMethodDefinition;
    }

    @Override
    public boolean checkIfPaymentMethodDefinitionExistsForLookupParameters(@Nonnull final PaymentMethodType paymentMethodType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType) {
        assertPaymentMethodDefinitionLookupParameters(paymentMethodType, currency, paymentProviderType);
        LOGGER.debug("Checking if payment method definition exists for payment method - {}, currency - {} and payment provider type - {}", paymentMethodType, currency, paymentProviderType);
        final IndividualPaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(paymentMethodType, currency, paymentProviderType);
        final boolean exists = (paymentMethodDefinition != null);
        LOGGER.debug("Payment method definition lookup result for payment method - {}, currency - {} and payment provider type - {} is - {} ", paymentMethodType, currency, paymentProviderType, exists);
        return exists;
    }

    @Override
    public IndividualPaymentMethodDefinition getPaymentMethodDefinitionForLookupParameters(@Nonnull final PaymentMethodType paymentMethodType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType) {
        assertPaymentMethodDefinitionLookupParameters(paymentMethodType, currency, paymentProviderType);
        LOGGER.debug("Getting payment method definition for payment method - {}, currency - {} and payment provider type - {}", paymentMethodType, currency, paymentProviderType);
        final IndividualPaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(paymentMethodType, currency, paymentProviderType);
        assertIndividualPaymentMethodDefinitionNotNullForLookupParameters(paymentMethodDefinition, paymentMethodType, currency, paymentProviderType);
        LOGGER.debug("Successfully retrieved payment method definition for payment method - {}, currency - {} and payment provider - {}. Payment method definition - {}", paymentMethodType, currency, paymentProviderType, paymentMethodDefinition);
        return paymentMethodDefinition;
    }

    /* Utility methods */
    private void assertIndividualPaymentMethodDefinitionNotNullForLookupParameters(final IndividualPaymentMethodDefinition paymentMethodDefinition, final PaymentMethodType paymentMethodType, final Currency currency, final PaymentProviderType paymentProviderType) {
        if (paymentMethodDefinition == null) {
            LOGGER.error("No payment method definition found for payment method - {}, currency - {}, payment provider - {}", paymentMethodType, currency, paymentProviderType);
            throw new IndividualPaymentMethodDefinitionNotFoundForLookupParameters(paymentMethodType, currency, paymentProviderType);
        }
    }

    private void assertPaymentMethodDefinitionLookupParameters(final PaymentMethodType paymentMethodType, final Currency currency, final PaymentProviderType paymentProviderType) {
        Assert.notNull(paymentMethodType, "Payment method type should not be null");
        Assert.notNull(currency, "Currency should not be null");
        Assert.notNull(paymentProviderType, "Payment provider type should not be null");
    }

    @Override
    protected Class<IndividualPaymentMethodDefinition> getInstanceClass() {
        return IndividualPaymentMethodDefinition.class;
    }

    @Override
    protected AbstractPaymentMethodDefinitionRepository<IndividualPaymentMethodDefinition> getRepository() {
        return individualPaymentMethodDefinitionRepository;
    }

    private void assertNoPaymentMethodDefinitionExists(final PaymentMethodType paymentMethodType, final Currency currency, final PaymentProviderType paymentProviderType) {
        final IndividualPaymentMethodDefinition existingDefinition = individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(paymentMethodType, currency, paymentProviderType);
        if (existingDefinition != null) {
            LOGGER.debug("Payment method definition with id - {} already exists for payment method - {}, currency - {}, payment provider type - {}", existingDefinition.getId(), paymentMethodType, currency, paymentProviderType);
            throw new PaymentMethodDefinitionAlreadyExistsException(existingDefinition.getId());
        }
    }

    public void assertIndividualPaymentMethodDefinitionDto(final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto) {
        assertPaymentMethodDefinitionDto(paymentMethodDefinitionDto);
        Assert.notNull(paymentMethodDefinitionDto.getPaymentMethodType(), "Payment method type should not be null in payment method definition DTO");
    }

    /* Properties getters and setters */
    public IndividualPaymentMethodDefinitionRepository getIndividualPaymentMethodDefinitionRepository() {
        return individualPaymentMethodDefinitionRepository;
    }

    public void setIndividualPaymentMethodDefinitionRepository(final IndividualPaymentMethodDefinitionRepository individualPaymentMethodDefinitionRepository) {
        this.individualPaymentMethodDefinitionRepository = individualPaymentMethodDefinitionRepository;
    }
}
