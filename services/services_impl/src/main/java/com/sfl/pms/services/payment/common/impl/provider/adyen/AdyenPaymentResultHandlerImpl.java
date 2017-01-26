package com.sfl.pms.services.payment.common.impl.provider.adyen;

import com.sfl.pms.services.payment.common.dto.PaymentResultDto;
import com.sfl.pms.services.payment.common.dto.adyen.AdyenPaymentResultDto;
import com.sfl.pms.services.payment.common.impl.provider.PaymentResultHandler;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.adyen.AdyenPaymentResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/14/15
 * Time: 8:51 PM
 */
@Component(value = "adyenPaymentResultHandler")
@SuppressWarnings("unchecked")
public class AdyenPaymentResultHandlerImpl implements PaymentResultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenPaymentResultHandlerImpl.class);

    /* Constructors */
    public AdyenPaymentResultHandlerImpl() {
        LOGGER.debug("Initializing Adyen payment result handler");
    }

    @Override
    public void assertPaymentResultDto(@Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        Assert.notNull(paymentResultDto, "Payment result DTO should not be null");
        Assert.isInstanceOf(AdyenPaymentResultDto.class, paymentResultDto);
        final AdyenPaymentResultDto adyenPaymentResultDto = (AdyenPaymentResultDto) paymentResultDto;
        Assert.notNull(adyenPaymentResultDto.getStatus(), "Status in payment result DTO should not be null");
        Assert.notNull(adyenPaymentResultDto.getResultCode(), "Result code in payment result DTO should not be null");

    }

    @Nonnull
    @Override
    public PaymentResult convertPaymentResultDto(@Nonnull final PaymentResultDto<? extends PaymentResult> paymentResultDto) {
        assertPaymentResultDto(paymentResultDto);
        final AdyenPaymentResultDto adyenPaymentResultDto = (AdyenPaymentResultDto) paymentResultDto;
        // Create payment result
        final AdyenPaymentResult paymentResult = new AdyenPaymentResult();
        adyenPaymentResultDto.updateDomainEntityProperties(paymentResult);
        return paymentResult;
    }
}
