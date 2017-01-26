package com.sfl.pms.persistence.repositories.payment.method.group;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
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
public interface GroupPaymentMethodDefinitionRepository extends AbstractPaymentMethodDefinitionRepository<GroupPaymentMethodDefinition> {


    /**
     * Finds group payment method definition using payment method group, currency and payment provider
     *
     * @param paymentMethodGroupType
     * @param currency
     * @param paymentProviderType
     * @return groupPaymentMethodDefinition
     */
    GroupPaymentMethodDefinition findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(@Nonnull final PaymentMethodGroupType paymentMethodGroupType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType);
}
