package com.sfl.pms.services.payment.method.impl.group;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.persistence.repositories.payment.method.group.GroupPaymentMethodDefinitionRepository;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.dto.group.GroupPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.exception.group.GroupPaymentMethodDefinitionNotFoundForLookupParameters;
import com.sfl.pms.services.payment.method.impl.AbstractPaymentMethodDefinitionServiceImpl;
import com.sfl.pms.services.payment.method.impl.AbstractPaymentMethodDefinitionServiceImplTest;
import com.sfl.pms.services.payment.method.model.PaymentMethodGroupType;
import com.sfl.pms.services.payment.method.model.group.GroupPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:10 PM
 */
public class GroupPaymentMethodDefinitionServiceImplTest extends AbstractPaymentMethodDefinitionServiceImplTest<GroupPaymentMethodDefinition> {

    /* Test subject and mocks */
    @TestSubject
    private GroupPaymentMethodDefinitionServiceImpl groupPaymentMethodDefinitionService = new GroupPaymentMethodDefinitionServiceImpl();

    @Mock
    private GroupPaymentMethodDefinitionRepository groupPaymentMethodDefinitionRepository;

    /* Constructors */
    public GroupPaymentMethodDefinitionServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentMethodDefinitionForLookupParametersWithInvalidArguments() {
        // Test data
        final PaymentMethodGroupType paymentMethodGroupType = PaymentMethodGroupType.CARD;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(null, currency, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodGroupType, null, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodGroupType, currency, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodDefinitionForLookupParametersWhenDefinitionDoesNotExist() {
        // Test data
        final PaymentMethodGroupType paymentMethodGroupType = PaymentMethodGroupType.CARD;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodGroupType), eq(currency), eq(paymentProviderType))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodGroupType, currency, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final GroupPaymentMethodDefinitionNotFoundForLookupParameters ex) {
            // Expected
            assertGroupPaymentMethodDefinitionNotFoundForLookupParameters(ex, paymentMethodGroupType, currency, paymentProviderType);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodDefinitionForLookupParametersWhenDefinitionExists() {
        // Test data
        final PaymentMethodGroupType paymentMethodType = PaymentMethodGroupType.CARD;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        final Long definitionId = 1L;
        final GroupPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createGroupPaymentMethodDefinition();
        paymentMethodDefinition.setId(definitionId);
        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodType), eq(currency), eq(paymentProviderType))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        final GroupPaymentMethodDefinition result = groupPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodType, currency, paymentProviderType);
        assertEquals(paymentMethodDefinition, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParametersWithInvalidArguments() {
        // Test data
        final PaymentMethodGroupType paymentMethodType = PaymentMethodGroupType.CARD;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(null, currency, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodType, null, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodType, currency, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParametersWhenDefinitionExists() {
        // Test data
        final PaymentMethodGroupType paymentMethodGroupType = PaymentMethodGroupType.CARD;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        final Long definitionId = 1L;
        final GroupPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createGroupPaymentMethodDefinition();
        paymentMethodDefinition.setId(definitionId);
        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodGroupType), eq(currency), eq(paymentProviderType))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodGroupType, currency, paymentProviderType);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParametersWhenDefinitionDoesNotExist() {
        // Test data
        final PaymentMethodGroupType paymentMethodGroupType = PaymentMethodGroupType.CARD;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodGroupType), eq(currency), eq(paymentProviderType))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = groupPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodGroupType, currency, paymentProviderType);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentMethodDefinitionWithInvalidArguments() {
        // Test data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesImplTestHelper().createGroupPaymentMethodDefinitionDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(new GroupPaymentMethodDefinitionDto(null, paymentMethodDefinitionDto.getAuthorizationSurcharge(), paymentMethodDefinitionDto.getPaymentSurcharge(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(new GroupPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodGroupType(), null, paymentMethodDefinitionDto.getPaymentSurcharge(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(new GroupPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getAuthorizationSurcharge(), null, paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(new GroupPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getAuthorizationSurcharge(), paymentMethodDefinitionDto.getPaymentSurcharge(), null, paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(new GroupPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodGroupType(), paymentMethodDefinitionDto.getAuthorizationSurcharge(), paymentMethodDefinitionDto.getPaymentSurcharge(), paymentMethodDefinitionDto.getCurrency(), null, true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentMethodDefinitionWhenItAlreadyExists() {
        // Test data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesImplTestHelper().createGroupPaymentMethodDefinitionDto();
        final Long existingDefinitionId = 1L;
        final GroupPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createGroupPaymentMethodDefinition();
        paymentMethodDefinition.setId(existingDefinitionId);

        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodDefinitionDto.getPaymentMethodGroupType()), eq(paymentMethodDefinitionDto.getCurrency()), eq(paymentMethodDefinitionDto.getPaymentProviderType()))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionAlreadyExistsException ex) {
            // Expected
            assertPaymentMethodDefinitionAlreadyExistsException(ex, existingDefinitionId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentMethodDefinition() {
        // Test data
        final GroupPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesImplTestHelper().createGroupPaymentMethodDefinitionDto();
        // Reset
        resetAll();
        // Expectations
        expect(groupPaymentMethodDefinitionRepository.findByPaymentMethodGroupTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodDefinitionDto.getPaymentMethodGroupType()), eq(paymentMethodDefinitionDto.getCurrency()), eq(paymentMethodDefinitionDto.getPaymentProviderType()))).andReturn(null).once();
        expect(groupPaymentMethodDefinitionRepository.save(isA(GroupPaymentMethodDefinition.class))).andAnswer(() -> (GroupPaymentMethodDefinition) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final GroupPaymentMethodDefinition result = groupPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        getServicesImplTestHelper().assertGroupPaymentMethodDefinition(result, paymentMethodDefinitionDto);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected GroupPaymentMethodDefinition getInstance() {
        return getServicesImplTestHelper().createGroupPaymentMethodDefinition();
    }

    @Override
    protected Class<GroupPaymentMethodDefinition> getInstanceClass() {
        return GroupPaymentMethodDefinition.class;
    }

    @Override
    protected AbstractPaymentMethodDefinitionServiceImpl<GroupPaymentMethodDefinition> getService() {
        return groupPaymentMethodDefinitionService;
    }

    @Override
    protected AbstractPaymentMethodDefinitionRepository<GroupPaymentMethodDefinition> getRepository() {
        return groupPaymentMethodDefinitionRepository;
    }

    private void assertGroupPaymentMethodDefinitionNotFoundForLookupParameters(final GroupPaymentMethodDefinitionNotFoundForLookupParameters ex, final PaymentMethodGroupType paymentMethodGroupType, final Currency currency, final PaymentProviderType paymentProviderType) {
        assertEquals(paymentMethodGroupType, ex.getPaymentMethodGroupType());
        assertEquals(currency, ex.getCurrency());
        assertEquals(paymentProviderType, ex.getPaymentProviderType());
    }
}
