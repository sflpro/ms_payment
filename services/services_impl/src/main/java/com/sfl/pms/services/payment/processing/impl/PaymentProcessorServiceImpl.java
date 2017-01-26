package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.processing.PaymentProcessorService;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDto;
import com.sfl.pms.services.payment.processing.dto.redirect.PaymentRedirectProcessingInformationDto;
import com.sfl.pms.services.payment.provider.model.PaymentProviderIntegrationType;
import com.sfl.pms.services.util.mutable.MutableHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/1/15
 * Time: 9:29 PM
 */
@Service
public class PaymentProcessorServiceImpl extends AbstractPaymentProcessorImpl implements PaymentProcessorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessorServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentResultProcessor paymentResultProcessor;

    /* Constructors */
    public PaymentProcessorServiceImpl() {
        LOGGER.debug("Initializing payment processor service");
    }

    @Override
    public PaymentProcessingResultDetailedInformationDto processPayment(@Nonnull final Long paymentId) {
        Assert.notNull(paymentId, "Payment id should not be null");
        LOGGER.debug("Starting processing for payment with id - {}", paymentId);
        final Payment payment = loadPayment(paymentId);
        // Update payment state
        updatePaymentState(payment.getId(), PaymentState.STARTED_PROCESSING, null);
        try {
            // Process payment result
            final PaymentProcessingResultDetailedInformationDto resultDto;
            final PaymentProviderIntegrationType paymentProviderIntegrationType = payment.getPaymentProcessingChannel().getPaymentProviderIntegrationType();
            if (PaymentProviderIntegrationType.API.equals(paymentProviderIntegrationType)) {
                resultDto = processPaymentApiIntegration(payment);
            } else if (PaymentProviderIntegrationType.REDIRECT.equals(paymentProviderIntegrationType)) {
                resultDto = processPaymentRedirectIntegration(payment);
            } else {
                throw new ServicesRuntimeException("Unknown payment provider integration type - " + paymentProviderIntegrationType);
            }
            LOGGER.debug("Retrieved following result during processing of payment with id - {}, result - {}", paymentId, resultDto);
            return resultDto;
        } catch (final Exception ex) {
            LOGGER.error("Error occurred while processing payment for id - " + paymentId, ex);
            updatePaymentState(payment.getId(), PaymentState.FAILED, null);
            throw new ServicesRuntimeException(ex);
        }
    }

    /* Utility methods */
    private PaymentProcessingResultDetailedInformationDto processPaymentApiIntegration(final Payment payment) {
        // Process payment transaction
        final PaymentResultDto<? extends PaymentResult> paymentResultDto = processPaymentTransaction(payment);
        // Process payment result
        final PaymentProcessingResultDetailedInformationDto resultDto = processPaymentResult(payment, paymentResultDto);
        LOGGER.debug("Retrieved following result during processing of payment with id - {}, result - {}", payment.getId(), resultDto);
        return resultDto;
    }

    private PaymentProcessingResultDetailedInformationDto processPaymentRedirectIntegration(final Payment payment) {
        final String redirectUrl = getPaymentTypeSpecificOperationsProcessor(payment.getType()).generatePaymentRedirectUrl(payment);
        updatePaymentState(payment.getId(), PaymentState.GENERATED_REDIRECT_URL, null);
        return new PaymentRedirectProcessingInformationDto(payment.getId(), redirectUrl);
    }

    private PaymentProcessingResultDetailedInformationDto processPaymentResult(final Payment payment, final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        final MutableHolder<PaymentProcessingResultDetailedInformationDto> resultDtoMutableHolder = new MutableHolder<>(null);
        getPersistenceUtilityService().runInNewTransaction(() -> {
            final PaymentProcessingResultDto resultDto = paymentResultProcessor.processPaymentResult(payment.getId(), null, null, paymentResultDto);
            resultDtoMutableHolder.setValue(resultDto.getInformationDto());
        });
        return resultDtoMutableHolder.getValue();
    }

    private PaymentResultDto<? extends PaymentResult> processPaymentTransaction(final Payment payment) {
        final MutableHolder<PaymentResultDto<? extends PaymentResult>> paymentResultDtoMutableHolder = new MutableHolder<>(null);
        getPersistenceUtilityService().runInNewTransaction(() -> {
            Payment reloadedPayment = getPaymentService().getPaymentById(payment.getId());
            reloadedPayment = getPersistenceUtilityService().initializeAndUnProxy(reloadedPayment);
            final PaymentTypeSpecificOperationsProcessor paymentTypeSpecificOperationsProcessor = getPaymentTypeSpecificOperationsProcessor(payment.getType());
            final PaymentResultDto<? extends PaymentResult> paymentResultDto = paymentTypeSpecificOperationsProcessor.processPaymentApiTransaction(reloadedPayment);
            paymentResultDtoMutableHolder.setValue(paymentResultDto);
        });
        return paymentResultDtoMutableHolder.getValue();
    }

    /* Properties getters and setters */
    public PaymentResultProcessor getPaymentResultProcessor() {
        return paymentResultProcessor;
    }

    public void setPaymentResultProcessor(final PaymentResultProcessor paymentResultProcessor) {
        this.paymentResultProcessor = paymentResultProcessor;
    }
}
