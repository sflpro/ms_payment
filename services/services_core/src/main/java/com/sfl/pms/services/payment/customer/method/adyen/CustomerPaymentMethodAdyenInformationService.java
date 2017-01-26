package com.sfl.pms.services.payment.customer.method.adyen;

import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodProviderInformationService;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:23 AM
 */
public interface CustomerPaymentMethodAdyenInformationService extends AbstractCustomerPaymentMethodProviderInformationService<CustomerPaymentMethodAdyenInformation> {

    /**
     * Checks if payment method information exists for recurring details reference
     *
     * @param recurringDetailReference
     * @return exists
     */
    boolean checkIfPaymentMethodInformationExistsForRecurringDetailReference(@Nonnull final String recurringDetailReference);


    /**
     * Finds customer payment method Adyen information by recurring details reference
     *
     * @param recurringDetailReference
     * @return customerPaymentMethodAdyenInformation
     */
    @Nonnull
    CustomerPaymentMethodAdyenInformation getPaymentMethodInformationByRecurringDetailReference(@Nonnull final String recurringDetailReference);


    /**
     * Updates recurring details reference for Adyen payment method information
     *
     * @param informationId
     * @param recurringDetailReference
     * @return customerPaymentMethodAdyenInformation
     */
    @Nonnull
    CustomerPaymentMethodAdyenInformation updatePaymentMethodInformationRecurringDetailReference(@Nonnull final Long informationId, @Nonnull final String recurringDetailReference);
}
