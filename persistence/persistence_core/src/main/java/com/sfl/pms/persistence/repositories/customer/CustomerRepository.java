package com.sfl.pms.persistence.repositories.customer;

import com.sfl.pms.services.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 10/30/14
 * Time: 9:19 AM
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find user for uuid
     *
     * @param uuId
     * @return user
     */
    Customer findByUuId(@Nonnull final String uuId);
}
