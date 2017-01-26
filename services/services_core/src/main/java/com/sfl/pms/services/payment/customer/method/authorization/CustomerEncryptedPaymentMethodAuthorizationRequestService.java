package com.sfl.pms.services.payment.customer.method.authorization;

import com.sfl.pms.services.payment.customer.method.dto.authorization.CustomerEncryptedPaymentMethodAuthorizationRequestDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerEncryptedPaymentMethodAuthorizationRequest;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 8:18 PM
 */
public interface CustomerEncryptedPaymentMethodAuthorizationRequestService extends AbstractCustomerPaymentMethodAuthorizationRequestService<CustomerEncryptedPaymentMethodAuthorizationRequest> {


    /**
     * Creates new customer Encrypted payment method authorization request
     *
     * @param customerId
     * @param paymentMethodAuthorizationRequestDto
     * @return customerEncryptedPaymentMethodAuthorizationRequest
     */
    @Nonnull
    CustomerEncryptedPaymentMethodAuthorizationRequest createPaymentMethodAuthorizationRequest(@Nonnull final Long customerId, @Nonnull final CustomerEncryptedPaymentMethodAuthorizationRequestDto paymentMethodAuthorizationRequestDto);
}
