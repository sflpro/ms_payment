package com.sfl.pms.services.payment.method.individual;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.dto.individual.IndividualPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:42 PM
 */
public interface IndividualPaymentMethodDefinitionService extends AbstractPaymentMethodDefinitionService<IndividualPaymentMethodDefinition> {


    /**
     * Creates new individual payment method definition
     *
     * @param methodDefinitionDto
     * @return individualPaymentMethodDefinition
     */
    IndividualPaymentMethodDefinition createPaymentMethodDefinition(@Nonnull final IndividualPaymentMethodDefinitionDto methodDefinitionDto);

    /**
     * Checks if payment method definition exists for payment method type, currency and payment provider type
     *
     * @param paymentMethodType
     * @param currency
     * @param paymentProviderType
     * @return exists
     */
    boolean checkIfPaymentMethodDefinitionExistsForLookupParameters(@Nonnull final PaymentMethodType paymentMethodType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType);

    /**
     * Gets payment method definition for payment method type, currency and payment provider type
     *
     * @param paymentMethodType
     * @param currency
     * @param paymentProviderType
     * @return paymentMethodDefinition
     */
    IndividualPaymentMethodDefinition getPaymentMethodDefinitionForLookupParameters(@Nonnull final PaymentMethodType paymentMethodType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType);
}
