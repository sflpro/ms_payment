package com.sfl.pms.persistence.repositories.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:19 PM
 */
public interface AbstractCustomerPaymentMethodAuthorizationRequestRepository<T extends CustomerPaymentMethodAuthorizationRequest> extends JpaRepository<T, Long> {

    /**
     * Gets payment method authorization request by UUID
     *
     * @param uuId
     * @return CustomerPaymentMethodAuthorizationRequest
     */
    T findByUuId(@Nonnull final String uuId);
}
