package com.sfl.pms.externalclients.payment.adyen.model.request;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.PaymentAmountModel;
import com.sfl.pms.externalclients.payment.adyen.model.shopper.ShopperDetailsModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 4:54 PM
 */
public class SubsequentRecurringPaymentRequest extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 5634108294075365511L;

    /* Properties */
    private final String reference;

    private final String recurringDetailReference;

    private final ShopperDetailsModel shopperDetails;

    private final PaymentAmountModel payment;

    private final String selectedBrand;


    /* Constructors */
    public SubsequentRecurringPaymentRequest(final String reference, final String recurringDetailReference, final ShopperDetailsModel shopperDetails, final PaymentAmountModel payment, final String selectedBrand) {
        Assert.hasText(reference);
        Assert.hasText(recurringDetailReference);
        Assert.notNull(shopperDetails);
        Assert.notNull(payment);
        this.reference = reference;
        this.recurringDetailReference = recurringDetailReference;
        this.shopperDetails = shopperDetails;
        this.payment = payment;
        this.selectedBrand = selectedBrand;
    }

    /* Getters */
    public String getReference() {
        return reference;
    }

    public String getRecurringDetailReference() {
        return recurringDetailReference;
    }

    public ShopperDetailsModel getShopperDetails() {
        return shopperDetails;
    }

    public PaymentAmountModel getPayment() {
        return payment;
    }

    public String getSelectedBrand() {
        return selectedBrand;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubsequentRecurringPaymentRequest)) {
            return false;
        }
        SubsequentRecurringPaymentRequest that = (SubsequentRecurringPaymentRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(reference, that.getReference());
        builder.append(recurringDetailReference, that.getRecurringDetailReference());
        builder.append(shopperDetails, that.getShopperDetails());
        builder.append(payment, that.getPayment());
        builder.append(selectedBrand, that.getSelectedBrand());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(reference);
        builder.append(recurringDetailReference);
        builder.append(shopperDetails);
        builder.append(payment);
        builder.append(selectedBrand);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("reference", reference);
        builder.append("recurringDetailReference", recurringDetailReference);
        builder.append("shopperDetails", shopperDetails);
        builder.append("payment", payment);
        builder.append("selectedBrand", selectedBrand);
        return builder.build();
    }
}
