package com.sfl.pms.services.payment.method.dto.individual;

import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.dto.PaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinitionType;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
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
public class IndividualPaymentMethodDefinitionDto extends PaymentMethodDefinitionDto<IndividualPaymentMethodDefinition> {

    private static final long serialVersionUID = 3847455009192516240L;

    /* Properties */
    private PaymentMethodType paymentMethodType;

    /* Constructors */
    public IndividualPaymentMethodDefinitionDto() {
        super(PaymentMethodDefinitionType.INDIVIDUAL);
    }

    public IndividualPaymentMethodDefinitionDto(final PaymentMethodType paymentMethodType, final BigDecimal authorizationSurcharge, final BigDecimal paymentSurcharge, final Currency currency, final PaymentProviderType paymentProviderType, final boolean storeEnabled) {
        super(PaymentMethodDefinitionType.INDIVIDUAL, authorizationSurcharge, paymentSurcharge, currency, paymentProviderType, storeEnabled);
        this.paymentMethodType = paymentMethodType;
    }

    /* Properties getters and setters */
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(final PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /* Public interface methods */

    @Override
    public void updateDomainEntityProperties(final IndividualPaymentMethodDefinition paymentMethodDefinition) {
        super.updateDomainEntityProperties(paymentMethodDefinition);
        paymentMethodDefinition.setPaymentMethodType(getPaymentMethodType());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndividualPaymentMethodDefinitionDto)) {
            return false;
        }
        final IndividualPaymentMethodDefinitionDto that = (IndividualPaymentMethodDefinitionDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getPaymentMethodType(), that.getPaymentMethodType());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getPaymentMethodType());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentMethodType", this.getPaymentMethodType());
        return builder.build();
    }
}
