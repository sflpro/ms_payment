package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.repositories.payment.notification.PaymentProviderNotificationRequestRepository;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationRequestService;
import com.sfl.pms.services.payment.notification.dto.PaymentProviderNotificationRequestDto;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationRequestNotFoundForIdException;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationRequestStateNotAllowedException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:30 AM
 */
@Service
public class PaymentProviderNotificationRequestServiceImpl implements PaymentProviderNotificationRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationRequestServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationRequestRepository paymentProviderNotificationRequestRepository;

    /* Constructors */
    public PaymentProviderNotificationRequestServiceImpl() {
        LOGGER.debug("Initializing payment provider notification request service");
    }

    @Transactional
    @Nonnull
    @Override
    public PaymentProviderNotificationRequest createPaymentProviderNotificationRequest(@Nonnull final PaymentProviderNotificationRequestDto requestDto) {
        assertPaymentProviderNotificationRequestDto(requestDto);
        LOGGER.debug("Creating payment provider notification request for DTO - {}", requestDto);
        // Create new notification request
        PaymentProviderNotificationRequest request = new PaymentProviderNotificationRequest(true);
        requestDto.updateDomainEntityProperties(request);
        // Persist request
        request = paymentProviderNotificationRequestRepository.save(request);
        LOGGER.debug("Successfully created payment provider notification request with id  - {}, request - {}", request.getId(), request);
        return request;
    }

    @Nonnull
    @Override
    public PaymentProviderNotificationRequest getPaymentProviderNotificationRequestById(@Nonnull final Long notificationRequestId) {
        assertNotificationRequestIdNotNull(notificationRequestId);
        LOGGER.debug("Retrieving payment provider notification request by id -  {}", notificationRequestId);
        final PaymentProviderNotificationRequest request = paymentProviderNotificationRequestRepository.findOne(notificationRequestId);
        assertPaymentProviderNotificationRequestNotNull(request, notificationRequestId);
        LOGGER.debug("Successfully retrieved payment provider notification request for id - {}, request - {}", notificationRequestId, request);
        return request;
    }

    @Transactional
    @Nonnull
    @Override
    public PaymentProviderNotificationRequest updatePaymentProviderNotificationRequestState(@Nonnull final Long requestId, @Nonnull final PaymentProviderNotificationRequestState state, @Nonnull final Set<PaymentProviderNotificationRequestState> allowedInitialStates) {
        assertNotificationRequestIdNotNull(requestId);
        Assert.notNull(state, "Payment provider notification request state should not be null");
        Assert.notNull(allowedInitialStates, "Payment provider notification request allowed initial states should not be null");
        LOGGER.debug("Updating payment provider notification request state with id - {}, state - {}, allowed initial states - {}", requestId, state, allowedInitialStates);
        PaymentProviderNotificationRequest request = paymentProviderNotificationRequestRepository.findByIdWithPessimisticWriteLock(requestId);
        assertPaymentProviderNotificationRequestNotNull(request, requestId);
        final PaymentProviderNotificationRequestState initialState = request.getState();
        if (allowedInitialStates.size() != 0) {
            assertNotificationRequestState(state, allowedInitialStates, initialState);
        }
        // Update state
        request.setState(state);
        request.setUpdated(new Date());
        // Persist request
        request = paymentProviderNotificationRequestRepository.save(request);
        LOGGER.debug("Successfully updated state for payment provider notification request with id - {}, new state - {}, initial state - {}", requestId, state, initialState);
        return request;
    }

    /* Utility methods */
    private void assertPaymentProviderNotificationRequestNotNull(final PaymentProviderNotificationRequest request, final Long requestId) {
        if (request == null) {
            LOGGER.error("No payment provider notification request was found for id - {}", requestId);
            throw new PaymentProviderNotificationRequestNotFoundForIdException(requestId);
        }
    }

    private void assertNotificationRequestIdNotNull(final Long notificationRequestId) {
        Assert.notNull(notificationRequestId, "Notification request id should not be null");
    }

    private void assertPaymentProviderNotificationRequestDto(final PaymentProviderNotificationRequestDto requestDto) {
        Assert.notNull(requestDto, "Request DTO should not be null");
        Assert.notNull(requestDto.getProviderType(), "Provider type in request DTO should not be null");
        Assert.notNull(requestDto.getRawContent(), "Raw content in request DTO should not be null");
    }

    private void assertNotificationRequestState(final PaymentProviderNotificationRequestState state, final Set<PaymentProviderNotificationRequestState> allowedInitialStates, final PaymentProviderNotificationRequestState initialState) {
        if (!allowedInitialStates.contains(initialState)) {
            LOGGER.error("{} state is not allowed since request has state - {} but expected request states are - {}", state, initialState, allowedInitialStates);
            throw new PaymentProviderNotificationRequestStateNotAllowedException(state, initialState, allowedInitialStates);
        }
    }

    /* Properties getters and setters */
    public PaymentProviderNotificationRequestRepository getPaymentProviderNotificationRequestRepository() {
        return paymentProviderNotificationRequestRepository;
    }

    public void setPaymentProviderNotificationRequestRepository(final PaymentProviderNotificationRequestRepository paymentProviderNotificationRequestRepository) {
        this.paymentProviderNotificationRequestRepository = paymentProviderNotificationRequestRepository;
    }
}
