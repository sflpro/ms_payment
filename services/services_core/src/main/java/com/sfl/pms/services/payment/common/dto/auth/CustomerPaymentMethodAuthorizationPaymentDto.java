package com.sfl.pms.services.payment.common.dto.auth;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.common.dto.PaymentDto;
import com.sfl.pms.services.payment.common.model.PaymentType;
import com.sfl.pms.services.payment.common.model.auth.CustomerPaymentMethodAuthorizationPayment;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:52 PM
 */
public class CustomerPaymentMethodAuthorizationPaymentDto extends PaymentDto<CustomerPaymentMethodAuthorizationPayment> {

    private static final long serialVersionUID = 6677191204909618881L;

    /* Constructors */
    public CustomerPaymentMethodAuthorizationPaymentDto(final PaymentProviderType paymentProviderType, final BigDecimal amount, final Currency currency, final String clientIpAddress) {
        super(PaymentType.PAYMENT_METHOD_AUTHORIZATION, paymentProviderType, amount, BigDecimal.ZERO, currency, clientIpAddress, true);
    }

    public CustomerPaymentMethodAuthorizationPaymentDto() {
        super(PaymentType.PAYMENT_METHOD_AUTHORIZATION, null, null, BigDecimal.ZERO, null, null, true);
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final CustomerPaymentMethodAuthorizationPayment payment) {
        super.updateDomainEntityProperties(payment);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPaymentMethodAuthorizationPaymentDto)) {
            return false;
        }
        final CustomerPaymentMethodAuthorizationPaymentDto that = (CustomerPaymentMethodAuthorizationPaymentDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        return builder.build();
    }
}
