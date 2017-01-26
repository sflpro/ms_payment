package com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 12:47 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdyenNotificationRequestItemJsonModel implements Serializable {
    private static final long serialVersionUID = 6033169368462324511L;

    /* Properties */
    @JsonProperty("additionalData")
    private AdyenNotificationAdditionalDataJsonModel additionalData;

    @JsonProperty("amount")
    private AdyenNotificationAmountJsonModel amount;

    @JsonProperty("eventCode")
    private String eventCode;

    @JsonProperty("eventDate")
    private String eventDate;

    @JsonProperty("merchantAccountCode")
    private String merchantAccountCode;

    @JsonProperty("merchantReference")
    private String merchantReference;

    @JsonProperty("operations")
    private List<String> operations;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("pspReference")
    private String pspReference;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("success")
    private Boolean success;

    /* Constructors */
    public AdyenNotificationRequestItemJsonModel() {
        this.operations = new ArrayList<>();
    }

    /* Properties getters and setters */
    public AdyenNotificationAdditionalDataJsonModel getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(final AdyenNotificationAdditionalDataJsonModel additionalData) {
        this.additionalData = additionalData;
    }

    public AdyenNotificationAmountJsonModel getAmount() {
        return amount;
    }

    public void setAmount(final AdyenNotificationAmountJsonModel amount) {
        this.amount = amount;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(final String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(final String eventDate) {
        this.eventDate = eventDate;
    }

    public String getMerchantAccountCode() {
        return merchantAccountCode;
    }

    public void setMerchantAccountCode(final String merchantAccountCode) {
        this.merchantAccountCode = merchantAccountCode;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(final String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(final List<String> operations) {
        this.operations = operations;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(final String pspReference) {
        this.pspReference = pspReference;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdyenNotificationRequestItemJsonModel)) {
            return false;
        }
        final AdyenNotificationRequestItemJsonModel that = (AdyenNotificationRequestItemJsonModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.getAdditionalData(), that.getAdditionalData());
        builder.append(this.getAmount(), that.getAmount());
        builder.append(this.getEventCode(), that.getEventCode());
        builder.append(this.getEventDate(), that.getEventDate());
        builder.append(this.getMerchantAccountCode(), that.getMerchantAccountCode());
        builder.append(this.getMerchantReference(), that.getMerchantReference());
        builder.append(this.getOperations(), that.getOperations());
        builder.append(this.getPaymentMethod(), that.getPaymentMethod());
        builder.append(this.getPspReference(), that.getPspReference());
        builder.append(this.getReason(), that.getReason());
        builder.append(this.getSuccess(), that.getSuccess());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getAdditionalData());
        builder.append(this.getAmount());
        builder.append(this.getEventCode());
        builder.append(this.getEventDate());
        builder.append(this.getMerchantAccountCode());
        builder.append(this.getMerchantReference());
        builder.append(this.getOperations());
        builder.append(this.getPaymentMethod());
        builder.append(this.getPspReference());
        builder.append(this.getReason());
        builder.append(this.getSuccess());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("additionalData", this.getAdditionalData());
        builder.append("amount", this.getAmount());
        builder.append("eventCode", this.getEventCode());
        builder.append("eventDate", this.getEventDate());
        builder.append("merchantAccountCode", this.getMerchantAccountCode());
        builder.append("merchantReference", this.getMerchantReference());
        builder.append("operations", this.getOperations());
        builder.append("paymentMethod", this.getPaymentMethod());
        builder.append("pspReference", this.getPspReference());
        builder.append("reason", this.getReason());
        builder.append("success", this.getSuccess());
        return builder.build();
    }
}
