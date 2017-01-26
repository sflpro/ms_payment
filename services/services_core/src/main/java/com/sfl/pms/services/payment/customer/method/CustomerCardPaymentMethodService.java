package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.dto.CustomerCardPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerCardPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/2/15
 * Time: 4:18 PM
 */
public interface CustomerCardPaymentMethodService extends AbstractCustomerPaymentMethodService<CustomerCardPaymentMethod> {


    /**
     * Creates new customer card payment method
     *
     * @param customerId
     * @param authorizationRequestId
     * @param paymentMethodDto
     * @param paymentMethodProviderInformationDto
     * @return customerPaymentMethod
     */
    @Nonnull
    CustomerCardPaymentMethod createCustomerPaymentMethod(@Nonnull final Long customerId, final Long authorizationRequestId, @Nonnull final CustomerCardPaymentMethodDto paymentMethodDto, @Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto);
}
