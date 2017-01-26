package com.sfl.pms.services.payment.customer.method.impl.authorization;

import com.sfl.pms.persistence.repositories.payment.customer.method.CustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestRepository;
import com.sfl.pms.services.payment.customer.method.authorization.AbstractCustomerPaymentMethodAuthorizationRequestService;
import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.exception.CustomerPaymentMethodAuthorizationRequestStateNotAllowedException;
import com.sfl.pms.services.payment.customer.method.exception.PaymentMethodAuthorizationRequestNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.exception.PaymentMethodAuthorizationRequestNotFoundForUuIdException;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:26 PM
 */
public abstract class AbstractCustomerPaymentMethodAuthorizationRequestServiceImpl<T extends CustomerPaymentMethodAuthorizationRequest> implements AbstractCustomerPaymentMethodAuthorizationRequestService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomerPaymentMethodAuthorizationRequestServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAuthorizationRequestRepository customerPaymentMethodAuthorizationRequestRepository;

    /* Constructors */
    public AbstractCustomerPaymentMethodAuthorizationRequestServiceImpl() {
        LOGGER.debug("Initializing abstract customer payment method authorization service");
    }


    @Nonnull
    @Override
    public T getPaymentMethodAuthorizationRequestById(@Nonnull final Long requestId) {
        assertAuthorizationRequestId(requestId);
        LOGGER.debug("Loading payment method authorization request for id - {}", requestId);
        final T request = getRepository().findOne(requestId);
        assertAuthorizationRequestNotNullForId(request, requestId);
        LOGGER.debug("Successfully retrieved payment method authorization request for id - {}, request - {}", request.getId(), request);
        return request;
    }

    @Nonnull
    @Override
    public T getPaymentMethodAuthorizationRequestByUuId(@Nonnull final String uuId) {
        Assert.notNull(uuId, "UUID should not be null");
        LOGGER.debug("Getting customer payment method authorization request with uuId - {}", uuId);
        final T authorizationRequest = getRepository().findByUuId(uuId);
        assertAuthorizationRequestNotNullForUuId(authorizationRequest, uuId);
        LOGGER.debug("Successfully retrieved payment method request for uuId - {}, request - {}", uuId, authorizationRequest);
        return authorizationRequest;
    }

    @Transactional
    @Nonnull
    @Override
    public T updatePaymentMethodAuthorizationRequestRedirectUrl(@Nonnull final Long authorizationRequestId, @Nonnull final String redirectUrl) {
        assertAuthorizationRequestId(authorizationRequestId);
        Assert.notNull(redirectUrl, "Authorization request redirect URL should not be null");
        LOGGER.debug("Updating payment redirect URL for authorization request with id - {}, payment redirect URL - {}", authorizationRequestId, redirectUrl);
        T authorizationRequest = getRepository().findOne(authorizationRequestId);
        assertAuthorizationRequestNotNullForId(authorizationRequest, authorizationRequestId);
        // Update redirect URL and persist
        authorizationRequest.setPaymentRedirectUrl(redirectUrl);
        authorizationRequest = getRepository().save(authorizationRequest);
        LOGGER.debug("Successfully updated redirect URL for authorization request with id - {}, request - {}", authorizationRequest.getId(), authorizationRequest);
        return authorizationRequest;
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerPaymentMethodAuthorizationRequest updatePaymentMethodAuthorizationRequestState(@Nonnull final Long requestId, @Nonnull final CustomerPaymentMethodAuthorizationRequestState state, @Nonnull final Set<CustomerPaymentMethodAuthorizationRequestState> allowedInitialStates) {
        assertAuthorizationRequestId(requestId);
        Assert.notNull(state, "Payment method authorization request state should not be null");
        Assert.notNull(allowedInitialStates, "Payment method authorization request allowed initial states should not be null");
        LOGGER.debug("Updating payment method authorization request state with id - {}, state - {}, allowed initial states - {}", requestId, state, allowedInitialStates);
        CustomerPaymentMethodAuthorizationRequest request = customerPaymentMethodAuthorizationRequestRepository.findByIdWithWriteLockFlushedAndFreshData(requestId);
        assertAuthorizationRequestNotNullForId(request, requestId);
        final CustomerPaymentMethodAuthorizationRequestState initialState = request.getState();
        if (allowedInitialStates.size() != 0) {
            assertPaymentRequestState(state, allowedInitialStates, initialState);
        }
        // Update state
        request.setState(state);
        request.setUpdated(new Date());
        // Persist request
        request = customerPaymentMethodAuthorizationRequestRepository.saveAndFlush(request);
        LOGGER.debug("Successfully updated state for customer payment method authorization request with id - {}, new state - {}, initial state - {}", requestId, state, initialState);
        return request;
    }

    /* Utility methods */
    private void assertPaymentRequestState(final CustomerPaymentMethodAuthorizationRequestState state, final Set<CustomerPaymentMethodAuthorizationRequestState> allowedInitialStates, final CustomerPaymentMethodAuthorizationRequestState initialState) {
        if (!allowedInitialStates.contains(initialState)) {
            LOGGER.error("{} state is not allowed since request has state - {} but expected request states are - {}", state, initialState, allowedInitialStates);
            throw new CustomerPaymentMethodAuthorizationRequestStateNotAllowedException(state, initialState, allowedInitialStates);
        }
    }

    private void assertAuthorizationRequestNotNullForUuId(final CustomerPaymentMethodAuthorizationRequest request, final String uuId) {
        if (request == null) {
            LOGGER.error("No payment method authorization request was found for uuId - {}, class - {}", uuId, getInstanceClass());
            throw new PaymentMethodAuthorizationRequestNotFoundForUuIdException(uuId, getInstanceClass());
        }
    }

    private void assertAuthorizationRequestNotNullForId(final CustomerPaymentMethodAuthorizationRequest request, final Long id) {
        if (request == null) {
            LOGGER.error("No payment method authorization request was found for id - {}, class - {}", id, getInstanceClass());
            throw new PaymentMethodAuthorizationRequestNotFoundForIdException(id, getInstanceClass());
        }
    }

    private void assertAuthorizationRequestId(final Long requestId) {
        Assert.notNull(requestId, "Payment method authorization request id should not be null");
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodAuthorizationRequestRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    protected void assertAuthorizationRequestDto(final CustomerPaymentMethodAuthorizationRequestDto<? extends CustomerPaymentMethodAuthorizationRequest> requestDto) {
        Assert.notNull(requestDto, "Payment method authorization request DTO should not be null");
        Assert.notNull(requestDto.getPaymentProviderType(), "Payment provider type in request DTO should not be null");
        Assert.notNull(requestDto.getPaymentProviderIntegrationType(), "Payment provider integration type in request DTO should not be null");
        Assert.notNull(requestDto.getCurrency(), "Currency in request DTO should not be null");
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAuthorizationRequestRepository getCustomerPaymentMethodAuthorizationRequestRepository() {
        return customerPaymentMethodAuthorizationRequestRepository;
    }

    public void setCustomerPaymentMethodAuthorizationRequestRepository(final CustomerPaymentMethodAuthorizationRequestRepository customerPaymentMethodAuthorizationRequestRepository) {
        this.customerPaymentMethodAuthorizationRequestRepository = customerPaymentMethodAuthorizationRequestRepository;
    }
}
