package com.sfl.pms.services.payment.method.dto.group;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.dto.PaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinitionType;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:50 PM
 */
public class GroupPaymentMethodDefinitionDto extends PaymentMethodDefinitionDto<GroupPaymentMethodDefinition> {

    private static final long serialVersionUID = 3847455009192516240L;

    /* Properties */
    private PaymentMethodGroupType paymentMethodGroupType;

    /* Constructors */
    public GroupPaymentMethodDefinitionDto() {
        super(PaymentMethodDefinitionType.GROUP);
    }

    public GroupPaymentMethodDefinitionDto(final PaymentMethodGroupType paymentMethodGroupType, final BigDecimal authorizationSurcharge, final BigDecimal paymentSurcharge, final Currency currency, final PaymentProviderType paymentProviderType, final boolean storeEnabled) {
        super(PaymentMethodDefinitionType.GROUP, authorizationSurcharge, paymentSurcharge, currency, paymentProviderType, storeEnabled);
        this.paymentMethodGroupType = paymentMethodGroupType;
    }

    /* Properties getters and setters */
    public PaymentMethodGroupType getPaymentMethodGroupType() {
        return paymentMethodGroupType;
    }

    public void setPaymentMethodGroupType(final PaymentMethodGroupType paymentMethodGroupType) {
        this.paymentMethodGroupType = paymentMethodGroupType;
    }

    /* Public interface methods */

    @Override
    public void updateDomainEntityProperties(final GroupPaymentMethodDefinition paymentMethodDefinition) {
        super.updateDomainEntityProperties(paymentMethodDefinition);
        paymentMethodDefinition.setPaymentMethodGroupType(getPaymentMethodGroupType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupPaymentMethodDefinitionDto)) {
            return false;
        }
        final GroupPaymentMethodDefinitionDto that = (GroupPaymentMethodDefinitionDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodGroupType(), that.getPaymentMethodGroupType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodGroupType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodGroupType", this.getPaymentMethodGroupType());
        return builder.build();
    }
}
