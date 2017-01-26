package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestPaymentMethodDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 2:17 PM
 */
public interface OrderRequestPaymentMethodDtoHandler {

    /**
     * Asserts order payment request payment method DTO
     *
     * @param paymentMethodDto
     * @param customer
     */
    void assertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer);

    /**
     * Converts order payment request payment method DTO into domain model object
     *
     * @param paymentMethodDto
     * @param customer
     * @return orderRequestPaymentMethod
     */
    OrderRequestPaymentMethod convertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer);
}
