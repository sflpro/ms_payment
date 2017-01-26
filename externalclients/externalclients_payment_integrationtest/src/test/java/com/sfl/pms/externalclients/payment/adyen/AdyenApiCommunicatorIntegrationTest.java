package com.sfl.pms.externalclients.payment.adyen;

import com.sfl.pms.externalclients.payment.adyen.communicator.AdyenApiCommunicator;
import com.sfl.pms.externalclients.payment.adyen.model.card.CreditCardDetailsModel;
import com.sfl.pms.externalclients.payment.adyen.model.datatypes.AdyenPaymentStatus;
import com.sfl.pms.externalclients.payment.adyen.model.payment.DynamicZeroValuePaymentModel;
import com.sfl.pms.externalclients.payment.adyen.model.payment.PaymentAmountModel;
import com.sfl.pms.externalclients.payment.adyen.model.recurring.RecurringContractCardDetailsModel;
import com.sfl.pms.externalclients.payment.adyen.model.request.DisableRecurringContractRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.EncryptedPaymentRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.ListRecurringContractDetailsRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.SubsequentRecurringPaymentRequest;
import com.sfl.pms.externalclients.payment.adyen.model.response.DisableRecurringContractResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.EncryptedPaymentResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.ListRecurringContractDetailsResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.SubsequentRecurringPaymentResponse;
import com.sfl.pms.externalclients.payment.adyen.model.shopper.ShopperDetailsModel;
import com.sfl.pms.externalclients.payment.test.AbstractPaymentIntegrationTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/26/14
 * Time: 12:49 PM
 */
@Ignore
public class AdyenApiCommunicatorIntegrationTest extends AbstractPaymentIntegrationTest {

    /* Constants */
    private static final String DISABLE_CONTRACT_SUCCESS = "[detail-successfully-disabled]";

    /* Dependencies */
    @Autowired
    private AdyenApiCommunicator adyenApiCommunicator;

    @Value("#{ appProperties['adyen.data.card.encrypted']}")
    private String adyenCardEncryptedData;

    /* Constructors */
    public AdyenApiCommunicatorIntegrationTest() {
    }


    /* Test methods */
    @Test
    public void testSubmitWithDynamicZeroValueAuth() {
        /* Test data */
        final String currencyCode = "EUR";
        final String uuId = UUID.randomUUID().toString();
        final String reference = "initialPayment_" + uuId;
        /* Credit card data (Master Card) */
        final String cardNumber = "4111 1111 1111 1111";
        final String holderName = "Adyen Test";
        final String cardCode = "737";
        final Integer expiryMonth = 6;
        final Integer expiryYear = 2016;
        /* Create shopper details model */
        final String shopperReference = "mher.sargsyan_" + uuId;
        final String shopperEmail = "mher.sargsyan_" + uuId + "@sflpro.com";
        final ShopperDetailsModel shopperDetails = new ShopperDetailsModel(shopperEmail, shopperReference, "1.1.1.1");
        /* Create payment model */
        final DynamicZeroValuePaymentModel paymentModel = new DynamicZeroValuePaymentModel(currencyCode);
        /* Create credit card model */
        final CreditCardDetailsModel creditCard = new CreditCardDetailsModel(cardNumber, holderName, cardCode, expiryMonth, expiryYear);
        /* Submit request */
        final EncryptedPaymentRequest request = new EncryptedPaymentRequest(reference, shopperDetails, paymentModel, creditCard, true);
        final EncryptedPaymentResponse response = adyenApiCommunicator.submitPaymentUsingEncryptedPaymentMethod(request);
        /* Validate response */
        assertCreateRecurringContractResponse(response);
        /* Get list of contract details */
        final ListRecurringContractDetailsRequest requestModel = new ListRecurringContractDetailsRequest(shopperReference);
        ListRecurringContractDetailsResponse listRecurringContracts = adyenApiCommunicator.listRecurringDetails(requestModel);
        assertRecurringContractsList(shopperReference, shopperEmail, listRecurringContracts);
        /* Verify credit card details */
        final RecurringContractCardDetailsModel detailsModel = listRecurringContracts.getRecurringCardDetails().get(0);
        assertNotNull(detailsModel);
        assertEquals(detailsModel.getCardHolderName(), holderName);
        assertEquals(detailsModel.getVariant(), "mc");
        assertEquals(detailsModel.getExpiryMonth(), expiryMonth);
        assertEquals(detailsModel.getExpiryYear(), expiryYear);
        /* Verify last 4 numbers */
        assertEquals(detailsModel.getCardNumber(), StringUtils.substring(cardNumber, 15));
        final String contractReference = detailsModel.getDetailReference();
        /* Submit subsequent payment */
        final String subsequentPaymentReference = "subsequentPayment_" + uuId;
        /* Create payment model */
        final PaymentAmountModel paymentAmountModel = new PaymentAmountModel(currencyCode, BigDecimal.TEN);
        final SubsequentRecurringPaymentRequest subsequentPaymentRequest = new SubsequentRecurringPaymentRequest(subsequentPaymentReference, contractReference, shopperDetails, paymentAmountModel, null);
        final SubsequentRecurringPaymentResponse subsequentPaymentResponse = adyenApiCommunicator.submitSubsequentPayment(subsequentPaymentRequest);
        assertNotNull(subsequentPaymentResponse);
        assertEquals(subsequentPaymentResponse.getPaymentResult().getResultCode(), AdyenPaymentStatus.AUTHORISED);
        /* Disable contract */
        final DisableRecurringContractRequest disableRequest = new DisableRecurringContractRequest(shopperReference, contractReference);
        final DisableRecurringContractResponse disableRecurringContractResponse = adyenApiCommunicator.disableRecurringContract(disableRequest);
        assertDisableRecurringContractResponse(disableRecurringContractResponse);
        /* Reload list of recurring details */
        listRecurringContracts = adyenApiCommunicator.listRecurringDetails(requestModel);
        assertNotNull(listRecurringContracts);
        assertTrue(listRecurringContracts.getRecurringContracts().size() == 0);
    }

