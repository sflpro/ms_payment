package com.sfl.pms.services.payment.settings.adyen;

import com.sfl.pms.services.payment.settings.AbstractPaymentProviderSettingsService;
import com.sfl.pms.services.payment.settings.dto.adyen.AdyenPaymentSettingsDto;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:35 PM
 */
public interface AdyenPaymentSettingsService extends AbstractPaymentProviderSettingsService<AdyenPaymentSettings> {

    /**
     * Creates Adyen payment settings
     *
     * @param paymentSettingsDto
     * @return adyenPaymentSettings
     */
    @Nonnull
    AdyenPaymentSettings createPaymentProviderSettings(@Nonnull final AdyenPaymentSettingsDto paymentSettingsDto);

    /**
     * Get active payment provider settings
     *
     * @return paymentProviderSettings
     */
    @Nonnull
    AdyenPaymentSettings getActivePaymentSettings();
}
