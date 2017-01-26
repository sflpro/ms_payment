package com.sfl.pms.externalclients.payment.adyen.model.request;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 9:24 PM
 */
public class DisableRecurringContractRequest extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 2887310562620036865L;

    /* Properties */
    private final String shopperReference;

    private final String contractReference;

    /* Constructors */
    public DisableRecurringContractRequest(final String shopperReference, final String contractReference) {
        Assert.hasText(shopperReference);
        Assert.hasText(contractReference);
        this.shopperReference = shopperReference;
        this.contractReference = contractReference;
    }

    /* Getters and setters */
    public String getShopperReference() {
        return shopperReference;
    }

    public String getContractReference() {
        return contractReference;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisableRecurringContractRequest)) {
            return false;
        }
        DisableRecurringContractRequest that = (DisableRecurringContractRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(shopperReference, that.getShopperReference());
        builder.append(contractReference, that.getContractReference());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(shopperReference);
        builder.append(contractReference);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("shopperReference", shopperReference);
        builder.append("contractReference", contractReference);
        return builder.build();
    }
}
