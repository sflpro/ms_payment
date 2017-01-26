package com.sfl.pms.services.customer;

import com.sfl.pms.services.customer.dto.CustomerDto;
import com.sfl.pms.services.customer.model.Customer;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 10/01/14
 * Time: 10:18 PM
 */
public interface CustomerService {

    /**
     * Returns user for provided id
     *
     * @return user
     */
    @Nonnull
    Customer getCustomerById(@Nonnull final Long id);


    /**
     * Creates user for DTO
     *
     * @param userDto
     * @return user
     */
    @Nonnull
    Customer createCustomer(@Nonnull final CustomerDto userDto);

    /**
     * Checks if user exists for UUID
     *
     * @param uuId
     * @return exists
     */
    @Nonnull
    boolean checkIfCustomerExistsForUuId(@Nonnull final String uuId);

    /**
     * Gets user for provided UUID
     *
     * @param uuId
     * @return user
     */
    @Nonnull
    Customer getCustomerByUuId(@Nonnull final String uuId);

    /**
     * Gets or creates user for provided uuid
     *
     * @param customerDto
     * @return user
     */
    @Nonnull
    Customer getOrCreateCustomerForUuId(final CustomerDto customerDto);
}
