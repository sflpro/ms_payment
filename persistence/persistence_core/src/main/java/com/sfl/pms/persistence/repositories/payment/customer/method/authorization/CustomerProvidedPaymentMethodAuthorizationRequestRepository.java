package com.sfl.pms.persistence.repositories.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:22 PM
 */
@Repository
public interface CustomerProvidedPaymentMethodAuthorizationRequestRepository extends AbstractCustomerPaymentMethodAuthorizationRequestRepository<CustomerProvidedPaymentMethodAuthorizationRequest> {
}
