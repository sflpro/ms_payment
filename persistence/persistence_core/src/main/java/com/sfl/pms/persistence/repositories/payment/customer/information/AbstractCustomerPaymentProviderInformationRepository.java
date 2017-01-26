package com.sfl.pms.persistence.repositories.payment.customer.information;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:53 AM
 */
public interface AbstractCustomerPaymentProviderInformationRepository<T extends CustomerPaymentProviderInformation> extends JpaRepository<T, Long> {


    /**
     * Find payment provider information by customer and type
     *
     * @param customer
     * @param type
     * @return customerPaymentProviderInformation
     */
    T findByCustomerAndType(@Nonnull final Customer customer, @Nonnull final PaymentProviderType type);
}
