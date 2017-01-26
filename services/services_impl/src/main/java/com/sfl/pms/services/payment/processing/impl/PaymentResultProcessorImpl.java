package com.sfl.pms.services.payment.processing.impl;

import com.sfl.pms.services.payment.common.PaymentService;
import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentState;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDetailedInformationDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultDto;
import com.sfl.pms.services.payment.processing.dto.PaymentProcessingResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 5/4/15
 * Time: 12:13 PM
 */
@Component
public class PaymentResultProcessorImpl extends AbstractPaymentProcessorImpl implements PaymentResultProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResultProcessorImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentService paymentService;

    /* Constructors */
    public PaymentResultProcessorImpl() {
        LOGGER.debug("Initializing payment result processor");
    }

    @Transactional
    @Override
    public PaymentProcessingResultDto processPaymentResult(@Nonnull final Long paymentId, final Long notificationId, final Long redirectResultId, @Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        Assert.notNull(paymentId, "Payment should not be null");
        Payment payment = paymentService.getPaymentByIdWithPessimisticWriteLock(paymentId);
        payment = getPersistenceUtilityService().initializeAndUnProxy(payment);
        // Update result on payment
        PaymentResult paymentResult = null;
        PaymentProcessingResultType resultType = null;
        if (paymentResultDto != null) {
            final PaymentProviderOperationsProcessor providerSpecificOperationsProcessor = getPaymentProviderProcessor(payment.getPaymentProviderType());
            final boolean paymentResultAlreadyExists = providerSpecificOperationsProcessor.checkIfPaymentResultAlreadyExists(payment, paymentResultDto);
            if (paymentResultAlreadyExists) {
                resultType = PaymentProcessingResultType.RESULT_ALREADY_EXISTED;
                LOGGER.debug("Payment result with similar fields as - {} already exists for payment with id - {}, skipping processing step. Notification id - {}, redirect result id - {}", paymentResultDto, payment.getId(), notificationId, redirectResultId);
            } else {
                LOGGER.debug("Payment result - {} does not exist yet for payment with id - {}, updating result on payment. Notification id - {}, redirect result id - {}", paymentResultDto, payment.getId(), notificationId, redirectResultId);
                paymentResult = updateResultOnPayment(payment.getId(), notificationId, redirectResultId, paymentResultDto);
            }
        }
        LOGGER.debug("Successfully set payment result for payment with id - {}, payment result - {}. Notification id - {}, redirect result id - {}", payment.getId(), paymentResult, notificationId, redirectResultId);
        // Process payment result only if payment is not paid already and if we do not have it processed once, in all other cases (even if result is null) it still has to be processed
        final PaymentState paymentState = payment.getLastState();
        final PaymentProcessingResultDto paymentProcessingResultDto;
        if (PaymentProcessingResultType.RESULT_ALREADY_EXISTED.equals(resultType)) {
            final PaymentTypeSpecificOperationsProcessor processor = getPaymentTypeSpecificOperationsProcessor(payment.getType());
            final PaymentProcessingResultDetailedInformationDto paymentProcessingResultDetailedInformationDto = processor.processDuplicatePaymentResult(payment, paymentResultDto);
            LOGGER.debug("Successfully processed duplicate result for payment with id - {}. Processing result - {}. Notification id - {}, redirect result id - {}", payment.getId(), paymentProcessingResultDetailedInformationDto, notificationId, redirectResultId);
            paymentProcessingResultDto = new PaymentProcessingResultDto(paymentProcessingResultDetailedInformationDto, PaymentProcessingResultType.RESULT_ALREADY_EXISTED);
        } else if (!PaymentState.PAID.equals(paymentState)) {
            LOGGER.debug("Payment with id - {} has payment state - {}, processing payment result - {}", paymentId, paymentState, paymentResult);
            final PaymentTypeSpecificOperationsProcessor processor = getPaymentTypeSpecificOperationsProcessor(payment.getType());
            final PaymentProcessingResultDetailedInformationDto paymentProcessingResultDetailedInformationDto = processor.processPaymentResult(payment, paymentResult);
            LOGGER.debug("Successfully retrieved payment processing result for payment with id - {}. Processing result - {}. Notification id - {}, redirect result id - {}", payment.getId(), paymentProcessingResultDetailedInformationDto, notificationId, redirectResultId);
            paymentProcessingResultDto = new PaymentProcessingResultDto(paymentProcessingResultDetailedInformationDto, PaymentProcessingResultType.RESULT_PROCESSED);
        } else {
            paymentProcessingResultDto = new PaymentProcessingResultDto(null, resultType);
        }
        return paymentProcessingResultDto;
    }

    /* Utility methods */
    private PaymentResult updateResultOnPayment(final Long paymentId, final Long notificationId, final Long redirectResultId, final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        final PaymentResult paymentResult = getPaymentService().createPaymentResultForPayment(paymentId, notificationId, redirectResultId, paymentResultDto);
        LOGGER.debug("Retrieved following result after updating result on payment for id - {}, payment result - {}", paymentId, paymentResult);
        return paymentResult;
    }

    /* Properties getters and setters */
    @Override
    public PaymentService getPaymentService() {
        return paymentService;
    }

    @Override
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
