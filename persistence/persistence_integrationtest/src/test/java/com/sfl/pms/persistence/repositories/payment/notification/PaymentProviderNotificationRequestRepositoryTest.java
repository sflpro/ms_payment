package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 1:09 PM
 */
public class PaymentProviderNotificationRequestRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationRequestRepository paymentProviderNotificationRequestRepository;

    /* Constructors */
    public PaymentProviderNotificationRequestRepositoryTest() {
    }

    /* Test methods */
    @Test
    public void testFindByIdWithPessimisticWriteLock() {
        // Prepare data
        final Long id = 1L;
        // Load and assert
        final PaymentProviderNotificationRequest providerNotificationRequest = paymentProviderNotificationRequestRepository.findByIdWithPessimisticWriteLock(id);
        assertNull(providerNotificationRequest);
    }
}
