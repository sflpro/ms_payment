package com.sfl.pms.services.payment.settings.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.system.environment.model.EnvironmentType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 2:40 PM
 */
public class PaymentProviderSettingsExistsForEnvironmentException extends ServicesRuntimeException {
    private static final long serialVersionUID = 3406687021382963598L;

    /* Properties */
    private final PaymentProviderType paymentProviderType;

    private final EnvironmentType environmentType;

    /* Constructors */
    public PaymentProviderSettingsExistsForEnvironmentException(final PaymentProviderType paymentProviderType, final EnvironmentType environmentType) {
        super("Payment provider settings already exists for payment provider type - " + paymentProviderType + " and environment type - " + environmentType);
        this.paymentProviderType = paymentProviderType;
        this.environmentType = environmentType;
    }

    /* Properties getters and setters */
    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }
}
