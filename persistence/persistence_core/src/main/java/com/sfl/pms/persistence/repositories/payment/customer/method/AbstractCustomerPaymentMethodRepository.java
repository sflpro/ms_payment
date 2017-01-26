package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:30 PM
 */
public interface AbstractCustomerPaymentMethodRepository<T extends CustomerPaymentMethod> extends JpaRepository<T, Long> {

    /**
     * Find customer payment method by authorization request
     *
     * @param authorizationRequest
     * @return customerPaymentMethod
     */
    T findByAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest authorizationRequest);

    /**
     * Find customer payment method by uuId
     *
     * @param uuId
     * @return customerPaymentMethod
     */
    T findByUuId(final String uuId);

    /**
     * Find customer payment methods by customer
     *
     * @param customer
     * @return customerPaymentMethods
     */
    List<T> findByCustomer(final Customer customer);
}
