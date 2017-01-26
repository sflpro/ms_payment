package com.sfl.pms.persistence.repositories.payment.settings;

import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:52 PM
 */
public interface AbstractPaymentProviderSettingsRepository<T extends PaymentProviderSettings> extends JpaRepository<T, Long> {

    /**
     * Find payment provider settings by environment type
     *
     * @param type
     * @param environmentType
     * @return paymentProviderSettings
     */
    T findByTypeAndEnvironmentType(@Nonnull final PaymentProviderType type, @Nonnull final EnvironmentType environmentType);
}
