package com.sfl.pms.persistence.repositories.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 4:33 PM
 */
public interface AbstractCustomerPaymentMethodProviderInformationRepository<T extends CustomerPaymentMethodProviderInformation> extends JpaRepository<T, Long> {
}
