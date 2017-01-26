package com.sfl.pms.services.payment.common.dto.order;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.common.dto.PaymentDto;
import com.sfl.pms.services.payment.common.model.PaymentType;
import com.sfl.pms.services.payment.common.model.order.OrderPayment;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 12:51 PM
 */
public class OrderPaymentDto extends PaymentDto<OrderPayment> {
    private static final long serialVersionUID = -3915240081322109791L;

    /* Constructors */
    public OrderPaymentDto(final PaymentProviderType paymentProviderType, final BigDecimal amount, final BigDecimal paymentMethodSurcharge, final Currency currency, final String clientIpAddress, final boolean storePaymentMethod) {
        super(PaymentType.ORDER, paymentProviderType, amount, paymentMethodSurcharge, currency, clientIpAddress, storePaymentMethod);
    }

    public OrderPaymentDto() {
        super(PaymentType.ORDER);
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final OrderPayment payment) {
        super.updateDomainEntityProperties(payment);
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentDto)) {
            return false;
        }
        final OrderPaymentDto that = (OrderPaymentDto) o;
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
