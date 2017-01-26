package com.sfl.pms.externalclients.payment.adyen.model.request;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.card.CreditCardDetailsModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.AbstractPaymentAmountModel;
import com.sfl.pms.externalclients.payment.adyen.model.shopper.ShopperDetailsModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 4:18 PM
 */
public class EncryptedPaymentRequest extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = -3294889446349207131L;

    /* Properties */
    private final String reference;

    private final ShopperDetailsModel shopperDetails;

    private final AbstractPaymentAmountModel paymentAmount;

    private final CreditCardDetailsModel creditCard;

    private final String encryptedPaymentMethodInformation;

    private final boolean createRecurringContract;

    /* Constructors */
    public EncryptedPaymentRequest(final String reference, final ShopperDetailsModel shopperDetails, final AbstractPaymentAmountModel paymentAmount, final CreditCardDetailsModel creditCard, final boolean createRecurringContract) {
        Assert.hasText(reference);
        Assert.notNull(shopperDetails);
        Assert.notNull(paymentAmount);
        Assert.notNull(creditCard);
        this.reference = reference;
        this.shopperDetails = shopperDetails;
        this.paymentAmount = paymentAmount;
        this.creditCard = creditCard;
        this.createRecurringContract = createRecurringContract;
        this.encryptedPaymentMethodInformation = null;
    }

    public EncryptedPaymentRequest(final String reference, final ShopperDetailsModel shopperDetails, final AbstractPaymentAmountModel paymentAmount, final String encryptedPaymentMethodInformation, final boolean createRecurringContract) {
        Assert.hasText(reference);
        Assert.notNull(shopperDetails);
        Assert.notNull(paymentAmount);
        Assert.hasText(encryptedPaymentMethodInformation);
        this.reference = reference;
        this.shopperDetails = shopperDetails;
        this.paymentAmount = paymentAmount;
        this.encryptedPaymentMethodInformation = encryptedPaymentMethodInformation;
        this.creditCard = null;
        this.createRecurringContract = createRecurringContract;
    }


    /* Getters and setters */
    public String getReference() {
        return reference;
    }

    public ShopperDetailsModel getShopperDetails() {
        return shopperDetails;
    }

    public AbstractPaymentAmountModel getPaymentAmount() {
        return paymentAmount;
    }

    public CreditCardDetailsModel getCreditCard() {
        return creditCard;
    }

    public String getEncryptedPaymentMethodInformation() {
        return encryptedPaymentMethodInformation;
    }

    public boolean isCreateRecurringContract() {
        return createRecurringContract;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncryptedPaymentRequest)) {
            return false;
        }
        EncryptedPaymentRequest that = (EncryptedPaymentRequest) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(reference, that.getReference());
        builder.append(shopperDetails, that.getShopperDetails());
        builder.append(paymentAmount, that.getPaymentAmount());
        builder.append(creditCard, that.getCreditCard());
        builder.append(encryptedPaymentMethodInformation, that.getEncryptedPaymentMethodInformation());
        builder.append(createRecurringContract, that.isCreateRecurringContract());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(reference);
        builder.append(shopperDetails);
        builder.append(paymentAmount);
        builder.append(creditCard);
        builder.append(encryptedPaymentMethodInformation);
        builder.append(createRecurringContract);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("reference", reference);
        builder.append("shopperDetails", shopperDetails);
        builder.append("payment", paymentAmount);
        builder.append("creditCard", creditCard);
        builder.append("encryptedPaymentMethodInformation", encryptedPaymentMethodInformation);
        builder.append("createRecurringContract", createRecurringContract);
        return builder.build();
    }
}
