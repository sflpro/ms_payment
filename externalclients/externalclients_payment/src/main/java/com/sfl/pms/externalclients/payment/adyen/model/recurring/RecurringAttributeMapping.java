package com.sfl.pms.externalclients.payment.adyen.model.recurring;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 5:05 PM
 */
public final class RecurringAttributeMapping {

    /* General */
    public static final String SHOPPER_REFERENCE = "shopperReference";
    public static final String SHOPPER_LAST_EMAIL = "lastKnownShopperEmail";
    public static final String DETAILS_FIELD_REFERENCE = "recurringDetailReference";
    public static final String SEPARATOR = ".";
    public static final String RECURRING = "recurring";
    public static final String CONTRACT = "contract";
    public static final String RESPONSE = "response";
    public static final String DETAILS_FIELD_CREATION_DATE = "creationDate";

    public static final String DETAILS_FIELD_VARIANT = "variant";
    public static final String DETAILS_FIELD_CONTRACT_TYPE_CARD = "card";
    public static final String DETAILS_FIELD_CONTRACT_TYPE_BANK = "bank";
    public static final String DETAILS_FIELD_CARD_NUMBER = "number";
    public static final String DETAILS_FIELD_CARD_HOLDER = "holderName";
    public static final String DETAILS_FIELD_CARD_EXPIRY_YEAR = "expiryYear";
    public static final String DETAILS_FIELD_CARD_EXPIRY_MONTH = "expiryMonth";

    public static final String DETAILS_FIELD_BANK_ACCOUNT_NUMBER = "bankAccountNumber";
    public static final String DETAILS_FIELD_BANK_LOCATION_ID = "bankLocationId";
    public static final String DETAILS_FIELD_BANK_NAME = "bankName";
    public static final String DETAILS_FIELD_BANK_BIC = "bic";
    public static final String DETAILS_FIELD_BANK_COUNTRY_CODE = "countryCode";
    public static final String DETAILS_FIELD_BANK_IBAN = "iban";
    public static final String DETAILS_FIELD_BANK_OWNER_NAME = "ownerName";

    /* Recurring contract details request */
    public static final String DETAILS_REQUEST_PREFIX = "recurringDetailsRequest";
    public static final String DETAILS_REQUEST_SHOPPER_REFERENCE = DETAILS_REQUEST_PREFIX + SEPARATOR + SHOPPER_REFERENCE;
    public static final String DETAILS_REQUEST_CONTRACT_TYPE = DETAILS_REQUEST_PREFIX + SEPARATOR + RECURRING + SEPARATOR + CONTRACT;
    /* Recurring contract details response */
    public static final String DETAILS_RESPONSE_PREFIX = "recurringDetailsResult";
    public static final String DETAILS_RESPONSE_SHOPPER_REF = DETAILS_RESPONSE_PREFIX + SEPARATOR + SHOPPER_REFERENCE;
    public static final String DETAILS_RESPONSE_CONTRACT_DATE = DETAILS_RESPONSE_PREFIX + SEPARATOR + DETAILS_FIELD_CREATION_DATE;
    public static final String DETAILS_RESPONSE_SHOPPER_EMAIL = DETAILS_RESPONSE_PREFIX + SEPARATOR + SHOPPER_LAST_EMAIL;

    /* Disable contract  request */
    public static final String DISABLE_REQUEST_PREFIX = "disableRequest";
    public static final String DISABLE_CONTRACT_REFERENCE = DISABLE_REQUEST_PREFIX + SEPARATOR + DETAILS_FIELD_REFERENCE;
    public static final String DISABLE_SHOPPER_REFERENCE = DISABLE_REQUEST_PREFIX + SEPARATOR + SHOPPER_REFERENCE;
    /* Disable contract  response */
    public static final String DISABLE_RESPONSE_PREFIX = "disableResult";
    public static final String DISABLE_CONTRACT_STATUS = DISABLE_RESPONSE_PREFIX + SEPARATOR + RESPONSE;

    /* Payment request */
    public static final String PAYMENT_PREFIX = "paymentRequest";
    public static final String PAYMENT_CONTRACT_TYPE = PAYMENT_PREFIX + SEPARATOR + RECURRING + SEPARATOR + CONTRACT;

    /*  Private constructor to prevent instantiation */
    private RecurringAttributeMapping() {
    }
}
