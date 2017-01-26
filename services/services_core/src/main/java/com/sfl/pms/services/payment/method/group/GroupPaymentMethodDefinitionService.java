package com.sfl.pms.services.payment.method.group;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.dto.group.GroupPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:43 PM
 */
public interface GroupPaymentMethodDefinitionService extends AbstractPaymentMethodDefinitionService<GroupPaymentMethodDefinition> {

    /**
     * Creates new group payment method definition
     *
     * @param paymentMethodDefinitionDto
     * @return paymentMethodDefinition
     */
    GroupPaymentMethodDefinition createPaymentMethodDefinition(@Nonnull final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto);

    /**
     * Checks if payment method definition exists for payment method type, currency and payment provider type
     *
     * @param paymentMethodGroupType
     * @param currency
     * @param paymentProviderType
     * @return exists
     */
    boolean checkIfPaymentMethodDefinitionExistsForLookupParameters(@Nonnull final PaymentMethodGroupType paymentMethodGroupType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType);

    /**
     * Gets payment method definition for payment method type, currency and payment provider type
     *
     * @param paymentMethodGroupType
     * @param currency
     * @param paymentProviderType
     * @return paymentMethodDefinition
     */
    GroupPaymentMethodDefinition getPaymentMethodDefinitionForLookupParameters(@Nonnull final PaymentMethodGroupType paymentMethodGroupType, @Nonnull final Currency currency, @Nonnull final PaymentProviderType paymentProviderType);
}
