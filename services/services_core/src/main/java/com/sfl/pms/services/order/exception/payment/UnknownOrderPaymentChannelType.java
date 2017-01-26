package com.sfl.pms.services.order.exception.payment;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.order.model.payment.OrderPaymentChannelType;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/31/15
 * Time: 6:18 PM
 */
public class UnknownOrderPaymentChannelType extends ServicesRuntimeException {
    private static final long serialVersionUID = 4812744811252734740L;

    /* Properties */
    private final OrderPaymentChannelType channelType;

    /* Constructors */
    public UnknownOrderPaymentChannelType(final OrderPaymentChannelType channelType) {
        super("Unknown order payment channel type - " + channelType);
        this.channelType = channelType;
    }

    /* Properties getters and setters */
    public OrderPaymentChannelType getChannelType() {
        return channelType;
    }
}
