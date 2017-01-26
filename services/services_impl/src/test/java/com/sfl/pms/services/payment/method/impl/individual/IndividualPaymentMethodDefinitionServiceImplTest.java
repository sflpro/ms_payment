package com.sfl.pms.services.payment.method.impl.individual;

import com.sfl.pms.persistence.repositories.payment.method.AbstractPaymentMethodDefinitionRepository;
import com.sfl.pms.persistence.repositories.payment.method.individual.IndividualPaymentMethodDefinitionRepository;
import com.sfl.pms.services.currency.model.Currency;
import com.sfl.pms.services.payment.method.dto.individual.IndividualPaymentMethodDefinitionDto;
import com.sfl.pms.services.payment.method.exception.PaymentMethodDefinitionAlreadyExistsException;
import com.sfl.pms.services.payment.method.exception.individual.IndividualPaymentMethodDefinitionNotFoundForLookupParameters;
import com.sfl.pms.services.payment.method.impl.AbstractPaymentMethodDefinitionServiceImpl;
import com.sfl.pms.services.payment.method.impl.AbstractPaymentMethodDefinitionServiceImplTest;
import com.sfl.pms.services.payment.method.model.PaymentMethodType;
import com.sfl.pms.services.payment.method.model.individual.IndividualPaymentMethodDefinition;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/25/15
 * Time: 2:10 PM
 */
public class IndividualPaymentMethodDefinitionServiceImplTest extends AbstractPaymentMethodDefinitionServiceImplTest<IndividualPaymentMethodDefinition> {

    /* Test subject and mocks */
    @TestSubject
    private IndividualPaymentMethodDefinitionServiceImpl individualPaymentMethodDefinitionService = new IndividualPaymentMethodDefinitionServiceImpl();

    @Mock
    private IndividualPaymentMethodDefinitionRepository individualPaymentMethodDefinitionRepository;

