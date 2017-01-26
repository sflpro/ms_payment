package com.sfl.pms.services.order.exception;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/28/14
 * Time: 3:56 PM
 */
public class NonRootPurchaseException extends ServicesRuntimeException {
    private static final long serialVersionUID = 7235129027827949924L;

    /* Properties */
    private final Long purchaseId;

    /* Constructors */
    public NonRootPurchaseException(final Long purchaseId) {
        super("Can not create order for not root purchase, purchase id - " + purchaseId);
        this.purchaseId = purchaseId;
    }

    /* Properties getters and setters */
    public Long getPurchaseId() {
        return purchaseId;
    }
}
