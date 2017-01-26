package com.sfl.pms.services.payment.method.impl;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.persistence.repositories.payment.method.PaymentMethodDefinitionRepository;
import com.sfl.pms.services.payment.method.PaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 1:57 PM
 */
@Service
public class PaymentMethodDefinitionServiceImpl extends AbstractPaymentMethodDefinitionServiceImpl<PaymentMethodDefinition> implements PaymentMethodDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodDefinitionServiceImpl.class);

    /* Dependencies */
    @Autowired
    private PaymentMethodDefinitionRepository paymentMethodDefinitionRepository;

    /* Constructors */
    public PaymentMethodDefinitionServiceImpl() {
        LOGGER.debug("Initializing payment method definition service");
    }

    /* Utility methods */
    @Override
    protected Class<PaymentMethodDefinition> getInstanceClass() {
        return PaymentMethodDefinition.class;
    }

    @Override
    protected AbstractPaymentMethodDefinitionRepository<PaymentMethodDefinition> getRepository() {
        return paymentMethodDefinitionRepository;
    }

    /* Properties getters and setters */
    public PaymentMethodDefinitionRepository getPaymentMethodDefinitionRepository() {
        return paymentMethodDefinitionRepository;
    }

    public void setPaymentMethodDefinitionRepository(final PaymentMethodDefinitionRepository paymentMethodDefinitionRepository) {
        this.paymentMethodDefinitionRepository = paymentMethodDefinitionRepository;
    }
}
