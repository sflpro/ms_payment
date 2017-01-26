package com.sfl.pms.services.payment.common.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.common.model.PaymentResultStatus;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/13/15
 * Time: 6:19 PM
 */
public class PaymentResultDto<T extends PaymentResult> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = -519780568173915232L;

    /* Properties */
    private PaymentResultStatus status;

    private final PaymentProviderType type;


    /* Constructors */
    public PaymentResultDto(final PaymentProviderType type, final PaymentResultStatus status) {
        this(type);
        this.status = status;
    }

    public PaymentResultDto(final PaymentProviderType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public PaymentResultStatus getStatus() {
        return status;
    }

    public void setStatus(final PaymentResultStatus status) {
        this.status = status;
    }

    public PaymentProviderType getType() {
        return type;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T paymentResult) {
        paymentResult.setStatus(getStatus());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentResultDto)) {
            return false;
        }
        final PaymentResultDto that = (PaymentResultDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getStatus(), that.getStatus());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getStatus());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("status", this.getStatus());
        return builder.build();
    }

}
