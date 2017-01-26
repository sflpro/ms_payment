package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 1:09 PM
 */
public class CustomerPaymentMethodAuthorizationRequestRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestRepository customerPaymentMethodAuthorizationRequestRepository;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestRepositoryTest() {
    }

    /* Test methods */
    @Test
    public void testFindByIdWithPessimisticWriteLock() {
        // Prepare data
        final Long id = 1L;
        // Load and assert
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest = customerPaymentMethodAuthorizationRequestRepository.findByIdWithWriteLockFlushedAndFreshData(id);
        assertNull(authorizationRequest);
    }
}
