package com.sfl.pms.services.payment.notification;

import com.sfl.pms.services.payment.notification.dto.PaymentProviderNotificationRequestDto;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequest;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotificationRequestState;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 10:30 AM
 */
public interface PaymentProviderNotificationRequestService {


    /**
     * Creates new payment provider notification for provided DTO
     *
     * @param requestDto
     * @return paymentProviderNotificationRequest
     */
    @Nonnull
    PaymentProviderNotificationRequest createPaymentProviderNotificationRequest(@Nonnull final PaymentProviderNotificationRequestDto requestDto);

    /**
     * Get new payment provider notification for id
     *
     * @param notificationRequestId
     * @return paymentProviderNotificationRequest
     */
    @Nonnull
    PaymentProviderNotificationRequest getPaymentProviderNotificationRequestById(@Nonnull final Long notificationRequestId);


    /**
     * Updates payment provider notification request state
     *
     * @param requestId
     * @param state
     * @param allowedInitialStates
     * @return paymentProviderNotificationRequest
     */
    @Nonnull
    PaymentProviderNotificationRequest updatePaymentProviderNotificationRequestState(@Nonnull final Long requestId, @Nonnull final PaymentProviderNotificationRequestState state, @Nonnull final Set<PaymentProviderNotificationRequestState> allowedInitialStates);
}
