package com.sfl.pms.services.payment.method.exception.individual;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 6:20 PM
 */
public class IndividualPaymentMethodDefinitionNotFoundForLookupParameters extends ServicesRuntimeException {

    private static final long serialVersionUID = -4896414334913201500L;

    /* Properties */
    private final PaymentMethodType paymentMethodType;

    private final Currency currency;

    private final PaymentProviderType paymentProviderType;

    /* Constructors */
    public IndividualPaymentMethodDefinitionNotFoundForLookupParameters(final PaymentMethodType paymentMethodType, final Currency currency, final PaymentProviderType paymentProviderType) {
        super("No payment method definition found for payment method - " + paymentMethodType + ", currency - " + currency + ", payment provider - " + paymentProviderType);
        this.paymentMethodType = paymentMethodType;
        this.currency = currency;
        this.paymentProviderType = paymentProviderType;
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }
}
