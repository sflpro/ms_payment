package com.sfl.pms.services.payment.common.impl.order.request;

import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestCustomerPaymentMethodDto;
import com.sfl.pms.services.payment.common.dto.order.request.OrderRequestPaymentMethodDto;
import com.sfl.pms.services.payment.common.exception.order.InvalidCustomerPaymentMethodException;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestCustomerPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethod;
import com.sfl.pms.services.payment.customer.method.CustomerPaymentMethodService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 2:18 PM
 */
@Component
public class OrderRequestCustomerPaymentMethodDtoHandlerImpl implements OrderRequestCustomerPaymentMethodDtoHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRequestCustomerPaymentMethodDtoHandlerImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodService customerPaymentMethodService;

    /* Constructors */
    public OrderRequestCustomerPaymentMethodDtoHandlerImpl() {
        LOGGER.debug("Initializing order request customer payment method DTO handler");
    }

    @Override
    public void assertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer) {
        Assert.notNull(paymentMethodDto, "Payment method DTO should not be null");
        Assert.notNull(customer, "Customer should not be null");
        Assert.isInstanceOf(OrderRequestCustomerPaymentMethodDto.class, paymentMethodDto, "Payment method DTO should be type of OrderRequestCustomerPaymentMethodDto");
        final OrderRequestCustomerPaymentMethodDto orderRequestCustomerPaymentMethodDto = (OrderRequestCustomerPaymentMethodDto) paymentMethodDto;
        Assert.notNull(orderRequestCustomerPaymentMethodDto.getCustomerPaymentMethodId(), "Customer payment method ID should not be null in payment method DTO");
        final CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodService.getCustomerPaymentMethodById(orderRequestCustomerPaymentMethodDto.getCustomerPaymentMethodId());
        if (!customer.getId().equals(customerPaymentMethod.getCustomer().getId())) {
            LOGGER.debug("Customer payment method with id - {} belongs to customer with id - {}, where as provided customer id is - {}", customerPaymentMethod.getId(), customerPaymentMethod.getCustomer().getId(), customer.getId());
            throw new InvalidCustomerPaymentMethodException(customer.getId(), customerPaymentMethod.getId(), customerPaymentMethod.getCustomer().getId());
        }
    }

    @Override
    public OrderRequestPaymentMethod convertOrderRequestPaymentMethodDto(@Nonnull final OrderRequestPaymentMethodDto<? extends OrderRequestPaymentMethod> paymentMethodDto, @Nonnull final Customer customer) {
        assertOrderRequestPaymentMethodDto(paymentMethodDto, customer);
        final OrderRequestCustomerPaymentMethodDto orderRequestCustomerPaymentMethodDto = (OrderRequestCustomerPaymentMethodDto) paymentMethodDto;
        // Load customer payment method
        final CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodService.getCustomerPaymentMethodById(orderRequestCustomerPaymentMethodDto.getCustomerPaymentMethodId());
        final OrderRequestCustomerPaymentMethod orderRequestCustomerPaymentMethod = new OrderRequestCustomerPaymentMethod();
        orderRequestCustomerPaymentMethod.setCustomerPaymentMethod(customerPaymentMethod);
        LOGGER.debug("Successfully converted order request payment method DTO - {} into domain model object - {}", orderRequestCustomerPaymentMethodDto, orderRequestCustomerPaymentMethod);
        return orderRequestCustomerPaymentMethod;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodService getCustomerPaymentMethodService() {
        return customerPaymentMethodService;
    }

    public void setCustomerPaymentMethodService(final CustomerPaymentMethodService customerPaymentMethodService) {
        this.customerPaymentMethodService = customerPaymentMethodService;
    }
}
