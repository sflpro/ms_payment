package com.sfl.pms.scheduler.jobs.payment.method.impl;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.scheduler.jobs.payment.method.CustomerPaymentMethodsSynchronizationJobImpl;
import com.sfl.pms.scheduler.test.AbstractSchedulerUnitTest;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentSearchParameters;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodsSynchronizationService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/6/15
 * Time: 4:30 PM
 */
public class CustomerPaymentMethodsSynchronizationJobImplTest extends AbstractSchedulerUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodsSynchronizationJobImpl customerPaymentMethodsSynchronizationJob = new CustomerPaymentMethodsSynchronizationJobImpl();

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private CustomerPaymentMethodsSynchronizationService customerPaymentMethodsSynchronizationService;

    @Mock
    private PaymentService paymentService;

    /* Constructors */
    public CustomerPaymentMethodsSynchronizationJobImplTest() {
    }

    /* Test methods */
    @Test
    public void testSynchronizeAdyenCustomerPaymentMethods() {
        // Test data
        final DateTime currentDateTime = new DateTime().withTimeAtStartOfDay();
        final PaymentSearchParameters searchParameters = new PaymentSearchParameters();
        searchParameters.setCreatedAfterDate(currentDateTime.minusDays(10).toDate())
                .setPaymentProviderType(PaymentProviderType.ADYEN)
                .setStorePaymentMethod(Boolean.TRUE)
                .setPaymentState(PaymentState.PAID);
        final Long expectedTotalItemsCount = 895L;
        final List<Long> customerIds = createCustomerIdsList(expectedTotalItemsCount);
        customerPaymentMethodsSynchronizationJob.setConcurrentProcessingEnabled(false);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInPersistenceSession(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.getCustomersCountForPaymentSearchParameters(eq(searchParameters))).andReturn(expectedTotalItemsCount).once();
        expect(paymentService.getCustomersForPaymentSearchParameters(eq(searchParameters), isA(Long.class), isA(Integer.class))).andAnswer(() -> {
            final Long startFrom = (Long) getCurrentArguments()[1];
            final Integer maxCount = (Integer) getCurrentArguments()[2];
            return customerIds.subList(startFrom.intValue(), Math.min(customerIds.size(), startFrom.intValue() + maxCount));
        }).anyTimes();
        customerIds.forEach(customerId -> {
            expect(customerPaymentMethodsSynchronizationService.synchronizeCustomerPaymentMethods(eq(customerId), eq(PaymentProviderType.ADYEN))).andReturn(new CustomerPaymentMethodsSynchronizationResult(Collections.emptyList(), Collections.emptyList())).once();
        });
        // Replay
        replayAll();
        // Run test scenario
        customerPaymentMethodsSynchronizationJob.synchronizeAdyenCustomerPaymentMethods();
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private List<Long> createCustomerIdsList(final Long count) {
        final List<Long> customerIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            customerIds.add(Long.valueOf(i));
        }
        return customerIds;
    }
}
