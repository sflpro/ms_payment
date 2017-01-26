package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.*;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodsSynchronizationResult;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethod;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.auth.CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.impl.AbstractPaymentTypeSpecificOperationsProcessorImpl;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 11:18 AM
 */
@Component("customerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor")
public class CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImpl extends AbstractPaymentTypeSpecificOperationsProcessorImpl implements CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImpl.class);

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentSpecificOperationsProcessorImpl() {
        LOGGER.debug("Initializing customer payment method authorization payment specific operations processor");
    }

    @Nonnull
    @Override
    public PaymentProcessingResultDetailedInformationDto processPaymentResult(@Nonnull final Payment payment, @Nonnull final PaymentResult paymentResult) {
        assertPaymentNotNull(payment);
        assertPaymentType(payment);
        // Create result list
        final List<Long> customerPaymentMethodIds = new ArrayList<>();
        if (paymentResult != null) {
            LOGGER.debug("Successfully created payment result for payment with id - {}, payment result - {}", payment.getId(), paymentResult);
            if (PaymentResultStatus.PAID.equals(paymentResult.getStatus())) {
                LOGGER.debug("Payment with id - {} created for authorization payment with id - {} was authorized by payment provider. Payment result - {}", payment.getId(), payment.getId(), paymentResult);
                updatePaymentState(payment.getId(), paymentResult.getStatus().getPaymentState(), null);
                // Create result holder
                final List<Long> createdCustomerPaymentMethodIds = synchronisePaymentMethodsAndScheduleTaskIfRequired(payment.getCustomer().getId(), payment.getPaymentProviderType());
                LOGGER.debug("Created {} customer payment methods when processing payment id - {}", createdCustomerPaymentMethodIds.size(), payment.getId());
                customerPaymentMethodIds.addAll(createdCustomerPaymentMethodIds);
            } else {
                LOGGER.debug("Payment with id - {} created for authorization payment with id - {} was refused by payment provider. Payment result - {}", payment.getId(), payment.getId(), paymentResult);
                updatePaymentState(payment.getId(), paymentResult.getStatus().getPaymentState(), null);
            }
        } else {
            updatePaymentState(payment.getId(), PaymentState.FAILED, null);
        }
        return new CustomerPaymentMethodAuthorizationPaymentProcessingResultDetailedInformationDto(payment.getId(), customerPaymentMethodIds);
    }


    @Override
    public PaymentProcessingResultDetailedInformationDto processDuplicatePaymentResult(@Nonnull final Payment payment, @Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        LOGGER.debug("Do not process duplicate result payment result for payment - {} , payment result - {}", payment, paymentResultDto);
        return null;
    }

    /* Utility methods */
    private List<Long> synchronisePaymentMethodsAndScheduleTaskIfRequired(final Long customerId, final PaymentProviderType paymentProviderType) {
        final CustomerPaymentMethodsSynchronizationResult synchronizationResult = getCustomerPaymentMethodsSynchronizationService().synchronizeCustomerPaymentMethods(customerId, paymentProviderType);
        final List<CustomerPaymentMethod> customerPaymentMethods = synchronizationResult.getCreatedCustomerPaymentMethods();
        LOGGER.debug("Created {} customer payment methods when processing payment, customer id - {}", customerPaymentMethods.size(), customerId);
        if (customerPaymentMethods.size() == 0) {
            // Schedule payment methods synchronization
            schedulePaymentMethodsSynchronizationTask(customerId, paymentProviderType);
        }
        return getCustomerPaymentMethodIds(customerPaymentMethods);
    }

    private void assertPaymentType(final Payment payment) {
        Assert.isInstanceOf(CustomerPaymentMethodAuthorizationPayment.class, payment, "Payment should be type of CustomerPaymentMethodAuthorizationPayment");
    }

    private List<Long> getCustomerPaymentMethodIds(final List<CustomerPaymentMethod> customerPaymentMethods) {
        return customerPaymentMethods.stream().map(customerPaymentMethod -> customerPaymentMethod.getId()).collect(Collectors.toCollection(ArrayList::new));
    }

    private void assertPaymentNotNull(final Payment payment) {
        Assert.notNull(payment, "Payment should not be null");
    }

    @Override
    protected PaymentType getPaymentType() {
        return PaymentType.PAYMENT_METHOD_AUTHORIZATION;
    }
}
