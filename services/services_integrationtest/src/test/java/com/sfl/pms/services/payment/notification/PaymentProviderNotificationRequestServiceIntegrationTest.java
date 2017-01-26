package com.sfl.pms.services.payment.notification;

import com.sfl.pms.services.payment.notification.dto.PaymentProviderNotificationRequestDto;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
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
 * Time: 10:35 AM
 */
public class PaymentProviderNotificationRequestServiceIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationRequestService paymentProviderNotificationRequestService;

    /* Constructors */
    public PaymentProviderNotificationRequestServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testUpdatePaymentProviderNotificationRequestState() {
        // Grab instance
        PaymentProviderNotificationRequest request = getServicesTestHelper().createPaymentProviderNotificationRequest();
        final PaymentProviderNotificationRequestState initialState = request.getState();
        final MutableHolder<PaymentProviderNotificationRequestState> newStateHolder = new MutableHolder<>(null);
        Arrays.asList(PaymentProviderNotificationRequestState.values()).forEach(currentState -> {
            if (!currentState.equals(initialState)) {
                newStateHolder.setValue(currentState);
            }
        });
        final PaymentProviderNotificationRequestState newState = newStateHolder.getValue();
        flushAndClear();
        request = paymentProviderNotificationRequestService.updatePaymentProviderNotificationRequestState(request.getId(), newState, new HashSet<>(Arrays.asList(initialState)));
        assertEquals(newState, request.getState());
        // FLush, reload and assert
        flushAndClear();
        request = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(request.getId());
        assertEquals(newState, request.getState());
    }

    @Test
    public void testGetPaymentProviderNotificationRequestById() {
        // Prepare data
        final PaymentProviderNotificationRequest request = getServicesTestHelper().createPaymentProviderNotificationRequest();
        // Load and assert
        PaymentProviderNotificationRequest result = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(request.getId());
        assertEquals(request, result);
        // Flush, clear, reload and assert
        flushAndClear();
        result = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(request.getId());
        assertEquals(request, result);
    }


    @Test
    public void testCreatePaymentProviderNotificationRequest() {
        // Prepare data
        final PaymentProviderNotificationRequestDto requestDto = getServicesTestHelper().createPaymentProviderNotificationRequestDto();
        // Create request
        PaymentProviderNotificationRequest request = paymentProviderNotificationRequestService.createPaymentProviderNotificationRequest(requestDto);
        getServicesTestHelper().assertPaymentProviderNotificationRequest(request, requestDto);
        // Flush, clear, reload and assert
        flushAndClear();
        request = paymentProviderNotificationRequestService.getPaymentProviderNotificationRequestById(request.getId());
        getServicesTestHelper().assertPaymentProviderNotificationRequest(request, requestDto);
    }

}
