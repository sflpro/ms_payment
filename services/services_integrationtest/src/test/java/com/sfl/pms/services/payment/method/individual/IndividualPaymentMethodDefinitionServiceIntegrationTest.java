package com.sfl.pms.services.payment.method.individual;

import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionServiceIntegrationTest;
import com.sfl.pms.services.payment.method.dto.individual.IndividualPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:19 PM
 */
public class IndividualPaymentMethodDefinitionServiceIntegrationTest extends AbstractPaymentMethodDefinitionServiceIntegrationTest<IndividualPaymentMethodDefinition> {

    /* Dependencies */
    @Autowired
    private IndividualPaymentMethodDefinitionService individualPaymentMethodDefinitionService;

    /* Constructors */
    public IndividualPaymentMethodDefinitionServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentMethodDefinitionForLookupParameters() {
        // Prepare data
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        final IndividualPaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        // Try to load payment method for look up parameters
        IndividualPaymentMethodDefinition result = individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodDefinition.getPaymentMethodType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertEquals(paymentMethodDefinition, result);
        // Flush, clear and assert again
        flushAndClear();
        result = individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodDefinition.getPaymentMethodType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertEquals(paymentMethodDefinition, result);
    }


    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParameters() {
        // Prepare data
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        // Check if payment method definition exists
        boolean result = individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodDefinitionDto.getPaymentMethodType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertFalse(result);
        // Create payment method definition
        final IndividualPaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        result = individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodDefinitionDto.getPaymentMethodType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertTrue(result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodDefinitionDto.getPaymentMethodType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertTrue(result);
    }

    @Test
    public void testCreatePaymentMethodDefinition() {
        // Prepare data
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        // Create payment method definition
        IndividualPaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        getServicesTestHelper().assertIndividualPaymentMethodDefinition(paymentMethodDefinition, paymentMethodDefinitionDto);
        // Flush, clear, reload and assert
        flushAndClear();
        paymentMethodDefinition = individualPaymentMethodDefinitionService.getPaymentMethodDefinitionById(paymentMethodDefinition.getId());
        getServicesTestHelper().assertIndividualPaymentMethodDefinition(paymentMethodDefinition, paymentMethodDefinitionDto);
    }

    @Test
    public void testCreatePaymentMethodDefinitionWhenItAlreadyExists() {
        // Prepare data
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createIndividualPaymentMethodDefinitionDto();
        // Create payment method definition
        final IndividualPaymentMethodDefinition paymentMethodDefinition = individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        // Try to create again
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionAlreadyExistsException ex) {
            // Expected
        }
        // Flush, clear and try again
        flushAndClear();
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionAlreadyExistsException ex) {
            // Expected
        }
    }

    /* Utility methods */
    @Override
    protected IndividualPaymentMethodDefinition getInstance() {
        return getServicesTestHelper().createIndividualPaymentMethodDefinition();
    }

    @Override
    protected AbstractPaymentMethodDefinitionService<IndividualPaymentMethodDefinition> getService() {
        return individualPaymentMethodDefinitionService;
    }

}
