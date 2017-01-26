package com.sfl.pms.services.payment.settings.impl;

import com.sfl.pms.persistence.repositories.payment.settings.AbstractPaymentProviderSettingsRepository;
import com.sfl.pms.persistence.repositories.payment.settings.PaymentProviderSettingsRepository;
import com.sfl.pms.services.payment.provider.exception.UnknownPaymentProviderTypeException;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.AbstractPaymentProviderSettingsService;
import com.sfl.pms.services.payment.settings.adyen.AdyenPaymentSettingsService;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:11 PM
 */
public class PaymentProviderSettingsServiceImplTest extends AbstractPaymentProviderSettingsServiceImplTest<PaymentProviderSettings> {

    /* Test subject and mocks */
    @TestSubject
    private PaymentProviderSettingsServiceImpl paymentProviderSettingsService = new PaymentProviderSettingsServiceImpl();

    @Mock
    private AdyenPaymentSettingsService adyenPaymentSettingsService;

    @Mock
    private PaymentProviderSettingsRepository paymentProviderSettingsRepository;

    /* Constructors */
    public PaymentProviderSettingsServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetActivePaymentProviderSettingsForTypeWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderSettingsService.getActivePaymentProviderSettingsForType(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetActivePaymentProviderSettingsForTypeWithInvalidPaymentProviderType() {
        // Test data
        final PaymentProviderType paymentProviderType = PaymentProviderType.BRAINTREE;
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            paymentProviderSettingsService.getActivePaymentProviderSettingsForType(paymentProviderType);
            fail("Exception should be thrown");
        } catch (final UnknownPaymentProviderTypeException ex) {
            // Expected
            assertUnknownPaymentProviderTypeException(ex, paymentProviderType);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetActivePaymentProviderSettingsForTypeWithAdyen() {
        // Test data
        final Long settingsId = 1L;
        final AdyenPaymentSettings adyenPaymentSettings = getServicesImplTestHelper().createAdyenPaymentSettings();
        adyenPaymentSettings.setId(settingsId);
        final PaymentProviderType paymentProviderType = PaymentProviderType.ADYEN;
        // Reset
        resetAll();
        // Expectations
        expect(adyenPaymentSettingsService.getActivePaymentSettings()).andReturn(adyenPaymentSettings).once();
        // Replay
        replayAll();
        // Run test scenario
        final PaymentProviderSettings result = paymentProviderSettingsService.getActivePaymentProviderSettingsForType(paymentProviderType);
        assertEquals(adyenPaymentSettings, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderSettingsService<PaymentProviderSettings> getService() {
        return paymentProviderSettingsService;
    }

    @Override
    protected AbstractPaymentProviderSettingsRepository<PaymentProviderSettings> getRepository() {
        return paymentProviderSettingsRepository;
    }

    @Override
    protected Class<PaymentProviderSettings> getInstanceClass() {
        return PaymentProviderSettings.class;
    }

    @Override
    protected PaymentProviderSettings getInstance() {
        return getServicesImplTestHelper().createAdyenPaymentSettings();
    }

    private void assertUnknownPaymentProviderTypeException(final UnknownPaymentProviderTypeException ex, final PaymentProviderType paymentProviderType) {
        Assert.assertEquals(paymentProviderType, ex.getPaymentProviderType());
    }
}
