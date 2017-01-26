package com.sfl.pms.persistence.repositories.order;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 11/12/14
 * Time: 12:48 PM
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    /**
     * Find order by uuid
     *
     * @param uuId
     * @return order
     */
    Order findByUuId(final String uuId);

    /**
     * Get orders count for customer
     *
     * @param customer
     * @return ordersCount
     */
    Long countByCustomer(final Customer customer);


    /**
     * Get total amount paid for customer
     *
     * @param customerId customer id
     * @return total amount
     */
    @Query(value = "SELECT SUM(o.paymentTotalWithVat) FROM Order o WHERE o.customer.id = :customerId and o.lastState = 'PAID'")
    BigDecimal getTotalAmountPaidForCustomer(@Param("customerId") final Long customerId);

}
