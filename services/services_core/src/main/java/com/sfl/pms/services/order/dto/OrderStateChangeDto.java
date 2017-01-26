package com.sfl.pms.services.order.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: Ruben Vardanyan
 * Company: SFL LLC
 * Date: 4/25/16
 * Time: 4:57 PM
 */
public class OrderStateChangeDto implements Serializable {
    private static final long serialVersionUID = -5147128498526652041L;

    /* Properties */
    private OrderStateChangeHistoryRecordDto orderStateChangeHistoryRecordDto;

    private String paymentUuid;

    private Long orderId;


    /* Constructor */
    public OrderStateChangeDto() {
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStateChangeDto)) {
            return false;
        }
        final OrderStateChangeDto that = (OrderStateChangeDto) o;
        return new EqualsBuilder()
                .append(orderStateChangeHistoryRecordDto, that.orderStateChangeHistoryRecordDto)
                .append(paymentUuid, that.paymentUuid)
                .append(orderId, that.orderId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(orderStateChangeHistoryRecordDto)
                .append(paymentUuid)
                .append(orderId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("orderStateChangeHistoryRecordDto", orderStateChangeHistoryRecordDto)
                .append("paymentUuid", paymentUuid)
                .append("orderId", orderId)
                .toString();
    }

    /* Properties getters and setters */
    public OrderStateChangeHistoryRecordDto getOrderStateChangeHistoryRecordDto() {
        return orderStateChangeHistoryRecordDto;
    }

    public void setOrderStateChangeHistoryRecordDto(final OrderStateChangeHistoryRecordDto orderStateChangeHistoryRecordDto) {
        this.orderStateChangeHistoryRecordDto = orderStateChangeHistoryRecordDto;
    }

    public String getPaymentUuid() {
        return paymentUuid;
    }

    public void setPaymentUuid(final String paymentUuid) {
        this.paymentUuid = paymentUuid;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
