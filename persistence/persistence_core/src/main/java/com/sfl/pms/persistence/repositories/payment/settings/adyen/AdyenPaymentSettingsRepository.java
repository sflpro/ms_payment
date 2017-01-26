package com.sfl.pms.persistence.repositories.payment.settings.adyen;

import com.sfl.pms.persistence.repositories.payment.settings.AbstractPaymentProviderSettingsRepository;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 12:55 PM
 */
@Repository
public interface AdyenPaymentSettingsRepository extends AbstractPaymentProviderSettingsRepository<AdyenPaymentSettings> {
}
