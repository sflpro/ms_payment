package com.sfl.pms.externalclients.payment.adyen.model.response;

import com.sfl.pms.externalclients.payment.adyen.exception.PaymentRuntimeException;
import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.AbstractRecurringContractDetailsModel;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.RecurringAttributeMapping;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.RecurringContractBankDetailsModel;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.RecurringContractCardDetailsModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 5:03 PM
 */
public class ListRecurringContractDetailsResponse extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = 2918112056106960855L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRecurringContractDetailsResponse.class);

    /* Constants */
    private static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private static final String RECURRING_CONTRACT_DETAILS_TYPE_FIELD_NAME = "recurringContractType";

    private static final int RECURRING_DETAILS_EXTENDED_SPLITS_COUNT = 5;

    private static final int RECURRING_DETAILS_EXTENDED_DETAILS_TYPE_INDEX = 3;

    private static final int RECURRING_DETAILS_EXTENDED_DETAILS_FIELD_NAME_INDEX = 4;

    private static final int RECURRING_DETAILS_SHORTENED_DETAILS_FIELD_NAME_INDEX = 3;

    /* Properties */
    private final String shopperEmail;

    private final String shopperReference;

    private final Date contractCreationDate;

    private final List<AbstractRecurringContractDetailsModel> recurringContracts;

    /* Constructors */
    public ListRecurringContractDetailsResponse() {
        this(Collections.emptyList(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }

    public ListRecurringContractDetailsResponse(final List<AbstractRecurringContractDetailsModel> recurringContracts, final String shopperEmail, final String shopperReference, final String contractCreationDate) {
        this.recurringContracts = Collections.unmodifiableList(recurringContracts);
        this.shopperEmail = shopperEmail;
        this.shopperReference = shopperReference;
        final DateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        Date date = null;
        try {
            /* Format and parse contract creation date */
            date = dateFormat.parse(contractCreationDate);
        } catch (final ParseException e) {
            LOGGER.debug("Unable to parse credit card creation date", e);
        }
        this.contractCreationDate = date;
        /* Parse and set card details */

    }

    /* Builders */
    public static ListRecurringContractDetailsResponse buildFromResponseBody(final MultiValueMap<String, String> responseBody) {
        Assert.notNull(responseBody);
        Assert.isTrue(responseBody.size() > 0);
        final Map<String, String> singleValues = responseBody.toSingleValueMap();
        final String shopperEmail = singleValues.get(RecurringAttributeMapping.DETAILS_RESPONSE_SHOPPER_EMAIL);
        final String shopperReference = singleValues.get(RecurringAttributeMapping.DETAILS_RESPONSE_SHOPPER_REF);
        final String contractCreationDate = singleValues.get(RecurringAttributeMapping.DETAILS_RESPONSE_CONTRACT_DATE);
        /* Extract keys containing numeric keys */
        final Map<Integer, Map<String, String>> parsedCardDetailsMap = extractRecurringDetails(singleValues);
        /* Parse card details map and create models */
        final List<AbstractRecurringContractDetailsModel> recurringDetails = new LinkedList<>();
        for (Map.Entry<Integer, Map<String, String>> entry : parsedCardDetailsMap.entrySet()) {
            final Map<String, String> recurringDetailsContractEntry = entry.getValue();
            final String recurringContractType = recurringDetailsContractEntry.get(RECURRING_CONTRACT_DETAILS_TYPE_FIELD_NAME);
            final AbstractRecurringContractDetailsModel recurringContractDetailsModel;
            if (RecurringAttributeMapping.DETAILS_FIELD_CONTRACT_TYPE_CARD.equals(recurringContractType)) {
                recurringContractDetailsModel = RecurringContractCardDetailsModel.newBuilder()
                        .detailReference(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_REFERENCE))
                        .variant(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_VARIANT))
                        .creationDateFromString(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_CREATION_DATE))
                        .cardNumber(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_CARD_NUMBER))
                        .cardHolderName(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_CARD_HOLDER))
                        .expiryMonthFromString(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_CARD_EXPIRY_MONTH))
                        .expiryYearFromString(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_CARD_EXPIRY_YEAR))
                        .build();
            } else if (RecurringAttributeMapping.DETAILS_FIELD_CONTRACT_TYPE_BANK.equals(recurringContractType)) {
                recurringContractDetailsModel = RecurringContractBankDetailsModel.newBuilder()
                        .detailReference(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_REFERENCE))
                        .variant(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_VARIANT))
                        .creationDateFromString(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_CREATION_DATE))
                        .bankAccountNumber(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_ACCOUNT_NUMBER))
                        .bankLocationId(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_LOCATION_ID))
                        .bankName(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_NAME))
                        .iban(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_IBAN))
                        .countryCode(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_COUNTRY_CODE))
                        .bic(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_BIC))
                        .ownerName(recurringDetailsContractEntry.get(RecurringAttributeMapping.DETAILS_FIELD_BANK_OWNER_NAME))
                        .build();
            } else {
                throw new PaymentRuntimeException("Unknown recurring Adyen contract type - " + recurringContractType);
            }
            /* Build card detail model */
            recurringDetails.add(recurringContractDetailsModel);
        }
        /* Create instance of payment result */
        final ListRecurringContractDetailsResponse instance = new ListRecurringContractDetailsResponse(recurringDetails, shopperEmail, shopperReference, contractCreationDate);
        return instance;
    }

    /* Getters and setters */
    public List<AbstractRecurringContractDetailsModel> getRecurringContracts() {
        return recurringContracts;
    }

    public List<RecurringContractCardDetailsModel> getRecurringCardDetails() {
        return recurringContracts.stream().filter(contract -> contract instanceof RecurringContractCardDetailsModel).map(contract -> (RecurringContractCardDetailsModel) contract).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public String getShopperReference() {
        return shopperReference;
    }

    public Date getContractCreationDate() {
        return (Date) contractCreationDate.clone();
    }

    /* Utility methods */
    private static Map<Integer, Map<String, String>> extractRecurringDetails(final Map<String, String> singleValues) {
        final Map<Integer, Map<String, String>> parsedDetailsMap = new LinkedHashMap<>();
        singleValues.entrySet().forEach(entry -> {
            if (entry.getKey().startsWith("recurringDetailsResult.details.")) {
                final String[] splits = entry.getKey().split(Pattern.quote("."));
                final Integer detailsIndex = Integer.valueOf(splits[2]);
                final String detailsType;
                final String detailsFieldName;
                if (splits.length == RECURRING_DETAILS_EXTENDED_SPLITS_COUNT) {
                    detailsType = splits[RECURRING_DETAILS_EXTENDED_DETAILS_TYPE_INDEX];
                    detailsFieldName = splits[RECURRING_DETAILS_EXTENDED_DETAILS_FIELD_NAME_INDEX];
                } else {
                    detailsType = null;
                    detailsFieldName = splits[RECURRING_DETAILS_SHORTENED_DETAILS_FIELD_NAME_INDEX];
                }
                // Check if we already have entry for this index
                Map<String, String> detailsMap = parsedDetailsMap.get(detailsIndex);
                if (detailsMap == null) {
                    detailsMap = new HashMap<>();
                    parsedDetailsMap.put(detailsIndex, detailsMap);
                }
                if (detailsType != null) {
                    detailsMap.put(RECURRING_CONTRACT_DETAILS_TYPE_FIELD_NAME, detailsType);
                }
                detailsMap.put(detailsFieldName, entry.getValue());
            }
        });
        return parsedDetailsMap;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListRecurringContractDetailsResponse)) {
            return false;
        }
        ListRecurringContractDetailsResponse that = (ListRecurringContractDetailsResponse) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(recurringContracts, that.getRecurringContracts());
        builder.append(shopperEmail, that.getShopperEmail());
        builder.append(shopperReference, that.getShopperReference());
        builder.append(contractCreationDate, that.getContractCreationDate());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(recurringContracts);
        builder.append(shopperEmail);
        builder.append(shopperReference);
        builder.append(contractCreationDate);
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("recurringContracts", recurringContracts);
        builder.append("shopperEmail", shopperEmail);
        builder.append("shopperReference", shopperReference);
        builder.append("contractCreationDate", contractCreationDate);
        return builder.build();
    }
}
