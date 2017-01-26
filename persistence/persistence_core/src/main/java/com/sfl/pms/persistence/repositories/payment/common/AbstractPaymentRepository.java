package com.sfl.pms.persistence.repositories.payment.common;

import com.sfl.pms.services.payment.common.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/11/15
 * Time: 11:43 AM
 */
public interface AbstractPaymentRepository<T extends Payment> extends JpaRepository<T, Long> {

    /**
     * Find payment by uuid
     *
     * @param uuId
     * @return payment
     */
    T findByUuId(final String uuId);
}
