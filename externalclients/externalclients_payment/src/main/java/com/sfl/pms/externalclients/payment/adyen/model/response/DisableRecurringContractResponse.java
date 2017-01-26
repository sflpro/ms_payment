package com.sfl.pms.externalclients.payment.adyen.model.response;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.DisableContractResultModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 9:25 PM
 */
public class DisableRecurringContractResponse extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 7830331698910681069L;

    /* Properties */
    private final DisableContractResultModel disableContractResult;

    /* Constructors */
    public DisableRecurringContractResponse(final DisableContractResultModel disableContractResult) {
        this.disableContractResult = disableContractResult;
    }

    /* Getters and setters */
    public DisableContractResultModel getDisableContractResult() {
        return disableContractResult;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisableRecurringContractResponse)) {
            return false;
        }
        DisableRecurringContractResponse that = (DisableRecurringContractResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(disableContractResult, that.getDisableContractResult());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(disableContractResult);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("disableContractResult", disableContractResult);
        return builder.build();
    }
}
