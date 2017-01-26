package com.sfl.pms.services.payment.method;

import com.sfl.pms.services.payment.method.model.PaymentMethodDefinition;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:16 PM
 */
@Ignore
public abstract class AbstractPaymentMethodDefinitionServiceIntegrationTest<T extends PaymentMethodDefinition> extends AbstractServiceIntegrationTest {

    /* Constructors */
    public AbstractPaymentMethodDefinitionServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentMethodDefinitionById() {
        // Prepare data
        final T paymentMethodDefinition = getInstance();
        // Try to load by id
        T result = getService().getPaymentMethodDefinitionById(paymentMethodDefinition.getId());
        assertEquals(paymentMethodDefinition, result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = getService().getPaymentMethodDefinitionById(paymentMethodDefinition.getId());
        assertEquals(paymentMethodDefinition, result);
    }

    /* Abstract methods */
    protected abstract T getInstance();

    protected abstract AbstractPaymentMethodDefinitionService<T> getService();
}
