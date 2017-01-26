package com.sfl.pms.persistence.repositories.payment.method.individual;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:03 PM
 */
@Repository
public interface IndividualPaymentMethodDefinitionRepository extends AbstractPaymentMethodDefinitionRepository<IndividualPaymentMethodDefinition> {

    /**
     * Find individual payment method definition by payment method type, currency and payment provider type
     *
     * @param paymentMethodType
     * @param currency
     * @param paymentProviderType
     * @return individualPaymentMethodDefinition
     */
    IndividualPaymentMethodDefinition findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(@Nonnull final PaymentMethodType paymentMethodType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType);
}
