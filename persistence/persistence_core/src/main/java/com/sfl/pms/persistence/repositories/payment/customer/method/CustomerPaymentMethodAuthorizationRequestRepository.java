package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:20 PM
 */
@Repository
public interface CustomerPaymentMethodAuthorizationRequestRepository extends AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerPaymentMethodAuthorizationRequest>, CustomerPaymentMethodAuthorizationRequestRepositoryCustom {

}
