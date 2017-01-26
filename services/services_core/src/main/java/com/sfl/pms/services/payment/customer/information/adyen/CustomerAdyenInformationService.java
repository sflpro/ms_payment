package com.sfl.pms.services.payment.customer.information.adyen;

import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:37 AM
 */
public interface CustomerAdyenInformationService extends AbstractCustomerPaymentProviderInformationService<CustomerAdyenInformation> {

    /**
     * Gets or creates payment provider information for customer
     *
     * @param customerId
     * @return customerAdyenInformation
     */
    @Nonnull
    CustomerAdyenInformation getOrCreateCustomerPaymentProviderInformation(@Nonnull final Long customerId);
}
