package com.sfl.pms.services.payment.settings;

import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:34 PM
 */
public interface AbstractPaymentProviderSettingsService<T extends PaymentProviderSettings> {

    /**
     * Get payment provider settings by ID
     *
     * @param paymentProviderSettingsId
     * @return paymentProviderSettings
     */
    @Nonnull
    T getPaymentProviderSettingsById(@Nonnull final Long paymentProviderSettingsId);
}
