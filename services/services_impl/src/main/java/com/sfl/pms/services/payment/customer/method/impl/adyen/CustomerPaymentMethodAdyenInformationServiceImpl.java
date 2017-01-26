package com.sfl.pms.services.payment.customer.method.impl.adyen;

import com.sfl.pms.persistence.repositories.payment.customer.method.adyen.CustomerPaymentMethodAdyenInformationRepository;
import com.sfl.pms.persistence.repositories.payment.customer.method.authorization.AbstractCustomerPaymentMethodProviderInformationRepository;
import com.sfl.pms.services.payment.customer.method.adyen.CustomerPaymentMethodAdyenInformationService;
import com.sfl.pms.services.payment.customer.method.exception.adyen.AdyenRecurringDetailReferenceAlreadyUsedException;
import com.sfl.pms.services.payment.customer.method.exception.adyen.PaymentMethodAdyenInformationNotFoundForDetailReferenceException;
import com.sfl.pms.services.payment.customer.method.exception.adyen.PaymentMethodAdyenInformationNotFoundForIdException;
import com.sfl.pms.services.payment.customer.method.impl.AbstractCustomerPaymentMethodProviderInformationServiceImpl;
import com.sfl.pms.services.payment.customer.method.model.adyen.CustomerPaymentMethodAdyenInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/15
 * Time: 11:28 AM
 */
@Service
public class CustomerPaymentMethodAdyenInformationServiceImpl extends AbstractCustomerPaymentMethodProviderInformationServiceImpl<CustomerPaymentMethodAdyenInformation> implements CustomerPaymentMethodAdyenInformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentMethodAdyenInformationServiceImpl.class);

    /* Dependencies */
    @Autowired
    private CustomerPaymentMethodAdyenInformationRepository customerPaymentMethodAdyenInformationRepository;

    /* Constructors */
    public CustomerPaymentMethodAdyenInformationServiceImpl() {
        LOGGER.debug("Initializing customer payment method Adyen information service");
    }

    @Override
    public boolean checkIfPaymentMethodInformationExistsForRecurringDetailReference(@Nonnull final String recurringDetailReference) {
        assertRecurringDetailReference(recurringDetailReference);
        LOGGER.debug("Checking Adyen payment method information exists for recurring detail reference - {}", recurringDetailReference);
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(recurringDetailReference);
        final boolean exists = (paymentMethodAdyenInformation != null);
        LOGGER.debug("Customer payment method Adyen information lookup result for detail reference - {} is - {}", recurringDetailReference, exists);
        return exists;
    }

    @Nonnull
    @Override
    public CustomerPaymentMethodAdyenInformation getPaymentMethodInformationByRecurringDetailReference(@Nonnull final String recurringDetailReference) {
        assertRecurringDetailReference(recurringDetailReference);
        LOGGER.debug("Getting customer payment method Adyen information by recurring detail reference - {}", recurringDetailReference);
        final CustomerPaymentMethodAdyenInformation paymentMethodAdyenInformation = customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(recurringDetailReference);
        assertCustomerPaymentMethodAdyenInformationNotNullForDetailReference(paymentMethodAdyenInformation, recurringDetailReference);
        LOGGER.debug("Successfully retrieved customer payment method Adyen information for recurring detail reference - {}, customer payment method Adyen information - {}", recurringDetailReference, paymentMethodAdyenInformation);
        return paymentMethodAdyenInformation;
    }

    @Transactional
    @Nonnull
    @Override
    public CustomerPaymentMethodAdyenInformation updatePaymentMethodInformationRecurringDetailReference(@Nonnull final Long informationId, @Nonnull final String recurringDetailReference) {
        Assert.notNull(informationId, "Payment method information id should not be null");
        assertRecurringDetailReference(recurringDetailReference);
        LOGGER.debug("Updating recurring details reference for customer payment method Adyen information with id - {}, reference - {}", informationId, recurringDetailReference);
        // Assert no Adyen information exists for provided reference
        assertNoCustomerPaymentMethodAdyenInformationExistsForReference(recurringDetailReference);
        // Load Adyen information and update
        CustomerPaymentMethodAdyenInformation information = customerPaymentMethodAdyenInformationRepository.findOne(informationId);
        assertCustomerPaymentMethodAdyenInformationNotNullForId(information, informationId);
        // Update details reference
        information.setRecurringDetailReference(recurringDetailReference);
        information = customerPaymentMethodAdyenInformationRepository.save(information);
        LOGGER.debug("Successfully updated recurring details reference for customer payment method Adyen information with id - {}, information - {}", information.getId(), information);
        return information;
    }

    /* Utility methods */
    private void assertNoCustomerPaymentMethodAdyenInformationExistsForReference(final String reference) {
        final CustomerPaymentMethodAdyenInformation adyenInformation = customerPaymentMethodAdyenInformationRepository.findByRecurringDetailReference(reference);
        if (adyenInformation != null) {
            LOGGER.error("Customer payment method Adyen information with id - {} already exists for reference - {}", adyenInformation.getId(), reference);
            throw new AdyenRecurringDetailReferenceAlreadyUsedException(reference, adyenInformation.getId());
        }
    }

    private void assertCustomerPaymentMethodAdyenInformationNotNullForDetailReference(final CustomerPaymentMethodAdyenInformation paymentMethodInformation, final String recurringDetailReference) {
        if (paymentMethodInformation == null) {
            LOGGER.error("No customer payment method Adyen information was found for recurring detail reference - {}", recurringDetailReference);
            throw new PaymentMethodAdyenInformationNotFoundForDetailReferenceException(recurringDetailReference);
        }
    }

    private void assertCustomerPaymentMethodAdyenInformationNotNullForId(final CustomerPaymentMethodAdyenInformation paymentMethodInformation, final Long id) {
        if (paymentMethodInformation == null) {
            LOGGER.error("No customer payment method Adyen information was found for id - {}", id);
            throw new PaymentMethodAdyenInformationNotFoundForIdException(id);
        }
    }

    private void assertRecurringDetailReference(final String recurringDetailReference) {
        Assert.notNull(recurringDetailReference, "Recurring detail reference should not be null");
    }

    @Override
    protected AbstractCustomerPaymentMethodProviderInformationRepository<CustomerPaymentMethodAdyenInformation> getRepository() {
        return customerPaymentMethodAdyenInformationRepository;
    }

    /* Dependencies getters and setters */
    public CustomerPaymentMethodAdyenInformationRepository getCustomerPaymentMethodAdyenInformationRepository() {
        return customerPaymentMethodAdyenInformationRepository;
    }

    public void setCustomerPaymentMethodAdyenInformationRepository(final CustomerPaymentMethodAdyenInformationRepository customerPaymentMethodAdyenInformationRepository) {
        this.customerPaymentMethodAdyenInformationRepository = customerPaymentMethodAdyenInformationRepository;
    }
}
