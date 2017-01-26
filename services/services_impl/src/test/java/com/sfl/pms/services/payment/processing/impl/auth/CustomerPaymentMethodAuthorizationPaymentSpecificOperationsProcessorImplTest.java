package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentStateChangeHistoryRecordDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.auth.CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.impl.AbstractPaymentTypeSpecificOperationsProcessorImpl;
import com.sfl.pms.services.payment.processing.impl.AbstractPaymentTypeSpecificOperationsProcessorImplTest;
import com.sfl.pms.services.payment.processing.impl.PaymentProviderOperationsProcessor;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 11:21 AM
 */
public class CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImplTest extends AbstractPaymentTypeSpecificOperationsProcessorImplTest {

    /* Test subject */
    @TestSubject
    private CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImpl customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor = new CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImpl();

    @Mock
    private PersistenceUtilityService persistenceUtilityService;

    @Mock
    private PaymentService paymentService;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImplTest() {
    }

    /* Test methods */

    @Test
    public void testProcessPaymentResultWithInvalidArguments() {
        // Test data
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentResult(null, paymentResult);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithInvalidPaymentType() {
        // Test data
        final Payment payment = getServicesImplTestHelper().createOrderPayment();
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithFailedPaymentResult() {
        // Test data
        final Long paymentId = 1L;
        final CustomerPaymentMethodAuthorizationPayment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        paymentResult.setStatus(PaymentResultStatus.REFUSED);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), EasyMock.eq(new PaymentStateChangeHistoryRecordDto(PaymentState.REFUSED, null)))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertPaymentProcessingResultDto(resultDto, paymentId, new ArrayList<>());
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithSuccessfulPaymentResultWithNewPaymentMethodsCreated() {
        // Test data
        final Long paymentId = 1L;
        final CustomerPaymentMethodAuthorizationPayment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        paymentResult.setStatus(PaymentResultStatus.PAID);
        final Long customerId = 4L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        payment.setCustomer(customer);
        // Build expected list of customer card data
        final Long customerCardPaymentMethodId = 5L;
        final CustomerCardPaymentMethod customerCardPaymentMethod = getServicesImplTestHelper().createCustomerCardPaymentMethod();
        customerCardPaymentMethod.setId(customerCardPaymentMethodId);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.PAID, null)))).andReturn(null).once();
        expect(getCustomerPaymentMethodsSynchronizationService().synchronizeCustomerPaymentMethods(eq(payment.getCustomer().getId()), EasyMock.eq(payment.getPaymentProviderType()))).andReturn(new CustomerPaymentMethodsSynchronizationResult(Arrays.asList(customerCardPaymentMethod), Collections.EMPTY_LIST)).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertPaymentProcessingResultDto(resultDto, paymentId, new ArrayList<>(Arrays.asList(customerCardPaymentMethodId)));
        // Verify
        verifyAll();
    }

    @Test
    public void testProcessPaymentResultWithSuccessfulPaymentResultWithoutNewPaymentMethodsCreated() {
        // Test data
        final Long paymentId = 1L;
        final CustomerPaymentMethodAuthorizationPayment payment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        payment.setId(paymentId);
        final Long paymentResultId = 2L;
        final PaymentResult paymentResult = getServicesImplTestHelper().createAdyenPaymentResult();
        paymentResult.setId(paymentResultId);
        paymentResult.setStatus(PaymentResultStatus.PAID);
        final Long customerId = 4L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        payment.setCustomer(customer);
        // Reset
        resetAll();
        // Expectations
        persistenceUtilityService.runInNewTransaction(isA(Runnable.class));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).anyTimes();
        expect(paymentService.updatePaymentState(eq(paymentId), eq(new PaymentStateChangeHistoryRecordDto(PaymentState.PAID, null)))).andReturn(null).once();
        expect(getCustomerPaymentMethodsSynchronizationService().synchronizeCustomerPaymentMethods(eq(customerId), EasyMock.eq(payment.getPaymentProviderType()))).andReturn(new CustomerPaymentMethodsSynchronizationResult(Collections.EMPTY_LIST, Collections.EMPTY_LIST)).times(PAYMENT_METHOD_SYNCHRONIZATION_MAX_ATTEMPTS + 2);
        getScheduledTaskExecutorService().scheduleTaskForExecution(isA(Runnable.class), eq(PAYMENT_METHOD_SYNCHRONIZATION_WAIT_PERIOD_IN_MILLIS), eq(TimeUnit.MILLISECONDS), eq(true));
        expectLastCall().andAnswer(() -> {
            final Runnable runnable = (Runnable) getCurrentArguments()[0];
            runnable.run();
            return null;
        }).times(PAYMENT_METHOD_SYNCHRONIZATION_MAX_ATTEMPTS + 1);
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProcessingResultDetailedInformationDto resultDto = customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor.processPaymentResult(payment, paymentResult);
        assertPaymentProcessingResultDto(resultDto, paymentId, new ArrayList<>());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertPaymentProcessingResultDto(final PaymentProcessingResultDetailedInformationDto resultDto, final Long paymentId, final List<Long> expectedCustomerPaymentMethodIds) {
        assertNotNull(resultDto);
        assertEquals(paymentId, resultDto.getPaymentId());
        assertTrue(resultDto instanceof CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto);
        final CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto authorizationPaymentProcessingResultDto = (CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto) resultDto;
        final List<Long> customerPaymentMethodIds = authorizationPaymentProcessingResultDto.getCustomerPaymentMethodIds();
        assertEquals(expectedCustomerPaymentMethodIds, customerPaymentMethodIds);
    }

    private PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData buildCustomerPaymentMethodProviderData() {
        final CustomerPaymentMethodDto<? extends CustomerPaymentMethod> customerPaymentMethodDto = getServicesImplTestHelper().createCustomerCardPaymentMethodDto();
        final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> customerPaymentMethodProviderInformationDto = getServicesImplTestHelper().createCustomerPaymentMethodAdyenInformationDto();
        return new PaymentProviderOperationsProcessor.CustomerPaymentMethodProviderData(customerPaymentMethodDto, customerPaymentMethodProviderInformationDto);
    }

    @Override
    protected Payment getPaymentInstance() {
        return getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
    }

    @Override
    protected Payment getPaymentInstanceWithInvalidType() {
        return getServicesImplTestHelper().createOrderPayment();
    }

    @Override
    protected AbstractPaymentTypeSpecificOperationsProcessorImpl getPaymentTypeSpecificOperationsProcessor() {
        return customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor;
    }

}
