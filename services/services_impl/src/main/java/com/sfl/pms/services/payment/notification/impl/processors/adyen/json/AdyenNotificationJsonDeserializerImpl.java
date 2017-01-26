package com.sfl.pms.services.payment.notification.impl.processors.adyen.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfl.pms.externalclients.common.http.exception.ExternalClientRuntimeException;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationJsonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 12:20 PM
 */
@Component
public class AdyenNotificationJsonDeserializerImpl implements AdyenNotificationJsonDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenNotificationJsonDeserializerImpl.class);

    /* Constructors */
    public AdyenNotificationJsonDeserializerImpl() {
        LOGGER.debug("Initializing Adyen notification Json deserializer");
    }

    @Nonnull
    @Override
    public AdyenNotificationJsonModel deserializeAdyenNotification(@Nonnull final String notificationRawContent) {
        Assert.notNull(notificationRawContent, "Notification raw content should not be null");
        LOGGER.debug("DeSerializing Adyen notification raw content  - {}", notificationRawContent);
        final AdyenNotificationJsonModel notificationJsonModel = deserializeJson(notificationRawContent, AdyenNotificationJsonModel.class);
        LOGGER.debug("Adyen notification deSerialization result is - {}, raw content - {}", notificationRawContent, notificationJsonModel);
        return notificationJsonModel;
    }

    /* Utility methods */
    protected <T> T deserializeJson(final String jsonString, final Class<T> resultClass) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final T result = objectMapper.readValue(jsonString, resultClass);
            LOGGER.debug("Successfully deSerialized jsonString - {} to object - {}", jsonString, result);
            return result;
        } catch (final Exception ex) {
            LOGGER.error("Error occurred while deSerializing JSON string - {}", jsonString, ex);
            throw new ExternalClientRuntimeException(ex);
        }
    }
}
