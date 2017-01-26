package com.sfl.pms.services.payment.common.impl.auth;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.persistence.repositories.payment.common.auth.CustomerPaymentMethodAuthorizationPaymentRepository;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.EncryptedPaymentMethodProcessingChannelDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.exception.auth.CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException;
import com.sfl.pms.services.payment.common.impl.AbstractPaymentServiceImplTest;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:29 PM
 */
public class CustomerPaymentMethodAuthorizationPaymentServiceImplTest extends AbstractPaymentServiceImplTest<CustomerPaymentMethodAuthorizationPayment> {

    /* Test subject and mocks */
    @TestSubject
    private CustomerPaymentMethodAuthorizationPaymentServiceImpl customerPaymentMethodAuthorizationPaymentService = new CustomerPaymentMethodAuthorizationPaymentServiceImpl();

    @Mock
    private CustomerPaymentMethodAuthorizationPaymentRepository customerPaymentMethodAuthorizationPaymentRepository;

    @Mock
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCreatePaymentWithInvalidArguments() {
        // Test data
        final Long authRequestId = 1l;
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(null, paymentDto, paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, null, paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, paymentDto, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, new CustomerPaymentMethodAuthorizationPaymentDto(null, paymentDto.getAmount(), paymentDto.getCurrency(), paymentDto.getClientIpAddress()), paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, new CustomerPaymentMethodAuthorizationPaymentDto(paymentDto.getPaymentProviderType(), null, paymentDto.getCurrency(), paymentDto.getClientIpAddress()), paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, new CustomerPaymentMethodAuthorizationPaymentDto(paymentDto.getPaymentProviderType(), paymentDto.getAmount(), null, paymentDto.getClientIpAddress()), paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentWhenRequestAlreadyHasAssociatedPayment() {
        // Test data
        final Long customerId = 4L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long authRequestId = 1l;
        final CustomerPaymentMethodAuthorizationRequest authRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authRequest.setId(authRequestId);
        authRequest.setCustomer(customer);
        final Long existingPaymentId = 2l;
        final CustomerPaymentMethodAuthorizationPayment existingPayment = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
        existingPayment.setId(existingPaymentId);
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(authRequestId))).andReturn(authRequest).once();
        expect(customerPaymentMethodAuthorizationPaymentRepository.findByAuthorizationRequest(eq(authRequest))).andReturn(existingPayment).once();
        getPaymentProcessingChannelHandler(paymentProcessingChannelDto.getType()).assertPaymentProcessingChannelDto(eq(paymentProcessingChannelDto), eq(authRequest.getCustomer()));
        expectLastCall().once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, paymentDto, paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException ex) {
            // Expected
            assertCustomerPaymentMethodAuthorizationPaymentAlreadyExistsException(ex, authRequestId, existingPaymentId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentWithInvalidPaymentChannelDto() {
        // Test data
        final Long customerId = 4L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final Long authRequestId = 1l;
        final CustomerPaymentMethodAuthorizationRequest authRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authRequest.setId(authRequestId);
        authRequest.setCustomer(customer);
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(authRequestId))).andReturn(authRequest).once();
        getPaymentProcessingChannelHandler(paymentProcessingChannelDto.getType()).assertPaymentProcessingChannelDto(eq(paymentProcessingChannelDto), eq(authRequest.getCustomer()));
        expectLastCall().andThrow(new IllegalArgumentException()).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, paymentDto, paymentProcessingChannelDto);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePayment() {
        // Test data
        final Long authRequestId = 1l;
        final Long customerId = 2L;
        final Customer customer = getServicesImplTestHelper().createCustomer();
        customer.setId(customerId);
        final CustomerPaymentMethodAuthorizationRequest authRequest = getServicesImplTestHelper().createCustomerEncryptedPaymentMethodAuthorizationRequest();
        authRequest.setCustomer(customer);
        authRequest.setId(authRequestId);
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPaymentDto();
        final EncryptedPaymentMethodProcessingChannelDto paymentProcessingChannelDto = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannelDto();
        final PaymentProcessingChannel paymentProcessingChannel = getServicesImplTestHelper().createEncryptedPaymentMethodProcessingChannel(paymentProcessingChannelDto);
        // Reset
        resetAll();
        // Expectations
        expect(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(eq(authRequestId))).andReturn(authRequest).once();
        expect(customerPaymentMethodAuthorizationPaymentRepository.findByAuthorizationRequest(eq(authRequest))).andReturn(null).once();
        expect(customerPaymentMethodAuthorizationPaymentRepository.save(isA(CustomerPaymentMethodAuthorizationPayment.class))).andAnswer(new IAnswer<CustomerPaymentMethodAuthorizationPayment>() {
            @Override
            public CustomerPaymentMethodAuthorizationPayment answer() throws Throwable {
                return (CustomerPaymentMethodAuthorizationPayment) getCurrentArguments()[0];
            }
        }).once();
        getPaymentProcessingChannelHandler(paymentProcessingChannelDto.getType()).assertPaymentProcessingChannelDto(eq(paymentProcessingChannelDto), eq(customer));
        expectLastCall().once();
        expect(getPaymentProcessingChannelHandler(paymentProcessingChannelDto.getType()).convertPaymentProcessingChannelDto(eq(paymentProcessingChannelDto), eq(customer))).andReturn(paymentProcessingChannel).once();
        // Replay
        replayAll();
        // Run test scenario
        final CustomerPaymentMethodAuthorizationPayment payment = customerPaymentMethodAuthorizationPaymentService.createPayment(authRequestId, paymentDto, paymentProcessingChannelDto);
        getServicesImplTestHelper().assertCustomerPaymentMethodAuthorizationPayment(payment, paymentDto);
        getServicesImplTestHelper().assertPaymentLastState(payment, PaymentState.CREATED, null, 1);
        assertEquals(authRequest, payment.getAuthorizationRequest());
        assertEquals(customer, payment.getCustomer());
        Assert.assertEquals(paymentProcessingChannel, payment.getPaymentProcessingChannel());
        // Verify
        verifyAll();
    }

    /* Utility methods */
    private void assertCustomerPaymentMethodAuthorizationPaymentAlreadyExistsException(final CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException ex, final Long authRequestId, final Long existingPaymentId) {
        assertEquals(authRequestId, ex.getPaymentMethodAuthorizationRequestId());
        assertEquals(existingPaymentId, ex.getExistingPaymentId());
    }

    @Override
    protected AbstractPaymentService<CustomerPaymentMethodAuthorizationPayment> getService() {
        return customerPaymentMethodAuthorizationPaymentService;
    }

    @Override
    protected AbstractPaymentRepository<CustomerPaymentMethodAuthorizationPayment> getRepository() {
        return customerPaymentMethodAuthorizationPaymentRepository;
    }

    @Override
    protected CustomerPaymentMethodAuthorizationPayment getInstance() {
        return getServicesImplTestHelper().createCustomerPaymentMethodAuthorizationPayment();
    }

    @Override
    protected Class<CustomerPaymentMethodAuthorizationPayment> getInstanceClass() {
        return CustomerPaymentMethodAuthorizationPayment.class;
    }

}
