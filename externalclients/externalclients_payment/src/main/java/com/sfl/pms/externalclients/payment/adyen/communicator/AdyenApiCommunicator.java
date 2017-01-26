package com.sfl.pms.externalclients.payment.adyen.communicator;

import com.sfl.pms.externalclients.payment.adyen.model.request.DisableRecurringContractRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.EncryptedPaymentRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.ListRecurringContractDetailsRequest;
import com.sfl.pms.externalclients.payment.adyen.model.request.SubsequentRecurringPaymentRequest;
import com.sfl.pms.externalclients.payment.adyen.model.response.DisableRecurringContractResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.EncryptedPaymentResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.ListRecurringContractDetailsResponse;
import com.sfl.pms.externalclients.payment.adyen.model.response.SubsequentRecurringPaymentResponse;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/25/14
 * Time: 6:50 PM
 */
public interface AdyenApiCommunicator {

    /**
     * Get list of shopper recurring details
     *
     * @param requestModel
     */
    ListRecurringContractDetailsResponse listRecurringDetails(final ListRecurringContractDetailsRequest requestModel);

    /**
     * Create recurring contract by submitting payment with zero value amount
     *
     * @param requestModel
     * @return
     */
    EncryptedPaymentResponse submitPaymentUsingEncryptedPaymentMethod(final EncryptedPaymentRequest requestModel);

    /**
     * Disable recurring contract
     *
     * @param requestModel
     * @return
     */
    DisableRecurringContractResponse disableRecurringContract(final DisableRecurringContractRequest requestModel);


    /**
     * submit subsequent payment
     *
     * @param requestModel
     * @return
     */
    SubsequentRecurringPaymentResponse submitSubsequentPayment(final SubsequentRecurringPaymentRequest requestModel);
}
