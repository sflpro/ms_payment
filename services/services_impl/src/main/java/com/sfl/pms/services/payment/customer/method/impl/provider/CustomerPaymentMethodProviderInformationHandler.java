package com.sfl.pms.services.payment.customer.method.impl.provider;

import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 1:44 PM
 */
public interface CustomerPaymentMethodProviderInformationHandler {

    /**
     * Asserts payment method provider information
     *
     * @param providerInformationDto
     */
    void assertProviderInformationDto(final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> providerInformationDto);

    /**
     * Asserts payment method provider information uniqueness
     *
     * @param providerInformationDto
     */
    void assertProviderInformationUniqueness(@Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> providerInformationDto);

    /**
     * Converts payment method provider information DTO to entity
     *
     * @param providerInformationDto
     * @return customerPaymentMethodProviderInformation
     */
    @Nonnull
    CustomerPaymentMethodProviderInformation convertPaymentMethodProviderInformationDto(@Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> providerInformationDto);
}
