package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:12 PM
 */
public interface CustomerPaymentMethodService extends AbstractCustomerPaymentMethodService<CustomerPaymentMethod> {

    /**
     * Gets customer payment methods for search parameters
     *
     * @param parameters
     * @param sortOrderSpecification
     * @param startFrom
     * @param maxCount
     * @return customer payment methods
     */
    @Nonnull
    List<CustomerPaymentMethod> getCustomerPaymentMethodsForSearchParameters(@Nonnull final CustomerPaymentMethodLookupParameters parameters, @Nonnull final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, @Nonnull final Long startFrom, @Nonnull final Integer maxCount);

    /**
     * Gets customer payment methods count for search parameters
     *
     * @param parameters
     * @return customer payment methods count
     */
    @Nonnull
    Long getCustomerPaymentMethodsCountForSearchParameters(@Nonnull final CustomerPaymentMethodLookupParameters parameters);
}
