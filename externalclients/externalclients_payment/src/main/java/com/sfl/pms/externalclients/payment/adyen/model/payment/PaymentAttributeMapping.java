package com.sfl.pms.externalclients.payment.adyen.model.payment;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 5:05 PM
 */
public final class PaymentAttributeMapping {
    /* General */
    public static final String CARD = ".card";
    public static final String CARD_CODE = ".code";
    public static final String ADDITIONAL_DATA = ".additionalData";
    public static final String ENCRYPTED = ".encrypted";
    public static final String JSON = ".json";

    /* Request */
    public static final String REQUEST_PREFIX = "paymentRequest";
    public static final String REQUEST_REFERENCE = REQUEST_PREFIX + ".reference";
    public static final String REQUEST_DETAIL_REFERENCE = REQUEST_PREFIX + ".selectedRecurringDetailReference";
    public static final String REQUEST_SHOPPER_IP = REQUEST_PREFIX + ".shopperIP";
    public static final String REQUEST_SHOPPER_EMAIL = REQUEST_PREFIX + ".shopperEmail";
    public static final String REQUEST_SHOPPER_REFERENCE = REQUEST_PREFIX + ".shopperReference";
    public static final String REQUEST_SHOPPER_INTERACTION = REQUEST_PREFIX + ".shopperInteraction";
    public static final String REQUEST_PAYMENT_METHOD_BRAND = REQUEST_PREFIX + ".selectedBrand";
    public static final String REQUEST_PAYMENT_AMOUNT = REQUEST_PREFIX + ".amount.value";
    public static final String REQUEST_PAYMENT_CURRENCY = REQUEST_PREFIX + ".amount.currency";
    public static final String REQUEST_CARD_NUMBER = REQUEST_PREFIX + ".card.number";
    public static final String REQUEST_CARD_HOLDER = REQUEST_PREFIX + ".card.holderName";
    public static final String REQUEST_CARD_EXPIRY_YEAR = REQUEST_PREFIX + ".card.expiryYear";
    public static final String REQUEST_CARD_EXPIRY_MONTH = REQUEST_PREFIX + ".card.expiryMonth";
    public static final String REQUEST_CARD_CODE = REQUEST_PREFIX + CARD + CARD_CODE;
    public static final String REQUEST_ENCRYPTED_CARD_DETAILS = REQUEST_PREFIX + ADDITIONAL_DATA + CARD + ENCRYPTED + JSON;

    /* Result */
    public static final String RESULT_PREFIX = "paymentResult";
    public static final String RESULT_AUTH_CODE = RESULT_PREFIX + ".authCode";
    public static final String RESULT_PSP_REFERENCE = RESULT_PREFIX + ".pspReference";
    public static final String RESULT_CODE = RESULT_PREFIX + ".resultCode";
    public static final String RESULT_REFUSAL_REASON = RESULT_PREFIX + ".refusalReason";

    /*  Private constructor to prevent instantiation */
    private PaymentAttributeMapping() {
    }
}
