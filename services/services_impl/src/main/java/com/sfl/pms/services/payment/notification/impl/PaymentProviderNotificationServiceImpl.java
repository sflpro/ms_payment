package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.repositories.payment.notification.AbstractPaymentProviderNotificationRepository;
import com.sfl.pms.persistence.repositories.payment.notification.PaymentProviderNotificationRepository;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationStateNotAllowedException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;
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
 * Date: 4/26/15
 * Time: 12:02 AM
 */
@Service
public class PaymentProviderNotificationServiceImpl extends AbstractPaymentProviderNotificationServiceImpl<PaymentProviderNotification> implements PaymentProviderNotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationRepository paymentProviderNotificationRepository;

    /* Constructors */
    public PaymentProviderNotificationServiceImpl() {
        LOGGER.debug("Initializing payment provider notification service");
    }

    @Transactional
    @Nonnull
    @Override
    public PaymentProviderNotification updatePaymentProviderNotificationState(@Nonnull final Long notificationId, @Nonnull final PaymentProviderNotificationState state, @Nonnull final Set<PaymentProviderNotificationState> allowedInitialStates) {
        assertPaymentProviderNotificationIdNotNull(notificationId);
        Assert.notNull(state, "Payment provider notification state should not be null");
        Assert.notNull(allowedInitialStates, "Payment provider notification allowed initial states should not be null");
        LOGGER.debug("Updating payment provider notification state with id - {}, state - {}, allowed initial states - {}", notificationId, state, allowedInitialStates);
        PaymentProviderNotification notification = paymentProviderNotificationRepository.findByIdWithPessimisticWriteLock(notificationId);
        assertPaymentProviderNotificationNotNullForId(notification, notificationId);
        final PaymentProviderNotificationState initialState = notification.getState();
        if (allowedInitialStates.size() != 0) {
            assertNotificationRequestState(state, allowedInitialStates, initialState);
        }
        // Update state
        notification.setState(state);
        notification.setUpdated(new Date());
        // Persist request
        notification = paymentProviderNotificationRepository.save(notification);
        LOGGER.debug("Successfully updated state for payment provider notification with id - {}, new state - {}, initial state - {}", notificationId, state, initialState);
        return notification;
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderNotificationRepository<PaymentProviderNotification> getRepository() {
        return paymentProviderNotificationRepository;
    }

    @Override
    protected Class<PaymentProviderNotification> getInstanceClass() {
        return PaymentProviderNotification.class;
    }

    private void assertNotificationRequestState(final PaymentProviderNotificationState state, final Set<PaymentProviderNotificationState> allowedInitialStates, final PaymentProviderNotificationState initialState) {
        if (!allowedInitialStates.contains(initialState)) {
            LOGGER.error("{} state is not allowed since request has state - {} but expected request states are - {}", state, initialState, allowedInitialStates);
            throw new PaymentProviderNotificationStateNotAllowedException(state, initialState, allowedInitialStates);
        }
    }

    /* Properties getters and setters */
    public PaymentProviderNotificationRepository getPaymentProviderNotificationRepository() {
        return paymentProviderNotificationRepository;
    }

    public void setPaymentProviderNotificationRepository(final PaymentProviderNotificationRepository paymentProviderNotificationRepository) {
        this.paymentProviderNotificationRepository = paymentProviderNotificationRepository;
    }
}
