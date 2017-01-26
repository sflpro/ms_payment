package com.sfl.pms.services.payment.settings.impl;

import com.sfl.pms.persistence.repositories.payment.settings.AbstractPaymentProviderSettingsRepository;
import com.sfl.pms.persistence.repositories.payment.settings.PaymentProviderSettingsRepository;
import com.sfl.pms.services.payment.provider.exception.UnknownPaymentProviderTypeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.PaymentProviderSettingsService;
import com.sfl.pms.services.payment.settings.adyen.AdyenPaymentSettingsService;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:43 PM
 */
@Service
public class PaymentProviderSettingsServiceImpl extends AbstractPaymentProviderSettingsServiceImpl<PaymentProviderSettings> implements PaymentProviderSettingsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderSettingsServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderSettingsRepository paymentProviderSettingsRepository;

    @Autowired
    private AdyenPaymentSettingsService adyenPaymentSettingsService;

    /* Constructors */
    public PaymentProviderSettingsServiceImpl() {
        LOGGER.debug("Initializing payment provider settings service");
    }


    @Nonnull
    @Override
    public PaymentProviderSettings getActivePaymentProviderSettingsForType(@Nonnull final PaymentProviderType paymentProviderType) {
        Assert.notNull(paymentProviderType, "Payment provider type should not be null");
        LOGGER.debug("Getting active payment provider settings for provider - {}", paymentProviderType);
        final PaymentProviderSettings settings;
        switch (paymentProviderType) {
            case ADYEN: {
                settings = adyenPaymentSettingsService.getActivePaymentSettings();
                break;
            }
            default: {
                LOGGER.error("Unsupported payment provider type - {}", paymentProviderType);
                throw new UnknownPaymentProviderTypeException(paymentProviderType);
            }
        }
        LOGGER.debug("Successfully retrieved payment provider settings for provider - {}, settings - {}", paymentProviderType, settings);
        return settings;
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderSettingsRepository<PaymentProviderSettings> getRepository() {
        return paymentProviderSettingsRepository;
    }

    @Override
    protected Class<PaymentProviderSettings> getInstanceClass() {
        return PaymentProviderSettings.class;
    }

    /* Properties getters and setters */
    public PaymentProviderSettingsRepository getPaymentProviderSettingsRepository() {
        return paymentProviderSettingsRepository;
    }

    public void setPaymentProviderSettingsRepository(final PaymentProviderSettingsRepository paymentProviderSettingsRepository) {
        this.paymentProviderSettingsRepository = paymentProviderSettingsRepository;
    }

    public AdyenPaymentSettingsService getAdyenPaymentSettingsService() {
        return adyenPaymentSettingsService;
    }

    public void setAdyenPaymentSettingsService(final AdyenPaymentSettingsService adyenPaymentSettingsService) {
        this.adyenPaymentSettingsService = adyenPaymentSettingsService;
    }
}
