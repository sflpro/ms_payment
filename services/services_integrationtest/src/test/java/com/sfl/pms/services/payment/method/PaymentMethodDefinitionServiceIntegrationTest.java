package com.sfl.pms.services.payment.method;

import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:19 PM
 */
public class PaymentMethodDefinitionServiceIntegrationTest extends AbstractPaymentMethodDefinitionServiceIntegrationTest<PaymentMethodDefinition> {

    /* Dependencies */
    @Autowired
    private PaymentMethodDefinitionService paymentMethodDefinitionService;

    /* Constructors */
    public PaymentMethodDefinitionServiceIntegrationTest() {
    }

    /* Utility methods */
    @Override
    protected PaymentMethodDefinition getInstance() {
        return getServicesTestHelper().createGroupPaymentMethodDefinition();
    }

    @Override
    protected AbstractPaymentMethodDefinitionService<PaymentMethodDefinition> getService() {
        return paymentMethodDefinitionService;
    }

}
