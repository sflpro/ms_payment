package com.sfl.pms.persistence.repositories.payment.redirect;

import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:41 PM
 */
public interface AbstractPaymentProviderRedirectResultRepository<T extends PaymentProviderRedirectResult> extends JpaRepository<T, Long> {


    /**
     * Gets payment provider redurect result for uuid
     *
     * @param uuId
     * @return paymentProviderRedirectResult
     */
    T findByUuId(@Nonnull final String uuId);
}
