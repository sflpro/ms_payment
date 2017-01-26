package com.sfl.pms.services.payment.customer.information;

import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:36 AM
 */
public interface AbstractCustomerPaymentProviderInformationService<T extends CustomerPaymentProviderInformation> {

    /**
     * Gets customer payment provider information for ID
     *
     * @param informationById
     * @return paymentProviderInformation
     */
    @Nonnull
    T getCustomerPaymentProviderInformationById(@Nonnull final Long informationById);
}
