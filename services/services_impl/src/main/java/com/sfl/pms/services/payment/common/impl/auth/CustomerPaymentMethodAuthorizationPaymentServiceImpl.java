package com.sfl.pms.services.payment.common.impl.auth;

import com.sfl.pms.persistence.repositories.payment.common.AbstractPaymentRepository;
import com.sfl.pms.persistence.repositories.payment.common.auth.CustomerPaymentMethodAuthorizationPaymentRepository;
import com.sfl.pms.services.payment.common.auth.CustomerPaymentMethodAuthorizationPaymentService;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.exception.auth.CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException;
import com.sfl.pms.services.payment.common.impl.AbstractPaymentServiceImpl;
import com.sfl.pms.services.payment.common.impl.channel.PaymentProcessingChannelHandler;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:15 PM
 */
@Service
public class CustomerPaymentMethodAuthorizationPaymentServiceImpl extends AbstractPaymentServiceImpl<CustomerPaymentMethodAuthorizationPayment> implements CustomerPaymentMethodAuthorizationPaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAuthorizationPaymentServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationPaymentRepository customerPaymentMethodAuthorizationPaymentRepository;

    @Autowired
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentServiceImpl() {
        LOGGER.debug("Initializing customer payment method authorization payment service");
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerPaymentMethodAuthorizationPayment createPayment(@Nonnull final Long paymentMethodAuthorizationRequestId, @Nonnull final CustomerPaymentMethodAuthorizationPaymentDto paymentDto, @Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto) {
        Assert.notNull(paymentMethodAuthorizationRequestId, "Payment method authorization request id should not be null");
        assertPaymentDto(paymentDto);
        Assert.notNull(paymentProcessingChannelDto, "Payment processing channel DTO should not be null");
        LOGGER.debug("Creating customer payment method authorization request payment, request id - {}, payment DTO - {}, payment processing channel DTO - {}", paymentMethodAuthorizationRequestId, paymentDto, paymentProcessingChannelDto);
        final PaymentProcessingChannelHandler paymentProcessingChannelHandler = getPaymentProcessingChannelHandler(paymentProcessingChannelDto.getType());
        final CustomerPaymentMethodAuthorizationRequest authorizationRequest = customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(paymentMethodAuthorizationRequestId);
        // Assert payment processing channel handler
        paymentProcessingChannelHandler.assertPaymentProcessingChannelDto(paymentProcessingChannelDto, authorizationRequest.getCustomer());
        // Assert no payment exists for Authorization payment
        assertNoPaymentExistsForAuthorizationRequest(authorizationRequest);
        // Create payment
        CustomerPaymentMethodAuthorizationPayment payment = new CustomerPaymentMethodAuthorizationPayment(true);
        paymentDto.updateDomainEntityProperties(payment);
        payment.setAuthorizationRequest(authorizationRequest);
        payment.setCustomer(authorizationRequest.getCustomer());
        // Create payment processing channel
        final PaymentProcessingChannel paymentProcessingChannel = paymentProcessingChannelHandler.convertPaymentProcessingChannelDto(paymentProcessingChannelDto, authorizationRequest.getCustomer());
        paymentProcessingChannel.setPayment(payment);
        payment.setPaymentProcessingChannel(paymentProcessingChannel);
        // Persist payment
        payment = customerPaymentMethodAuthorizationPaymentRepository.save(payment);
        LOGGER.debug("Successfully created payment method authorization payment with id - {}, payment - {}", payment.getId(), payment);
        return payment;
    }

    /* Utility methods */
    private void assertNoPaymentExistsForAuthorizationRequest(final CustomerPaymentMethodAuthorizationRequest authorizationRequest) {
        final CustomerPaymentMethodAuthorizationPayment payment = customerPaymentMethodAuthorizationPaymentRepository.findByAuthorizationRequest(authorizationRequest);
        if (payment != null) {
            LOGGER.error("Payment with id - {} already exists for payment method authorization request with id - {}", payment.getId(), authorizationRequest.getId());
            throw new CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException(authorizationRequest.getId(), payment.getId());
        }
    }

    @Override
    protected AbstractPaymentRepository<CustomerPaymentMethodAuthorizationPayment> getRepository() {
        return customerPaymentMethodAuthorizationPaymentRepository;
    }

    @Override
    protected Class<CustomerPaymentMethodAuthorizationPayment> getInstanceClass() {
        return CustomerPaymentMethodAuthorizationPayment.class;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationPaymentRepository getCustomerPaymentMethodAuthorizationPaymentRepository() {
        return customerPaymentMethodAuthorizationPaymentRepository;
    }

    public void setCustomerPaymentMethodAuthorizationPaymentRepository(final CustomerPaymentMethodAuthorizationPaymentRepository customerPaymentMethodAuthorizationPaymentRepository) {
        this.customerPaymentMethodAuthorizationPaymentRepository = customerPaymentMethodAuthorizationPaymentRepository;
    }

    public CustomerPaymentMethodAuthorizationRequestService getCustomerPaymentMethodAuthorizationRequestService() {
        return customerPaymentMethodAuthorizationRequestService;
    }

    public void setCustomerPaymentMethodAuthorizationRequestService(final CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService) {
        this.customerPaymentMethodAuthorizationRequestService = customerPaymentMethodAuthorizationRequestService;
    }
}
