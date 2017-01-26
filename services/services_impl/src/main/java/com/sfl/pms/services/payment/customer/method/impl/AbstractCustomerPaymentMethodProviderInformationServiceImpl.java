package com.sfl.pms.services.payment.customer.method.impl;

import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodProviderInformationRepository;
import com.sfl.pms.services.payment.customer.method.AbstractCustomerPaymentMethodProviderInformationService;
import com.sfl.pms.services.payment.customer.method.model.CustomerPaymentMethodProviderInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:26 AM
 */
public abstract class AbstractCustomerPaymentMethodProviderInformationServiceImpl<T extends CustomerPaymentMethodProviderInformation> implements AbstractCustomerPaymentMethodProviderInformationService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomerPaymentMethodProviderInformationServiceImpl.class);

    /* Public methods */

    @Nonnull
    @Override
    public T getCustomerPaymentMethodProviderInformationById(@Nonnull final Long paymentMethodProviderInformationId) {
        Assert.notNull(paymentMethodProviderInformationId, "Payment method provider information should not be null");
        LOGGER.debug("Retrieving customer payment method provider information by id - {}", paymentMethodProviderInformationId);
        final T customerPaymentMethodProviderInformation = getRepository().findOne(paymentMethodProviderInformationId);
        LOGGER.debug("Successfully retrieved payment method provider information - {}", customerPaymentMethodProviderInformation);
        return customerPaymentMethodProviderInformation;
    }

    /* Constructors */
    public AbstractCustomerPaymentMethodProviderInformationServiceImpl() {
        LOGGER.debug("Initializing abstract customer payment method provider information service");
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentMethodProviderInformationRepository<T> getRepository();
}
