package com.sfl.pms.services.payment.notification;

import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationState;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:06 AM
 */
public class PaymentProviderNotificationServiceIntegrationTest extends AbstractPaymentProviderNotificationServiceIntegrationTest<PaymentProviderNotification> {

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationService paymentProviderNotificationService;

    /* Constructors */
    public PaymentProviderNotificationServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentProviderNotificationState() {
        // Grab instance
        PaymentProviderNotification notification = getInstance();
        final PaymentProviderNotificationState initialState = notification.getState();
        final MutableHolder<PaymentProviderNotificationState> newStateHolder = new MutableHolder<>(null);
        Arrays.asList(PaymentProviderNotificationState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                newStateHolder.setValue(currentState);
            }
        });
        final PaymentProviderNotificationState newState = newStateHolder.getValue();
        flushAndClear();
        notification = paymentProviderNotificationService.updatePaymentProviderNotificationState(notification.getId(), newState, new HashSet<>(Arrays.asList(initialState)));
        assertEquals(newState, notification.getState());
        // FLush, reload and assert
        flushAndClear();
        notification = paymentProviderNotificationService.getPaymentProviderNotificationById(notification.getId());
        assertEquals(newState, notification.getState());
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderNotificationService<PaymentProviderNotification> getService() {
        return paymentProviderNotificationService;
    }

    @Override
    protected Class<PaymentProviderNotification> getInstanceClass() {
        return PaymentProviderNotification.class;
    }

    @Override
    protected PaymentProviderNotification getInstance() {
        return getServicesTestHelper().createAdyenPaymentProviderNotification();
    }
}
