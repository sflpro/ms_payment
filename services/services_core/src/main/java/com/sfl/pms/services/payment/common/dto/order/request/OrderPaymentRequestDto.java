package com.sfl.pms.services.payment.common.dto.order.request;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.common.model.order.request.OrderPaymentRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 2/4/15
 * Time: 1:06 PM
 */
public class OrderPaymentRequestDto extends AbstractDomainEntityModelDto<OrderPaymentRequest> {
    private static final long serialVersionUID = 6799808181072745796L;

    /* Properties */
    private String clientIpAddress;

    private boolean storePaymentMethod;

    /* Constructors */
    public OrderPaymentRequestDto() {
    }

    public OrderPaymentRequestDto(final String clientIpAddress, final boolean storePaymentMethod) {
        this.clientIpAddress = clientIpAddress;
        this.storePaymentMethod = storePaymentMethod;
    }

    /* Properties getters and setters */

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(final String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public boolean isStorePaymentMethod() {
        return storePaymentMethod;
    }

    public void setStorePaymentMethod(final boolean storePaymentMethod) {
        this.storePaymentMethod = storePaymentMethod;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final OrderPaymentRequest orderPaymentRequest) {
        orderPaymentRequest.setClientIpAddress(getClientIpAddress());
        orderPaymentRequest.setStorePaymentMethod(isStorePaymentMethod());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPaymentRequestDto)) {
            return false;
        }
        final OrderPaymentRequestDto that = (OrderPaymentRequestDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getClientIpAddress(), that.getClientIpAddress());
        builder.append(this.isStorePaymentMethod(), that.isStorePaymentMethod());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getClientIpAddress());
        builder.append(this.isStorePaymentMethod());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("clientIpAddress", this.getClientIpAddress());
        builder.append("storePaymentMethod", this.isStorePaymentMethod());
        return builder.build();
    }


}
