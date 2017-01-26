package com.sfl.pms.persistence.repositories.payment.notification;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/19/15
 * Time: 1:09 PM
 */
public class PaymentProviderNotificationRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationRepository paymentProviderNotificationRepository;

    /* Constructors */
    public PaymentProviderNotificationRepositoryTest() {
    }

    /* Test methods */
    @Test
    public void testFindByIdWithPessimisticWriteLock() {
        // Prepare data
        final Long id = 1L;
        // Load and assert
        final PaymentProviderNotification providerNotification = paymentProviderNotificationRepository.findByIdWithPessimisticWriteLock(id);
        assertNull(providerNotification);
    }
}
