package com.sfl.pms.services.payment.settings;

import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:35 PM
 */
public interface PaymentProviderSettingsService extends AbstractPaymentProviderSettingsService<PaymentProviderSettings> {


    /**
     * Get active payment provider settings for type
     *
     * @param paymentProviderType
     * @return paymentProviderSettings
     */
    @Nonnull
    PaymentProviderSettings getActivePaymentProviderSettingsForType(@Nonnull final PaymentProviderType paymentProviderType);
}
