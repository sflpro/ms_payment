package com.sfl.pms.persistence.repositories.payment.redirect;

import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.springframework.stereotype.Repository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 12:41 PM
 */
@Repository
public interface PaymentProviderRedirectResultRepository extends AbstractPaymentProviderRedirectResultRepository<PaymentProviderRedirectResult>, PaymentProviderRedirectResultRepositoryCustom {

}
