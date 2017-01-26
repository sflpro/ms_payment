package com.sfl.pms.externalclients.payment.adyen.model.recurring;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 1/8/15
 * Time: 12:40 PM
 */
public class DisableContractResultModel extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 2145487006302845113L;

    /* Properties */
    private String status;

    /* Constructors */
    public DisableContractResultModel(final String status) {
        this.status = status;
    }

    public static DisableContractResultModel buildFromResponseBody(final MultiValueMap<String, String> responseBody) {
        Assert.notNull(responseBody);
        Assert.isTrue(responseBody.size() > 0);
        final Map<String, String> singleValues = responseBody.toSingleValueMap();
        final String authCode = singleValues.get(RecurringAttributeMapping.DISABLE_CONTRACT_STATUS);
        /* Create instance of disable contract result */
        return new DisableContractResultModel(authCode);
    }

    /* Getters and setters */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisableContractResultModel)) {
            return false;
        }
        DisableContractResultModel that = (DisableContractResultModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(status, that.getStatus());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(status);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("status", status);
        return builder.build();
    }
}
