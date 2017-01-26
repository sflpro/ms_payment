package com.sfl.pms.services.payment.encryption.adyen;

import com.sfl.pms.services.payment.encryption.dto.PaymentCardEncryptionInformationDto;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/21/15
 * Time: 1:19 AM
 */
public interface AdyenPaymentCardEncryptionService {

    /**
     * Encrypts payment card information
     *
     * @param paymentCardEncryptionInformationDto
     * @return encryptedData
     */
    @Nonnull
    String encryptPaymentCardInformation(@Nonnull final PaymentCardEncryptionInformationDto paymentCardEncryptionInformationDto);
}
