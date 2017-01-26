package com.sfl.pms.services.order.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.order.model.Order;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/16/16
 * Time: 6:45 PM
 */
public class OrderDto extends AbstractDomainEntityModelDto<Order> {

    private static final long serialVersionUID = -2113725219688276250L;

    /* Properties */
    private String uuId;

    private BigDecimal paymentTotalWithVat;

    private Currency currency;

    /* Constructors */
    public OrderDto() {
    }

    public OrderDto(final String uuId, final BigDecimal paymentTotalWithVat, final Currency currency) {
        this.uuId = uuId;
        this.paymentTotalWithVat = paymentTotalWithVat;
        this.currency = currency;
    }

    /* Properties getters and setters */
    public String getUuId() {
        return uuId;
    }

    public void setUuId(final String uuId) {
        this.uuId = uuId;
    }

    public BigDecimal getPaymentTotalWithVat() {
        return paymentTotalWithVat;
    }

    public void setPaymentTotalWithVat(final BigDecimal paymentTotalWithVat) {
        this.paymentTotalWithVat = paymentTotalWithVat;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    /* Validation methods */
    @Override
    public void updateDomainEntityProperties(final Order order) {
        Assert.notNull(order, "Order should not be null");
        order.setUuId(getUuId());
        order.setPaymentTotalWithVat(getPaymentTotalWithVat());
        order.setCurrency(getCurrency());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDto)) {
            return false;
        }
        final OrderDto that = (OrderDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getUuId(), that.getUuId());
        builder.append(AbstractDomainEntityModel.getDoubleValueOrNull(getPaymentTotalWithVat()), AbstractDomainEntityModel.getDoubleValueOrNull(that.getPaymentTotalWithVat()));
        builder.append(getCurrency(), that.getCurrency());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getUuId());
        builder.append(AbstractDomainEntityModel.getDoubleValueOrNull(getPaymentTotalWithVat()));
        builder.append(getCurrency());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("uuId", getUuId());
        builder.append("paymentTotalWithVat", getPaymentTotalWithVat());
        builder.append("currency", getCurrency());
        return builder.build();
    }
}
