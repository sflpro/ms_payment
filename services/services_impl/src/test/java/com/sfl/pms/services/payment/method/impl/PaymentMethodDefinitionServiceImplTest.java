package com.sfl.pms.services.payment.method.impl;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.persistence.repositories.payment.method.PaymentMethodDefinitionRepository;
import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import org.easymock.Mock;
import org.easymock.TestSubject;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:10 PM
 */
public class PaymentMethodDefinitionServiceImplTest extends AbstractPaymentMethodDefinitionServiceImplTest<PaymentMethodDefinition> {

    /* Test subject and mocks */
    @TestSubject
    private PaymentMethodDefinitionServiceImpl paymentMethodDefinitionService = new PaymentMethodDefinitionServiceImpl();

    @Mock
    private PaymentMethodDefinitionRepository paymentMethodDefinitionRepository;

    /* Constructors */
    public PaymentMethodDefinitionServiceImplTest() {
    }

    /* Utility methods */
    @Override
    protected PaymentMethodDefinition getInstance() {
        return getServicesImplTestHelper().createIndividualPaymentMethodDefinition();
    }

    @Override
    protected Class<PaymentMethodDefinition> getInstanceClass() {
        return PaymentMethodDefinition.class;
    }

    @Override
    protected AbstractPaymentMethodDefinitionServiceImpl<PaymentMethodDefinition> getService() {
        return paymentMethodDefinitionService;
    }

    @Override
    protected AbstractPaymentMethodDefinitionRepository<PaymentMethodDefinition> getRepository() {
        return paymentMethodDefinitionRepository;
    }
}
