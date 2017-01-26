package com.sfl.pms.services.payment.settings.impl.adyen;

import com.sfl.pms.persistence.repositories.payment.settings.AbstractPaymentProviderSettingsRepository;
import com.sfl.pms.persistence.repositories.payment.settings.adyen.AdyenPaymentSettingsRepository;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.adyen.AdyenPaymentSettingsService;
import com.sfl.pms.services.payment.settings.dto.adyen.AdyenPaymentSettingsDto;
import com.sfl.pms.services.payment.settings.exception.PaymentProviderSettingsNotFoundForEnvironmentException;
import com.sfl.pms.services.payment.settings.impl.AbstractPaymentProviderSettingsServiceImpl;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:45 PM
 */
@Service
public class AdyenPaymentSettingsServiceImpl extends AbstractPaymentProviderSettingsServiceImpl<AdyenPaymentSettings> implements AdyenPaymentSettingsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenPaymentSettingsServiceImpl.class);

    /* Dependencies */
    @Autowired
    private AdyenPaymentSettingsRepository adyenPaymentSettingsRepository;

    @Value("#{ appProperties['adyen.environment.type']}")
    private String environmentType;


    /* Constructors */
    public AdyenPaymentSettingsServiceImpl() {
        LOGGER.debug("Initializing Adyen payment provider settings service");
    }

    @Transactional
    @Nonnull
    @Override
    public AdyenPaymentSettings createPaymentProviderSettings(@Nonnull final AdyenPaymentSettingsDto paymentSettingsDto) {
        assertPaymentSettingsDto(paymentSettingsDto);
        LOGGER.debug("Creating Adyen payment settings for DTO - {}", paymentSettingsDto);
        assertPaymentProviderSettingsForEnvironment(paymentSettingsDto.getEnvironmentType(), PaymentProviderType.ADYEN);
        // Create payment settings
        AdyenPaymentSettings paymentSettings = new AdyenPaymentSettings();
        paymentSettingsDto.updateDomainEntityProperties(paymentSettings);
        // Persist payment settings
        paymentSettings = adyenPaymentSettingsRepository.save(paymentSettings);
        LOGGER.debug("Successfully created Adyen payment settings, id - {}, settings - {}", paymentSettings.getId(), paymentSettings);
        return paymentSettings;
    }

    @Nonnull
    @Override
    public AdyenPaymentSettings getActivePaymentSettings() {
        // Grab environment type
        final EnvironmentType currentEnvironmentType = getEnvironmentType();
        LOGGER.debug("Getting payment settings, environment type - {}", currentEnvironmentType);
        final AdyenPaymentSettings adyenPaymentSettings = getAdyenPaymentSettingsRepository().findByTypeAndEnvironmentType(PaymentProviderType.ADYEN, currentEnvironmentType);
        assertAdyenPaymentSettingsNotNullForEnvironmentAndPaymentProvider(adyenPaymentSettings, PaymentProviderType.ADYEN, currentEnvironmentType);
        LOGGER.debug("Found Adyen payment settings for payment provider type - {}, environment type - {}", PaymentProviderType.ADYEN, currentEnvironmentType);
        return adyenPaymentSettings;
    }

    /* Utility methods */
    private void assertAdyenPaymentSettingsNotNullForEnvironmentAndPaymentProvider(final AdyenPaymentSettings adyenPaymentSettings, final PaymentProviderType paymentProviderType, final EnvironmentType environmentType) {
        if (adyenPaymentSettings == null) {
            LOGGER.error("No payment provider settings are found payment provider type - {} and environment type - {}", paymentProviderType, environmentType);
            throw new PaymentProviderSettingsNotFoundForEnvironmentException(paymentProviderType, environmentType);
        }
    }

    private void assertPaymentSettingsDto(final AdyenPaymentSettingsDto paymentSettingsDto) {
        Assert.notNull(paymentSettingsDto, "Payment settings DTO should not be null");
        Assert.notNull(paymentSettingsDto.getEnvironmentType(), "Environment type in settings DTO should not be null");
        Assert.notNull(paymentSettingsDto.getUserName(), "UserName in settings DTO should not be null");
        Assert.notNull(paymentSettingsDto.getPassword(), "Password in settings DTO should not be null");
        Assert.notNull(paymentSettingsDto.getNotificationsToken(), "Notifications token in settings DTO should not be null");
    }

    @Override
    protected AbstractPaymentProviderSettingsRepository<AdyenPaymentSettings> getRepository() {
        return adyenPaymentSettingsRepository;
    }

    @Override
    protected Class<AdyenPaymentSettings> getInstanceClass() {
        return AdyenPaymentSettings.class;
    }

    private EnvironmentType getEnvironmentType() {
        return EnvironmentType.valueOf(environmentType.toUpperCase());
    }

    /* Properties getters and setters */
    public AdyenPaymentSettingsRepository getAdyenPaymentSettingsRepository() {
        return adyenPaymentSettingsRepository;
    }

    public void setAdyenPaymentSettingsRepository(final AdyenPaymentSettingsRepository adyenPaymentSettingsRepository) {
        this.adyenPaymentSettingsRepository = adyenPaymentSettingsRepository;
    }

    public String isEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(final String environmentType) {
        this.environmentType = environmentType;
    }
}
