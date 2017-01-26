package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequestState;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:16 PM
 */
public interface AbstractCustomerPaymentMethodAuthorizationRequestService<T extends CustomerPaymentMethodAuthorizationRequest> {

    /**
     * Gets customer payment method authorization request by id
     *
     * @param requestId
     * @return customerPaymentMethodAuthorizationRequest
     */
    @Nonnull
    T getPaymentMethodAuthorizationRequestById(@Nonnull final Long requestId);

    /**
     * Gets payment method authorization request by uuId
     *
     * @param uuId
     * @return customerPaymentMethodAuthorizationRequest
     */
    @Nonnull
    T getPaymentMethodAuthorizationRequestByUuId(@Nonnull final String uuId);

    /**
     * Updated payment redirect URL for authorization request
     *
     * @param authorizationRequestId
     * @param redirectUrl
     * @return customerPaymentMethodAuthorizationRequest
     */
    @Nonnull
    T updatePaymentMethodAuthorizationRequestRedirectUrl(@Nonnull final Long authorizationRequestId, @Nonnull final String redirectUrl);

    /**
     * Updates state of payment method authorization request
     *
     * @param requestId
     * @param state
     * @param allowedInitialStates
     * @return customerPaymentMethodAuthorizationRequest
     */
    @Nonnull
    CustomerPaymentMethodAuthorizationRequest updatePaymentMethodAuthorizationRequestState(@Nonnull final Long requestId, @Nonnull final CustomerPaymentMethodAuthorizationRequestState state, @Nonnull final Set<CustomerPaymentMethodAuthorizationRequestState> allowedInitialStates);


}
