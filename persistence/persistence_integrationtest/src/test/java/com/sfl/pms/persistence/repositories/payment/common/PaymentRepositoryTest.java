package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.persistence.repositories.AbstractRepositoryTest;
import com.sfl.pms.services.payment.common.dto.PaymentSearchParameters;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/6/15
 * Time: 9:56 AM
 */
public class PaymentRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private PaymentRepository paymentRepository;

    /* Constructors */
    public PaymentRepositoryTest() {
    }

    /* Test methods */
    @Test
    public void testFindByIdWithPessimisticWriteLock() {
        // Prepare data
        final Long id = 1L;
        // Load and assert
        final Payment payment = paymentRepository.findByIdWithWriteLockFlushedAndFreshData(id);
        assertNull(payment);
    }

    @Test
    public void testGetCustomersCountForPaymentSearchParameters() {
        // Create search parameters
        final PaymentSearchParameters parameters = createPaymentSearchParameters();
        // Execute search
        final Long customerIdsCount = paymentRepository.getCustomersCountForPaymentSearchParameters(parameters);
        assertNotNull(customerIdsCount);
    }

    @Test
    public void testFindCustomersForPaymentSearchParameters() {
        // Create search parameters
        final PaymentSearchParameters parameters = createPaymentSearchParameters();
        final Long startFrom = 0L;
        final Integer maxCount = 10;
        // Execute search
        final List<Long> customerIds = paymentRepository.findCustomersForPaymentSearchParameters(parameters, startFrom, maxCount);
        assertNotNull(customerIds);
        assertEquals(0, customerIds.size());
    }

    /* Utility methods */
    private PaymentSearchParameters createPaymentSearchParameters() {
        final PaymentSearchParameters parameters = new PaymentSearchParameters();
        parameters.setStorePaymentMethod(Boolean.TRUE);
        parameters.setPaymentState(PaymentState.PAID);
        parameters.setPaymentProviderType(PaymentProviderType.ADYEN);
        // Grab current date
        final DateTime dateTime = new DateTime();
        parameters.setCreatedAfterDate(dateTime.minusDays(10).toDate());
        parameters.setCreatedBeforeDate(dateTime.plusDays(10).toDate());
        return parameters;
    }
}
