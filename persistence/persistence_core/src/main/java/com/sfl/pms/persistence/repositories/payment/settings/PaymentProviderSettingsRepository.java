package com.sfl.pms.persistence.repositories.payment.settings;

import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:54 PM
 */
@Repository
public interface PaymentProviderSettingsRepository extends AbstractPaymentProviderSettingsRepository<PaymentProviderSettings> {
}
