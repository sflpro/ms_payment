package com.sfl.pms.services.payment.common.exception.channel;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.model.channel.PaymentProcessingChannelType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 3:00 PM
 */
public class UnknownPaymentProcessingChannelTypeException extends ServicesRuntimeException {

    private static final long serialVersionUID = -4546755548230483816L;

    /* Properties */
    private final PaymentProcessingChannelType paymentProcessingChannelType;

    /* Constructors */
    public UnknownPaymentProcessingChannelTypeException(final PaymentProcessingChannelType paymentProcessingChannelType) {
        super("Unknown payment processing channel type - " + paymentProcessingChannelType);
        this.paymentProcessingChannelType = paymentProcessingChannelType;
    }

    /* Properties getters and setters */
    public PaymentProcessingChannelType getPaymentProcessingChannelType() {
        return paymentProcessingChannelType;
    }
}
