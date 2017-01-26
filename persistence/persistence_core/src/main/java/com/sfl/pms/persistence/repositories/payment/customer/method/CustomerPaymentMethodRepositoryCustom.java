package com.sfl.pms.persistence.repositories.payment.customer.method;

import com.sfl.pms.services.common.sortorder.SortOrderSpecification;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodLookupParameters;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.customer.method.sortorder.CustomerPaymentMethodSortColumn;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 3/5/15
 * Time: 2:44 PM
 */
public interface CustomerPaymentMethodRepositoryCustom {

    /**
     * Gets count of customer payment methods for search parameters
     *
     * @param parameters
     * @return customerPaymentMethodsCount
     */
    @Nonnull
    Long getCustomerPaymentMethodsCount(@Nonnull final CustomerPaymentMethodLookupParameters parameters);

    /**
     * Loads customer payment methods for search parameters
     *
     * @param parameters
     * @param maxCount
     * @return customerPaymentMethods
     */
    @Nonnull
    List<CustomerPaymentMethod> findCustomerPaymentMethods(@Nonnull final CustomerPaymentMethodLookupParameters parameters, @Nonnull final SortOrderSpecification<CustomerPaymentMethodSortColumn> sortOrderSpecification, final long startFrom, final int maxCount);
}
