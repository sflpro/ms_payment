package com.sfl.pms.services.payment.encryption.adyen.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.payment.encryption.adyen.AdyenPaymentCardEncryptionService;
import com.sfl.pms.services.payment.encryption.dto.PaymentCardEncryptionInformationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/21/15
 * Time: 1:20 AM
 */
@Service
public class AdyenPaymentCardEncryptionServiceImpl implements AdyenPaymentCardEncryptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenPaymentCardEncryptionServiceImpl.class);

    /* Dependencies */
    @Value("#{ appProperties['adyen.encryption.key.public']}")
    private String adyenPublicKey;


    /* Constructors */
    public AdyenPaymentCardEncryptionServiceImpl() {
        LOGGER.debug("Initializing Adyen payment card encryption service");
    }


    @Nonnull
    @Override
    public String encryptPaymentCardInformation(@Nonnull final PaymentCardEncryptionInformationDto paymentCardEncryptionInformationDto) {
        assertPaymentCardEncryptionInformationDto(paymentCardEncryptionInformationDto);
        LOGGER.debug("Starting encryption for payment card - {}", paymentCardEncryptionInformationDto);
        final String cardJsonData = createJsonStringForEncryptedCardData(paymentCardEncryptionInformationDto);
        final String result = encryptCardData(cardJsonData);
        LOGGER.debug("Successfully created encryption result - {}", result);
        return result;
    }

    /* Utility methods */
    private String encryptCardData(final String cardJsonData) {
        final AdyenEncrypter adyenEncrypter = new AdyenEncrypter(adyenPublicKey);
        return adyenEncrypter.encrypt(cardJsonData);
    }

    private String createJsonStringForEncryptedCardData(final PaymentCardEncryptionInformationDto paymentCardEncryptionInformationDto) {
        final Map<String, Object> jsonMap = new LinkedHashMap<>();
        // Create simple date format
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        jsonMap.put("generationtime", dateFormat.format(new Date()));
        jsonMap.put("number", paymentCardEncryptionInformationDto.getNumber());
        jsonMap.put("holderName", paymentCardEncryptionInformationDto.getHolderName());
        jsonMap.put("cvc", paymentCardEncryptionInformationDto.getCvc());
        jsonMap.put("expiryMonth", paymentCardEncryptionInformationDto.getExpiryMonth());
        jsonMap.put("expiryYear", paymentCardEncryptionInformationDto.getExpiryYear());
        // Create JSON object string
        return serializeDataMapIntoJson(jsonMap);
    }

    private String serializeDataMapIntoJson(final Map<String, Object> dataSourceMap) {
        try {
            // Create object mapper
            final ObjectMapper objectMapper = new ObjectMapper();
            // Read
            final String jsonString = objectMapper.writeValueAsString(dataSourceMap);
            LOGGER.debug("Successfully created json object - {}", jsonString);
            return jsonString;
        } catch (final Exception ex) {
            throw new ServicesRuntimeException(ex);
        }
    }

    private void assertPaymentCardEncryptionInformationDto(final PaymentCardEncryptionInformationDto paymentCardEncryptionInformationDto) {
        Assert.notNull(paymentCardEncryptionInformationDto, "Payment card encrypted information should not be null");
        Assert.notNull(paymentCardEncryptionInformationDto.getHolderName(), "Holder name in payment card encrypted information should not be null");
        Assert.notNull(paymentCardEncryptionInformationDto.getNumber(), "Card number in payment card encrypted information should not be null");
        Assert.notNull(paymentCardEncryptionInformationDto.getCvc(), "CVC in payment card encrypted information should not be null");
    }

    /* Properties getters and setters */
    public String getAdyenPublicKey() {
        return adyenPublicKey;
    }

    public void setAdyenPublicKey(final String adyenPublicKey) {
        this.adyenPublicKey = adyenPublicKey;
    }
}
