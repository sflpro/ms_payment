package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodProviderInformationRepository;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 4:34 PM
 */
@Repository
public interface CustomerPaymentMethodProviderInformationRepository extends AbstractCustomerPaymentMethodProviderInformationRepository<CustomerPaymentMethodProviderInformation> {
}
