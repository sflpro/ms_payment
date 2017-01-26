package com.sfl.pms.core.api.internal.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.core.api.internal.model.common.AbstractApiModel;
import com.sfl.pms.core.api.internal.model.common.request.ValidatableRequest;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import com.sfl.pms.core.api.internal.model.currency.CurrencyClientType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/19/16
 * Time: 2:03 PM
 */
public class OrderModel extends AbstractApiModel implements ValidatableRequest {

    private static final long serialVersionUID = 2974452137506930594L;

    /* Properties */
    @JsonProperty("uuId")
    private String uuId;

    @JsonProperty("paymentTotalWithVat")
    private BigDecimal paymentTotalWithVat;

    @JsonProperty("currency")
    private CurrencyClientType currency;

    /* Constructors */
    public OrderModel() {
    }

    public OrderModel(final String uuId, final BigDecimal paymentTotalWithVat, final CurrencyClientType currency) {
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

    public CurrencyClientType getCurrency() {
        return currency;
    }

    public void setCurrency(final CurrencyClientType currency) {
        this.currency = currency;
    }

    /* Validation methods */
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(getUuId())) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_MISSING_UUID));
        }
        if (getPaymentTotalWithVat() == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_MISSING_PAYMENT_TOTAL_WITH_VAT));
        }
        if (getCurrency() == null) {
            errors.add(new ErrorResponseModel(ErrorType.ORDER_MISSING_CURRENCY));
        }
        return errors;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderModel)) {
            return false;
        }
        final OrderModel that = (OrderModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getUuId(), that.getUuId());
        builder.append(getPaymentTotalWithVat(), that.getPaymentTotalWithVat());
        builder.append(getCurrency(), that.getCurrency());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getUuId());
        builder.append(getPaymentTotalWithVat());
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
