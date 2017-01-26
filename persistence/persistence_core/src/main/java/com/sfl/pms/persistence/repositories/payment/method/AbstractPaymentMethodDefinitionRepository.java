package com.sfl.pms.persistence.repositories.payment.method;

import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:02 PM
 */
@Repository
public interface AbstractPaymentMethodDefinitionRepository<T extends PaymentMethodDefinition> extends JpaRepository<T, Long> {
}
