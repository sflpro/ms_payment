package com.sfl.pms.externalclients.payment.adyen.communicator;

import com.sfl.pms.externalclients.common.http.rest.RestClient;
import com.sfl.pms.externalclients.payment.adyen.model.card.CreditCardDetailsModel;
import com.sfl.pms.externalclients.payment.adyen.model.datatypes.ApiActions;
import com.sfl.pms.externalclients.payment.adyen.model.datatypes.ContractType;
import com.sfl.pms.externalclients.payment.adyen.model.payment.AbstractPaymentAmountModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.AdyenPaymentResultModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.PaymentAmountModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.PaymentAttributeMapping;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.DisableContractResultModel;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.RecurringAttributeMapping;
import com.sfl.pms.externalclients.payment.adyen.model.request.DisableRecurringContractRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.EncryptedPaymentRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.ListRecurringContractDetailsRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.SubsequentRecurringPaymentRequest;
import com.sfl.pms.externalclients.payment.adyen.model.response.DisableRecurringContractResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.EncryptedPaymentResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.ListRecurringContractDetailsResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.SubsequentRecurringPaymentResponse;
import com.sfl.pms.externalclients.payment.adyen.model.shopper.ShopperDetailsModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 6:51 PM
 */
@Component
public class AdyenApiCommunicatorImpl implements AdyenApiCommunicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenApiCommunicatorImpl.class);

    /* Constants */
    private static final String AUTHENTICATION_HEADER_NAME = "Authorization";

    private static final String AUTHENTICATION_BASIC_PREFIX = "Basic ";

    private static final String API_ACTION = "action";

    @Value("#{ appProperties['adyen.merchant.account']}")
    private String merchantAccount;

    @Value("#{ appProperties['adyen.ws.url']}")
    private String url;

    @Value("#{ appProperties['adyen.ws.username']}")
    private String username;

    @Value("#{ appProperties['adyen.ws.password']}")
    private String password;

    /* Dependencies */
    @Autowired
    private RestClient restClient;

    /* Constructors */
    public AdyenApiCommunicatorImpl() {
    }

    @PostConstruct
    private void init() {
        LOGGER.info("Initializing Adyen payments api communicator with credentials: merchant - {}, url - {}, username - {}, password - {}", merchantAccount, url, username, password);
    }


    /* Public methods */
    @Override
    public EncryptedPaymentResponse submitPaymentUsingEncryptedPaymentMethod(final EncryptedPaymentRequest requestModel) {
        /* Create Headers */
        LOGGER.debug("Submitting encrypted payment request with model - {}", requestModel);
        final HttpHeaders headers = createHttpHeaders();
        addHttpBasicAuthentication(headers);

        final LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(API_ACTION, ApiActions.PAYMENT_AUTHORIZE.getActionName());
        parameters.add("paymentRequest.merchantAccount", merchantAccount);

        parameters.add(PaymentAttributeMapping.REQUEST_REFERENCE, requestModel.getReference());
        if (requestModel.isCreateRecurringContract()) {
            parameters.add(RecurringAttributeMapping.PAYMENT_CONTRACT_TYPE, ContractType.RECURRING.name());
        }
        /* Set shopper details */
        final ShopperDetailsModel shopperDetails = requestModel.getShopperDetails();
        addShopperDetails(parameters, shopperDetails);
        /* Set payment amount details */
        final AbstractPaymentAmountModel paymentModel = requestModel.getPaymentAmount();
        addPaymentAmount(parameters, paymentModel);
        /* Set credit card details */
        final CreditCardDetailsModel creditCard = requestModel.getCreditCard();
        if (creditCard != null) {
            addCreditCardDetails(parameters, creditCard);
        } else {
            parameters.add(PaymentAttributeMapping.REQUEST_ENCRYPTED_CARD_DETAILS, requestModel.getEncryptedPaymentMethodInformation());
        }
        /* Create http request entity */
        final HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        /* Execute POST request */
        final ResponseEntity<LinkedMultiValueMap> responseEntity = restClient.postForEntity(url, httpEntity, LinkedMultiValueMap.class, Collections.emptyMap());
        /* Check response status */
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("Invalid response status returned - " + responseEntity);
        }
        LOGGER.debug("Successfully processed encrypted payment request, retrieved response - {}", responseEntity);
        @SuppressWarnings({"unchecked"})
        final AdyenPaymentResultModel resultModel = AdyenPaymentResultModel.buildFromResponseBody(responseEntity.getBody());

        return new EncryptedPaymentResponse(resultModel);
    }


    @Override
    public ListRecurringContractDetailsResponse listRecurringDetails(final ListRecurringContractDetailsRequest requestModel) {

        /* Create Headers */
        final HttpHeaders headers = createHttpHeaders();
        addHttpBasicAuthentication(headers);

        final LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(API_ACTION, ApiActions.RECURRING_LIST_DETAILS.getActionName());
        parameters.add("recurringDetailsRequest.merchantAccount", merchantAccount);

        parameters.add(RecurringAttributeMapping.DETAILS_REQUEST_SHOPPER_REFERENCE, requestModel.getShopperReference());
        parameters.add(RecurringAttributeMapping.DETAILS_REQUEST_CONTRACT_TYPE, requestModel.getContractTypeName());

        final HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        /* Send request */
        LOGGER.debug("Sending http request with entity - {}", httpEntity);
        final ResponseEntity<LinkedMultiValueMap> responseEntity = restClient.exchange(url,
                HttpMethod.POST, httpEntity,
                LinkedMultiValueMap.class, Collections.emptyMap());
        /* Check response status */
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("Invalid response status returned - " + responseEntity);
        }

        @SuppressWarnings({"unchecked"})
        final MultiValueMap<String, String> result = responseEntity.getBody();
        final ListRecurringContractDetailsResponse response;
        if (result != null && result.size() > 0) {
            response = ListRecurringContractDetailsResponse.buildFromResponseBody(result);
        } else {
            response = new ListRecurringContractDetailsResponse();
        }
        LOGGER.debug("Successfully retrieved recurring contract details - {}, created response model - {}", result, response);
        return response;
    }


    @Override
    public DisableRecurringContractResponse disableRecurringContract(final DisableRecurringContractRequest requestModel) {
        /* Create Headers */
        LOGGER.debug("Sending request to disable recurring contract with model - {}", requestModel);
        final HttpHeaders headers = createHttpHeaders();
        addHttpBasicAuthentication(headers);
        /* Set http post parameters */
        final LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(API_ACTION, ApiActions.RECURRING_DISABLE.getActionName());
        parameters.add("disableRequest.merchantAccount", merchantAccount);

        parameters.add(RecurringAttributeMapping.DISABLE_CONTRACT_REFERENCE, requestModel.getContractReference());
        parameters.add(RecurringAttributeMapping.DISABLE_SHOPPER_REFERENCE, requestModel.getShopperReference());
        final HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        /* Send request */
        LOGGER.debug("Sending http request with entity - {}", httpEntity);
        final ResponseEntity<LinkedMultiValueMap> responseEntity = restClient.exchange(url,
                HttpMethod.POST, httpEntity,
                LinkedMultiValueMap.class, Collections.emptyMap());
        /* Check response status */
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("Invalid response status returned - " + responseEntity);
        }

        /* Fetch response body */
        @SuppressWarnings({"unchecked"})
        final MultiValueMap<String, String> result = responseEntity.getBody();
        LOGGER.debug("Retrieved response - {} from disabled recurring contract request - {}", result);

        final DisableContractResultModel resultModel = DisableContractResultModel.buildFromResponseBody(result);
        return new DisableRecurringContractResponse(resultModel);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public SubsequentRecurringPaymentResponse submitSubsequentPayment(final SubsequentRecurringPaymentRequest requestModel) {
        /* Create Headers */
        LOGGER.debug("Submitting zero value payment request with model - {}", requestModel);
        final HttpHeaders headers = createHttpHeaders();
        addHttpBasicAuthentication(headers);
        final LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(API_ACTION, ApiActions.PAYMENT_AUTHORIZE.getActionName());
        parameters.add("paymentRequest.merchantAccount", merchantAccount);
        /* Set recurring detail reference */
        parameters.add(PaymentAttributeMapping.REQUEST_DETAIL_REFERENCE, requestModel.getRecurringDetailReference());

        parameters.add(PaymentAttributeMapping.REQUEST_REFERENCE, requestModel.getReference());
        parameters.add(RecurringAttributeMapping.PAYMENT_CONTRACT_TYPE, ContractType.RECURRING.name());
        /* Set shopper details */
        final ShopperDetailsModel shopperDetails = requestModel.getShopperDetails();
        addShopperDetails(parameters, shopperDetails);
        /* Set selected payment recurring payment method brand */
        if (requestModel.getSelectedBrand() != null) {
            addSelectedPaymentBrand(parameters, requestModel.getSelectedBrand());
        }
        /* Set payment amount details */
        final PaymentAmountModel paymentModel = requestModel.getPayment();
        addPaymentAmount(parameters, paymentModel);
        /* Create http request entity */
        final HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
        /* Execute POST request */
        final ResponseEntity<LinkedMultiValueMap> responseEntity = restClient.postForEntity(url, httpEntity, LinkedMultiValueMap.class, Collections.emptyMap());
        /* Check response status */
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("Invalid response status returned - " + responseEntity);
        }
        LOGGER.debug("Successfully processed zero value payment request, retrieved response - {}", responseEntity);
        final AdyenPaymentResultModel resultModel = AdyenPaymentResultModel.buildFromResponseBody(responseEntity.getBody());
        return new SubsequentRecurringPaymentResponse(resultModel);
    }

    /* Utility methods */
    private void addHttpBasicAuthentication(final HttpHeaders headers) {
        final String auth = username + ":" + password;
        final String encodedAuth = Base64.encodeBase64String(StringUtils.getBytesUsAscii(auth));
        final String authHeaderValue = AUTHENTICATION_BASIC_PREFIX + encodedAuth;
        headers.add(AUTHENTICATION_HEADER_NAME, authHeaderValue);
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        httpHeaders.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
        return httpHeaders;
    }

    private static void addShopperDetails(final LinkedMultiValueMap<String, String> parameters, final ShopperDetailsModel shopperDetails) {
        parameters.add(PaymentAttributeMapping.REQUEST_SHOPPER_IP, shopperDetails.getShopperIp());
        parameters.add(PaymentAttributeMapping.REQUEST_SHOPPER_EMAIL, shopperDetails.getShopperEmail());
        parameters.add(PaymentAttributeMapping.REQUEST_SHOPPER_REFERENCE, shopperDetails.getShopperReference());
        parameters.add(PaymentAttributeMapping.REQUEST_SHOPPER_INTERACTION, shopperDetails.getShopperInteraction());
    }

    private static void addPaymentAmount(final LinkedMultiValueMap<String, String> parameters, final AbstractPaymentAmountModel paymentModel) {
        parameters.add(PaymentAttributeMapping.REQUEST_PAYMENT_CURRENCY, paymentModel.getAmountCurrency());
        // Payment amount has to be specified in minor units
        parameters.add(PaymentAttributeMapping.REQUEST_PAYMENT_AMOUNT, String.valueOf(paymentModel.getPaymentAmountInMinorUnits()));
    }

    private static void addSelectedPaymentBrand(final LinkedMultiValueMap<String, String> parameters, final String selectedPaymentMethodBrand) {
        parameters.add(PaymentAttributeMapping.REQUEST_PAYMENT_METHOD_BRAND, selectedPaymentMethodBrand);
    }

    private static void addCreditCardDetails(final LinkedMultiValueMap<String, String> parameters, final CreditCardDetailsModel creditCard) {
        parameters.add(PaymentAttributeMapping.REQUEST_CARD_NUMBER, creditCard.getNumber());
        parameters.add(PaymentAttributeMapping.REQUEST_CARD_HOLDER, creditCard.getHolderName());
        parameters.add(PaymentAttributeMapping.REQUEST_CARD_EXPIRY_YEAR, String.valueOf(creditCard.getExpiryYear()));
        parameters.add(PaymentAttributeMapping.REQUEST_CARD_EXPIRY_MONTH, String.valueOf(creditCard.getExpiryMonth()));
        parameters.add(PaymentAttributeMapping.REQUEST_CARD_CODE, creditCard.getCode());
    }


    /* Getters */
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RestClient getRestClient() {
        return restClient;
    }
}
