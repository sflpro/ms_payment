package com.sfl.pms.persistence.repositories.payment.customer.information.adyen;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.services.payment.customer.information.model.adyen.CustomerAdyenInformation;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:54 AM
 */
@Repository
public interface CustomerAdyenInformationRepository extends AbstractCustomerPaymentProviderInformationRepository<CustomerAdyenInformation> {
}
