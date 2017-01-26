package com.sfl.pms.services.payment.common.dto.order.request;

import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethodType;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestRedirectPaymentMethod;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 11:42 AM
 */
public class OrderRequestRedirectPaymentMethodDto extends OrderRequestPaymentMethodDto<OrderRequestRedirectPaymentMethod> {

    private static final long serialVersionUID = -2468637713077886947L;

    /* Properties */
    private PaymentMethodType paymentMethodType;

    private PaymentProviderType paymentProviderType;

    /* Constructors */
    public OrderRequestRedirectPaymentMethodDto() {
        super(OrderRequestPaymentMethodType.REDIRECT_PAYMENT_METHOD);
    }

    public OrderRequestRedirectPaymentMethodDto(final PaymentMethodType paymentMethodType, final PaymentProviderType paymentProviderType) {
        this();
        this.paymentMethodType = paymentMethodType;
        this.paymentProviderType = paymentProviderType;
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    /* Public interface methods */

    @Override
    public void updateDomainEntityProperties(final OrderRequestRedirectPaymentMethod paymentMethod) {
        super.updateDomainEntityProperties(paymentMethod);
        paymentMethod.setPaymentMethodType(getPaymentMethodType());
        paymentMethod.setPaymentProviderType(getPaymentProviderType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestRedirectPaymentMethodDto)) {
            return false;
        }
        final OrderRequestRedirectPaymentMethodDto that = (OrderRequestRedirectPaymentMethodDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodType(), that.getPaymentMethodType());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodType());
        builder.append(this.getPaymentProviderType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodType", this.getPaymentMethodType());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        return builder.build();
    }

}
