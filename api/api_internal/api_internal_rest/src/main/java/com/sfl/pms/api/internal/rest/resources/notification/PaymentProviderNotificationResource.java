package com.sfl.pms.api.internal.rest.resources.notification;

import com.sfl.pms.api.internal.facade.notification.PaymentProviderNotificationFacade;
import com.sfl.pms.api.internal.facade.notification.exception.InvalidPaymentProviderNotificationsTokenException;
import com.sfl.pms.core.api.internal.model.common.result.ResultResponseModel;
import com.sfl.pms.core.api.internal.model.notification.request.CreatePaymentProviderNotificationRequest;
import com.sfl.pms.core.api.internal.model.notification.response.CreatePaymentProviderNotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/24/15
 * Time: 7:10 PM
 */
@Component
@Path("provider/notification")
@Produces("application/json")
public class PaymentProviderNotificationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderNotificationResource.class);

    /* Dependencies */
    @Autowired
    private PaymentProviderNotificationFacade paymentProviderNotificationFacade;

    /* Constructors */
    public PaymentProviderNotificationResource() {
        LOGGER.debug("Initializing Adyen payment notifications resource");
    }

    @POST
    @Path("/create")
    public Response processAdyenPaymentNotification(final CreatePaymentProviderNotificationRequest request) {
        try {
            LOGGER.debug("Got payment provider notification, request - {}", request);
            final ResultResponseModel<CreatePaymentProviderNotificationResponse> result = paymentProviderNotificationFacade.createPaymentProviderNotificationRequest(request);
            LOGGER.debug("Successfully retrieved result - {}, for request - {}", result, request);
            return Response.ok(result).build();
        } catch (final InvalidPaymentProviderNotificationsTokenException ex) {
            LOGGER.error("Invalid payment provider notifications token exception occurred", ex);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (final Exception ex) {
            LOGGER.error("Error occurred while processing payment provider notification request", ex);
            return Response.serverError().build();
        }
    }

}
