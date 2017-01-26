package com.sfl.pms.services.payment.customer.method;

import com.sfl.pms.services.payment.customer.method.dto.CustomerBankPaymentMethodDto;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.model.CustomerBankPaymentMethod;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/3/15
 * Time: 4:37 PM
 */
public interface CustomerBankPaymentMethodService extends AbstractCustomerPaymentMethodService<CustomerBankPaymentMethod> {

    /**
     * Creates new customer bank payment method
     *
     * @param customerId
     * @param authorizationRequestId
     * @param paymentMethodDto
     * @param paymentMethodProviderInformationDto
     * @return customerPaymentMethod
     */
    @Nonnull
    CustomerBankPaymentMethod createCustomerPaymentMethod(@Nonnull final Long customerId, final Long authorizationRequestId, @Nonnull final CustomerBankPaymentMethodDto paymentMethodDto, @Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> paymentMethodProviderInformationDto);
}
