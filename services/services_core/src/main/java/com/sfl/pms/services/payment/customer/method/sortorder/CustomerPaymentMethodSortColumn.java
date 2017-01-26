package com.sfl.pms.services.payment.customer.method.sortorder;

import com.sfl.pms.services.common.sortorder.SortColumn;
import com.sfl.pms.services.common.sortorder.SortDirection;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 3/5/15
 * Time: 3:01 PM
 */
public enum CustomerPaymentMethodSortColumn implements SortColumn {
    DATE("cpm.created"),
    TYPE("cpm.type");

    private String specification;

    CustomerPaymentMethodSortColumn(final String specification) {
        this.specification = specification;
    }

    /* Public method overrides */
    public String getSpecification() {
        return specification;
    }

    /* Static methods */
    public static CustomerPaymentMethodSortColumn getDefaultSortColumn() {
        return DATE;
    }

    public static SortDirection getDefaultDirection() {
        return SortDirection.DESCENDING;
    }
}
