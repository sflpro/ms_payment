package com.sfl.pms.persistence.repositories.payment.method;

import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:03 PM
 */
@Repository
public interface PaymentMethodDefinitionRepository extends AbstractPaymentMethodDefinitionRepository<PaymentMethodDefinition> {
}
