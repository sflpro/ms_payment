package com.sfl.pms.queue.consumer.mail;

import javax.annotation.Nonnull;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 2/11/15
 * Time: 5:20 PM
 */
public interface MailQueueConsumerService {

    /**
     * Send mail to car wash employee with new password
     *
     * @param carWashEmployeeId
     * @param carWashEmployeePassword
     */
    void processMailToCarWashEmployeeWithUpdatedPassword(@Nonnull final Long carWashEmployeeId, @Nonnull final String carWashEmployeePassword);

    /**
     * Send mail to external integration user with new password
     *
     * @param externalIntegrationUserId
     * @param externalIntegrationUserPassword
     */
    void processMailToExternalIntegrationUserWithUpdatedPassword(@Nonnull final Long externalIntegrationUserId, @Nonnull final String externalIntegrationUserPassword);

    /**
     * Send email to back office manager with external integration user info
     *
     * @param externalIntegrationUserId
     */
    void processMailToBackOfficeManagerWithExternalIntegrationUserInfo(@Nonnull final Long externalIntegrationUserId);

    /**
     * Send mail to car wash employee with account details
     *
     * @param carWashEmployeeId
     * @param carWashEmployeePassword
     */
    void processMailToCarWashEmployeeWithAccountDetails(@Nonnull final Long carWashEmployeeId, @Nonnull final String carWashEmployeePassword);

    /**
     * Send mail to customer with rewarded voucher info
     *
     * @param rewardedVouchersGenerationRequestId
     */
    void processMailToCustomerWithRewardedVoucher(@Nonnull final Long rewardedVouchersGenerationRequestId);

    /**
     * Send email to business manager with rewarded voucher info
     *
     * @param rewardedVouchersGenerationRequestId
     */
    void processMailToBusinessManagerWithRewardedVoucher(@Nonnull final Long rewardedVouchersGenerationRequestId);

    /**
     * Send mail to customer with car wash appointment order information
     *
     * @param carWashAppointmentId
     */
    void processMailToCustomerWithCarWashAppointmentOrder(@Nonnull final Long carWashAppointmentId);

    /**
     * Send mail to customer with cancelled car wash appointment
     *
     * @param carWashAppointmentId
     */
    void processMailToCustomerWithCarWashAppointmentCancellation(@Nonnull final Long carWashAppointmentId);

    /**
     * Send mail to customer with fulfilled car wash appointment
     *
     * @param carWashAppointmentId
     */
    void processMailToCustomerWithCarWashAppointmentFulfillment(@Nonnull final Long carWashAppointmentId);

    /**
     * Send mail to car wash manager with car wash company status update
     *
     * @param carWashCompanyId
     */
    void processMailToCarWashManagerWithCompanyStatusUpdate(@Nonnull final Long carWashCompanyId);

    /**
     * Send mail to car wash manager with car wash company status update
     *
     * @param supportTicketId
     */
    void processMailToSupportWithOpenedSupportTicket(@Nonnull final Long supportTicketId);

    /**
     * Send mail to car wash manager with car wash station approval status changed
     *
     * @param carWashStationId
     */
    void processMailToCarWashManagerWithStationApprovalStatusChange(@Nonnull final Long carWashStationId);

    /**
     * Send mail to user with identity verification url
     *
     * @param verificationTokenId
     */
    void processMailToUserWithIdentityVerificationUrl(@Nonnull final Long verificationTokenId);

    /**
     * Send mail to car wash manager with car wash company financial report
     *
     * @param reportId
     */
    void processMailToCarWashManagerWithCarWashCompanyFinancialReport(@Nonnull final Long reportId);

    /**
     * Send email to backoffice manager with car wash manager info
     *
     * @param carWashManagerId
     */
    void processMailToBackofficeManagerWithCarWashManagerInfo(@Nonnull final Long carWashManagerId);

    /**
     * Send email to business company manager with updated password
     *
     * @param businessManagerId
     * @param businessManagerPassword
     */
    void processMailToBusinessCompanyManagerWithUpdatedPassword(@Nonnull final Long businessManagerId, @Nonnull final String businessManagerPassword);

    /**
     * Send mail to backoffice manager with created business manager info
     *
     * @param businessManagerId
     */
    void processMailToBackOfficeManagerWithNewlyCreatedBusinessManagerInfo(@Nonnull final Long businessManagerId);

    /**
     * Send email to business manager with business company activation info
     *
     * @param businessCompanyId
     */
    void processMailToBusinessCompanyManagerWithBusinessCompanyActivated(@Nonnull final Long businessCompanyId);

    /**
     * Send email to customer with deferred payment order info
     *
     * @param orderId
     */
    void processEmailToCustomerWithReminderAboutDeferredPaymentOrder(@Nonnull final Long orderId);

    /**
     * Process email message to customer with order payment reminder event
     *
     * @param carWashAppointmentUuid
     */
    void processEmailToCustomerWithOrderPaymentReminder(@Nonnull final String carWashAppointmentUuid);

    /**
     * Process mail to customer with car wash retention reminder info
     *
     * @param carWashRetentionReminderId
     */
    void processEmailToCustomerWithCarWashRetentionReminderInfo(@Nonnull final Long carWashRetentionReminderId);
}
