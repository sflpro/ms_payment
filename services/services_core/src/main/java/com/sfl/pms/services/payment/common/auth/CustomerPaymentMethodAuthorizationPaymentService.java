package com.sfl.pms.services.payment.common.auth;

import com.sfl.pms.services.payment.common.AbstractPaymentService;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.common.dto.channel.PaymentProcessingChannelDto;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannel;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:41 AM
 */
public interface CustomerPaymentMethodAuthorizationPaymentService extends AbstractPaymentService<CustomerPaymentMethodAuthorizationPayment> {

    /**
     * Creates new customer payment method authorization payment
     *
     * @param paymentMethodAuthorizationRequestId
     * @param paymentDto
     * @param paymentProcessingChannelDto
     * @return customerPaymentMethodAuthorizationPayment
     */
    @Nonnull
    CustomerPaymentMethodAuthorizationPayment createPayment(@Nonnull final Long paymentMethodAuthorizationRequestId, @Nonnull final CustomerPaymentMethodAuthorizationPaymentDto paymentDto, @Nonnull final PaymentProcessingChannelDto<? extends PaymentProcessingChannel> paymentProcessingChannelDto);
}