    @Test
    public void testSubmitWithDynamicZeroValueAuthAndEncryptedCardDetail() {
        /* Test data */
        final String currencyCode = "EUR";
        final String uuId = UUID.randomUUID().toString();
        final String reference = "initialPayment_" + uuId;
        /* Credit card data (Master Card) */
        /* Create shopper details model */
        final String shopperReference = "mher.sargsyan_" + uuId;
        final String shopperEmail = "mher.sargsyan_" + uuId + "@sflpro.com";
        final ShopperDetailsModel shopperDetails = new ShopperDetailsModel(shopperEmail, shopperReference, "1.1.1.1");
        /* Create payment model */
        final DynamicZeroValuePaymentModel paymentModel = new DynamicZeroValuePaymentModel(currencyCode);
        final String encryptedCard = getEncryptedCreditCard();
        /* Submit request */
        final EncryptedPaymentRequest request = new EncryptedPaymentRequest(reference, shopperDetails, paymentModel, encryptedCard, true);
        EncryptedPaymentResponse response = adyenApiCommunicator.submitPaymentUsingEncryptedPaymentMethod(request);
        /* Validate response */
        assertCreateRecurringContractResponse(response);
        /* Get list of contract details */
        final ListRecurringContractDetailsRequest requestModel = new ListRecurringContractDetailsRequest(shopperReference);
        ListRecurringContractDetailsResponse listRecurringContracts = adyenApiCommunicator.listRecurringDetails(requestModel);
        assertRecurringContractsList(shopperReference, shopperEmail, listRecurringContracts);
        /* Verify credit card details */
        final RecurringContractCardDetailsModel detailsModel = listRecurringContracts.getRecurringCardDetails().get(0);
        assertNotNull(detailsModel);
        final String contractReference = detailsModel.getDetailReference();
        /* Disable contract */
        final DisableRecurringContractRequest disableRequest = new DisableRecurringContractRequest(shopperReference, contractReference);
        final DisableRecurringContractResponse disableRecurringContractResponse = adyenApiCommunicator.disableRecurringContract(disableRequest);
        assertDisableRecurringContractResponse(disableRecurringContractResponse);
        /* Reload list of recurring details */
        listRecurringContracts = adyenApiCommunicator.listRecurringDetails(requestModel);
        assertNotNull(listRecurringContracts);
        assertTrue(listRecurringContracts.getRecurringContracts().size() == 0);
        /* Create contract with the same details again */
        response = adyenApiCommunicator.submitPaymentUsingEncryptedPaymentMethod(request);
        /* Validate response */
        assertCreateRecurringContractResponse(response);
        /* Retrieve recreated contract details */
        listRecurringContracts = adyenApiCommunicator.listRecurringDetails(requestModel);
        assertRecurringContractsList(shopperReference, shopperEmail, listRecurringContracts);
        /* Verify credit card details */
        final RecurringContractCardDetailsModel recreatedContractDetailsModel = listRecurringContracts.getRecurringCardDetails().get(0);
        assertNotNull(recreatedContractDetailsModel);
        /* Assert previously created and latest created contracts are not the same */
        assertNotEquals(recreatedContractDetailsModel.getDetailReference(), detailsModel);
    }

    /* Utility methods */
    private String getEncryptedCreditCard() {
        return adyenCardEncryptedData;
    }

    private void assertRecurringContractsList(final String shopperReference, final String shopperEmail, final ListRecurringContractDetailsResponse listRecurringContracts) {
        assertNotNull(listRecurringContracts);
        assertTrue(listRecurringContracts.getRecurringContracts().size() > 0);
        assertEquals(listRecurringContracts.getShopperEmail(), shopperEmail);
        assertEquals(listRecurringContracts.getShopperReference(), shopperReference);
        assertNotNull(listRecurringContracts.getContractCreationDate());
    }

    private void assertDisableRecurringContractResponse(final DisableRecurringContractResponse disableRecurringContractResponse) {
        assertNotNull(disableRecurringContractResponse);
        assertNotNull(disableRecurringContractResponse.getDisableContractResult());
        assertEquals(disableRecurringContractResponse.getDisableContractResult().getStatus(), DISABLE_CONTRACT_SUCCESS);
    }

    private void assertCreateRecurringContractResponse(final EncryptedPaymentResponse response) {
        assertNotNull(response);
        assertNotNull(response.getPaymentResult());
        assertEquals(response.getPaymentResult().getResultCode(), AdyenPaymentStatus.AUTHORISED);
    }
}
