package com.sfl.pms.services.payment.common.dto.order.request;

import com.sfl.pms.services.payment.common.model.order.request.OrderRequestCustomerPaymentMethod;
import com.sfl.pms.services.payment.common.model.order.request.OrderRequestPaymentMethodType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/20/15
 * Time: 11:47 AM
 */
public class OrderRequestCustomerPaymentMethodDto extends OrderRequestPaymentMethodDto<OrderRequestCustomerPaymentMethod> {

    private static final long serialVersionUID = -915007037773560797L;

    /* Properties */
    private Long customerPaymentMethodId;

    /* Constructors */
    public OrderRequestCustomerPaymentMethodDto() {
        super(OrderRequestPaymentMethodType.CUSTOMER_PAYMENT_METHOD);
    }

    public OrderRequestCustomerPaymentMethodDto(final Long customerPaymentMethodId) {
        this();
        this.customerPaymentMethodId = customerPaymentMethodId;
    }

    /* Properties getters and setters */
    public Long getCustomerPaymentMethodId() {
        return customerPaymentMethodId;
    }

    public void setCustomerPaymentMethodId(final Long customerPaymentMethodId) {
        this.customerPaymentMethodId = customerPaymentMethodId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequestCustomerPaymentMethodDto)) {
            return false;
        }
        final OrderRequestCustomerPaymentMethodDto that = (OrderRequestCustomerPaymentMethodDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getCustomerPaymentMethodId(), that.getCustomerPaymentMethodId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getCustomerPaymentMethodId());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("customerPaymentMethodId", this.getCustomerPaymentMethodId());
        return builder.build();
    }
}
