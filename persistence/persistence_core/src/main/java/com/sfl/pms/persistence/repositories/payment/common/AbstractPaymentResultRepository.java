package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.services.payment.common.model.Payment;
import com.sfl.pms.services.payment.common.model.PaymentResult;
import com.sfl.pms.services.payment.notification.model.PaymentProviderNotification;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/14/15
 * Time: 10:19 PM
 */
public interface AbstractPaymentResultRepository<T extends PaymentResult> extends JpaRepository<T, Long> {

    /**
     * Finds payment results by payment
     *
     * @param payment
     * @return paymentResults
     */
    List<T> findByPayment(@Nonnull final Payment payment);

    /**
     * Finds payment result by notification
     *
     * @param notification
     * @return paymentResult
     */
    T findByNotification(@Nonnull final PaymentProviderNotification notification);

    /**
     * Finds payment result by redirect
     *
     * @param redirectResult
     * @return paymentResult
     */
    T findByRedirectResult(@Nonnull final PaymentProviderRedirectResult redirectResult);
}
