package com.sfl.pms.services.payment.customer.method.exception.adyen;

import com.sfl.pms.services.common.exception.EntityNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 12:16 PM
 */
public class PaymentMethodAdyenInformationNotFoundForIdException extends EntityNotFoundForIdException {
    private static final long serialVersionUID = 5313671853348112804L;

    /* Properties */
    public PaymentMethodAdyenInformationNotFoundForIdException(final Long informationId) {
        super(informationId, CustomerPaymentMethodAdyenInformation.class);
    }
}
