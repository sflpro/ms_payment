package com.sfl.pms.services.payment.settings.impl;

import com.sfl.pms.persistence.repositories.payment.settings.AbstractPaymentProviderSettingsRepository;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.AbstractPaymentProviderSettingsService;
import com.sfl.pms.services.payment.settings.exception.PaymentProviderSettingsExistsForEnvironmentException;
import com.sfl.pms.services.payment.settings.exception.PaymentProviderSettingsNotFoundForIdException;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:40 PM
 */
public abstract class AbstractPaymentProviderSettingsServiceImpl<T extends PaymentProviderSettings> implements AbstractPaymentProviderSettingsService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPaymentProviderSettingsServiceImpl.class);

    /* Constructors */
    public AbstractPaymentProviderSettingsServiceImpl() {
        LOGGER.debug("Initializing abstract payment provider settings service");
    }

    @Nonnull
    @Override
    public T getPaymentProviderSettingsById(@Nonnull final Long paymentProviderSettingsId) {
        assertPaymentProviderSettingsId(paymentProviderSettingsId);
        LOGGER.debug("Getting payment provider settings for id - {}", paymentProviderSettingsId);
        final T paymentProviderSettings = getRepository().findOne(paymentProviderSettingsId);
        assertPaymentProviderSettingsNotNull(paymentProviderSettings, paymentProviderSettingsId);
        LOGGER.debug("Successfully retrieved payment provider settings for id - {}, settings - {}", paymentProviderSettingsId, paymentProviderSettings);
        return paymentProviderSettings;
    }

    /* Utility methods */
    protected void assertPaymentProviderSettingsNotNull(final T paymentProviderSettings, final Long id) {
        if (paymentProviderSettings == null) {
            LOGGER.error("No payment provider settings were found for id - {}, settings class - {}", id, getInstanceClass());
            throw new PaymentProviderSettingsNotFoundForIdException(id, getInstanceClass());
        }
    }

    protected void assertPaymentProviderSettingsId(final Long paymentProviderSettingsId) {
        Assert.notNull(paymentProviderSettingsId, "Payment provider settings ID should not be null");
    }

    protected void assertPaymentProviderSettingsForEnvironment(final EnvironmentType environmentType, final PaymentProviderType paymentProviderType) {
        final T settings = getRepository().findByTypeAndEnvironmentType(paymentProviderType, environmentType);
        if (settings != null) {
            LOGGER.error("Payment provider settings already exists for environment type - {}, payment provider type - {}", environmentType, paymentProviderType);
            throw new PaymentProviderSettingsExistsForEnvironmentException(paymentProviderType, environmentType);
        }
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderSettingsRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    /* Properties getters and setters */
}
