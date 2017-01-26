package com.sfl.pms.services.order.exception.payment;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/17/15
 * Time: 10:54 AM
 */
public class InvalidOrderAmountException extends ServicesRuntimeException {

    private static final long serialVersionUID = -2746146097524878586L;

    /* Properties */
    private final BigDecimal expectedAmount;

    private final BigDecimal actualAmount;

    /* Constructors */
    public InvalidOrderAmountException(final BigDecimal expectedAmount, final BigDecimal actualAmount) {
        super("Invalid order amount, expected amount - " + expectedAmount + ", actual amount - " + actualAmount);
        this.expectedAmount = expectedAmount;
        this.actualAmount = actualAmount;
    }

    /* Properties getters and setters */
    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }
}
