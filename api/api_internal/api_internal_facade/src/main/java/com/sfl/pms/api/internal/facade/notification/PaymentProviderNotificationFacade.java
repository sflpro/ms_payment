package com.sfl.pms.api.internal.facade.notification;

import com.sfl.pms.core.api.internal.model.common.result.ResultResponseModel;
import com.sfl.pms.core.api.internal.model.notification.request.CreatePaymentProviderNotificationRequest;
import com.sfl.pms.core.api.internal.model.notification.response.CreatePaymentProviderNotificationResponse;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/27/15
 * Time: 12:19 PM
 */
public interface PaymentProviderNotificationFacade {

    /**
     * Creates new payment provider notification
     *
     * @param request
     * @return response
     */
    ResultResponseModel<CreatePaymentProviderNotificationResponse> createPaymentProviderNotificationRequest(@Nonnull final CreatePaymentProviderNotificationRequest request);
}
