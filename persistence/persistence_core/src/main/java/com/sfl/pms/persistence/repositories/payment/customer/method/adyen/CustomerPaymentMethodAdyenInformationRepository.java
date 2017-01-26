package com.sfl.pms.persistence.repositories.payment.customer.method.adyen;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodProviderInformationRepository;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 4:36 PM
 */
@Repository
public interface CustomerPaymentMethodAdyenInformationRepository extends AbstractCustomerPaymentMethodProviderInformationRepository<CustomerPaymentMethodAdyenInformation> {

    /**
     * Finds Adyen payment method information based on recurring detail reference
     *
     * @param recurringDetailReference
     * @return customerPaymentMethodAdyenInformation
     */
    CustomerPaymentMethodAdyenInformation findByRecurringDetailReference(@Nonnull final String recurringDetailReference);
}
