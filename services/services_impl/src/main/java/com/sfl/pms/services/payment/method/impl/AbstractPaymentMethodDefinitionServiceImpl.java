package com.sfl.pms.services.payment.method.impl;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.dto.PaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionNotFoundForIdException;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:56 PM
 */
public abstract class AbstractPaymentMethodDefinitionServiceImpl<T extends PaymentMethodDefinition> implements AbstractPaymentMethodDefinitionService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPaymentMethodDefinitionServiceImpl.class);

    /* Constructors */
    public AbstractPaymentMethodDefinitionServiceImpl() {
    }

    @Override
    public T getPaymentMethodDefinitionById(@Nonnull final Long paymentMethodDefinitionId) {
        assertPaymentMethodDefinitionId(paymentMethodDefinitionId);
        LOGGER.debug("Getting payment method definition for id - {} , class - {}", paymentMethodDefinitionId, getInstanceClass());
        final T paymentMethodDefinition = getRepository().findOne(paymentMethodDefinitionId);
        assertPaymentMethodDefinitionNotNullForId(paymentMethodDefinition, paymentMethodDefinitionId);
        LOGGER.debug("Successfully retrieved payment method definition for id - {}, class - {}", paymentMethodDefinition, getInstanceClass());
        return paymentMethodDefinition;
    }

    /* Utility methods */
    protected void assertPaymentMethodDefinitionNotNullForId(final T paymentMethodDefinition, final Long id) {
        if (paymentMethodDefinition == null) {
            LOGGER.error("No payment method definition was found for id - {}, class - {}", id, getInstanceClass());
            throw new PaymentMethodDefinitionNotFoundForIdException(id, getInstanceClass());
        }
    }

    protected void assertPaymentMethodDefinitionId(final Long paymentMethodDefinitionId) {
        Assert.notNull(paymentMethodDefinitionId, "Payment method definition should not be null");
    }

    protected void assertPaymentMethodDefinitionDto(final PaymentMethodDefinitionDto paymentMethodDefinitionDto) {
        Assert.notNull(paymentMethodDefinitionDto, "Payment method definition DTO should not be null");
        Assert.notNull(paymentMethodDefinitionDto.getPaymentProviderType(), "Payment provider type in payment method definition DTO should not be null");
        Assert.notNull(paymentMethodDefinitionDto.getAuthorizationSurcharge(), "Authorization surcharge in payment method definition DTO should not be null");
        Assert.notNull(paymentMethodDefinitionDto.getPaymentSurcharge(), "Payment surcharge in payment method definition DTO should not be null");
        Assert.notNull(paymentMethodDefinitionDto.getCurrency(), "Currency in payment method definition DTO should not be null");
    }

    /* Abstract methods */
    protected abstract Class<T> getInstanceClass();

    protected abstract AbstractPaymentMethodDefinitionRepository<T> getRepository();
}
