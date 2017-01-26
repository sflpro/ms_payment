package com.sfl.pms.services.payment.customer.information.impl;

import com.sfl.pms.persistence.repositories.payment.customer.information.AbstractCustomerPaymentProviderInformationRepository;
import com.sfl.pms.services.payment.customer.information.AbstractCustomerPaymentProviderInformationService;
import com.sfl.pms.services.payment.customer.information.exception.CustomerPaymentProviderInformationNotFoundForIdException;
import com.sfl.pms.services.payment.customer.information.model.CustomerPaymentProviderInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/10/15
 * Time: 11:56 AM
 */
public abstract class AbstractCustomerPaymentProviderInformationServiceImpl<T extends CustomerPaymentProviderInformation> implements AbstractCustomerPaymentProviderInformationService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomerPaymentProviderInformationServiceImpl.class);

    /* Constructors */
    public AbstractCustomerPaymentProviderInformationServiceImpl() {
        LOGGER.debug("Initializing abstract customer payment provider information");
    }

    @Nonnull
    @Override
    public T getCustomerPaymentProviderInformationById(@Nonnull final Long informationId) {
        assertPaymentProviderInformationIdIsNotNull(informationId);
        LOGGER.debug("Getting customer payment provider information for informationId - {}, information class - {}", informationId, getInstanceClass());
        final T information = getRepository().findOne(informationId);
        assertPaymentProviderInformationNotNull(information, informationId);
        return information;
    }

    /* Utility methods */
    private void assertPaymentProviderInformationNotNull(final T information, final Long informationId) {
        if (information == null) {
            LOGGER.error("No customer payment provider information was found for informationId - {} and class - {}", informationId, getInstanceClass());
            throw new CustomerPaymentProviderInformationNotFoundForIdException(informationId, getInstanceClass());
        }
    }

    private void assertPaymentProviderInformationIdIsNotNull(final Long informationById) {
        Assert.notNull(informationById, "Customer payment provider information ID should not be null");
    }

    /* Abstract methods */
    protected abstract AbstractCustomerPaymentProviderInformationRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();
}
