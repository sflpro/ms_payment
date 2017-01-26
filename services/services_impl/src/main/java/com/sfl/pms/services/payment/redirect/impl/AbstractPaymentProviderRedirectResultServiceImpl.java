package com.sfl.pms.services.payment.redirect.impl;

import com.sfl.pms.persistence.repositories.payment.redirect.AbstractPaymentProviderRedirectResultRepository;
import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.redirect.AbstractPaymentProviderRedirectResultService;
import com.sfl.pms.services.payment.redirect.exception.PaymentProviderRedirectResultNotFoundForIdException;
import com.sfl.pms.services.payment.redirect.exception.PaymentProviderRedirectResultNotFoundForUuidException;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:36 PM
 */
public abstract class AbstractPaymentProviderRedirectResultServiceImpl<T extends PaymentProviderRedirectResult> implements AbstractPaymentProviderRedirectResultService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPaymentProviderRedirectResultServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentService paymentService;

    /* Constructors */
    public AbstractPaymentProviderRedirectResultServiceImpl() {
        LOGGER.debug("Initializing abstract payment provider redirect result service");
    }

    @Nonnull
    @Override
    public T getPaymentProviderRedirectResultById(@Nonnull final Long redirectResultId) {
        assertPaymentProviderRedirectResultIdNotNull(redirectResultId);
        LOGGER.debug("Getting payment provider redirect result for id - {}, redirect class - {}", redirectResultId, getInstanceClass());
        final T redirectResult = getRepository().findOne(redirectResultId);
        assertPaymentProviderRedirectResultNotNullForId(redirectResult, redirectResultId);
        LOGGER.debug("Successfully retrieved payment provider redirect result for id - {}, class - {}, result - {}", redirectResultId, getInstanceClass(), redirectResult);
        return redirectResult;
    }

    @Nonnull
    @Override
    public T getPaymentProviderRedirectResultByUuId(@Nonnull final String uuId) {
        Assert.notNull(uuId, "Payment provider redirect result uuid should not be null");
        LOGGER.debug("Getting payment provider redirect result for uuid - {}, class - {}", uuId, getInstanceClass());
        final T redirectResult = getRepository().findByUuId(uuId);
        assertPaymentProviderRedirectResultNotNullForUuId(redirectResult, uuId);
        LOGGER.debug("Successfully retrieved payment provider redirect result for uuid - {}, class - {}, result - {}", uuId, getInstanceClass(), redirectResult);
        return redirectResult;
    }

    @Transactional
    @Nonnull
    @Override
    public T updatePaymentForRedirectResult(@Nonnull final Long redirectResultId, @Nonnull final Long paymentId) {
        assertPaymentProviderRedirectResultIdNotNull(redirectResultId);
        Assert.notNull(paymentId, "Payment id should not be null");
        LOGGER.debug("Updating payment for redirect result with id - {}. Payment id - {}", redirectResultId, paymentId);
        T redirectResult = getRepository().findOne(redirectResultId);
        assertPaymentProviderRedirectResultNotNullForId(redirectResult, redirectResultId);
        // Load payment
        final Payment payment = paymentService.getPaymentById(paymentId);
        redirectResult.setPayment(payment);
        redirectResult = getRepository().save(redirectResult);
        LOGGER.debug("Successfully updated payment for redirect result - {}, payment - {}", redirectResult, payment);
        return redirectResult;
    }

    /* Utility methods */
    protected void assertPaymentProviderRedirectResultIdNotNull(final Long redirectResultId) {
        Assert.notNull(redirectResultId, "Redirect result id should not be null");
    }

    protected void assertPaymentProviderRedirectResultNotNullForId(final T redirectResult, final Long redirectResultId) {
        if (redirectResult == null) {
            LOGGER.error("No payment provider redirect result was found for id - {}, class - {}", redirectResultId, getInstanceClass());
            throw new PaymentProviderRedirectResultNotFoundForIdException(redirectResultId, getInstanceClass());
        }
    }

    protected void assertPaymentProviderRedirectResultNotNullForUuId(final T redirectResult, final String uuId) {
        if (redirectResult == null) {
            LOGGER.error("No payment provider redirect result was found for uuid - {}, class - {}", uuId, getInstanceClass());
            throw new PaymentProviderRedirectResultNotFoundForUuidException(uuId, getInstanceClass());
        }
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderRedirectResultRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    /* Properties getters and setters */
    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
