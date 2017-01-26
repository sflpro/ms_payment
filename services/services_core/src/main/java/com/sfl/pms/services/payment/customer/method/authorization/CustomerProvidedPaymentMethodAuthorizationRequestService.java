package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerProvidedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerProvidedPaymentMethodAuthorizationRequest;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:18 PM
 */
public interface CustomerProvidedPaymentMethodAuthorizationRequestService extends AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerProvidedPaymentMethodAuthorizationRequest> {


    /**
     * Creates new customer Provided payment method authorization request
     *
     * @param customerId
     * @param paymentMethodAuthorizationRequestDto
     * @return customerProvidedPaymentMethodAuthorizationRequest
     */
    @Nonnull
    CustomerProvidedPaymentMethodAuthorizationRequest createPaymentMethodAuthorizationRequest(@Nonnull final Long customerId, @Nonnull final CustomerProvidedPaymentMethodAuthorizationRequestDto paymentMethodAuthorizationRequestDto);
}
