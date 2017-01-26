package com.sfl.pms.services.payment.processing.impl.auth;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.common.dto.auth.CustomerPaymentMethodAuthorizationPaymentDto;
import com.sfl.pms.services.payment.customer.method.model.authorization.CustomerPaymentMethodAuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/9/15
 * Time: 5:10 PM
 */
public abstract class AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl.class);

    /* Constructors */
    public AbstractPaymentMethodAuthorizationRequestTypeOperationsProcessorImpl() {
    }

    /* Utility methods */
    protected CustomerPaymentMethodAuthorizationPaymentDto createPaymentDto(final CustomerPaymentMethodAuthorizationRequest authorizationRequest, final Currency currency, final BigDecimal paymentAmount) {
        final CustomerPaymentMethodAuthorizationPaymentDto paymentDto = new CustomerPaymentMethodAuthorizationPaymentDto(authorizationRequest.getPaymentProviderType(), paymentAmount, currency, authorizationRequest.getClientIpAddress());
        LOGGER.debug("Created payment DTO - {} for authorization request - {}", paymentDto, authorizationRequest);
        return paymentDto;
    }
}
