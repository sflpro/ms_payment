package com.sfl.pms.persistence.repositories.payment.redirect.adyen;

import com.sfl.pms.persistence.repositories.payment.redirect.AbstractPaymentProviderRedirectResultRepository;
import com.sfl.pms.services.payment.redirect.model.adyen.AdyenRedirectResult;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:41 PM
 */
@Repository
public interface AdyenRedirectResultRepository extends AbstractPaymentProviderRedirectResultRepository<AdyenRedirectResult> {
}
