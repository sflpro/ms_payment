package com.sfl.pms.persistence.repositories.order;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.order.model.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

/**
 * User: Vazgen Danielyan
 * Company: SFL LLC
 * Date: 3/2/15
 * Time: 5:29 PM
 */
public class OrderRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private OrderRepository orderRepository;


    /* Constructors */
    public OrderRepositoryTest() {
    }


    /* Test methods */
    @Test
    public void testGetTotalAmountPaidForCustomer() {
        final Long customerId = 1L;
        orderRepository.getTotalAmountPaidForCustomer(customerId);
    }

    @Test
    public void testFindByIdWithPessimisticWriteLock() {
        // Prepare data
        final Long id = 1L;
        // Load and assert
        final Order order = orderRepository.findByIdWithWriteLockFlushedAndFreshData(id);
        assertNull(order);
    }
}
