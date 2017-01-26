package com.sfl.pms.persistence.repositories.payment.common.auth;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:45 AM
 */
@Repository
public interface CustomerPaymentMethodAuthorizationPaymentRepository extends AbstractPaymentRepository<CustomerPaymentMethodAuthorizationPayment> {

    /**
     * Finds payment by authorization request
     *
     * @param authorizationRequest
     * @return customerPaymentMethodAuthorizationPayment
     */
    CustomerPaymentMethodAuthorizationPayment findByAuthorizationRequest(@Nonnull final CustomerPaymentMethodAuthorizationRequest authorizationRequest);
}
