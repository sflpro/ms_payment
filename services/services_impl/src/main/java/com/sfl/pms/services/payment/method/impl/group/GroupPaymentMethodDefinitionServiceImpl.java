package com.sfl.pms.services.payment.method.impl.group;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.persistence.repositories.payment.method.group.GroupPaymentMethodDefinitionRepository;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.dto.group.GroupPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.exception.group.GroupPaymentMethodDefinitionNotFoundForLookupParameters;
import com.sfl.pms.services.payment.method.group.GroupPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.impl.AbstractPaymentMethodDefinitionServiceImpl;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
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
public class GroupPaymentMethodDefinitionServiceImpl extends AbstractPaymentMethodDefinitionServiceImpl<GroupPaymentMethodDefinition> implements GroupPaymentMethodDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupPaymentMethodDefinitionServiceImpl.class);

    /* Dependencies */
    @Autowired
    private GroupPaymentMethodDefinitionRepository groupPaymentMethodDefinitionRepository;

    /* Constructors */
    public GroupPaymentMethodDefinitionServiceImpl() {
        LOGGER.debug("Initializing group payment method definition service");
    }

    @Transactional
    @Override
    public GroupPaymentMethodDefinition createPaymentMethodDefinition(@Nonnull final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto) {
        assertGroupPaymentMethodDefinition(paymentMethodDefinitionDto);
        LOGGER.debug("Creating group payment method definition for DTO - {}", paymentMethodDefinitionDto);
        assertPaymentMethodDefinitionDoesNotExist(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        // Create new group payment method definition
        GroupPaymentMethodDefinition paymentMethodDefinition = new GroupPaymentMethodDefinition();
        paymentMethodDefinitionDto.updateDomainEntityProperties(paymentMethodDefinition);
        // Persist payment method definition
        paymentMethodDefinition = groupPaymentMethodDefinitionRepository.save(paymentMethodDefinition);
        LOGGER.debug("Successfully created group payment method definition with id - {}, payment method definition - {}", paymentMethodDefinition.getId(), paymentMethodDefinition);
        return paymentMethodDefinition;
    }

    @Override
    public boolean checkIfPaymentMethodDefinitionExistsForLookupParameters(@Nonnull final PaymentMethodGroupType paymentMethodGroupType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType) {
        assertPaymentMethodDefinitionLookupParameters(paymentMethodGroupType, currency, paymentProviderType);
        LOGGER.debug("Checking if payment method definition exists for payment method - {}, currency - {} and payment provider type - {}", paymentMethodGroupType, currency, paymentProviderType);
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(paymentMethodGroupType, currency, paymentProviderType);
        final boolean exists = (paymentMethodDefinition != null);
        LOGGER.debug("Payment method definition lookup result for payment method - {}, currency - {} and payment provider type - {} is - {} ", paymentMethodGroupType, currency, paymentProviderType, exists);
        return exists;
    }

    @Override
    public GroupPaymentMethodDefinition getPaymentMethodDefinitionForLookupParameters(@Nonnull final PaymentMethodGroupType paymentMethodGroupType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType) {
        assertPaymentMethodDefinitionLookupParameters(paymentMethodGroupType, currency, paymentProviderType);
        LOGGER.debug("Getting payment method definition for payment method group - {}, currency - {} and payment provider type - {}", paymentMethodGroupType, currency, paymentProviderType);
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(paymentMethodGroupType, currency, paymentProviderType);
        assertGroupPaymentMethodDefinitionNotNullForLookupParameters(paymentMethodDefinition, paymentMethodGroupType, currency, paymentProviderType);
        LOGGER.debug("Successfully retrieved payment method definition for payment method group - {}, currency - {} and payment provider - {}. Payment method definition - {}", paymentMethodGroupType, currency, paymentProviderType, paymentMethodDefinition);
        return paymentMethodDefinition;
    }

    /* Utility methods */
    private void assertGroupPaymentMethodDefinitionNotNullForLookupParameters(final GroupPaymentMethodDefinition paymentMethodDefinition, final PaymentMethodGroupType paymentMethodGroupType, final Currency currency, final PaymentProviderType paymentProviderType) {
        if (paymentMethodDefinition == null) {
            LOGGER.error("No payment method definition found for payment method group - {}, currency - {}, payment provider - {}", paymentMethodGroupType, currency, paymentProviderType);
            throw new GroupPaymentMethodDefinitionNotFoundForLookupParameters(paymentMethodGroupType, currency, paymentProviderType);
        }
    }

    private void assertPaymentMethodDefinitionLookupParameters(final PaymentMethodGroupType paymentMethodGroupType, final Currency currency, final PaymentProviderType paymentProviderType) {
        Assert.notNull(paymentMethodGroupType, "Payment method group type should not be null");
        Assert.notNull(currency, "Currency should not be null");
        Assert.notNull(paymentProviderType, "Payment provider type should not be null");
    }

    @Override
    protected Class<GroupPaymentMethodDefinition> getInstanceClass() {
        return GroupPaymentMethodDefinition.class;
    }

    @Override
    protected AbstractPaymentMethodDefinitionRepository<GroupPaymentMethodDefinition> getRepository() {
        return groupPaymentMethodDefinitionRepository;
    }

    private void assertPaymentMethodDefinitionDoesNotExist(final PaymentMethodGroupType paymentMethodGroupType, final Currency currency, final PaymentProviderType paymentProviderType) {
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(paymentMethodGroupType, currency, paymentProviderType);
        if (paymentMethodDefinition != null) {
            LOGGER.error("Payment method group definition with id - {} already exists for payment method group type - {}, currency - {}, payment provider type - {}", paymentMethodDefinition.getId(), paymentMethodGroupType, currency, paymentProviderType);
            throw new PaymentMethodDefinitionAlreadyExistsException(paymentMethodDefinition.getId());
        }
    }

    private void assertGroupPaymentMethodDefinition(final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto) {
        assertPaymentMethodDefinitionDto(paymentMethodDefinitionDto);
        Assert.notNull(paymentMethodDefinitionDto.getPaymentMethodGroupType(), "Payment method group type in payment method definition should not be null");
    }

    /* Properties getters and setters */
    public GroupPaymentMethodDefinitionRepository getGroupPaymentMethodDefinitionRepository() {
        return groupPaymentMethodDefinitionRepository;
    }

    public void setGroupPaymentMethodDefinitionRepository(final GroupPaymentMethodDefinitionRepository groupPaymentMethodDefinitionRepository) {
        this.groupPaymentMethodDefinitionRepository = groupPaymentMethodDefinitionRepository;
    }
}
