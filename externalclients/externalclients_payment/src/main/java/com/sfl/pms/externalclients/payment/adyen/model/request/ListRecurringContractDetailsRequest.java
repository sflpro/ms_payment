package com.sfl.pms.externalclients.payment.adyen.model.request;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.datatypes.ContractType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 5:03 PM
 */
public class ListRecurringContractDetailsRequest extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 2918112056106960855L;

    /* Properties */
    private final String shopperReference;

    private final ContractType contractType;

    /* Constructors */
    public ListRecurringContractDetailsRequest(final String shopperReference) {
        Assert.hasText(shopperReference);
        this.shopperReference = shopperReference;
        contractType = ContractType.RECURRING;
    }

    public ListRecurringContractDetailsRequest(final String shopperReference, final ContractType contractType) {
        Assert.hasText(shopperReference);
        Assert.notNull(contractType);
        this.shopperReference = shopperReference;
        this.contractType = contractType;
    }

    /* Getters and setters */
    public String getShopperReference() {
        return shopperReference;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public String getContractTypeName() {
        return contractType.name();
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListRecurringContractDetailsRequest)) {
            return false;
        }
        ListRecurringContractDetailsRequest that = (ListRecurringContractDetailsRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(shopperReference, that.getShopperReference());
        builder.append(contractType, that.getContractType());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(shopperReference);
        builder.append(contractType);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("shopperReference", shopperReference);
        builder.append("contractType", contractType);
        return builder.build();
    }
}
