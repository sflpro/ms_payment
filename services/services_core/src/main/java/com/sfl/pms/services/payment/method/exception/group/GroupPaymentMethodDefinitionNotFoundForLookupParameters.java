package com.sfl.pms.services.payment.method.exception.group;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 6:20 PM
 */
public class GroupPaymentMethodDefinitionNotFoundForLookupParameters extends ServicesRuntimeException {

    private static final long serialVersionUID = -4896414334913201500L;

    /* Properties */
    private final PaymentMethodGroupType paymentMethodGroupType;

    private final Currency currency;

    private final PaymentProviderType paymentProviderType;

    /* Constructors */
    public GroupPaymentMethodDefinitionNotFoundForLookupParameters(final PaymentMethodGroupType paymentMethodGroupType, final Currency currency, final PaymentProviderType paymentProviderType) {
        super("No payment method definition found for payment method group - " + paymentMethodGroupType + ", currency - " + currency + ", payment provider - " + paymentProviderType);
        this.paymentMethodGroupType = paymentMethodGroupType;
        this.currency = currency;
        this.paymentProviderType = paymentProviderType;
    }

    /* Properties getters and setters */
    public PaymentMethodGroupType getPaymentMethodGroupType() {
        return paymentMethodGroupType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }
}
