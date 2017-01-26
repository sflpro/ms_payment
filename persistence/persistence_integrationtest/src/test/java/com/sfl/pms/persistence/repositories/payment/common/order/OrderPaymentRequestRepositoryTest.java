package com.sfl.pms.persistence.repositories.payment.common.order;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 1:09 PM
 */
public class OrderPaymentRequestRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private OrderPaymentRequestRepository orderPaymentRequestRepository;

    /* Constructors */
    public OrderPaymentRequestRepositoryTest() {
    }

    /* Test methods */
    @Test
    public void testFindByIdWithPessimisticWriteLock() {
        // Prepare data
        final Long id = 1L;
        // Load and assert
        final OrderPaymentRequest orderPaymentRequest = orderPaymentRequestRepository.findByIdWithWriteLockFlushedAndFreshData(id);
        assertNull(orderPaymentRequest);
    }
}
