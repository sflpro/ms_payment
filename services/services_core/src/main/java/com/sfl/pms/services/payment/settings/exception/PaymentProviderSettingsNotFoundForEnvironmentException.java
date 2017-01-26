package com.sfl.pms.services.payment.settings.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.system.environment.model.EnvironmentType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/1/15
 * Time: 11:52 AM
 */
public class PaymentProviderSettingsNotFoundForEnvironmentException extends ServicesRuntimeException {

    private static final long serialVersionUID = -8077350930534706488L;

    /* Properties */
    private final PaymentProviderType paymentProviderType;

    private final EnvironmentType environmentType;

    public PaymentProviderSettingsNotFoundForEnvironmentException(final PaymentProviderType paymentProviderType, final EnvironmentType environmentType) {
        super("No settings are found for payment provider type - " + paymentProviderType + ", environment type - " + environmentType);
        this.paymentProviderType = paymentProviderType;
        this.environmentType = environmentType;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }
}
