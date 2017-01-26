package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:32 PM
 */
@Repository
public interface CustomerCardPaymentMethodRepository extends AbstractCustomerPaymentMethodRepository<CustomerCardPaymentMethod> {
}
