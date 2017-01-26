package com.sfl.pms.externalclients.payment.adyen.model.response;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.AdyenPaymentResultModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 2:53 PM
 */
public class EncryptedPaymentResponse extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 3075748574560437763L;

    /* Properties */
    private final AdyenPaymentResultModel paymentResult;

    /* Constructors */
    public EncryptedPaymentResponse(final AdyenPaymentResultModel paymentResult) {
        this.paymentResult = paymentResult;
    }

    /* Getters and setters */
    public AdyenPaymentResultModel getPaymentResult() {
        return paymentResult;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncryptedPaymentResponse)) {
            return false;
        }
        EncryptedPaymentResponse that = (EncryptedPaymentResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(paymentResult, that.getPaymentResult());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(paymentResult);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("paymentResult", paymentResult);
        return builder.build();
    }
}
