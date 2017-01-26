package com.sfl.pms.services.payment.customer.method.impl.provider;

import com.sfl.pms.services.payment.customer.method.adyen.CustomerPaymentMethodAdyenInformationService;
import com.sfl.pms.services.payment.customer.method.dto.CustomerPaymentMethodProviderInformationDto;
import com.sfl.pms.services.payment.customer.method.dto.adyen.CustomerPaymentMethodAdyenInformationDto;
import com.sfl.pms.services.payment.customer.method.exception.adyen.AdyenRecurringDetailReferenceAlreadyUsedException;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/4/15
 * Time: 1:47 PM
 */
@Component(value = "customerPaymentMethodAdyenInformationHandler")
public class CustomerPaymentMethodAdyenInformationHandlerImpl implements CustomerPaymentMethodProviderInformationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAdyenInformationHandlerImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAdyenInformationService customerPaymentMethodAdyenInformationService;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformationHandlerImpl() {
        LOGGER.debug("Initializing customer payment method Adyen information handler");
    }

    @Override
    public void assertProviderInformationDto(final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> providerInformationDto) {
        Assert.notNull(providerInformationDto, "provider information DTO should not be null");
        Assert.isInstanceOf(CustomerPaymentMethodAdyenInformationDto.class, providerInformationDto, "Provider information DTO should not be null");
        Assert.notNull(((CustomerPaymentMethodAdyenInformationDto) providerInformationDto).getRecurringDetailReference(), "Recurring details reference in provider information DTO should not be null");
    }

    @Override
    public void assertProviderInformationUniqueness(@Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> providerInformationDto) {
        assertProviderInformationDto(providerInformationDto);
        final CustomerPaymentMethodAdyenInformationDto paymentMethodAdyenInformationDto = (CustomerPaymentMethodAdyenInformationDto) providerInformationDto;
        final boolean paymentMethodInformationExists = customerPaymentMethodAdyenInformationService.checkIfPaymentMethodInformationExistsForRecurringDetailReference(paymentMethodAdyenInformationDto.getRecurringDetailReference());
        if (paymentMethodInformationExists) {
            final CustomerPaymentMethodAdyenInformation paymentMethodInformation = customerPaymentMethodAdyenInformationService.getPaymentMethodInformationByRecurringDetailReference(paymentMethodAdyenInformationDto.getRecurringDetailReference());
            LOGGER.error("Payment method Adyen information already exists for recurring details reference - {}. Information - {}", paymentMethodAdyenInformationDto.getRecurringDetailReference(), paymentMethodInformation);
            throw new AdyenRecurringDetailReferenceAlreadyUsedException(paymentMethodAdyenInformationDto.getRecurringDetailReference(), paymentMethodInformation.getId());
        }
    }

    @Nonnull
    @Override
    public CustomerPaymentMethodProviderInformation convertPaymentMethodProviderInformationDto(@Nonnull final CustomerPaymentMethodProviderInformationDto<? extends CustomerPaymentMethodProviderInformation> providerInformationDto) {
        assertProviderInformationDto(providerInformationDto);
        final CustomerPaymentMethodAdyenInformationDto paymentMethodAdyenInformationDto = (CustomerPaymentMethodAdyenInformationDto) providerInformationDto;
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = new CustomerPaymentMethodAdyenInformation();
        paymentMethodAdyenInformationDto.updateDomainEntityProperties(paymentMethodAdyenInformation);
        return paymentMethodAdyenInformation;
    }

    /* Properties getters and setters */
    public CustomerPaymentMethodAdyenInformationService getCustomerPaymentMethodAdyenInformationService() {
        return customerPaymentMethodAdyenInformationService;
    }

    public void setCustomerPaymentMethodAdyenInformationService(final CustomerPaymentMethodAdyenInformationService customerPaymentMethodAdyenInformationService) {
        this.customerPaymentMethodAdyenInformationService = customerPaymentMethodAdyenInformationService;
    }
}
