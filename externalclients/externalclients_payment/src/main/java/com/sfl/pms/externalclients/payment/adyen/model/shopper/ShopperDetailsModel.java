package com.sfl.pms.externalclients.payment.adyen.model.shopper;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/29/14
 * Time: 4:01 PM
 */
public class ShopperDetailsModel extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = -3555809050705955001L;

    /* Constants */
    private static final String DEFAULT_SHOPPER_INTERACTION = "ContAuth";

    /* Properties */
    private String shopperEmail;

    private String shopperReference;

    private String shopperIp;

    private String shopperInteraction;

    /* Constructors */
    public ShopperDetailsModel(final String shopperEmail, final String shopperReference, final String shopperIp) {
        Assert.hasText(shopperEmail);
        Assert.hasText(shopperReference);
        Assert.hasText(shopperIp);
        this.shopperEmail = shopperEmail;
        this.shopperReference = shopperReference;
        this.shopperIp = shopperIp;
        shopperInteraction = DEFAULT_SHOPPER_INTERACTION;
    }

    public ShopperDetailsModel(final String shopperEmail, final String shopperReference, final String shopperIp, final String shopperInteraction) {
        this(shopperEmail, shopperReference, shopperIp);
        this.shopperInteraction = shopperInteraction;
    }

    public String getShopperIp() {
        return shopperIp;
    }

    public void setShopperIp(String shopperIp) {
        this.shopperIp = shopperIp;
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(String shopperEmail) {
        this.shopperEmail = shopperEmail;
    }

    public String getShopperReference() {
        return shopperReference;
    }

    public void setShopperReference(String shopperReference) {
        this.shopperReference = shopperReference;
    }

    public String getShopperInteraction() {
        return shopperInteraction;
    }

    public void setShopperInteraction(String shopperInteraction) {
        this.shopperInteraction = shopperInteraction;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShopperDetailsModel)) {
            return false;
        }
        ShopperDetailsModel that = (ShopperDetailsModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(shopperIp, that.getShopperIp());
        builder.append(shopperEmail, that.getShopperEmail());
        builder.append(shopperInteraction, that.getShopperInteraction());
        builder.append(shopperReference, that.getShopperReference());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(shopperIp);
        builder.append(shopperEmail);
        builder.append(shopperInteraction);
        builder.append(shopperReference);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("shopperIp", shopperIp);
        builder.append("shopperEmail", shopperEmail);
        builder.append("shopperInteraction", shopperInteraction);
        builder.append("shopperReference", shopperReference);
        return builder.build();
    }
}
