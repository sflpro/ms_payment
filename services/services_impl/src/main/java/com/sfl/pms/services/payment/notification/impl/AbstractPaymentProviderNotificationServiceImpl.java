package com.sfl.pms.services.payment.notification.impl;

import com.sfl.pms.persistence.repositories.payment.notification.AbstractPaymentProviderNotificationRepository;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.notification.AbstractPaymentProviderNotificationService;
import com.sfl.pms.services.payment.notification.PaymentProviderNotificationRequestService;
import com.sfl.pms.services.payment.notification.exception.PaymentProviderNotificationNotFoundForIdException;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/25/15
 * Time: 11:59 PM
 */
public abstract class AbstractPaymentProviderNotificationServiceImpl<T extends PaymentProviderNotification> implements AbstractPaymentProviderNotificationService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPaymentProviderNotificationServiceImpl.class);

    /* Spring beans */
    @Autowired
    private PaymentProviderNotificationRequestService paymentProviderNotificationRequestService;

    @Autowired
    private PaymentService paymentService;

    /* Constructors */
    public AbstractPaymentProviderNotificationServiceImpl() {
        LOGGER.debug("Initializing abstract payment provider notification service");
    }

    @Nonnull
    @Override
    public T getPaymentProviderNotificationById(@Nonnull final Long notificationId) {
        assertPaymentProviderNotificationIdNotNull(notificationId);
        LOGGER.debug("Getting payment provider notification for id - {}", notificationId);
        final T notification = getRepository().findOne(notificationId);
        assertPaymentProviderNotificationNotNullForId(notification, notificationId);
        LOGGER.debug("Successfully retrieved payment provider notification for id - {}, notification class  - {}", notificationId, getInstanceClass());
        return notification;
    }

    @Transactional
    @Nonnull
    @Override
    public T updatePaymentForNotification(@Nonnull final Long notificationId, @Nonnull final Long paymentId) {
        assertPaymentProviderNotificationIdNotNull(notificationId);
        Assert.notNull(paymentId, "Payment id should not be null");
        LOGGER.debug("Updating payment for notification with id - {}, payment id  - {}", notificationId, paymentId);
        T notification = getRepository().findOne(notificationId);
        assertPaymentProviderNotificationNotNullForId(notification, notificationId);
        final Payment payment = paymentService.getPaymentById(paymentId);
        // Update payment for notification
        notification.setPayment(payment);
        // Persist notification
        notification = getRepository().save(notification);
        LOGGER.debug("Successfully updated payment for notification with id - {}, payment id - {}", notificationId, paymentId);
        return notification;
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderNotificationRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    /* Utility methods */
    protected void assertPaymentProviderNotificationIdNotNull(final Long notificationId) {
        Assert.notNull(notificationId, "Notification id should not be null");
    }

    protected void assertPaymentProviderNotificationNotNullForId(final T notification, final Long notificationId) {
        if (notification == null) {
            LOGGER.error("No payment provider notification was found for id - {}, notification class - {}", notificationId, getInstanceClass());
            throw new PaymentProviderNotificationNotFoundForIdException(notificationId, getInstanceClass());
        }
    }

    /* Properties getters and setters */
    public PaymentProviderNotificationRequestService getPaymentProviderNotificationRequestService() {
        return paymentProviderNotificationRequestService;
    }

    public void setPaymentProviderNotificationRequestService(final PaymentProviderNotificationRequestService paymentProviderNotificationRequestService) {
        this.paymentProviderNotificationRequestService = paymentProviderNotificationRequestService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
