package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.persistence.utility.PersistenceUtilityService;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.auth.CustomerPaymentMethodAuthorizationPaymentService;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.exception.auth.CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;
import com.sfl.pms.services.payment.customer.method.authorization.CustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAlreadyBoundAuthorizationRequestException;
import com.sfl.pms.services.payment.customer.method.exception.UnknownCustomerPaymentMethodAuthorizationRequestTypeException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestType;
import com.sfl.pms.services.payment.processing.PaymentProcessorService;
import com.sfl.pms.services.payment.processing.auth.CustomerPaymentMethodAuthorizationRequestProcessorService;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.redirect.PaymentRedirectProcessingInformationDto;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 3:20 PM
 */
@Service
public class CustomerPaymentMethodAuthorizationRequestProcessorServiceImpl implements CustomerPaymentMethodAuthorizationRequestProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAuthorizationRequestProcessorServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService;

    @Autowired
    private CustomerPaymentMethodAuthorizationPaymentService customerPaymentMethodAuthorizationPaymentService;

    @Autowired
    private PaymentProcessorService paymentProcessorService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    @Autowired
    private EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;

    @Autowired
    private ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationRequestProcessorServiceImpl() {
        LOGGER.debug("Initializing customer payment method authorization payment processing service");
    }

    // Do not mark this method as transactional
    @Nonnull
    @Override
    public PaymentProcessingResultDetailedInformationDto processCustomerPaymentMethodAuthorizationRequest(@Nonnull final Long requestId) {
        Assert.notNull(requestId, "Customer payment method authorization request should not be null");
        LOGGER.debug("Processing customer payment method authorization request with id - {}", requestId);
        // Update state on request
        updatePaymentMethodRequestState(requestId, CustomerPaymentMethodAuthorizationRequestState.PROCESSING, new LinkedHashSet<>(Arrays.asList(CustomerPaymentMethodAuthorizationRequestState.CREATED, CustomerPaymentMethodAuthorizationRequestState.FAILED)));
        try {
            // Load customer payment method authorization request
            final Pair<CustomerPaymentMethodAuthorizationRequest, Customer> requestCustomerPair = loadAuthorizationRequestAndCustomer(requestId);
            final CustomerPaymentMethodAuthorizationRequest request = requestCustomerPair.getLeft();
            // Create payment method authorization payment
            final CustomerPaymentMethodAuthorizationPayment payment = createPaymentMethodAuthorizationPayment(request);
            // Create payment processing DTO
            final PaymentProcessingResultDetailedInformationDto processingResultDto = paymentProcessorService.processPayment(payment.getId());
            if (processingResultDto instanceof PaymentRedirectProcessingInformationDto) {
                final PaymentRedirectProcessingInformationDto redirectProcessingInformationDto = (PaymentRedirectProcessingInformationDto) processingResultDto;
                updateAuthorizationRequestRedirectUrl(requestId, redirectProcessingInformationDto.getRedirectUrl());
            }
            // Update state on request
            updatePaymentMethodRequestState(requestId, CustomerPaymentMethodAuthorizationRequestState.PROCESSED, new LinkedHashSet<>());
            // Return customer payment method ids
            return processingResultDto;
        } catch (final Exception ex) {
            LOGGER.error("Error occurred during performing payment for authorization request with id - " + requestId, ex);
            // Update state on request
            updatePaymentMethodRequestState(requestId, CustomerPaymentMethodAuthorizationRequestState.FAILED, new LinkedHashSet<>());
            // Rethrow error
            throw new ServicesRuntimeException(ex);
        }
    }

    /* Utility methods */
    private void updateAuthorizationRequestRedirectUrl(final Long requestId, final String paymentRedirectUrl) {
        getPersistenceUtilityService().runInNewTransaction(() -> {
            customerPaymentMethodAuthorizationRequestService.updatePaymentMethodAuthorizationRequestRedirectUrl(requestId, paymentRedirectUrl);
        });
    }

    private void updatePaymentMethodRequestState(final Long requestId, final CustomerPaymentMethodAuthorizationRequestState state, final Set<CustomerPaymentMethodAuthorizationRequestState> allowedStates) {
        getPersistenceUtilityService().runInNewTransaction(() -> {
            getCustomerPaymentMethodAuthorizationRequestService().updatePaymentMethodAuthorizationRequestState(requestId, state, allowedStates);
        });
    }


    private CustomerPaymentMethodAuthorizationPayment createPaymentMethodAuthorizationPayment(final CustomerPaymentMethodAuthorizationRequest request) {
        // Create payment DTO
        final MutableHolder<CustomerPaymentMethodAuthorizationPayment> paymentMutableHolder = new MutableHolder<>(null);
        getPersistenceUtilityService().runInNewTransaction(() -> {
            final CustomerPaymentMethodAuthorizationRequest reloadedRequest = persistenceUtilityService.initializeAndUnProxy(customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(request.getId()));
            final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = getPaymentMethodAuthorizationRequestTypeOperationsProcessor(reloadedRequest.getType()).createPaymentDto(request);
            final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> processingChannelDto = createPaymentProcessingChannelDto(reloadedRequest);
            final CustomerPaymentMethodAuthorizationPayment payment = customerPaymentMethodAuthorizationPaymentService.createPayment(request.getId(), paymentDto, processingChannelDto);
            paymentMutableHolder.setValue(payment);
        });
        return paymentMutableHolder.getValue();
    }

    private PaymentProcessingChannelDto<? extends PaymentProcessingChannel> createPaymentProcessingChannelDto(final CustomerPaymentMethodAuthorizationRequest request) {
        final PaymentMethodAuthorizationRequestTypeOperationsProcessor operationsProcessor = getPaymentMethodAuthorizationRequestTypeOperationsProcessor(request.getType());
        return operationsProcessor.createPaymentProcessingChannelDto(request);
    }

    private Pair<CustomerPaymentMethodAuthorizationRequest, Customer> loadAuthorizationRequestAndCustomer(final Long customerPaymentMethodAuthorizationRequestId) {
        // Load customer payment method authorization request
        final MutableHolder<CustomerPaymentMethodAuthorizationRequest> requestMutableHolder = new MutableHolder<>(null);
        final MutableHolder<Customer> customerMutableHolder = new MutableHolder<>(null);
        getPersistenceUtilityService().runInNewTransaction(() -> {
            CustomerPaymentMethodAuthorizationRequest request = customerPaymentMethodAuthorizationRequestService.getPaymentMethodAuthorizationRequestById(customerPaymentMethodAuthorizationRequestId);
            request = getPersistenceUtilityService().initializeAndUnProxy(request);
            // Perform assertions
            assertNoPaymentExistsForRequest(request);
            assertNoPaymentMethodExistsForRequest(request);
            // Set data to return
            requestMutableHolder.setValue(request);
            customerMutableHolder.setValue(request.getCustomer());
        });
        return new ImmutablePair<>(requestMutableHolder.getValue(), customerMutableHolder.getValue());
    }


    private void assertNoPaymentMethodExistsForRequest(final CustomerPaymentMethodAuthorizationRequest request) {
        if (request.getCustomerPaymentMethod() != null) {
            LOGGER.error("Customer payment method authorization request with id - {} already has payment method associated with id. Payment id - {}", request.getId(), request.getCustomerPaymentMethod().getId());
            throw new CustomerPaymentMethodAlreadyBoundAuthorizationRequestException(request.getCustomerPaymentMethod().getId(), request.getId());
        }
    }

    private void assertNoPaymentExistsForRequest(final CustomerPaymentMethodAuthorizationRequest request) {
        if (request.getPayment() != null) {
            LOGGER.error("Customer payment method authorization request with id - {} already has payment associated with id. Payment id - {}", request.getId(), request.getPayment().getId());
            throw new CustomerPaymentMethodAuthorizationPaymentAlreadyExistsException(request.getId(), request.getPayment().getId());
        }
    }

    private PaymentMethodAuthorizationRequestTypeOperationsProcessor getPaymentMethodAuthorizationRequestTypeOperationsProcessor(final CustomerPaymentMethodAuthorizationRequestType requestType) {
        switch (requestType) {
            case ENCRYPTED_PAYMENT_METHOD:
                return encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
            case PROVIDED_PAYMENT_METHOD:
                return providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
            default: {
                LOGGER.error("Unknown payment method authorization request type - {}", requestType);
                throw new UnknownCustomerPaymentMethodAuthorizationRequestTypeException(requestType);
            }
        }
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestService getCustomerPaymentMethodAuthorizationRequestService() {
        return customerPaymentMethodAuthorizationRequestService;
    }

    public void setCustomerPaymentMethodAuthorizationRequestService(final CustomerPaymentMethodAuthorizationRequestService customerPaymentMethodAuthorizationRequestService) {
        this.customerPaymentMethodAuthorizationRequestService = customerPaymentMethodAuthorizationRequestService;
    }

    public CustomerPaymentMethodAuthorizationPaymentService getCustomerPaymentMethodAuthorizationPaymentService() {
        return customerPaymentMethodAuthorizationPaymentService;
    }

    public void setCustomerPaymentMethodAuthorizationPaymentService(final CustomerPaymentMethodAuthorizationPaymentService customerPaymentMethodAuthorizationPaymentService) {
        this.customerPaymentMethodAuthorizationPaymentService = customerPaymentMethodAuthorizationPaymentService;
    }

    public PaymentProcessorService getPaymentProcessorService() {
        return paymentProcessorService;
    }

    public void setPaymentProcessorService(final PaymentProcessorService paymentProcessorService) {
        this.paymentProcessorService = paymentProcessorService;
    }

    public PersistenceUtilityService getPersistenceUtilityService() {
        return persistenceUtilityService;
    }

    public void setPersistenceUtilityService(final PersistenceUtilityService persistenceUtilityService) {
        this.persistenceUtilityService = persistenceUtilityService;
    }

    public EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor getEncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor() {
        return encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
    }

    public void setEncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor(final EncryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor) {
        this.encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor = encryptedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
    }

    public ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor getProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor() {
        return providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
    }

    public void setProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor(final ProvidedPaymentMethodAuthorizationRequestTypeOperationsProcessor providedPaymentMethodAuthorizationRequestTypeOperationsProcessor) {
        this.providedPaymentMethodAuthorizationRequestTypeOperationsProcessor = providedPaymentMethodAuthorizationRequestTypeOperationsProcessor;
    }
}
