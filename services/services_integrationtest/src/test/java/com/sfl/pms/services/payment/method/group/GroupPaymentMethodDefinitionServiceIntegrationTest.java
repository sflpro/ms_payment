package com.sfl.pms.services.payment.method.group;

import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionService;
import com.sfl.pms.services.payment.method.AbstractPaymentMethodDefinitionServiceIntegrationTest;
import com.sfl.pms.services.payment.method.dto.group.GroupPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:19 PM
 */
public class GroupPaymentMethodDefinitionServiceIntegrationTest extends AbstractPaymentMethodDefinitionServiceIntegrationTest<GroupPaymentMethodDefinition> {

    /* Dependencies */
    @Autowired
    private GroupPaymentMethodDefinitionService groupPaymentMethodDefinitionService;

    /* Constructors */
    public GroupPaymentMethodDefinitionServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentMethodDefinitionForLookupParameters() {
        // Prepare data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createGroupPaymentMethodDefinitionDto();
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        // Try to load payment method for look up parameters
        GroupPaymentMethodDefinition result = groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodDefinition.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertEquals(paymentMethodDefinition, result);
        // Flush, clear and assert again
        flushAndClear();
        result = groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodDefinition.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertEquals(paymentMethodDefinition, result);
    }


    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParameters() {
        // Prepare data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createGroupPaymentMethodDefinitionDto();
        // Check if payment method definition exists
        boolean result = groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertFalse(result);
        // Create payment method definition
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        result = groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertTrue(result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType());
        assertTrue(result);
    }

    @Test
    public void testCreatePaymentMethodDefinition() {
        // Prepare data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createGroupPaymentMethodDefinitionDto();
        // Create payment method definition
        GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        getServicesTestHelper().assertGroupPaymentMethodDefinition(paymentMethodDefinition, paymentMethodDefinitionDto);
        // Flush, clear, reload and assert
        flushAndClear();
        paymentMethodDefinition = groupPaymentMethodDefinitionService.getPaymentMethodDefinitionById(paymentMethodDefinition.getId());
        getServicesTestHelper().assertGroupPaymentMethodDefinition(paymentMethodDefinition, paymentMethodDefinitionDto);
    }

    @Test
    public void testCreatePaymentMethodDefinitionWhenItAlreadyExists() {
        // Prepare data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesTestHelper().createGroupPaymentMethodDefinitionDto();
        // Create payment method definition
        final GroupPaymentMethodDefinition paymentMethodDefinition = groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        assertNotNull(paymentMethodDefinition);
        // Try to create again
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionAlreadyExistsException ex) {
            // Expected
        }
        // Flush, clear and try again
        flushAndClear();
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionAlreadyExistsException ex) {
            // Expected
        }
    }

    /* Utility methods */
    @Override
    protected GroupPaymentMethodDefinition getInstance() {
        return getServicesTestHelper().createGroupPaymentMethodDefinition();
    }

    @Override
    protected AbstractPaymentMethodDefinitionService<GroupPaymentMethodDefinition> getService() {
        return groupPaymentMethodDefinitionService;
    }

}
