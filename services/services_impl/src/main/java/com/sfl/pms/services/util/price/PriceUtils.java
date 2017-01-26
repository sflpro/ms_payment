package com.sfl.pms.services.util.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/18/15
 * Time: 11:50 AM
 */
public final class PriceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceUtils.class);

    /* Constants */
    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);

    /* Constructors */
    private PriceUtils() {
    }

    public static BigDecimal calculatePercentage(final BigDecimal amount, final Integer percentage) {
        Assert.notNull(amount, "Amount should not be null");
        Assert.notNull(percentage, "Percentage should not be null");
        final BigDecimal calculatedPercentage = amount.multiply(BigDecimal.valueOf(percentage)).divide(HUNDRED_PERCENT);
        LOGGER.debug("Calculated {} percentage of amount - {} is - {}", percentage, amount, amount);
        return calculatedPercentage;
    }
}
