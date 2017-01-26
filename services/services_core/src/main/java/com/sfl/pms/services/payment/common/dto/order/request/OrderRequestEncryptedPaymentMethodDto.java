package com.sfl.pms.services.payment.common.dto.order.request;

import com.sfl.pms.services.payment.common.model.order.request.OrderRequestEncryptedPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethodType;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
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
public class OrderRequestEncryptedPaymentMethodDto extends OrderRequestPaymentMethodDto<OrderRequestEncryptedPaymentMethod> {

    private static final long serialVersionUID = -2468637713077886947L;

    /* Properties */
    private String encryptedData;

    private PaymentProviderType paymentProviderType;

    private PaymentMethodGroupType paymentMethodGroupType;

    /* Constructors */
    public OrderRequestEncryptedPaymentMethodDto() {
        super(OrderRequestPaymentMethodType.ENCRYPTED_PAYMENT_METHOD);
    }

    public OrderRequestEncryptedPaymentMethodDto(final String encryptedData, final PaymentProviderType paymentProviderType, final PaymentMethodGroupType paymentMethodGroupType) {
        this();
        this.paymentProviderType = paymentProviderType;
        this.encryptedData = encryptedData;
        this.paymentMethodGroupType = paymentMethodGroupType;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final OrderRequestEncryptedPaymentMethod paymentMethod) {
        super.updateDomainEntityProperties(paymentMethod);
        paymentMethod.setPaymentProviderType(getPaymentProviderType());
        paymentMethod.setEncryptedData(getEncryptedData());
        paymentMethod.setPaymentMethodGroupType(getPaymentMethodGroupType());
    }

    /* Properties getters and setters */
    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(final String paymentMethodEncryptedInformation) {
        this.encryptedData = paymentMethodEncryptedInformation;
    }

    public PaymentProviderType getPaymentProviderType() {
        return paymentProviderType;
    }

    public void setPaymentProviderType(final PaymentProviderType paymentProviderType) {
        this.paymentProviderType = paymentProviderType;
    }

    public PaymentMethodGroupType getPaymentMethodGroupType() {
        return paymentMethodGroupType;
    }

    public void setPaymentMethodGroupType(final PaymentMethodGroupType paymentMethodGroupType) {
        this.paymentMethodGroupType = paymentMethodGroupType;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestEncryptedPaymentMethodDto)) {
            return false;
        }
        final OrderRequestEncryptedPaymentMethodDto that = (OrderRequestEncryptedPaymentMethodDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getEncryptedData(), that.getEncryptedData());
        builder.append(this.getPaymentProviderType(), that.getPaymentProviderType());
        builder.append(this.getPaymentMethodGroupType(), that.getPaymentMethodGroupType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getEncryptedData());
        builder.append(this.getPaymentProviderType());
        builder.append(this.getPaymentMethodGroupType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodEncryptedInformation", this.getEncryptedData());
        builder.append("paymentProviderType", this.getPaymentProviderType());
        builder.append("paymentMethodGroupType", this.getPaymentMethodGroupType());
        return builder.build();
    }

}