    /* Constructors */
    public IndividualPaymentMethodDefinitionServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentMethodDefinitionForLookupParametersWithInvalidArguments() {
        // Test data
        final PaymentMethodType paymentMethodType = PaymentMethodType.IDEAL;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(null, currency, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodType, null, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodType, currency, null);
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
        final PaymentMethodType paymentMethodType = PaymentMethodType.IDEAL;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodType), eq(currency), eq(paymentProviderType))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodType, currency, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IndividualPaymentMethodDefinitionNotFoundForLookupParameters ex) {
            // Expected
            assertIndividualPaymentMethodDefinitionNotFoundForLookupParameters(ex, paymentMethodType, currency, paymentProviderType);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentMethodDefinitionForLookupParametersWhenDefinitionExists() {
        // Test data
        final PaymentMethodType paymentMethodType = PaymentMethodType.IDEAL;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        final Long definitionId = 1L;
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createIndividualPaymentMethodDefinition();
        paymentMethodDefinition.setId(definitionId);
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodType), eq(currency), eq(paymentProviderType))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        final IndividualPaymentMethodDefinition result = individualPaymentMethodDefinitionService.getPaymentMethodDefinitionForLookupParameters(paymentMethodType, currency, paymentProviderType);
        assertEquals(paymentMethodDefinition, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParametersWithInvalidArguments() {
        // Test data
        final PaymentMethodType paymentMethodType = PaymentMethodType.IDEAL;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(null, currency, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodType, null, paymentProviderType);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodType, currency, null);
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
        final PaymentMethodType paymentMethodType = PaymentMethodType.IDEAL;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        final Long definitionId = 1L;
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createIndividualPaymentMethodDefinition();
        paymentMethodDefinition.setId(definitionId);
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodType), eq(currency), eq(paymentProviderType))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodType, currency, paymentProviderType);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfPaymentMethodDefinitionExistsForLookupParametersWhenDefinitionDoesNotExist() {
        // Test data
        final PaymentMethodType paymentMethodType = PaymentMethodType.IDEAL;
        final Currency currency = Currency.EUR;
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(eq(paymentMethodType), eq(currency), eq(paymentProviderType))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = individualPaymentMethodDefinitionService.checkIfPaymentMethodDefinitionExistsForLookupParameters(paymentMethodType, currency, paymentProviderType);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentMethodDefinitionWithInvalidArguments() {
        // Test data
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesImplTestHelper().createIndividualPaymentMethodDefinitionDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(new IndividualPaymentMethodDefinitionDto(null, paymentMethodDefinitionDto.getAuthorizationSurcharge(), paymentMethodDefinitionDto.getPaymentSurcharge(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(new IndividualPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodType(), null, paymentMethodDefinitionDto.getPaymentSurcharge(), paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(new IndividualPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodType(), paymentMethodDefinitionDto.getAuthorizationSurcharge(), null, paymentMethodDefinitionDto.getCurrency(), paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(new IndividualPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodType(), paymentMethodDefinitionDto.getAuthorizationSurcharge(), paymentMethodDefinitionDto.getPaymentSurcharge(), null, paymentMethodDefinitionDto.getPaymentProviderType(), true));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(new IndividualPaymentMethodDefinitionDto(paymentMethodDefinitionDto.getPaymentMethodType(), paymentMethodDefinitionDto.getAuthorizationSurcharge(), paymentMethodDefinitionDto.getPaymentSurcharge(), paymentMethodDefinitionDto.getCurrency(), null, true));
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
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesImplTestHelper().createIndividualPaymentMethodDefinitionDto();
        final Long definitionId = 1L;
        final IndividualPaymentMethodDefinition paymentMethodDefinition = getServicesImplTestHelper().createIndividualPaymentMethodDefinition();
        paymentMethodDefinition.setId(definitionId);
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(EasyMock.eq(paymentMethodDefinitionDto.getPaymentMethodType()), EasyMock.eq(paymentMethodDefinitionDto.getCurrency()), EasyMock.eq(paymentMethodDefinitionDto.getPaymentProviderType()))).andReturn(paymentMethodDefinition).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
            fail("Exception should be thrown");
        } catch (final PaymentMethodDefinitionAlreadyExistsException ex) {
            // Expected
            assertPaymentMethodDefinitionAlreadyExistsException(ex, definitionId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreatePaymentMethodDefinition() {
        // Test data
        final IndividualPaymentMethodDefinitionDto paymentMethodDefinitionDto = getServicesImplTestHelper().createIndividualPaymentMethodDefinitionDto();
        // Reset
        resetAll();
        // Expectations
        expect(individualPaymentMethodDefinitionRepository.findByPaymentMethodTypeAndCurrencyAndPaymentProviderType(EasyMock.eq(paymentMethodDefinitionDto.getPaymentMethodType()), EasyMock.eq(paymentMethodDefinitionDto.getCurrency()), EasyMock.eq(paymentMethodDefinitionDto.getPaymentProviderType()))).andReturn(null).once();
        expect(individualPaymentMethodDefinitionRepository.save(isA(IndividualPaymentMethodDefinition.class))).andAnswer(() -> (IndividualPaymentMethodDefinition) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final IndividualPaymentMethodDefinition result = individualPaymentMethodDefinitionService.createPaymentMethodDefinition(paymentMethodDefinitionDto);
        getServicesImplTestHelper().assertIndividualPaymentMethodDefinition(result, paymentMethodDefinitionDto);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected IndividualPaymentMethodDefinition getInstance() {
        return getServicesImplTestHelper().createIndividualPaymentMethodDefinition();
    }

    @Override
    protected Class<IndividualPaymentMethodDefinition> getInstanceClass() {
        return IndividualPaymentMethodDefinition.class;
    }

    @Override
    protected AbstractPaymentMethodDefinitionServiceImpl<IndividualPaymentMethodDefinition> getService() {
        return individualPaymentMethodDefinitionService;
    }

    @Override
    protected AbstractPaymentMethodDefinitionRepository<IndividualPaymentMethodDefinition> getRepository() {
        return individualPaymentMethodDefinitionRepository;
    }

    private void assertIndividualPaymentMethodDefinitionNotFoundForLookupParameters(final IndividualPaymentMethodDefinitionNotFoundForLookupParameters ex, final PaymentMethodType paymentMethodType, final Currency currency, final PaymentProviderType paymentProviderType) {
        Assert.assertEquals(paymentMethodType, ex.getPaymentMethodType());
        Assert.assertEquals(currency, ex.getCurrency());
        Assert.assertEquals(paymentProviderType, ex.getPaymentProviderType());
    }
}
